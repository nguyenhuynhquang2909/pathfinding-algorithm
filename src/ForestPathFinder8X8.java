import java.util.*;

public class ForestPathFinder8X8 {
    private static final int N = 8;
    private static final int MOVES = 63;
    private static final int[] DR = {-1, 1, 0, 0};
    private static final int[] DC = {0, 0, -1, 1};
    private static final int TARGET_CELL = 56; // cell index of (7,0)
    private static final int[][] nextPos = new int[64][4];
    private static final int[] DIR_MAP = new int[128];
    private static final char[] pathArr = new char[MOVES];
    private static final int[] distToTarget = new int[64];

    static {
        // Map directions from characters to indices
        Arrays.fill(DIR_MAP, -1);
        DIR_MAP['U'] = 0; DIR_MAP['D'] = 1; DIR_MAP['L'] = 2; DIR_MAP['R'] = 3;

        // Precompute next positions for each cell and direction
        for (int r = 0; r < N; r++) {
            for (int c = 0; c < N; c++) {
                int cellIndex = r * N + c;
                for (int d = 0; d < 4; d++) {
                    int nr = r + DR[d], nc = c + DC[d];
                    if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
                        nextPos[cellIndex][d] = -1;
                    } else {
                        nextPos[cellIndex][d] = nr * N + nc;
                    }
                }
            }
        }

        // Precompute shortest distance from every cell to the target using BFS
        Arrays.fill(distToTarget, Integer.MAX_VALUE);
        distToTarget[TARGET_CELL] = 0;
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(TARGET_CELL);

        while (!q.isEmpty()) {
            int cur = q.poll();
            int cr = cur / N, cc = cur % N;
            for (int i = 0; i < 4; i++) {
                int nr = cr + DR[i], nc = cc + DC[i];
                if (nr >= 0 && nr < N && nc >= 0 && nc < N) {
                    int nxt = nr * N + nc;
                    if (distToTarget[nxt] > distToTarget[cur] + 1) {
                        distToTarget[nxt] = distToTarget[cur] + 1;
                        q.offer(nxt);
                    }
                }
            }
        }
    }

    // Validate that the input is exactly MOVES characters and contains only U, D, L, R, or *
    private static boolean isValidInput(String path) {
        if (path.length() != MOVES) return false;
        for (int i = 0; i < MOVES; i++) {
            char c = path.charAt(i);
            if (c != 'U' && c != 'D' && c != 'L' && c != 'R' && c != '*') return false;
        }
        return true;
    }

    private static long countPaths() {
        // Start from cell 0 (top-left corner), with only that cell visited
        return dfs(0, 0, 1L);
    }

    // DFS with pruning: step is current move index, cell is current cell index,
    // visited is a bitmask representing visited cells.
    private static long dfs(int step, int cell, long visited) {
        if (step == MOVES) {
            return (cell == TARGET_CELL) ? 1 : 0;
        }

        int remaining = MOVES - step;
        // Prune paths where it's impossible to reach the target in the remaining steps
        if (distToTarget[cell] > remaining) return 0;

        char dir = pathArr[step];
        if (dir == '*') {
            // When direction is '*', we may choose any direction.
            // Check reachability to ensure all remaining steps can be filled with unvisited cells.
            if (!isReachableAll(cell, visited, remaining)) return 0;

            long count = 0;
            for (int d = 0; d < 4; d++) {
                int nextCell = nextPos[cell][d];
                if (nextCell >= 0 && (visited & (1L << nextCell)) == 0) {
                    count += dfs(step + 1, nextCell, visited | (1L << nextCell));
                }
            }
            return count;
        } else {
            // Direction is fixed
            int d = DIR_MAP[dir];
            int nextCell = nextPos[cell][d];
            if (nextCell < 0) return 0; // out of bounds
            long mask = 1L << nextCell;
            if ((visited & mask) != 0) return 0; // already visited
            return dfs(step + 1, nextCell, visited | mask);
        }
    }

    // Check if all needed cells are reachable from the current cell using only unvisited cells.
    private static boolean isReachableAll(int cell, long visited, int remaining) {
        int needed = remaining + 1; // current cell + remaining steps
        boolean[] vis = new boolean[64];
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(cell);
        vis[cell] = true;
        int count = 1;

        while (!q.isEmpty()) {
            int cur = q.poll();
            for (int i = 0; i < 4; i++) {
                int nxt = nextPos[cur][i];
                if (nxt >= 0 && !vis[nxt] && ((visited & (1L << nxt)) == 0)) {
                    vis[nxt] = true;
                    q.offer(nxt);
                    count++;
                    if (count == needed) return true;
                }
            }
        }
        return count == needed;
    }

    public static void main(String[] args) {
        System.out.print("Enter your path (63 characters of U, D, L, R, or *): ");
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine().trim();
        sc.close();

        if (!isValidInput(input)) {
            System.out.println("Invalid input");
            return;
        }

        input.getChars(0, MOVES, pathArr, 0);

        long start = System.currentTimeMillis();
        long totalPaths = countPaths();
        long end = System.currentTimeMillis();

        System.out.println("Total paths: " + totalPaths);
        System.out.println("Time (ms): " + (end - start));
    }
}
