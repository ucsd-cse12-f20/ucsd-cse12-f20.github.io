import java.time.LocalDateTime;

public class Event {
  final LocalDateTime start;
  final LocalDateTime end;
  final String location;

  public Event(LocalDateTime start, LocalDateTime end, String location) {
    this.start = start;
    this.end = end;
    this.location = location;
  }

  /*
    @param other the Event to compare to
    @return true if the other event happens at an overlapping time and the same location
  */
  public boolean conflict(Event other) {
    
	  if (!location.equals(other.location)) { return false; }
	  
	  if (start.compareTo(other.start) <= 0) 
	  {
		  if (end.compareTo(other.start) > 0)
		  {
			  return true;
		  }
	  }
	  
	  if (start.compareTo(other.start) >= 0) 
	  {
		  if (start.compareTo(other.end) < 0)
		  {
			  return true;
		  }
	  }
	  
	  
	  return false;
  }
}
