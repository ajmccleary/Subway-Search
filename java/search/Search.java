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
		frontier.add(initial);

		//if start(initial) node is goal node
		if (problem.goalTest(initial.getState())){
			return initial;
		}

		//initialize explored set
		HashSet<Node> explored = new HashSet<Node>();
		explored.add(initial);
		
		while(!frontier.isEmpty()){
			Node leaf = frontier.remove();
			explored.add(leaf);
		
			for(Node successor : leaf.expand(problem)){
				if(problem.goalTest(successor.getState())){
					return successor;
				} else {
					if(!frontier.contains(successor) && !explored.contains(successor)){
						frontier.add(successor);
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
		//Jessica Theodore Q4
		 PriorityQueue<Node> frontier = new PriorityQueue<>(Comparator.comparingDouble(Node::getPathCost));
        HashSet<State> explored = new HashSet<>();

        Node initial = new Node(problem.getInitial(), null, null, 0);
        frontier.add(initial);

        while (!frontier.isEmpty()) {
            Node node = frontier.poll();

            if (problem.goalTest(node.getState())) {
                return node;
            }

            explored.add(node.getState());

            for (Node successor : node.expand(problem)) {
                if (!explored.contains(successor.getState()) && !frontier.contains(successor)) {
                    frontier.add(successor);
                }
            }
        }
        return null;
    }

	// Informed (Heuristic) Search
	
	public static Node aStarSearch(Problem problem){
		//YOUR CODE HERE
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
		
		
		
	}
}