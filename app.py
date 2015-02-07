from flask import Flask, request, jsonify
from datetime import datetime
import math, json, os
import firebase as firebase
app = Flask(__name__, static_folder='client', static_url_path='')

@app.route('/')
def index():
    return app.send_static_file('index.html')

''' Cab Service Login '''
@app.route('/login')
def login():
	usr = request.args.get('usr')
	pwd = request.args.get('pwd')
	if not usr:
		return jsonify(results={"success":False,"msg":"please send usr"})
	if not pwd:
		return jsonify(results={"success":False,"msg":"please send pwd"})
	user = firebase.pull_from_firebase('users', usr)
	print user
	if not user:
		return jsonify(results={"success":False,"msg":"usr does not exist!!"})
	else:
		password = firebase.pull_from_firebase('users/'+usr+'/', 'pwd')
	if password == pwd:
		return jsonify(results={"success":True,"msg":"Succesfully connected with OLA Account!"})
	else:
		return jsonify(results={"success":False,"msg":"Incorrect pwd!!"})

@app.route('/signup')
def signup():
	usr = request.args.get('usr')
	pwd = request.args.get('pwd')
	if not usr:
		return jsonify(results={"success":False,"msg":"please send usr"})
	if not pwd:
		return jsonify(results={"success":False,"msg":"please send pwd"})
	firebase.push_to_firebase('users', usr, '{"pwd":"'+pwd+'"}')
	return jsonify(results={"success":True,"msg":"Succesfully signed up!"})

if __name__ == '__main__':
	port = int(os.environ.get("PORT", 5000))
	app.debug = True
	app.run(host='0.0.0.0', port = port)

