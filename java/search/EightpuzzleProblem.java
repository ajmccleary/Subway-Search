package search;

import java.util.ArrayList;

public class EightpuzzleProblem extends Problem {

    public EightpuzzleProblem(State initial, State goal) {
        super(initial, goal);
    }

    @Override
    public ArrayList<Tuple> successor(State state) {

        ArrayList<Tuple> successors = new ArrayList<>();
        int[][] board = stateToBoard(state);

        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                // if we found our empty square
                if (board[col][row] == 0) {
                    // check up direction
                    if (isValidPosition(row - 1, col)) {
                        char[] array = state.getName().toCharArray();

                        // swap the digit above with the 0
                        array[(row * 3) + col] = array[((row - 1) * 3) + col];
                        array[((row - 1) * 3) + col] = '0';

                        successors.add(new Tuple(new Action("Up"), new State(arrayToString(array))));
                    }

                    // check left direction
                    if (isValidPosition(row, col - 1)) {
                        char[] array = state.getName().toCharArray();

                        // swap the digit to the left with the 0
                        array[(row * 3) + col] = array[(row * 3) + (col - 1)];
                        array[(row * 3) + (col - 1)] = '0';

                        successors.add(new Tuple(new Action("Left"), new State(arrayToString(array))));
                    }

                    // check down direction
                    if (isValidPosition(row + 1, col)) {
                        char[] array = state.getName().toCharArray();

                        // swap the digit below with the 0
                        array[(row * 3) + col] = array[((row + 1) * 3) + col];
                        array[((row + 1) * 3) + col] = '0';

                        successors.add(new Tuple(new Action("Down"), new State(arrayToString(array))));
                    }

                    // check right direction
                    if (isValidPosition(row, col + 1)) {
                        char[] array = state.getName().toCharArray();

                        // swap the digit to the left with the 0
                        array[(row * 3) + col] = array[(row * 3) + (col + 1)];
                        array[(row * 3) + (col + 1)] = '0';

                        successors.add(new Tuple(new Action("Right"), new State(arrayToString(array))));
                    }
                }
            }
        }

        return successors;
    }

    private boolean isValidPosition(int newRow, int newCol) {
        return newRow >= 0 && newRow < 3 && newCol >= 0 && newCol < 3;
    }

    private static String arrayToString(char[] array) {

        StringBuilder sb = new StringBuilder();

        for (char c : array) {
            sb.append(c);
        }

        return sb.toString();
    }

    private static int[][] stateToBoard(State state) {
        String stateString = state.getName();
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            board[i % 3][i / 3] = stateString.charAt(i) - '0';
        }

        return board;
    }

    private static void printBoard(int[][] board) {
        for (int col = 0; col < board.length; col++) {
            for (int row = 0; row < board.length; row++) {
                System.out.print("[" + board[row][col] +"]");
            }

            System.out.println();
        }

        System.out.println();
    }

    @Override
    /**
	Return the heuristic function value for a particular node. Returns informed heuristic value.
	@param node A search node
	@return The heuristic value for the state in that search node.
	*/
    public double h(Node node) {
        double totalH = 0.0;

        String current = node.getState().getName();
        String goalString = this.goal.getName();

        for (int i = 0; i < current.length(); i++) {
            if (current.charAt(i) != '0') {
                int difference = Math.abs(goalString.indexOf(current.charAt(i)) - i);
                totalH += difference;
            }
        }

        return totalH;
    }
}
