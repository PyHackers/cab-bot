import urllib2
import json
import time

def push_to_firebase(path, key, json):
	#curl -X PUT -d '{ "alanisawesome": { "name": "Alan Turing", "birthday": "June 23, 1912" } }' https://docs-examples.firebaseio.com/rest/quickstart/users.json
	if not key:
		key = str(int(time.time()))
	opener = urllib2.build_opener(urllib2.HTTPHandler)
	request = urllib2.Request('https://blistering-inferno-8126.firebaseio.com/'+path+'/'+key+'.json', data=json)
	request.get_method = lambda: 'PUT'
	url = opener.open(request)

def pull_from_firebase(path, key):
	url = 'https://blistering-inferno-8126.firebaseio.com/'+path+'/'+key+'.json'
	print url
	response = urllib2.urlopen(url)
	return json.loads(response.read())

def pull_array_from_firebase(path):
	url = 'https://blistering-inferno-8126.firebaseio.com/'+path+'.json'
	print url
	response = urllib2.urlopen(url)
	return json.loads(response.read())