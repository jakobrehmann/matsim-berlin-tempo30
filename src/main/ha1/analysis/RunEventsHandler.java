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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import org.matsim.core.utils.io.IOUtils;
import org.matsim.vehicles.Vehicle;

public class RunEventsHandler {
	static Path inputNetwork = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\input\\be_5_network_with-pt-ride-freight.xml") ;
	static Path inputEventsBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\web_output\\berlin-v5.3-1pct.output_events.xml.gz");
	static Path inputEventsTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\output\\ITERS\\it.200\\berlin-v5.3-1pct.200.events.xml.gz");
	static Path outputHandlerBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\handler\\carDistancesBase.txt");
	static Path outputHandlerTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\handler\\carDistancesTempo30.txt");
	static Path LinksWithinRing = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\LinksWithinRing.txt");
	private static Path VehWithinRingBase = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehWithinRingBase.txt");
	private static Path VehWithinRingTempo30 = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehWithinRingTempo30.txt");
	private static Path VehUniqueInBase = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehUniqueInBase.txt");
	private static Path VehUniqueInTempo30 = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehUniqueInTempo30.txt");

	public static void main(String[] args) throws FileNotFoundException {
		
	
		
		
		
//		{
//			EventsManager eventsManager = EventsUtils.createEventsManager();
//			Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
//			
//			new MatsimNetworkReader(scenario.getNetwork()).readFile(inputNetwork.toString());
//			
//			CarTravelDistanceEvaluator carTravelDistanceEvaluator = new CarTravelDistanceEvaluator(scenario.getNetwork(), linksWithinRing);
//			eventsManager.addHandler(carTravelDistanceEvaluator);
//			new MatsimEventsReader(eventsManager).readFile(inputEventsBase.toString());
//			
//			writeDistancesToFile(carTravelDistanceEvaluator.getDistanceDistribution(), outputHandlerBase.toString());
//			System.out.print(carTravelDistanceEvaluator.getTravelledDistanceTotal());
//		}
		
		
		// If cars cross into inner city
//		{
//			EventsManager events = EventsUtils.createEventsManager();
//
//			//create the handler and add it
//			CityCenterEventEnterHandler cityCenterEventEnterHandler = new CityCenterEventEnterHandler();
//
//			//add the links here that you want to monitor
//			ArrayList<String> linksWithinRing = readLinksFile(LinksWithinRing.toString()) ;
//			for (String link : linksWithinRing) {
//				System.out.println();
//				cityCenterEventEnterHandler.addLinkId(Id.createLinkId(link));
//			}
//			events.addHandler(cityCenterEventEnterHandler);
//
//
//	        //create the reader and read the file
//			MatsimEventsReader reader = new MatsimEventsReader(events);
//			reader.readFile(inputEventsBase.toString());
//			System.out.println(cityCenterEventEnterHandler.getVehiclesInCityCenter().size());
//			
//			
//			//System.out.println(cityCenterEventEnterHandler.getVehiclesInCityCenter());
//			
//			System.out.println("Events file read!");
//			WriteVehicleIdsCityCenter(cityCenterEventEnterHandler.getVehiclesInCityCenter(), VehWithinRingBase.toString() ) ;
//		}
			
		
		//Analysis of Veh in City Center
//		{
//			ArrayList<String> base = readLinksFile(VehWithinRingBase.toString()) ;
//			ArrayList<String> tempo30 = readLinksFile(VehWithinRingTempo30.toString()) ;
//			ArrayList<String> vehUniqueInBase = new ArrayList<String>() ;
//			ArrayList<String> vehUniqueInTempo30 = new ArrayList<String>() ;
//			
//			for (String veh : base) {
//				if (!tempo30.contains(veh)) {
//					vehUniqueInBase.add(veh);
//					//System.out.println(veh);
//				}
//			}
//			
//			for (String veh : tempo30) {
//				if (!base.contains(veh)) {
//					vehUniqueInTempo30.add(veh);
//					//System.out.println(veh);
//				}
//			}
//			System.out.println(vehUniqueInBase.size());
//			System.out.println(vehUniqueInTempo30.size());
//			WriteVehicleIds(vehUniqueInBase, VehUniqueInBase.toString());
//			WriteVehicleIds(vehUniqueInTempo30, VehUniqueInTempo30.toString());
//			
//		}
		// Travel Time
		{
			TravelTimeEventHandler timeHandler = new TravelTimeEventHandler() ;
			EventsManager manager = EventsUtils.createEventsManager();
			manager.addHandler(timeHandler);
			new MatsimEventsReader(manager).readFile(inputEventsTempo30.toString());
			double totalTravelTime = timeHandler.computeOverallTravelTime() ;
			System.out.println("time = " + totalTravelTime/3600 + " hours") ;
			
			// For Subpopulation
			Map<Id<Person>, Double> travelTimeByAgent = timeHandler.getTravelTimeByAgent() ;
			ArrayList<String> base = readLinksFile(VehWithinRingBase.toString()) ;
			double subPopTravelTimeTotal = 0. ;
			
			for (Id<Person> per : travelTimeByAgent.keySet()) {
				if (base.contains(per.toString())) {
					subPopTravelTimeTotal += travelTimeByAgent.get(per);
				}
			}
			System.out.println("Subpop time = " + subPopTravelTimeTotal/3600 + " hours") ;
			
		}
		
	}
	
	static ArrayList<String> readLinksFile(String fileName) throws FileNotFoundException {
		Scanner s = new Scanner(new File(fileName));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
		}
		s.close();
		return list;
	}
	
	static void WriteVehicleIdsCityCenter(ArrayList<Id<Vehicle>> VehIds, String fileName){
		BufferedWriter bw = IOUtils.getBufferedWriter(fileName);
		try {
			for (int i = 0;i<VehIds.size();i++){
			bw.newLine();
			bw.write(VehIds.get(i).toString());	
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	static void WriteVehicleIds(ArrayList<String> VehIds, String fileName){
		BufferedWriter bw = IOUtils.getBufferedWriter(fileName);
		try {
			for (int i = 0;i<VehIds.size();i++){
			bw.newLine();
			bw.write(VehIds.get(i).toString());	
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	
	
	static void writeDistancesToFile(int[] distanceDistribution, String fileName){
		BufferedWriter bw = IOUtils.getBufferedWriter(fileName);
		try {
			bw.write("Distance\tRides");
			for (int i = 0;i<distanceDistribution.length;i++){
			bw.newLine();
			bw.write(i+"\t"+distanceDistribution[i]);	
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

}
