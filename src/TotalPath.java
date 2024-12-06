public class TotalPath {
    private static final int GRID_SIZE = 3;
    private static int totalPaths = 0;

    public static void main(String[] args) {
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true;

        // Start the recursive pathfinding
        findPaths(0, 0, 1, visited);

        // Print the total number of paths
        System.out.println("Total possible paths: " + totalPaths);
    }
    private static void findPaths(int row, int col, int steps, boolean[][] visited) {
        // Base case: all 64 cells are visited
        if (steps == GRID_SIZE * GRID_SIZE ) {
            if (row == GRID_SIZE - 1 && col == 0) { // End at (7, 0)
                totalPaths++;
            }
            return;
        }

        // Define possible moves: {row offset, col offset}
        int[][] moves = {{1,0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] move: moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];

            // Check if the move is valid
            if (isValidMove(newRow, newCol, visited)) {
                // Mark the cell as visited
                visited[newRow][newCol] = true;

                // Recurse to the next step
                findPaths(newRow, newCol, steps + 1, visited);

                // Backtrack: unmarked the cell
                visited[newRow][newCol] = false;


            }
        }
    }
    private static boolean isValidMove(int row, int col, boolean[][] visited) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visited[row][col];
    }
}
