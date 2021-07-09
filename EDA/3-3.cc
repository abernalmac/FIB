#include <iostream>
#include <vector>
using namespace std;

typedef vector<vector<int>> graph;

void cycles(const graph &G, vector<bool> &vtd, int i, int prev, bool &is_a_tree) {
    if (not vtd[i]) {
        vtd[i] = true;
        for (int j = 0; j < G[i].size(); ++j) {
            if (G[i][j] != prev) {
                cycles(G,vtd,G[i][j],i,is_a_tree);
                if (not is_a_tree) return;
            }
        }
    }
    else is_a_tree = false;
}

void dfs(const graph &G) {
    int n = G.size();
    vector<bool> vtd(n, false);
    int count = 0;
    for (int i = 0; i < n; ++i) {
        if (not vtd[i]) {
            bool is_a_tree = true;
            cycles(G,vtd,i,i,is_a_tree);
            if (not is_a_tree) {
                cout << "no" << endl;
                return;
            }
        else ++count;
        }
    }
    /*if (is_a_tree)*/ cout << count << endl;
}

int main() {
    int n, m;
    while (cin >> n >> m) {
        graph G(n);
        int u, v;
        for (int i = 0; i < m; ++i) {
            cin >> u >> v;
            G[u].push_back(v);
            G[v].push_back(u);
        }
        dfs(G);
    }
}
