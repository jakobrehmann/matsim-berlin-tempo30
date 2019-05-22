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
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.matsim.api.core.v01.Scenario;
import org.matsim.core.api.experimental.events.EventsManager;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.events.EventsUtils;
import org.matsim.core.events.MatsimEventsReader;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.utils.io.IOUtils;

public class RunEventsHandler {
	static Path inputNetwork = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\input\\be_5_network_with-pt-ride-freight.xml") ;
	static Path inputEventsBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\web_output\\berlin-v5.3-1pct.output_events.xml.gz");
	static Path inputEventsTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\output\\ITERS\\it.200\\berlin-v5.3-1pct.200.events.xml.gz");
	static Path outputHandlerBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\handler\\carDistancesBase.txt");
	static Path outputHandlerTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\handler\\carDistancesTempo30.txt");

	public static void main(String[] args) {

		EventsManager eventsManager = EventsUtils.createEventsManager();
		Scenario scenario = ScenarioUtils.createScenario(ConfigUtils.createConfig());
		
		new MatsimNetworkReader(scenario.getNetwork()).readFile(inputNetwork.toString());
		
		CarTravelDistanceEvaluator carTravelDistanceEvaluator = new CarTravelDistanceEvaluator(scenario.getNetwork());
		eventsManager.addHandler(carTravelDistanceEvaluator);
		new MatsimEventsReader(eventsManager).readFile(inputEventsBase.toString());
		
		writeDistancesToFile(carTravelDistanceEvaluator.getDistanceDistribution(), outputHandlerBase.toString());
		System.out.print(carTravelDistanceEvaluator.getTravelledDistanceTotal());
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
