package main.ha1.analysis;

import java.util.HashMap;
import java.util.Map;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.ActivityEndEvent;
import org.matsim.api.core.v01.events.ActivityStartEvent;
import org.matsim.api.core.v01.events.handler.ActivityEndEventHandler;
import org.matsim.api.core.v01.events.handler.ActivityStartEventHandler;
import org.matsim.api.core.v01.population.Person;

public class TravelTimeEventHandler 
implements ActivityEndEventHandler, ActivityStartEventHandler {

	private final Map<Id<Person>, Double> travelTimebyAgent = new HashMap<>() ;
	private final Map<Id<Person>, ActivityEndEvent> openTrips =  new HashMap<>() ;
	
	Map<Id<Person>, Double> getTravelTimeByAgent() {
		return travelTimebyAgent ;
	}
	
	double computeOverallTravelTime() {
		return travelTimebyAgent.values().stream().mapToDouble(d -> d).sum(); // stream api Important! Read Isis
	}
			
	@Override
	public void handleEvent(ActivityStartEvent event) {
		if(openTrips.containsKey(event.getPersonId()) && isNotInteraction(event.getActType())) {
			ActivityEndEvent endEvent = openTrips.remove(event.getPersonId()) ;
			double travelTime = event.getTime() - endEvent.getTime() ;
			travelTimebyAgent.merge(event.getPersonId(), travelTime, (a,b) -> a + b);
			// lambda function, a is original thing, b is new travel time
		}
	}

	@Override
	public void handleEvent(ActivityEndEvent event) {
		if(isNotInteraction(event.getActType())) {
			openTrips.put(event.getPersonId(), event) ;
		}
	}
	
	private boolean isNotInteraction(String activityType) {
		return !activityType.contains("interaction") ;
	}
	
}
