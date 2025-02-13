package subway;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import search.*;

public class SubwayNavigationProblem extends Problem {
    public SubwayMap problemMap;

    public SubwayNavigationProblem (State initial, State goal, String city) {
        super(initial, goal);

        //create map based on input
        try {
        if (city.toLowerCase().equals("london"))
            this.problemMap = SubwayMap.buildLondonMap();
        else
            this.problemMap = SubwayMap.buildBostonMap();

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.toString());
        }
    }

    @Override
    public ArrayList<Tuple> successor (State state) {
        //create solution array list of tuples
        ArrayList <Tuple> successors = new ArrayList<Tuple>();

        //store current station from argument state
        Station currentStation = problemMap.getStationByName(state.getName());

        //if station exists
        if (currentStation != null) {
            //loop through all links from station
            for (Link currentLink : problemMap.incidentLinks(currentStation)) {
                //create and add Action and State from each adjacent link
                successors.add(new Tuple(new Action(currentLink.toString()), new State(currentLink.opposite(currentStation).toString())));
            }
        }
        
        //return arraylist of all successor states + actions
        return successors;
    }

    @Override
    public double pathCost (double cost, State initialState, Action action, State finalState) {
        

        return cost + 1;
    }
}
