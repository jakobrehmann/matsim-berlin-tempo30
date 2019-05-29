package main.ha1.analysis;


import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;
import main.ha1.utils.MyUtils;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

class AnalysePlans{
	
	private static Path popBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\web_output\\berlin-v5.3-1pct.output_plans.xml.gz");
	private static Path popTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\output\\ITERS\\it.200\\berlin-v5.3-1pct.200.plans.xml.gz");
	private static Path VehWithinRingBase = Paths.get(".\\output\\VehWithinRingBase.txt");
	
	public static void main ( String [] args ) throws FileNotFoundException {


		Scenario sc = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        new PopulationReader(sc).readFile(popTempo30.toString()); 
        
        final Population pop = sc.getPopulation();
        
        ArrayList<String> vehWithinRing = MyUtils.readLinksFile(VehWithinRingBase.toString()) ;
        
        
        long nCarLegs = 0 ;
    	long nPtLegs = 0 ;
    	long nCarLegsSubPop = 0 ;
    	long nPtLegsSubPop = 0 ;
        long nCarUsingPersons = 0 ;
        double totalCarDistance = 0. ;
        double totalPtDistance = 0. ;
        double totalCarDistanceSubPop = 0. ;
        double totalPtDistanceSubPop = 0. ;
        
        
        for ( Person person : pop.getPersons().values() ) {
            boolean carUser = false ;
            Plan plan = person.getSelectedPlan() ;
            for ( Leg leg : TripStructureUtils.getLegs( plan ) ) {
                if ( TransportMode.car.equals( leg.getMode() ) ) {
                    nCarLegs++ ;
                    carUser = true ;
                    totalCarDistance += leg.getRoute().getDistance() ;
                    
                    //for every person in the sub pop, check how much they drove (within and outside of Ring)
                    if (vehWithinRing.contains(plan.getPerson().getId().toString())) {
                    	nCarLegsSubPop ++ ;
                    	totalCarDistanceSubPop += leg.getRoute().getDistance() ;
                    }
                }
                else if ( TransportMode.pt.equals(leg.getMode())) {

					nPtLegs++ ;
					totalPtDistance += leg.getRoute().getDistance() ;
                    if (vehWithinRing.contains(plan.getPerson().getId().toString())) {
                    	nPtLegsSubPop++ ;
                    	totalPtDistanceSubPop += leg.getRoute().getDistance() ;
                    }
                }
            }
            if ( carUser ) nCarUsingPersons++ ;
        }

        System.out.println( "Number of persons =" + pop.getPersons().size() ) ;
        System.out.println( "Number of car legs = " + nCarLegs ) ;
        System.out.println( "Number of car legs per person = " + 1.*nCarLegs/pop.getPersons().size() ) ;
        System.out.println( "Number of car using persons = " + nCarUsingPersons ) ;
        System.out.println( "Number of pt legs = " + nPtLegs ) ; 
        System.out.println( "Total Driving Distance = " + totalCarDistance/1000 ) ;
        System.out.println( "Total Pt Distance = " + totalPtDistance/1000 ) ;
        System.out.println( "Total Subpop Car Dist = " + totalCarDistanceSubPop/1000 ) ;
        System.out.println( "Total Subpop Pt Dist = " + totalPtDistanceSubPop/1000 ) ;
        System.out.println( "Number of car legs SubPop = " + nCarLegsSubPop ) ;
        System.out.println( "Number of Pt legs SubPop = " + nPtLegsSubPop ) ;
    }

}
