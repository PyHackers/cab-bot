from flask import Flask, request
from datetime import datetime
import math, json, os
app = Flask(__name__, static_folder='client', static_url_path='')

@app.route('/')
def index():
    return app.send_static_file('index.html')

''' Cab Service Login '''
@app.route('/login', methods=["POST"])
def login():
	response = {}
	
	email = request.args.get('email')
	password = request.args.get('password')
	isAuth = authenticate(email, password)

	if isAuth:
		response["success"] = True
		response["user_id"] = isAuth
	else:
		response["success"] = False

	return json.dumps(response)

def authenticate(email, password):
	user_id = "test"

	if email is not None and password is not None and email == "admin" and password == "admin":
		return user_id
	return

if __name__ == '__main__':
	port = int(os.environ.get("PORT", 5000))
	app.debug = True
	app.run(host='0.0.0.0', port = port)

