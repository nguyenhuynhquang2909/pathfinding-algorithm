public class PathFinder {

    private static final int SIZE = 8; // Grid size is 8x8
    private static boolean[][] visited;
    private static int totalPaths = 0;
    
    private static final int[] ROW_DIRS = {-1, 1, 0, 0}; // U, D, L, R
    private static final int[] COL_DIRS = {0, 0, -1, 1}; // U, D, L, R

    public static void main(String[] args) {
        String input = "***************************************************************"; // Example input
        long startTime = System.currentTimeMillis();

        visited = new boolean[SIZE][SIZE];
        explorePaths(0, 0, input, 0);

        long endTime = System.currentTimeMillis();
        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (endTime - startTime));
    }

    // Explore paths starting from a given position (r, c) for the given path sequence.
    public static void explorePaths(int r, int c, String path, int index) {
        // Base case: If we have completed the path and all cells are visited
        if (index == path.length()) {
            if (r == 7 && c == 0) { // End at (7, 0)
                totalPaths++;
            }
            return;
        }

        // Mark this cell as visited
        visited[r][c] = true;

        char currentMove = path.charAt(index);
        if (currentMove == '*') {
            // Try all four possible directions for '*'
            for (int i = 0; i < 4; i++) {
                int newR = r + ROW_DIRS[i];
                int newC = c + COL_DIRS[i];
                if (isValidMove(newR, newC)) {
                    explorePaths(newR, newC, path, index + 1);
                }
            }
        } else {
            // Apply the current move direction
            int directionIndex = directionIndex(currentMove);
            int newR = r + ROW_DIRS[directionIndex];
            int newC = c + COL_DIRS[directionIndex];
            if (isValidMove(newR, newC)) {
                explorePaths(newR, newC, path, index + 1);
            }
        }

        // Backtrack: Unmark this cell as visited
        visited[r][c] = false;
    }

    // Checks if the move is valid (within bounds and not visited)
    public static boolean isValidMove(int r, int c) {
        return r >= 0 && r < SIZE && c >= 0 && c < SIZE && !visited[r][c];
    }

    // Helper method to map directions to indices
    public static int directionIndex(char direction) {
        switch (direction) {
            case 'U': return 0;
            case 'D': return 1;
            case 'L': return 2;
            case 'R': return 3;
            default: throw new IllegalArgumentException("Invalid direction: " + direction);
        }
    }
}
