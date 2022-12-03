package org.example;

import java.util.Scanner;

public class NQueensImprovementsApplication {

    // Number of Queens to be placed on an N*N Chess Board
    static int N;

    // counter to store information indicating the number of times the algorithm backtracked.
    static long backtrackingCount = 0;

    // rows array to store information indicating which row indices the Queen is placed for a column.
    static Boolean [] rows;

    // downwardDiagonals array to store information indicating if Queen is placed in indices row+col.
    static Boolean []downwardDiagonals;

    // upwardDiagonals array to store information indicating if Queen is placed in indices row-col+N-1.
    static Boolean []upwardDiagonals;

    // Main method
    public static void main(String[] args) {
        System.out.println("\n Improved Backtracking Algorithm for N Queens Problem :");
        System.out.println("---------------------------------------------------");

        // Taking dynamic input of number of Queens from the user
        Scanner input = new Scanner(System.in);
        System.out.print("\nEnter the value of number of Queens: ");
        N = input.nextInt();
        System.out.println("\n"+ N +" Queens are placed on "+ N +"*"+ N +" chess board with no clashes as below:\n");

        // Closing the scanner object
        input.close();

        // Start-time of the N-queens backtracking implementation
        long startTime = System.currentTimeMillis();

        downwardDiagonals = new Boolean[2*N-1];
        upwardDiagonals = new Boolean[2*N-1];
        rows = new Boolean[N];

        // Calling N-Queens Implementation method
        int[][] FinalBoard = solveNQueens();

        // End-time of the N-queens backtracking implementation
        long endTime   = System.currentTimeMillis();

        // Total time taken to execute this implementation
        long totalTime = endTime - startTime;

        // Call display function to print the solution board
         displayFinalSolution(FinalBoard);

        System.out.println("\nTotal time taken:  " + totalTime + " milliseconds");
        System.out.println("Number of times backtracking occurred: "+ backtrackingCount + "\n");
    }


    /* A method that initiates the N Queen problem using backtracking along with improvements.*/
    static int[][] solveNQueens()
    {
        // Initialize an N*N matrix to mimic the N*N ChessBoard
        int[][] board = new int[N][N];

        if (!NQueenHelper(board, 0))
        {
            System.out.print("There is not solution for the give N value");
            return null;
        }
        return board;
    }


    /* A method to solve N Queen problem recursively */
    static boolean NQueenHelper(int[][] board, int column)
    {
        // When all queens are placed then can stop execution.
        if (column >= N)
            return true;

        // Consider this column and try placing this queen in all rows one by one
        int rowValue = 0;
        while(rowValue!=N)
        {
            if(isPossibleToPlaceQueen(board, rowValue, column))
            {
                // Check for next column now
                if (NQueenHelper(board, column + 1))
                    return true;

                // Placing queen in board[i][column] lead to a clash,then need to remove queen and backtrack.
                board[rowValue][column] = 0;
                //Update the three arrays with this new information too.
                downwardDiagonals[rowValue - column + N - 1] = upwardDiagonals[rowValue + column] = rows[rowValue] = Boolean.FALSE;
                backtrackingCount= backtrackingCount+1;
            }
            rowValue= rowValue+1;
        }
        // Return False if the queen cannot be placed in any row in this column with indices row & col.
        return false;
    }

    /* A method to check if the queen can be placed in any cells of each row for given column i.e. on board [x][column]
    where x is row indices. To do this, we can check upwardDiagonals[row + column] and
    downwardDiagonals[row - column + N - 1], along with columns[row].
    */
    static boolean isPossibleToPlaceQueen(int[][] board, int row, int column)
    {
        if ((downwardDiagonals[row - column + N - 1] != Boolean.TRUE &&
                    upwardDiagonals[row + column] != Boolean.TRUE) && rows[row] != Boolean.TRUE) {
                // If no clashes found, place the queen in board[row][column]
                board[row][column] = 1;
                // Update three arrays with information of this new queens position.
                downwardDiagonals[row - column + N - 1] = upwardDiagonals[row + column] = rows[row] = Boolean.TRUE;
                return true;
            }
        return false;
    }

    /* A method to display final solution of n*n matrix (chessboard) with queens represented as 1 and remaining empty
    cells with 0 */
    static void displayFinalSolution(int[][] board)
    {
        for (int x = 0; x < N; x++)
        {
            for (int y = 0; y < N; y++)
                System.out.printf(" %d ", board[x][y]);
            System.out.print("\n");
        }
    }
}
