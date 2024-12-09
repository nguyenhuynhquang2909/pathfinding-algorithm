import java.util.Scanner;

public class OptimizedGridPathSolver {
    private static final int GRID_SIZE = 8;
    private static int totalPaths = 0;
    private static final int[] DX = {1, -1, 0, 0}; // Down, Up, Right, Left
    private static final int[] DY = {0, 0, 1, -1};
    private static final char[] DIRECTIONS = {'D', 'U', 'R', 'L'};

    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter the 63-character path:");
            String path = scanner.nextLine();

            boolean[][] visited = new boolean[GRID_SIZE][GRID_SIZE];
            visited[0][0] = true;

            long startTime = System.nanoTime();
            backtrack(0, 0, visited, path, 0, 1); // Track visited cells count
            long endTime = System.nanoTime();

            System.out.println("Total paths: " + totalPaths);
            System.out.printf("Time (ms): %.3f%n", (endTime - startTime) / 1_000_000.0);
        }
    }

    private static void backtrack(int x, int y, boolean[][] visited, String path, int step, int visitedCells) {
        // Early termination if remaining steps cannot visit all cells
        if (GRID_SIZE * GRID_SIZE - visitedCells > path.length() - step) {
            return;
        }

        // Base case: path completed and all cells visited
        if (step == path.length()) {
            if (visitedCells == GRID_SIZE * GRID_SIZE) {
                totalPaths++;
            }
            return;
        }

        char direction = path.charAt(step);

        for (int i = 0; i < 4; i++) {
            if (direction == '*' || direction == DIRECTIONS[i]) {
                int newX = x + DX[i];
                int newY = y + DY[i];

                if (isValidMove(newX, newY, visited)) {
                    visited[newX][newY] = true;
                    backtrack(newX, newY, visited, path, step + 1, visitedCells + 1);
                    visited[newX][newY] = false; // Backtrack
                }
            }
        }
    }

    private static boolean isValidMove(int x, int y, boolean[][] visited) {
        return x >= 0 && x < GRID_SIZE && 
               y >= 0 && y < GRID_SIZE && 
               !visited[x][y];
    }
}