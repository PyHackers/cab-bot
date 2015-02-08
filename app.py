from flask import Flask, request, jsonify
from datetime import datetime
from server import parser
from time import mktime
import olatrip
import math, json, os
import firebase as firebase
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

@app.route('/offlineSMS')
def offlineSMS():
	sms = request.args.get('sms')
	firebase.push_to_firebase('sms', None, '{"msg":"'+sms+'"}')
	return "smsPosted"

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

	time.sleep( 2 )

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
	
	mytype = parse_details.get("type").lower()
	
	#get geocordinates
	geocode = postParse(parse_details)
	
	#get departure time
	mydate = parse_details.get("date")
	mytime = parse_details.get("time")
	mydatetime = datetime.combine(mydate, mytime)

	destination_tsp = mydatetime.strftime('%s')
	new_destination_tsp = int(destination_tsp) + 34200#GMT + 5:30
	# new_destination_tsp = int(destination_tsp) - 34200#GMT + 5:30
	# new_destination_tsp = destination_tsp
	# print "mydatetime"
	# print mydatetime
	# print new_destination_tsp
	# print datetime.utcfromtimestamp(float(new_destination_tsp))

	a = mylat + "," + mylong #Chesterfields
	b = str(geocode.get("lat")) + "," + str(geocode.get("long"))

	total_time = olatrip.departure_time(a, b, destination_tsp, slack, mytype) 
	total_time = int(total_time) + 19800 - 34200#GMT + 5:30
	effective_time = datetime.utcfromtimestamp(float(total_time))
	print "effective_time"
	print effective_time

	details["time"] = effective_time.time().strftime("%H:%M")
	details["date"] = effective_time.date().strftime("%Y-%m-%d")
	details["booked_time"] = effective_time.strftime('%s')
	details["pickup_point"] = str(olatrip.get_reverse_geocode(mylat, mylong))
	details["second_title"] = "book a return cab ?" if mytype == "movie" else ("book a cab from " + parse_details.get('to') + " Airport?" )
	details["lat"] = str(mylat)
	details["long"] = str(mylong)
	details["title"] = ("Ah! Want to Book a cab for " + str(parse_details.get("name")) + " movie?") if mytype == "movie" else ("Ah! Want to Book a cab for " + str(parse_details.get("from")) + " Airport?" )
	# details
	details["type"] = mytype
	details["event_time"] = mytime.strftime("%H:%M")

	#RETURN CAB
	duration = getDuration(parse_details)
	# cab_2 = effective_time.timedelta(0, duration) #days, seconds
	print destination_tsp
	print duration
	cab_2_tsp = int(destination_tsp) + duration + 19800 - 34200#GMT + 5:30
	cab_2 = datetime.utcfromtimestamp(float(cab_2_tsp))
	print "cab_2"
	print cab_2
	
	details2 = {}

	details2["time"] = cab_2.time().strftime("%H:%M")
	details2["date"] = cab_2.date().strftime("%Y-%m-%d")
	details2["booked_time"] = cab_2.strftime('%s')
	details2["pickup_point"] = parse_details.get('location') if mytype == 'movie' else (parse_details.get('to') + ' Airport')
	cab_2_geo = olatrip.get_geocode(details2["pickup_point"])
	details2["lat"] = str(cab_2_geo.get('lat'))
	details2["long"] = str(cab_2_geo.get('long'))
	details2["title"] = ("Book a cab for " + parse_details.get("name") + " movie") if mytype == "movie" else ("Book a cab for " + parse_details.get("from") + " Airport" )
	details2["type"] = mytype
	details2["second_title"] = "book a return cab ?" if mytype == "movie" else ("book a cab from " + parse_details.get('to') + " Airport?" )
	details2["event_time"] = cab_2.time().strftime("%H:%M")

	app_second = details["second_title"]

	response.append(details)
	response.append(details2)
	print response
	return jsonify(results={"success": True, "msg": response, "second_title": str(app_second)})

def getDuration(details):
	mytype = details.get('type')
	duration = 0
	if mytype == 'movie':
		duration = olatrip.getMovieDuration(details.get('name'))
	else:
		duration = olatrip.getFlightDuration(details.get('fid'), details.get('from'), details.get('to'))
	duration = duration * 60 #seconds
	return duration

def postParse(details):
	mytype = details.get("type")
	#get address
	address = ""
	if mytype == 'flight':
		address = details.get("from") + "+Airport" #Append with + to avoid bad request
	elif mytype == 'movie':
		address = details.get("location")
	#get cordinates
	geo = olatrip.get_geocode(address)

	return geo

if __name__ == '__main__':
	port = int(os.environ.get("PORT", 5000))
	app.debug = True
	app.run(host='0.0.0.0', port = port)
