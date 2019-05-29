/* *********************************************************************** *
 * project: org.matsim.*												   *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2008 by the members listed in the COPYING,        *
 *                   LICENSE and WARRANTY file.                            *
 * email           : info at matsim dot org                                *
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *   See also COPYING, LICENSE and WARRANTY file                           *
 *                                                                         *
 * *********************************************************************** */
package main.ha1.analysis;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.population.Person;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;

import main.ha1.utils.MyUtils;

public class RunEventsHandler {
	static Path inputNetwork = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\input\\be_5_network_with-pt-ride-freight.xml") ;
	static Path inputEventsBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\web_output\\berlin-v5.3-1pct.output_events.xml.gz");
	static Path inputEventsTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\output\\ITERS\\it.200\\berlin-v5.3-1pct.200.events.xml.gz");
	static Path outputCarDistBase = Paths.get(".\\output\\carDistancesBase.txt");
	static Path outputCarDistTempo30 = Paths.get(".\\output\\carDistancesTempo30.txt");
	static Path LinksWithinRing = Paths.get(".\\output\\LinksWithinRing.txt");
	private static Path VehWithinRingBase = Paths.get(".\\output\\VehWithinRingBase.txt");
	private static Path VehWithinRingTempo30 = Paths.get(".\\output\\VehWithinRingTempo30.txt");
	
	public static void main(String[] args) {
		ArrayList<String> linksWithinRing = MyUtils.readLinksFile(LinksWithinRing.toString()) ;
		ArrayList<String> vehWithinRing = MyUtils.readLinksFile(VehWithinRingBase.toString()) ;

		//Initialization
		String inputEvents = inputEventsTempo30.toString(); //IMPORTANT 
		EventsManager manager = EventsUtils.createEventsManager();
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		new MatsimNetworkReader(scenario.getNetwork()).readFile(inputNetwork.toString());

		
		// Car Distance Evaluator 
		{
			CarTravelDistanceEvaluator carTravelDistanceEvaluator = new CarTravelDistanceEvaluator(scenario.getNetwork(), linksWithinRing);
			manager.addHandler(carTravelDistanceEvaluator);
			new MatsimEventsReader(manager).readFile(inputEvents);	
			MyUtils.writeDistancesToFile(carTravelDistanceEvaluator.getDistanceDistribution(), outputCarDistTempo30.toString());
			System.out.print(carTravelDistanceEvaluator.getTravelledDistanceTotal());
		}
		
	
//		 If cars cross into inner city
		{
			CityCenterEventEnterHandler cityCenterEventEnterHandler = new CityCenterEventEnterHandler();

			for (String link : linksWithinRing) { //add city center links
				System.out.println();
				cityCenterEventEnterHandler.addLinkId(Id.createLinkId(link));
			}
			manager.addHandler(cityCenterEventEnterHandler);

			System.out.println(cityCenterEventEnterHandler.getVehiclesInCityCenter().size());		
			System.out.println("Events file read!");
			MyUtils.writeArrayListVehicle(cityCenterEventEnterHandler.getVehiclesInCityCenter(), VehWithinRingTempo30.toString() ) ;
		}
			
		// Travel Time
		{
			TravelTimeEventHandler timeHandler = new TravelTimeEventHandler() ;
			manager.addHandler(timeHandler);
			new MatsimEventsReader(manager).readFile(inputEvents);	
			double totalTravelTime = timeHandler.computeOverallTravelTime() ;
			System.out.println("time = " + totalTravelTime/3600 + " hours") ;
			
			// For Subpopulation
			Map<Id<Person>, Double> travelTimeByAgent = timeHandler.getTravelTimeByAgent() ;
			double subPopTravelTimeTotal = 0. ;
			
			for (Id<Person> per : travelTimeByAgent.keySet()) {
				if (vehWithinRing.contains(per.toString())) {
					subPopTravelTimeTotal += travelTimeByAgent.get(per);
				}
			}
			System.out.println("Subpop time = " + subPopTravelTimeTotal/3600 + " hours") ;
			
		}

	}

}
