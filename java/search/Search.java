package search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import subway.SubwayNavigationProblem;

/**
 * This code is adapted from search.py in the AIMA Python implementation, which
 * is published with the license below:
 * 
 * The MIT License (MIT)
 * 
 * Copyright (c) 2016 aima-python contributors
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * 
 * 
 **/

public class Search {
	/* DO NOT MODIFY THE HEADERS OF ANY OF THESE FUNCTIONS */

	// Uninformed Search algorithms

	public static Node breadthFirstSearch(Problem problem) {
		// initialize frontier
		Node initial = new Node(problem.getInitial()); // initial node
		Queue<Node> frontier = new LinkedList<Node>();
		HashSet<State> frontierStates = new HashSet<State>();
		frontier.add(initial);
		frontierStates.add(initial.getState());

		// if start(initial) node is goal node
		if (problem.goalTest(initial.getState())) {
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

	public static Node depthFirstSearch(Problem problem) {
		// intialize the stack and empty set for visited vertices
		Node initial = new Node(problem.getInitial());
		Stack<Node> stack = new Stack<>();
		HashSet<State> visited = new HashSet<State>();

		stack.push(initial); // push the initial node into the stack

		if (problem.goalTest(initial.getState())) { // if the current node is the goal
			return initial;
		}

		visited.add(initial.getState());

		while (!stack.isEmpty()) {
			Node current = stack.pop(); // Pop the top vertex from the stack

			for (Node successor : current.expand(problem)) {
				if (problem.goalTest(successor.getState())) {
					System.out.println("Visited nodes: " + visited.size());
					return successor;
				}

				if (!visited.contains(successor.getState())) {
					visited.add(successor.getState());

					stack.push(successor);
				}
			}
		}

		return null;

	}
	
	public static Node uniformCostSearch(Problem problem){
		//Jessica Theodore Q4
		 PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getPathCost));
       
		 // HashSet to keep track of explored states to avoid revisiting them.
		 HashSet<State> explored = new HashSet<>();

	     // Create the initial node with the start state, no parent, no action, and zero cost.
        Node initial = new Node(problem.getInitial(), null, null, 0);
        frontier.add(initial);

        while (!frontier.isEmpty()) { // Loop until there are no more nodes to explore
            Node node = frontier.poll(); // Retrieve and remove the node with the lowest path cost.

            if (problem.goalTest(node.getState())) {// Check if the goal state is reached.
                return node;// Return the goal node to reconstruct the solution path.
            }

            explored.add(node.getState());// Mark this state as explored.

			// Expand the current node to generate its successors.
            for (Node successor : node.expand(problem)) {
				 // Add successor to frontier only if it's not in explored set or frontier
                if (!explored.contains(successor.getState()) && !frontier.contains(successor)) {
                    frontier.add(successor);
                }
            }
        }
        return null;
    }

	// Informed (Heuristic) Search
	
	public static Node aStarSearch(Problem problem){
		//initial
		Node initial = new Node(problem.getInitial()); 
		//initial = new Node(problem.getInitial(), null, null, (0 + problem.h(initial))); //initial node
		
		//initialize frontier
		PriorityQueue<Node> frontier = new PriorityQueue<>(new Comparator<Node>() {
            @Override
            public int compare(Node n1, Node n2) {
                return Double.compare(n1.getPathCost() + problem.h(n1), n2.getPathCost()+ problem.h(n2));
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
				//checks if successor 
				if(!frontierStates.contains(successor.getState()) && !explored.contains(successor.getState())){ //if successor already in frontier/explored
					frontier.add(successor);
					frontierStates.add(successor.getState());
					
				} else if (frontierStates.contains(successor.getState())){  //if successor is better than current node with same state in frontier/explored
					LinkedList<Node> tempHold = new LinkedList<>();
					
					//check each node in frontier to see if path is better
					while(!frontier.isEmpty()){
						Node temp = frontier.remove();
						if(temp.getState().equals(successor.getState())){
							if((temp.getPathCost() + problem.h(temp)) == (successor.getPathCost() + problem.h(successor))){
								temp = successor;
							}
						}

						tempHold.add(temp);
					}

					for(Node putBack : tempHold){
						frontier.add(putBack);
					}
				}

			}

		}

		return null;
	}

	// Main
	public static void main(String[] args) {
		//store command line args
		String city = args[0];
		String searchMethod = args[1];
		String startState = args[2];
		String goalState = args[3];

		//if distance inputted
		if (args.length == 5) {
			//store distance
			double distance = Integer.parseInt(args[4]);
		} else {
			double distance = 0;
		}			

		// testing prob #1 - heres how these work, check .csv's if confused - REMOVE BEFORE SUBMISSION
		SubwayNavigationProblem snp = new SubwayNavigationProblem(new State("Airport"), new State("Maverick"),"boston");
		System.out.println(snp.pathCost(0, new State("Airport"), new Action("Blue"), new State("Maverick")));
		for (Tuple testTuple : snp.successor(new State("Maverick")))
			System.out.println(testTuple.getAction() + "   " + testTuple.getState());

		// System.out.println(args[0]);

		// problem #3: Breadth First Search
		// if (args[1].equals("bfs")){
		// SubwayNavigationProblem bfs = new SubwayNavigationProblem(new State
		// (args[2]), new State(args[3]), args[0]);
		// Node solution = breadthFirstSearch(bfs);
		// System.out.println("Path:" + solution.path());
		// System.out.println("Path Cost:" + solution.getPathCost());
		// System.out.println("# Visited:" + solution.getDepth());
		// }
		// sample

		long currentTime = System.currentTimeMillis();

		SubwayNavigationProblem bfs = new SubwayNavigationProblem(new State("Fenway"), new State("South Station"),
				"boston");
		Node solution = breadthFirstSearch(bfs);
		System.out.println("Path:" + solution.path());
		System.out.println("Path Cost:" + solution.getPathCost());
		System.out.println("# Visited:" + solution.getDepth());
		
		//problem #5 A* Search
		SubwayNavigationProblem astar = new SubwayNavigationProblem(new State("Fenway"), new State("South Station"), "boston");
		Node answer = aStarSearch(astar);

		double heuristicPathCost = answer.getPathCost();
		for(Node successor : answer.path()){
			heuristicPathCost += successor.getPathCost();
		}
		
		System.out.println("Path:" + answer.path());
		System.out.println("Path Cost:" + answer.getPathCost());
		System.out.println("Heuristic Path Cost:" + heuristicPathCost);
		System.out.println("# Visited:" + answer.getDepth());

		

		

		System.out.println("Time taken: " + (System.currentTimeMillis() - currentTime) + "ms");
		System.out.println();

		currentTime = System.currentTimeMillis();

		// Depth first search
		SubwayNavigationProblem dfs = new SubwayNavigationProblem(new State("Fenway"), new State("South Station"),
				"boston");
		Node solution2 = depthFirstSearch(dfs);
		System.out.println("Path:" + solution2.path());
		System.out.println("Path Cost:" + solution2.getPathCost());
		System.out.println("# Visited:" + solution2.getDepth());

		System.out.println("Time taken: " + (System.currentTimeMillis() - currentTime) + "ms");

	}
}