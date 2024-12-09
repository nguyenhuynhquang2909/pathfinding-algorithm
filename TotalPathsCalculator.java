public class TotalPathsCalculator {

    static final int GRID_SIZE = 8; // Size of the grid
    static final int PATH_LENGTH = GRID_SIZE * GRID_SIZE - 1; // Total path length (63)
    static int totalPaths = 0; // Total number of valid paths

    public static void main(String[] args) {
        // Test input string
        // String inputPath = "DDDDDDRUUUUUURDDDDDDRUUUUUURDDDDDDRUUUUUURRDLDRDLDRDLDRDLLLLLLL";
        String inputPath = "***************************************************************";
        // Input validation
        if (!isValidInput(inputPath)) {
            System.out.println("Invalid input. Please enter exactly 63 characters of D, U, L, R, *.");
            return;
        }

        long startTime = System.currentTimeMillis(); // Measure start time

        // Pathfinding logic
        boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
        visited[0][0] = true; // Mark the start position as visited
        explorePaths(inputPath, 0, 0, 0, visited);

        long endTime = System.currentTimeMillis(); // Measure end time

        // Output results
        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    /**
     * Recursive method to explore all possible paths
     * @param path Input path string
     * @param index Current index in the path string
     * @param row Current row in the grid
     * @param col Current column in the grid
     * @param visited 2D array to track visited cells
     */
    private static void explorePaths(String path, int index, int row, int col, boolean[][] visited) {
        // Termination condition: when all cells are visited and the path ends
        if (index == PATH_LENGTH) {
            if (row == GRID_SIZE - 1 && col == 0) { // Check if it ends at the bottom-left corner
                totalPaths++;
            }
            return;
        }

        // Get the current direction from the path
        char direction = path.charAt(index);
        int[][] moves = {{1, 0}, {-1, 0}, {0, -1}, {0, 1}}; // D, U, L, R
        char[] dirs = {'D', 'U', 'L', 'R'};

        // If direction is '*', explore all directions; otherwise, explore the specific direction
        for (int i = 0; i < moves.length; i++) {
            if (direction != '*' && direction != dirs[i]) continue;

            int newRow = row + moves[i][0];
            int newCol = col + moves[i][1];

            // Check if the move is valid
            if (isValid(newRow, newCol, visited)) {
                visited[newRow][newCol] = true; // Mark the cell as visited
                explorePaths(path, index + 1, newRow, newCol, visited);
                visited[newRow][newCol] = false; // Backtrack
            }
        }
    }

    /**
     * Check if a move is valid
     * @param row Target row
     * @param col Target column
     * @param visited 2D array to track visited cells
     * @return True if the move is valid, false otherwise
     */
    private static boolean isValid(int row, int col, boolean[][] visited) {
        // Valid if within the grid and the cell has not been visited
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE && !visited[row][col];
    }

    /**
     * Validate the input string
     * @param inputPath Input path string
     * @return True if the input is valid, false otherwise
     */
    private static boolean isValidInput(String inputPath) {
        // 1. Check if the input is null or empty
        if (inputPath == null || inputPath.isEmpty()) {
            return false;
        }

        // 2. Check if the input string length matches PATH_LENGTH
        if (inputPath.length() != PATH_LENGTH) {
            return false;
        }

        // 3. Check if the input string contains only valid characters
        for (char c : inputPath.toCharArray()) {
            if (c != 'D' && c != 'U' && c != 'L' && c != 'R' && c != '*') {
                return false;
            }
        }

        return true; // Return true if all conditions are satisfied
    }
}