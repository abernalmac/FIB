#include <iostream>
#include <vector>
#include <climits>
#include <queue>
using namespace std;

typedef pair<int,int> pedge;
typedef vector<vector<pedge>> pgraph;

int dijkstra (const pgraph &PG, int u, int y) {
    int n = PG.size();
    vector<int> dist(n,INT_MAX); 
    dist[u] = 0;
    vector<int> vtd(n,false);
    priority_queue<pedge,vector<pedge>,greater<pedge>> Q;
    Q.push({0,u});
    while (not Q.empty()) {
        int current = Q.top().second;
        Q.pop();
        if (not vtd[current]) {
            vtd[current] = true;
            for (int i = 0; i < PG[current].size(); ++i) {
                int v = PG[current][i].second;
                int c = PG[current][i].first;
                if (dist[v] > dist[current] + c) {
                    dist[v] = dist[current] + c;     
                    Q.push(pedge(dist[v],v));
                }
            }
        }
    }
    return dist[y];
}


int main() {
    int n, m;
    while (cin >> n >> m) {
        pgraph PG(n);
        int u, v, d;
        for (int i = 0; i < m; ++i) {
            cin >> u >> v >> d;
            PG[u].push_back(make_pair(d,v));
        }
        int x, y;
        cin >> x >> y;
        int res = dijkstra(PG,x,y);
        if (res == INT_MAX) cout << "no path from " << x << " to " << y << endl;
        else cout << res << endl;
    }
}
