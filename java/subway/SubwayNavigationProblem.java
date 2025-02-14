package subway;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import search.*;

public class SubwayNavigationProblem extends Problem {
    public SubwayMap subwayMap;

    public SubwayNavigationProblem (State initial, State goal, String city) { //city potentially not needed? don't know how else we'd do this tho
        super(initial, goal);

        //create map based on input (RUN THIS BY PROF)
        try {
        if (city.toLowerCase().equals("london"))
            this.subwayMap = SubwayMap.buildLondonMap();
        else
            this.subwayMap = SubwayMap.buildBostonMap();

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.toString());
        }
    }

    @Override //do i need full java doc? (RUN THIS BY PROF)
    public ArrayList<Tuple> successor (State state) {
        //create solution array list of tuples
        ArrayList <Tuple> successors = new ArrayList<Tuple>();

        //store current station from argument state
        Station currentStation = subwayMap.getStationByName(state.getName());

        //if station exists
        if (currentStation != null) {
            //loop through all links from station
            for (Link currentLink : subwayMap.incidentLinks(currentStation)) {
                //create and add Action and State from each adjacent link
                successors.add(new Tuple(new Action(currentLink.line), new State(currentLink.opposite(currentStation).name)));
            }
        }
        
        //return arraylist of all successor states + actions
        return successors;
    }

    @Override
    public double pathCost (double cost, State initialState, Action action, State finalState) {
        //create stations from states
        Station initialStation = subwayMap.getStationByName(initialState.getName());
        Station finalStation = subwayMap.getStationByName(finalState.getName());

        //loop through all links between two initial and final stations
        for (Link currentLink : subwayMap.getLinksBetween(initialStation, finalStation)) {
            //find link equal to action argument
            if (currentLink.line.equals(action.getName())) {
                //return cost + distance added by action
                return (cost + currentLink.getDistance());
            }
        }

        //if no links between stations or if no link equal to action, return unmodified cost (ALSO RUN THIS BY PROF)
        return cost;
    }
}
