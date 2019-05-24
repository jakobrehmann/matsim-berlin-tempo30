package main.ha1.network;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import main.ha1.utils.MyUtils;

public class CreateSubPopulations {
	private static Path VehUniqueInBase = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehUniqueInBase.txt");
	private static Path VehUniqueInTempo30 = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehUniqueInTempo30.txt");
	private static Path VehWithinRingBase = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehWithinRingBase.txt");
	private static Path VehWithinRingTempo30 = Paths.get("C:\\Users\\jakob\\eclipse-workspace\\matsim-berlin-tempo30\\output\\VehWithinRingTempo30.txt");
	

	public static void main(String[] args) {
		{
			ArrayList<String> base = MyUtils.readLinksFile(VehWithinRingBase.toString()) ;
			ArrayList<String> tempo30 = MyUtils.readLinksFile(VehWithinRingTempo30.toString()) ;
			ArrayList<String> vehUniqueInBase = new ArrayList<String>() ;
			ArrayList<String> vehUniqueInTempo30 = new ArrayList<String>() ;
			
			for (String veh : base) {
				if (!tempo30.contains(veh)) {
					vehUniqueInBase.add(veh);
				}
			}
			
			for (String veh : tempo30) {
				if (!base.contains(veh)) {
					vehUniqueInTempo30.add(veh);
				}
			}
			System.out.println(vehUniqueInBase.size());
			System.out.println(vehUniqueInTempo30.size());
			MyUtils.writeArrayListString(vehUniqueInBase, VehUniqueInBase.toString());
			MyUtils.writeArrayListString(vehUniqueInTempo30, VehUniqueInTempo30.toString());
			
		}

	}

}
