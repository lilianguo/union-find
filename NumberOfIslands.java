class NumberOfIslands {
    // 200 
    /*
    Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. 
    An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
    You may assume all four edges of the grid are all surrounded by water.
    */
    // bfs traverse each point, mark all adjacent as 1 island per traverse
    // thus number of island  = number of bfs done
    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    markIsland(grid, i, j);
                }
            }
        }
        return count;
    }

    private void markIsland(char[][] grid, int x, int y) {
        int[] dx = new int[] {1, 0, -1, 0};
        int[] dy = new int[] {0, 1, 0, -1};
        for (int dir = 0; dir < 4; dir++) {
            int nx = x + dx[dir];
            int ny = y + dy[dir];
            if (inBound(nx, ny, grid) && grid[nx][ny] == '1') {
                grid[nx][ny] = '0';
                markIsland(grid, nx, ny); 
            }   
        }
        return;
    }
    
    private boolean inBound(int x, int y, char[][] grid) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
    }
}