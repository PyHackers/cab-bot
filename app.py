from flask import Flask, request, jsonify
from datetime import datetime
from server import parser
from time import mktime
import olatrip
import math, json, os
import firebase as firebase
import unicodedata
app = Flask(__name__, static_folder='client', static_url_path='')

@app.route('/')
def index():
    return app.send_static_file('index.html')

''' Cab Service Login '''
@app.route('/login')
def login():
	usr = request.args.get('username')
	pwd = request.args.get('password')
	if not usr:
		return jsonify(results={"success":False,"msg":"please send username"})
	if not pwd:
		return jsonify(results={"success":False,"msg":"please send password"})
	user = firebase.pull_from_firebase('users', usr)
	print user
	if not user:
		return jsonify(results={"success":False,"msg":"usr does not exist!!"})
	else:
		password = firebase.pull_from_firebase('users/'+usr+'/', 'pwd')
	if password == pwd:
		return jsonify(results={"success":True, "user_id": usr, "username": usr,"msg":"Succesfully connected with OLA Account!"})
	else:
		return jsonify(results={"success":False,"msg":"Incorrect pwd!!"})

@app.route('/signup')
def signup():
	usr = request.args.get('username')
	pwd = request.args.get('password')
	if not usr:
		return jsonify(results={"success":False,"msg":"please send username"})
	if not pwd:
		return jsonify(results={"success":False,"msg":"please send password"})
	firebase.push_to_firebase('users', usr, '{"pwd":"'+pwd+'"}')
	return jsonify(results={"success":True,"msg":"Succesfully signed up!"})

@app.route('/bookcab')
def bookcab():
	user_id = request.args.get('user_id')
	cabtype = request.args.get('type')
	jsonString = request.args.get('JSON')
	jsonObject = json.loads(jsonString)

	if not user_id:
		return jsonify(results={"success":False,"msg":"please send user_id"})
	if not cabtype:
		return jsonify(results={"success":False,"msg":"please send type"})
	if not jsonString:
		return jsonify(results={"success":False,"msg":"please send JSON"})

	msg = "Cabs successfully booked for " + ("the movie" if int(cabtype) == 1 else "the flight") + "!!"    

	return jsonify(results={"success":True,"msg":msg})

@app.route('/parse')
def parse():
	msg = request.args.get('msg')
	user_id = request.args.get('user_id')
	receiver_id = request.args.get('receiver_id')
	slack = request.args.get('slack')
	mylat = request.args.get('lat')
	mylong = request.args.get('long')
	# mytype = request.args.get('type')

	if not msg:
		return jsonify(results={"success": False,"msg": "please send msg"})
	# if not user_id:
	# 	return jsonify(results={"success": False,"msg": "please send user_id"})
	# if not receiver_id:
	# 	return jsonify(results={"success": False,"msg": "please send receiver_id"})
	if not slack:
		return jsonify(results={"success": False,"msg": "please send slack time"})
	if not mylat:
		return jsonify(results={"success": False,"msg": "please send latitude"})
	if not mylong:
		return jsonify(results={"success": False,"msg": "please send longitude"})


	# if not mytype:
		# return jsonify(results={"success": False,"msg": "please send type: movie or flight"})
	#msg = "Hi Customer, Booking ID: AGSN0004863077. Seats: DIAMOND-B10,B11,B12,B13 for Interstellar on Sat, 22 Nov, 2014 10:30pm at AGS Cinemas OMR: Navlur (SCREEN 4). Please carry your CC/DC card which was used for booking tickets"
	# details = parser.parse(msg, mytype)
	response = []
	details = {}
	parse_details = parser.parse(msg)
	
	#get geocordinates
	geocode = postParse(parse_details)
	
	#get departure time
	mydate = parse_details.get("date")
	mytime = parse_details.get("time")
	mydatetime = datetime.combine(mydate, mytime)

	destination_tsp = mydatetime.strftime('%s')
	print destination_tsp
	a = mylat + "," + mylong
	b = str(geocode.get("lat")) + "," + str(geocode.get("long"))

	total_time = olatrip.departure_time(a, b, destination_tsp, slack)
	total_time = total_time

	effective_time = datetime.utcfromtimestamp(float(total_time))

	mytype = parse_details.get("type").lower()

	details["time"] = effective_time.time().strftime("%H:%M")
	details["date"] = effective_time.date().strftime("%Y-%m-%d")
	details["pickup_point"] = str(olatrip.get_reverse_geocode(mylat, mylong))
	details["lat"] = str(mylat)
	details["long"] = str(mylong)
	details["title"] = (parse_details.get("name") if mytype == "movie" else parse_details.get("from") )
	details["type"] = mytype
	details["event_time"] = mytime.strftime("%H:%M")

	#RETURN CAB
	details2 = {}

	# details2["time"] = 

	response.append(details)
	response.append(details2)
	print response
	return jsonify(results={"success": True,"msg": response})

def postParse(details):
	mytype = details.get("type")
	#get address
	address = ""
	if mytype == 'flight':
		address = details.get("to") + "+Airport" #Append with + to avoid bad request
	elif mytype == 'movie':
		address = details.get("location")
	#get cordinates
	geo = olatrip.get_geocode(address)

	return geo

if __name__ == '__main__':
	port = int(os.environ.get("PORT", 5000))
	app.debug = True
	app.run(host='0.0.0.0', port = port)
