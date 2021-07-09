#include <iostream>
#include <vector>
#include <list>
#include <stack>
#include <queue>
using namespace std;

typedef vector<vector<int>> graph;

void topological_sort(graph &G, vector<int> &v) {
    int n = G.size(); 
    priority_queue<int, vector<int>, greater<int>> s;
    for (int i = 0; i < n; ++i) 
        if (v[i] == 0) s.push(i);
    list<int> l;
    while (not s.empty()) {
        int u = s.top();
        s.pop();
        l.push_back(u);
        int m = G[u].size();
        for (int i = 0; i < m; ++i) {
            int x = G[u][i];
            if (--v[x] == 0) s.push(x);
        }
    }
    if (not l.empty()) {
            cout << l.front(); 
            l.pop_front();
    }
    while (not l.empty()) {
        cout << ' ' << l.front();
        l.pop_front();
    }
}


int main() {
    int n, m;
    while (cin >> n >> m) {
        graph G(n);
        vector<int> ve(n,0);
        for (int i = 0; i < m; i++) {
            int u, v;
            cin >> u >> v;
            G[u].push_back(v);
            ve[v]++;
        }
        topological_sort(G,ve);
        cout << endl;
    }    
}
