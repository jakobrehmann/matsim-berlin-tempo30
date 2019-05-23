package main.ha1.network;

import java.io.BufferedWriter;
import java.io.IOException;
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
import org.matsim.core.utils.io.IOUtils;
import org.opengis.feature.simple.SimpleFeature;

public class GetRingLinks {
	static Path inputFile = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\input\\be_5_network_with-pt-ride-freight.xml") ;
	private static Path filterShape = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\GIS\\ring6.shp") ;
	private static Path outputFile = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\LinksWithinRing.txt");
	private static Path outputFileOutsideRing = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\LinksOutsideRing.txt");
	static ArrayList<String> linkIds = new ArrayList<>() ;
	static ArrayList<String> linkIdsOutsideRing = new ArrayList<>() ;
	
	public static void main(String[] args) {
		
		// Read Network
		Network network = NetworkUtils.createNetwork();
		new MatsimNetworkReader(network).readFile(inputFile.toString());
		
		// Read Shapefile
		final Collection<Geometry> geometries = new ArrayList<>();		
		for (SimpleFeature feature : ShapeFileReader.getAllFeatures(filterShape.toString())) {
			geometries.add((Geometry) feature.getDefaultGeometry());
		}
		
		for(Link i : network.getLinks().values()) {
			Coord coord = i.getCoord() ;
			double speed = i.getFreespeed() ;
			Set<String> modes = i.getAllowedModes() ;
			if ((geometries.stream().anyMatch(geom -> geom.contains(MGC.coord2Point(coord)))) && (speed >= 4.5) && (modes.contains("car"))){
				linkIds.add(i.getId().toString());
			}
			else if ((modes.contains("car"))){
				linkIdsOutsideRing.add(i.getId().toString());
			}
		}
		
		writeRingLinksToFile(linkIds, outputFile.toString());
		writeRingLinksToFile(linkIdsOutsideRing, outputFileOutsideRing.toString());

	}
	
	static void writeRingLinksToFile(ArrayList<String> linkIds, String outputFile){
		BufferedWriter bw = IOUtils.getBufferedWriter(outputFile);
		try {
			for (int i = 0;i< linkIds.size();i++){
			bw.write(linkIds.get(i));	
			bw.newLine();
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
