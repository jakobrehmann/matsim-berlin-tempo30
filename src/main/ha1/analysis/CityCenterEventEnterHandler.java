/* *********************************************************************** *
 * project: org.matsim.*
 * RunEmissionToolOffline.java
 *                                                                         *
 * *********************************************************************** *
 *                                                                         *
 * copyright       : (C) 2009 by the members listed in the COPYING,        *
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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.events.LinkEnterEvent;
import org.matsim.api.core.v01.events.handler.LinkEnterEventHandler;
import org.matsim.api.core.v01.network.Link;
import org.matsim.vehicles.Vehicle;

/**
 * An event handler to determine if a vehicle has driven over a certain set of links.
 * 
 * @author jbischoff
 */
public class CityCenterEventEnterHandler implements LinkEnterEventHandler {

	
	ArrayList<Id<Vehicle>> agentsInCityCenter = new ArrayList<>();
	List<Id<Link>> cityCenterLinks = new ArrayList<>();
	
	@Override
	public void reset(int iteration) {
		this.agentsInCityCenter.clear();
	}

	//get unique vehicles in city center
	CharSequence c = "freight" ;
	@Override
	public void handleEvent(LinkEnterEvent event) {
		if ((this.cityCenterLinks.contains(event.getLinkId())) && (!agentsInCityCenter.contains(event.getVehicleId()))
				&& (!event.getVehicleId().toString().contains(c)))
//		if (this.cityCenterLinks.contains(event.getLinkId()))
		{
		this.agentsInCityCenter.add(event.getVehicleId());
		}
	}
	public void addLinkId(Id<Link> linkId){
		this.cityCenterLinks.add(linkId);
	}

	public ArrayList<Id<Vehicle>> getVehiclesInCityCenter() {
		return agentsInCityCenter;
	}

	
}