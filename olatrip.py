from flask import Flask
from flask import request
import requests
import urllib2
app = Flask(__name__)

@app.route('/')
def welcome():
    return 'Welcome to OLA-TRIP !!!'

@app.route('/flights')
def get_flights():
	src = request.args.get('src')
	dest = request.args.get('dest')
	if not src:
		return 'please send src'
	if not dest:
		return 'please send dest'
	return get_flights(src , dest, '2015-02-15', '2015-02-20')

@app.route('/movie')
def get_movie_details():
	movie = request.args.get('name')
	if not movie:
		return 'please send name'
	movie_details = get_movie_details_from_imdb(movie)
	push_movie_to_firebase(movie, movie_details)
	return 'posted to firebase'

@app.route('/chat')
def chat():
	msg = request.args.get('msg')
	author = request.args.get('author')
	if not msg:
		return 'please send msg'
	if not author:
		author = 'Rajdeep'
	push_chat_msg(author, msg)
	return 'posted to firebase'

# @app.route('/flights', methods=['POST', 'GET'])
# def login():
# 	if request:
# 		if request.method == 'GET':
# 			if not request.form['src']:
# 				return 'please enter src'
# 			if not request.form['dest']:
# 				return 'please enter dest'
# 			get_flights(request.form['src'],request.form['dest'], '2015-02-15', '2015-02-20')
# 		else:
# 			return 'Please send GET request'
# 	else:
# 		return 'Please send the request parameters (src, dest, ddate, rdate)' 

def get_flights(src, dest, ddate, rdate):
	#http://api.cleartrip.com/air/1.0/search?from=BOM&to=DEL&depart-date=2015-02-15&return-date=2015-02-20&adults=1&country=IN&currency=INR

	url = 'http://www.google.co.in'
	return requests.get(url).content



def get_movie_details_from_imdb(movie):
	url = 'http://www.omdbapi.com?t=' + movie
	return requests.get(url).content

def push_movie_to_firebase(movie, json):
	#curl -X PUT -d '{ "alanisawesome": { "name": "Alan Turing", "birthday": "June 23, 1912" } }' https://docs-examples.firebaseio.com/rest/quickstart/users.json
	opener = urllib2.build_opener(urllib2.HTTPHandler)
	request = urllib2.Request('https://blistering-inferno-8126.firebaseio.com/movies/'+movie+'.json', data=json)
	request.get_method = lambda: 'PUT'
	url = opener.open(request)

def push_chat_msg(author, msg):
	#curl -X PUT -d '{ "alanisawesome": { "name": "Alan Turing", "birthday": "June 23, 1912" } }' https://docs-examples.firebaseio.com/rest/quickstart/users.json
	opener = urllib2.build_opener(urllib2.HTTPHandler)
	request = urllib2.Request('https://blistering-inferno-8126.firebaseio.com/chat/KEY.json', data='{"author":"'+author+'", "message":"'+msg+'"}')
	request.get_method = lambda: 'PUT'
	url = opener.open(request)

if __name__ == '__main__':
    app.run(debug=True)