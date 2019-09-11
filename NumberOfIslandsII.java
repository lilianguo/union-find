import com.sun.xml.internal.ws.dump.LoggingDumpTube.Position;

class NumberOfIslandsII {
    // 305 
    // union find, 变相 merge island
    /*
    A 2d grid map of m rows and n columns is initially filled with water. 
    We may perform an addLand operation which turns the water at position (row, col) into a land. 
    Given a list of positions to operate, count the number of islands after each addLand operation. 
    An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. 
    You may assume all four edges of the grid are all surrounded by water.
    */
    
    // add path compression in find, updating the father for each node for faster find next time
    // deleted the print 
    // time ok this time
    /*
    Time complexity : O(m×n+L) where LL is the number of operations, m is the number of rows and n is the number of columns. 
    it takes O(m×n) to initialize UnionFind, and O(L) to process positions. 
    Note that Union operation takes essentially constant time[1] when UnionFind is implemented with both path compression and union by rank.

    Space complexity : O(m×n) as required by UnionFind data structure.
    */
    public List<Integer> numIslands2(int m, int n, int[][] positions) {
        List<Integer> res = new ArrayList<>();
        if (positions == null || positions.length == 0) {
            return res;
        }
        UnionFind uf = new UnionFind(m * n);
        boolean[][] isIsland = new boolean[m][n];
        int[] dx = new int[] {1, 0, -1, 0};
        int[] dy = new int[] {0, 1, 0, -1};
        for (int i = 0; i < positions.length; i++) { 
            int x = positions[i][0];
            int y = positions[i][1];
            if (isIsland[x][y]) {
                res.add(uf.count);
                continue;
            }

            uf.count++;
            isIsland[x][y] = true;
            for (int dir = 0; dir < 4; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if (inBound(nx, ny, isIsland) && isIsland[nx][ny]) {
                    uf.union(nx * n +  ny, x * n + y);
                }
            }
            res.add(uf.count);
        }
        return res;
    }

    private boolean inBound(int x, int y, boolean[][] grid) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length;
    }
    
}

class UnionFind {
    int[] father;
    int count;
    public UnionFind(int size) {
        father = new int[size];
        count = 0;
        for (int i = 0; i < size; i++) {
            father[i] = i;
        }
    }

    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            father[rootA] = rootB;
            count--; 
        }
    }

    public int find(int a) {
        if (father[a] == a) {
            return a;
        }
        int ancestor = father[a];
        while(ancestor != father[ancestor]) {
            ancestor = father[ancestor];
        }

        // compress pass
        int fa = a;
        while(fa != father[fa]) {
            int tmp = father[a];
            father[a] = ancestor;
            fa = tmp;
        }
        return ancestor;
    }
}