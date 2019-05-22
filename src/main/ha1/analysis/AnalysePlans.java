package main.ha1.analysis;

import org.matsim.api.core.v01.Coord;
import org.matsim.api.core.v01.Id;
import org.matsim.api.core.v01.Scenario;
import org.matsim.api.core.v01.TransportMode;
import org.matsim.api.core.v01.network.Link;
import org.matsim.api.core.v01.population.Activity;
import org.matsim.api.core.v01.population.Leg;
import org.matsim.api.core.v01.population.Person;
import org.matsim.api.core.v01.population.Plan;
import org.matsim.api.core.v01.population.Population;
import org.matsim.api.core.v01.population.PopulationFactory;
import org.matsim.core.config.Config;
import org.matsim.core.config.ConfigUtils;
import org.matsim.core.network.io.MatsimNetworkReader;
import org.matsim.core.population.io.PopulationReader;
import org.matsim.core.population.routes.RouteFactories;
import org.matsim.core.router.TripStructureUtils;
import org.matsim.core.scenario.ScenarioUtils;
import org.matsim.core.scenario.ScenarioUtils.ScenarioBuilder;
import org.matsim.core.utils.io.IOUtils;
//import org.matsim.examples.ExamplesUtils;
import org.matsim.facilities.ActivityFacility;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

class AnalysePlans{
	
	private static Path configTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\input\\config_tempo30.xml");
	private static Path popBase = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\web_output\\berlin-v5.3-1pct.output_plans.xml.gz");
	private static Path popTempo30 = Paths.get("C:\\Users\\jakob\\Dropbox\\Documents\\Education-TUB\\2019_SS\\MATSim\\HA1\\tempo30Case\\output\\ITERS\\it.200\\berlin-v5.3-1pct.200.plans.xml.gz");
	
	public static void main ( String [] args ) {
		System.out.println("hello");
//        URL configUrl = IOUtils.newUrl( ExamplesUtils.getTestScenarioURL( "equil" ), "config.xml" );;
//        Config config = ConfigUtils.loadConfig( configUrl ) ;


		Scenario sc = ScenarioUtils.createScenario(ConfigUtils.createConfig());
        new PopulationReader(sc).readFile(popBase.toString()); 
        final Population pop = sc.getPopulation();

        long nCarLegs = 0 ;
    	long nPtLegs = 0 ;
        long nCarUsingPersons = 0 ;
        double totalCarDistance = 0. ;
        double totalPtDistance = 0. ;
        for ( Person person : pop.getPersons().values() ) {
            boolean carUser = false ;
            Plan plan = person.getSelectedPlan() ;
            for ( Leg leg : TripStructureUtils.getLegs( plan ) ) {
                if ( TransportMode.car.equals( leg.getMode() ) ) {
                    nCarLegs++ ;
                    carUser = true ;
                    totalCarDistance += leg.getRoute().getDistance() ;
                }
                else if ( TransportMode.pt.equals(leg.getMode())) {

					nPtLegs++ ;
					totalPtDistance += leg.getRoute().getDistance() ;
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
    }

}
