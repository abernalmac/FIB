#include <iostream>
#include <vector>
#include <climits>
#include <queue>
using namespace std;

typedef vector<vector<char>> graph;

vector<pair<int,int>> directions = {{1,0},{-1,0},{0,1},{0,-1}};

int bfs(graph &m, int f, int c) {
    vector<vector<int>> dist(m.size(), vector<int>(m[0].size(),INT_MAX));
    dist[f][c] = 0;
    queue<pair<int,int>> q;
    q.push({f,c});
    int max_dist = 0;
    while (not q.empty()) {
        int u_i = q.front().first;
        int u_j = q.front().second;
        if (m[u_i][u_j] == 't') 
            if (max_dist < dist[u_i][u_j]) max_dist = dist[u_i][u_j];
        q.pop();
        for (auto d : directions) {
            int v_i = u_i + d.first;
            int v_j = u_j + d.second;
            if (m[v_i][v_j] != 'X' and dist[v_i][v_j] == INT_MAX) {
                q.push({v_i,v_j});
                dist[v_i][v_j] = dist[u_i][u_j] + 1;
            }
        }
    }
    return max_dist;
}

int main() {
    int n, m;
    cin >> n >> m;
    graph ma(n+2, vector<char>(m+2, 'X'));
    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= m; ++j)
            cin >> ma[i][j];
    }
    int f, c;
    cin >> f >> c;
    int test = bfs(ma, f, c);
    if (test) cout << "maximum distance: " << test << endl;
    else cout << "no treasure can be reached" << endl;
}
