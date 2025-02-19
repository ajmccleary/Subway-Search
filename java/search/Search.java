package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import subway.SubwayNavigationProblem;

/**
This code is adapted from search.py in the AIMA Python implementation, which is published with the license below:

	The MIT License (MIT)

	Copyright (c) 2016 aima-python contributors

	Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


**/

public class Search{
	/* DO NOT MODIFY THE HEADERS OF ANY OF THESE FUNCTIONS */
	
	// Uninformed Search algorithms
	
	public static Node breadthFirstSearch(Problem problem){
		//initialize frontier
		Node initial = new Node(problem.getInitial()); //initial node
		Queue<Node> frontier = new LinkedList<Node>();
		HashSet<State> frontierStates = new HashSet<State>();
		frontier.add(initial);
		frontierStates.add(initial.getState());

		//if start(initial) node is goal node
		if (problem.goalTest(initial.getState())){
			return initial;
		}

		//initialize explored set
		HashSet<State> explored = new HashSet<State>();
		explored.add(initial.getState());
		
		while(!frontier.isEmpty()){
			Node leaf = frontier.remove();
			frontierStates.remove(leaf.getState());
			explored.add(leaf.getState());
		
			for(Node successor : leaf.expand(problem)){
				if(problem.goalTest(successor.getState())){
					return successor;
				} else {
					if(!frontierStates.contains(successor.getState()) && !explored.contains(successor.getState())){
						frontier.add(successor);
						frontierStates.add(successor.getState());
					}					
				}
			}
			
		}

		return null;
	}
	
	public static Node depthFirstSearch(Problem problem){
		//YOUR CODE HERE
		return null; 

	}
	
	public static Node uniformCostSearch(Problem problem){
		//YOUR CODE HERE
		return null;
	}

	// Informed (Heuristic) Search
	
	public static Node aStarSearch(Problem problem){
		//initial
		Node initial = new Node(problem.getInitial()); //initial node
		initial = new Node(problem.getInitial(), null, null, (0 + problem.h(initial))); //initial node
		
		//initialize frontier
		PriorityQueue<Node> frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return Double.compare(n1.getPathCost(), n2.getPathCost());
            }
        });
		HashSet<State> frontierStates = new HashSet<State>(); //keeps track of states
		frontier.add(initial);
		frontierStates.add(initial.getState());
		

		//initialize explored set
		HashSet<State> explored = new HashSet<State>();

		//search frontier
		while(!frontier.isEmpty()){
			Node leaf = frontier.remove();
			if(problem.goalTest(leaf.getState())){
				return leaf;
			}
			explored.add(leaf.getState());

			for(Node successor : leaf.expand(problem)){
				//checks if successor is new
			

				if(!frontierStates.contains(successor.getState()) && !explored.contains(successor.getState())){ //if successor already in frontier/explored
					frontier.add(new Node(successor.getState(), successor.get));
					frontierStates.add(successor.getState());
					
				} else if (frontierStates.contains(successor.getState()) && ){  //if successor is better than current node with same state in frontier/explored

				}

				//alt implement: search through all
					
				
			}

		}

		return null;
	}
	
	// Main
	public static void main(String[] args){
		//Replace this code with code that runs the program specified by
		//the command arguments
		
		//testing prob #1 - heres how these work, check .csv's if confused
		SubwayNavigationProblem snp = new SubwayNavigationProblem(new State("Airport"), new State("Maverick"), "boston");
		System.out.println(snp.pathCost(0, new State("Airport"), new Action("Blue"), new State("Maverick")));
		for (Tuple testTuple : snp.successor(new State("Maverick")))
			System.out.println(testTuple.getAction() + "   " + testTuple.getState());

		//System.out.println(args[0]);

		//problem #3: Breadth First Search
		// if (args[1].equals("bfs")){
		// 	SubwayNavigationProblem bfs = new SubwayNavigationProblem(new State (args[2]), new State(args[3]), args[0]);
		// 	Node solution = breadthFirstSearch(bfs);
		// 	System.out.println("Path:" + solution.path());
		// 	System.out.println("Path Cost:" + solution.getPathCost());
		// 	System.out.println("# Visited:" + solution.getDepth());
		// }
		//sample
		SubwayNavigationProblem bfs = new SubwayNavigationProblem(new State("Fenway"), new State("South Station"), "boston");
		Node solution = breadthFirstSearch(bfs);
		System.out.println("Path:" + solution.path());
		System.out.println("Path Cost:" + solution.getPathCost());
		System.out.println("# Visited:" + solution.getDepth());
		
		//problem #5 A* Search
		SubwayNavigationProblem astar = new SubwayNavigationProblem(new State("Fenway"), new State("South Station"), "boston");
		Node answer = aStarSearch(astar);
		System.out.println("Path:" + answer.path());
		System.out.println("Path Cost:" + answer.getPathCost());
		System.out.println("# Visited:" + answer.getDepth());

		
	}
}