import re, os
import dateutil.parser as dparser

# PNR_PATTERN = '[a-zA-Z0-9]{5,6}' #SG281, 00WJSS
FLIGHT_ID_PATTERN = '(flight\:|flt)\s(.*?)\s'
LOCATION_PATTERN = '([A-Z]+)\s*(\-|to)\s*([A-Z]+)'
DATE_PATTERN = '(DATE|on)\s*(.*)\s*(TIME\:|AT)\s*(.*?)\.' #\s*(TIME|AT)
# TEST_DATE_PATTERN = '(on)\s*(.*?)\s*(TIME|AT)\s*(.*?)'

def getFlightId(msg):
	response = {}
	m = re.search(FLIGHT_ID_PATTERN, msg, re.IGNORECASE)
	if m:
		fid = m.group(2)
		response["fid"] = fid
	return response

def getLocation(msg):
	response = {}
	m = re.search(LOCATION_PATTERN, msg, re.IGNORECASE)
	if m:
		start = m.group(1)
		end = m.group(3)
		response["from"] = start
		response["to"] = end
	return response

def getDateTime(msg):
	response = {}
	m = re.search(DATE_PATTERN, msg, re.IGNORECASE)
	if m:
		date = getDate(m.group(2))
		time = getTime(m.group(4))
		response["date"] = date
		response["time"] = time
	else:
		print "Date Time Parsing failed."
	return response

def getDate(upto):
	date = dparser.parse(upto, fuzzy=True) 
	return date.date()

def getTime(upto):
	upto = "01-01-2015 " + upto #Appending dummy date
	date = dparser.parse(upto, fuzzy=True)
	return date.time()

MOVIE_PATTERN = 'for\s(.*)\son\s(.*)\sat\s(.*)\('
MOVIE2_PATTERN = '\|(.*)\|'
def parseMovie(msg):
	response = {}

	m = re.search(MOVIE_PATTERN, msg, re.IGNORECASE)
	if m:
		name = m.group(1)
		upto = m.group(2)
		venue = m.group(3)
		date_time = dparser.parse(upto, fuzzy=True)
		date = date_time.date()
		time = date_time.time()

		response["name"] = name
		response["location"] = venue
		response["date"] = date
		response["time"] = time
	else: 
		s = re.search(MOVIE2_PATTERN, msg, re.IGNORECASE)
		if s:
			details = {}
			groups_str = s.group(1)
			groups = groups_str.split(' | ')
			for group_str in groups:
				group = group_str.split(': ')
				if len(group) == 2:
					o_id = group[0].lower().strip()
					if o_id == 'location':
						details["location"] = group[1]
					elif o_id == 'movie':
						details["name"] = group[1] #@TODO Check appended (U/A)
					elif o_id == 'show details':
						details["date"] = dparser.parse(group[1], fuzzy=True).date()
				else:
					details['time'] = dparser.parse(group[0], fuzzy=True).time() # for time
			response = details


		else :
			print "Movie API Failed"
	return response
	

def parseMovies(msg):
	d = parseMovie(msg)
	t = {"type": "movie"}
	details = dict(d.items() + t.items())
	return details

def parseFlight(msg):
	loc = getLocation(msg) #to, from
	date = getDateTime(msg) #date, time
	fid = getFlightId(msg) #fid
	t = {"type": "flight"}
	details = dict(loc.items() + date.items() + fid.items() + t.items())
	return details

def test(msg):
	# print dparser.parse("Date: 09/01/2015 Dep.Time: 05:20hrs",fuzzy=True)
	# print dparser.parse("on 28 Oct 14 at 05:50 hrs",fuzzy=True)
	print dparser.parse("9-Aug | 06:45 PM",fuzzy=True)
	# a =  dparser.parse("on Fri, 09 Jan from MAA to AMD at 2015",fuzzy=True)
	# print a.year

def parse(msg):
	print "inside parse"
	print msg
	details = {}
	# if i == "movie":
	# 	details = parseMovies(msg)
	# elif i == "flight":
	# 	details = parseFlight(msg)
	# else:
	# 	print "parse type not defined"
	checkmsg = msg.lower()
	if "movie" in checkmsg or "seats" in checkmsg:
		details = parseMovies(msg)
	elif "flight" in checkmsg or "flt" in checkmsg:
		details = parseFlight(msg)
	else:
		print "parse type not defined"
	print details
	print "------"
	return details


if __name__ == '__main__':
	msg1 = "Dear Guest, Your PNR: 00WJSS for Flight: LB632 MAA-AMD Date: 09/01/2015 Dep.Time: 05:20hrs. Contact 1800-4250-0666 for assistance. Happy Flying! air costa"
	msg2 = "The PNR for your Spicejet Flt SG281 for Ahmedabad-Chennai on 28 Oct 14 at 05:50 hrs is LEHW8B. Thank you."
	msg3 = "Dear Guest, Your PNR is 00WJSS for flight LB632 on Fri, 09 Jan from MAA to AMD at 0520 and 00WJSS for flight SG281 on Tue, 20 Jan from AMD to MAA at 0555"
	movie = "Hi Customer, Booking ID: AGSN0004863077. Seats: DIAMOND-B10,B11,B12,B13 for Interstellar on Sat, 22 Nov, 2014 10:30pm at AGS Cinemas OMR: Navlur (SCREEN 4). Please carry your CC/DC card which was used for booking tickets"
	movie2 = "Booking ID: WWT782B | Movie: HERCULES (U/A) | Show Details: 9-Aug | 06:45 PM | Screen: SCREEN-9 | Class: ELITE | Seats: C13, C14 | Multiplex: LUXE | Location: PHOENIX MARKET CITY, VELACHERY, CHENNAI | No ticket required. Please show this SMS to a cinema attendant for entry into the screen. Thank you"

	# parse(msg1)
	# parse(msg2)
	# parse(movie)
	# parse(movie2)
