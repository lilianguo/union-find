import java.util.List;

/*
Input: 
[["John","johnsmith@mail.com","john_newyork@mail.com"],
["John","johnsmith@mail.com","john00@mail.com"],
["Mary","mary@mail.com"],
["John","johnnybravo@mail.com"]]

map.get(email) is 0
i = 0
email = johnsmith@mail.com
map.get(email) is 0
i = 0
email = john_newyork@mail.com
map.get(email) is 0
i = 1
email = johnsmith@mail.com
map.get(email) is 1
i = 1
email = john00@mail.com
map.get(email) is 2
i = 2
email = mary@mail.com
map.get(email) is 3
i = 3
email = johnnybravo@mail.com



Output: 
[["John", 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com'],  
["John", "johnnybravo@mail.com"], 
["Mary", "mary@mail.com"]]
Explanation: 
The first and third John's are the same person as they have the common email "johnsmith@mail.com".
The second John and Mary are different people as none of their email addresses are used by other accounts.
We could return these lists in any order, for example the answer [['Mary', 'mary@mail.com'], ['John', 'johnnybravo@mail.com'], 
['John', 'john00@mail.com', 'john_newyork@mail.com', 'johnsmith@mail.com']] would still be accepted.
*/

/*
Given a list accounts, each element accounts[i] is a list of strings, where the first element accounts[i][0] is a name, and the rest of the elements are emails representing emails of the account.

Now, we would like to merge these accounts. Two accounts definitely belong to the same person if there is some email that is common to both accounts. Note that even if two accounts have the same name, they may belong to different people as people could have the same name. A person can have any number of accounts initially, but all of their accounts definitely have the same name.

After merging the accounts, return the accounts in the following format: the first element of each account is the name, and the rest of the elements are emails in sorted order. The accounts themselves can be returned in any order.
*/
class UnionFind {
    private int[] father;
    
    public UnionFind(int size) {
        father = new int[size];
        for (int i = 0; i < size; i++) {
            father[i] = i;
        }
    }
    
    public int find(int a) {
        if (a == father[a]) {
            return a;
        }
        return father[a] = find(father[a]);
    }
    
    public void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if (rootA != rootB) {
            father[rootA] = rootB;
        }
    }
}

class AccountsMerge {
    public List<List<String>> accountsMerge(List<List<String>> accounts) {
        // write your code here
        List<List<String>> res = new ArrayList<>();
        if (accounts.size() == 0) {
            return res;
        }
        

        // union emails with same father
        UnionFind uf = new UnionFind(accounts.size());
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < accounts.size(); i++) {
            List<String> account = accounts.get(i);
            for (int j = 1; j < account.size(); j++) {
                String email = account.get(j);
                // this email address hasn't shown up before, current i is the
                // only father
                map.putIfAbsent(email, i);
                // map.get(email) could get the father of email 
                // and merge these two different fathers to 1 union
                uf.union(map.get(email), i);
            }
        }
        
        // group emails with same father into one integer
        HashMap<Integer, List<String>> indexEmail = new HashMap<>();
        for (String email : map.keySet()) {
            int root = uf.find(map.get(email));
            indexEmail.putIfAbsent(root, new ArrayList<>());
            indexEmail.get(root).add(email);
        }

        // construct result with sorted emails
        for (Integer index : indexEmail.keySet()) {
            List<String> account = new ArrayList<>();
            account.add(accounts.get(index).get(0));
            
            List<String> emails = indexEmail.get(index);
            Collections.sort(emails);
            
            account.addAll(emails);
            res.add(account);
        }
        
        return res;
    }
}