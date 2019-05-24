package main.ha1.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.matsim.api.core.v01.Id;
import org.matsim.core.utils.io.IOUtils;
import org.matsim.vehicles.Vehicle;

public class MyUtils {
	
	public static ArrayList<String> readLinksFile(String fileName){
		Scanner s ;
		ArrayList<String> list = new ArrayList<String>();
		try {
			s = new Scanner(new File(fileName));	
			while (s.hasNext()){
		    list.add(s.next());
		}
			s.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		

		return list;
	}

	public static void writeArrayListVehicle(ArrayList<Id<Vehicle>> VehIds, String fileName){
		BufferedWriter bw = IOUtils.getBufferedWriter(fileName);
		try {
			for (int i = 0;i<VehIds.size();i++){
			bw.newLine();
			bw.write(VehIds.get(i).toString());	
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void writeArrayListString(ArrayList<String> VehIds, String fileName){
		BufferedWriter bw = IOUtils.getBufferedWriter(fileName);
		try {
			for (int i = 0;i<VehIds.size();i++){
			bw.newLine();
			bw.write(VehIds.get(i).toString());	
			}
			bw.flush();
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}

	public static void writeDistancesToFile(int[] distanceDistribution, String fileName){
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
