class FriendCirle {
    // 2 sigma 电面 https://www.1point3acres.com/bbs/forum.php?mod=viewthread&tid=496638&highlight=twosigma
    // https://www.cnblogs.com/EdwardLiu/p/6177825.html
    public int friendCirclesDFS (String[] friends) {
        if (friends == null || friends.length == 0) {
            return 0;
        }
        char[][] relations = createRelation(friends);
        int friendCircle = 0;
        for (int i = 0; i < friends.length; i++) {
            for (int j = 0; j < friends[0].length; j++) {
                if (relations[i][j] == 'Y') {
                    markFriend(relations, i, j);
                    friendCircle++;
                }
            }
        }
        return friendCircle;
    }

    private void markFriend(char[][] relations, int x, int y) {
        int m = relations.length;
        int n = relations[0].length;
        int[] dir_x = {1, 0, -1, 0};
        int[] dir_y = {0, 1, 0, -1};
        for (int i = 0; i < 4; i++) {
            int dx = x + dir_x[i];
            int dy = y + dir_y[i];
            if (dx >= 0 && dx < m && dy >= 0 && dy < n && relations[dx][dy] == 'Y') {
                relations[dx][dy] = 'N';
                markFriend(relations, dx, dy);
            }
        }
    }

    private char[][] createRelation(String[] friends) {
        int m = friends.length;
        int n = friends[0].length();
        char[][] relations = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                relations[i][j] = friends[i].charAt(j);
            }
        }
        return relations;
    }


    private friendCircleUF (int[][] M) {
        int[] parent = new int[M.length];
        Arrays.fill(parent, -1);
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M.length; j++) {
                if (M[i][j] == 1 && i != j) {
                    union(parent, i, j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < parent.length; i++) {
            if (parent[i] != -1) {
                count++;
            }
        }
        return count;
    }

    private void union (int[] parent, int x, int y) {
        int xSet = find(parent, x);
        int ySet = find(parent, y);
        if (xSet != ySet) {
            parent[xSet] = ySet;
        }
    }
    private int find(int[] parent, int i) {
        if (parent[i] == -1) {
            return i;
        }
        return find(parent, parent[i]);
    }
}