class Calendar { /*  */ }

//create an event - check for conflicts so that no two events overlap


class Event {
	Date startTime;
	Date endTime;
  
	//military time - minutes in a 24 hour clock
	int startTime;
	int endTIme;

	int secondsFrom1970;	//int is fixed size 2^32
	long secondFrom1970;	//long is fixed size 2^64
	
	//location
	string location;
	double lat, lng;
	
	Location loc;
	
	string name;
	
	
	
  
}
