#include <iostream>
#include <climits>
#include <vector> 
#include <queue>
#include <stack>
using namespace std;

typedef pair<int,int> pedge; //first -> distance | second -> vertice
typedef vector<vector<pedge>> pgraph;


vector<int> dijkstra (const pgraph &PG, int x, int y, int &d) {
    int n = PG.size();
    vector<int> prev(n,-1);
    vector<int> dist(n, INT_MAX);
    dist[x] = 0;
    vector<bool> vtd(n,false);
    priority_queue<pedge,vector<pedge>,greater<pedge>> Q;
    Q.push(pedge(0,x));
    while (not Q.empty()) {
        int current = Q.top().second;
        Q.pop();
        if (not vtd[current]) {
            vtd[current] = true;
            for (int i = 0; i < PG[current].size(); ++i) {
                int d = PG[current][i].first;
                int v = PG[current][i].second;
                if (dist[v] > dist[current] + d) {
                    dist[v] = dist[current] + d;
                    Q.push(pedge(dist[v],v));
                    prev[v] = current;
                }
            }
        }
    }
    d = dist[y];
    return prev;
}

int main() {
    int n, m; 
    while (cin >> n >> m) {
        pgraph PG(n);
        int u, a, d;
        for (int i = 0; i < m; ++i) {
            cin >> u >> a >> d;
            PG[u].push_back(pedge(d,a));
        }
        int x, y;
        cin >> x >> y;
        vector<int> prev;
        int v = y;
        prev = dijkstra(PG,x,y,d);
        if (d == INT_MAX) cout << "no path from " << x << " to " << y;
        else {
            stack<int> res;
            while (prev[v] != -1) {
                res.push(v);
                v = prev[v];
            }
            res.push(v);
            cout << res.top(); res.pop();
            while (!res.empty()) {
                cout << " " << res.top();
                res.pop();
            }
        }
        cout << endl;
    }
}
