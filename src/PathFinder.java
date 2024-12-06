public class PathFinder {
    static final int GRID_SIZE = 8;
    static int totalPath = 0;

    public static void main(String[] args) {

    }
    private static void explorePaths(int row, int col, char[] directions, boolean[][] visited) {
        // Base case: reached the end cell after 63 moves
        if (row == GRID_SIZE) {
            if (row == GRID_SIZE - 1 && col == 0) {
                totalPath++;

            }
        }
    }
}
