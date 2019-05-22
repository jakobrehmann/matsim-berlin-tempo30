package main.ha1.network;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.locationtech.jts.geom.Geometry;
import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.network.Network;
import org.matsim.core.network.NetworkUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.network.io.NetworkWriter;
import org.matsim.core.utils.geometry.geotools.MGC;
import org.matsim.core.utils.gis.ShapeFileReader;
import org.opengis.feature.simple.SimpleFeature;

public class ModifyNetworkTempo30 {
	static Path inputFile = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\input\\be_5_network_with-pt-ride-freight.xml") ;
	private static Path filterShape = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\GIS\\ring6.shp") ;
	private static Path outputFile = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\input\\network_tempo30.xml");
	
	
	public static void main(String[] args) {
		
		// Read Network
		Network network = NetworkUtils.createNetwork();
		new MatsimNetworkReader(network).readFile(inputFile.toString());
		
		// Read Shapefile
		final Collection<Geometry> geometries = new ArrayList<>();		
		for (SimpleFeature feature : ShapeFileReader.getAllFeatures(filterShape.toString())) {
			geometries.add((Geometry) feature.getDefaultGeometry());
		}
		
		// Set Maximum FreeSpeed in Ring to 30
		// NOTE: Speeds are actually changed to 15 kmh, since in MATSim all the Freespeeds are half of what they should be.
		for(Link i : network.getLinks().values()) {
			Coord coord = i.getCoord() ;
			double speed = i.getFreespeed() ;
			Set<String> modes = i.getAllowedModes() ;
			if ((geometries.stream().anyMatch(geom -> geom.contains(MGC.coord2Point(coord)))) && (speed >= 4.5) && (modes.contains("car"))){
				i.setFreespeed(4.1666666666666667);
			}
		}
		
		new NetworkWriter(network).write(outputFile.toString());
	}
}

