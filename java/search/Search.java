package search;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
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
		frontier.add(initial);

		// if start(initial) node is goal node
		if (problem.goalTest(initial.getState())) {
			return initial;
		}

		// initialize explored set
		HashSet<State> explored = new HashSet<State>();
		explored.add(initial.getState());

		while (!frontier.isEmpty()) {
			Node leaf = frontier.remove();
			explored.add(leaf.getState());

			for (Node successor : leaf.expand(problem)) {
				if (problem.goalTest(successor.getState())) {
					System.out.println("Visited nodes: " + explored.size());
					return successor;
				} else {
					if (!frontier.contains(successor) && !explored.contains(successor.getState())) {
						frontier.add(successor);
					}
				}
			}
		}

		return null;
	}

	public static Node depthFirstSearch(Problem problem) {
		// YOUR CODE HERE
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

	public static Node uniformCostSearch(Problem problem) {
		// YOUR CODE HERE
		return null;
	}

	// Informed (Heuristic) Search

	public static Node aStarSearch(Problem problem) {
		// YOUR CODE HERE
		return null;
	}

	// Main
	public static void main(String[] args) {
		// Replace this code with code that runs the program specified by
		// the command arguments

		// testing prob #1 - heres how these work, check .csv's if confused
		SubwayNavigationProblem snp = new SubwayNavigationProblem(new State("Airport"), new State("Maverick"),
				"boston");
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