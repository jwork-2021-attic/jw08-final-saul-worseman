package maze;

import java.util.Arrays;

class UnionFind{
    int[] set;
    public UnionFind(int n){
        this.set = new int[n];
        Arrays.fill(set, -1);
    }

    public int find(int x){
        if(set[x] < 0)
            return x;
        else{
            return set[x] = find (set[x]);
        }
    }

    public void unite(int a, int b){
        int x = find(a);
        int y = find(b);
        if(x == y)
            return;
        if(set[x] < set[y]){
            set[x] += set[y];
            set[y] = x;
        }
        else{
            set[y] += set[x];
            set[x] = y;
        }
    }

    public boolean connected(int a, int b){
        int x = find(a);
        int y = find(b);
        if(x == y)
            return true;
        else
            return false;
    }

    public int clusterSize(int i){
        int parent = find(i);
        return -set[parent];
    }
}
