package subway;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import search.*;

public class SubwayNavigationProblem extends Problem { //should i do javadoc? should i put my name commented by what parts i did?
    public SubwayMap subwayMap;
    public double distance;

    public SubwayNavigationProblem (State initial, State goal, String city) {
        //call super constructor
        super(initial, goal);

        //RUN THIS BY PROF TO SEE SPECIFICS OF IF THIS IS FINE
        try {
        if (city.toLowerCase().equals("london"))
            this.subwayMap = SubwayMap.buildLondonMap();
        else
            this.subwayMap = SubwayMap.buildBostonMap();

        } catch (FileNotFoundException fnfe) {
            System.out.println(fnfe.toString());
        }

        //set distance to default (0) since unprovided
        this.distance = 0;
    }

    public SubwayNavigationProblem (State initial, State goal, String city, double d) {
        //call three argument constructor
        this(initial, goal, city);

        //set distance to inputted distance
        this.distance = d;
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
    public boolean goalTest (State state) {
        //if straight line distance (CHECK WITH PROF) is less than distance specified by input, return true that state is a goal
        if (SubwayMap.straightLineDistance(subwayMap.getStationByName(state.getName()), subwayMap.getStationByName(this.goal.getName())) < this.distance)
            return true;

        return state.equals(this.goal);
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
