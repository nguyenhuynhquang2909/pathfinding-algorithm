import java.util.Scanner;

public class GridPathSolver1 {

    static int totalPaths = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the 63-character path:");
        String path = scanner.nextLine();  // User input for the path
        scanner.close();

        int gridSize = 8;  // 8x8 grid

        // Initialize visited grid
        boolean[][] visited = new boolean[gridSize][gridSize];
        visited[0][0] = true; // Start point

        // Start backtracking
        long startTime = System.currentTimeMillis();
        backtrack(0, 0, visited, gridSize, path, 0);
        long endTime = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    static void backtrack(int row, int col, boolean[][] visited, int gridSize, String path, int step) {
        // Base case: If we reach the end of the path and all cells are visited
        if (step == path.length()) {
            if (allCellsVisited(visited)) {
                totalPaths++;
            }
            return;
        }

        char direction = path.charAt(step);
        int[] dRow = {1, -1, 0, 0}; // Down, Up, Right, Left
        int[] dCol = {0, 0, 1, -1};
        char[] directions = {'D', 'U', 'R', 'L'};

        // Explore all directions if wildcard (*) is present
        for (int i = 0; i < 4; i++) {
            if (direction == '*' || direction == directions[i]) {
                int newRow = row + dRow[i];
                int newCol = col + dCol[i];

                if (isValid(newRow, newCol, visited, gridSize)) {
                    visited[newRow][newCol] = true; // Mark as visited
                    backtrack(newRow, newCol, visited, gridSize, path, step + 1);
                    visited[newRow][newCol] = false; // Backtrack
                }
            }
        }
    }

    static boolean isValid(int row, int col, boolean[][] visited, int gridSize) {
        // Check if the new position is within bounds and not visited
        return row >= 0 && row < gridSize && col >= 0 && col < gridSize && !visited[row][col];
    }

    static boolean allCellsVisited(boolean[][] visited) {
        // Check if all cells in the grid have been visited
        for (boolean[] row : visited) {
            for (boolean cell : row) {
                if (!cell) {
                    return false;
                }
            }
        }
        return true;
    }
}
