import re, os
import dateutil.parser as dparser

PNR_PATTERN = '[a-zA-Z0-9]{5,6}' #SG281, 00WJSS
LOCATION_PATTERN = '([A-Z]+)\s*(\-|to)\s*([A-Z]+)'

def getLocation(msg):
	m = re.search(LOCATION_PATTERN, msg, re.IGNORECASE)
	print m
	if m:
		start = m.group(1)
		end = m.group(3)

def parse(msg):
	loc = getLocation(msg)

def test(msg):
	print dparser.parse("Date: 09/01/2015 Dep.Time: 05:20hrs",fuzzy=True)
	print dparser.parse("on 28 Oct 14 at 05:50 hrs",fuzzy=True)
	print dparser.parse("on Fri, 09 Jan from",fuzzy=True)
	a =  dparser.parse("on Fri, 09 Jan from MAA to AMD at 2015",fuzzy=True)
	print a.year

if __name__ == '__main__':
	msg1 = "Dear Guest, Your PNR: 00WJSS for Flight: LB632 MAA-AMD Date: 09/01/2015 Dep.Time: 05:20hrs. Contact 1800-4250-0666 for assistance. Happy Flying! air costa"
	msg2 = "The PNR for your Spicejet Flt SG281 for Ahmedabad-Chennai on 28 Oct 14 at 05:50 hrs is LEHW8B. Thank you."
	msg3 = "Dear Guest, Your PNR is 00WJSS for flight LB632 on Fri, 09 Jan from MAA to AMD at 0520 and 00WJSS for flight SG281 on Tue, 20 Jan from AMD to MAA at 0555"
	# parse(msg1)
	# parse(msg2)
	# parse(msg3)
	test(msg2)
