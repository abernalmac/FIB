#include <iostream>
#include <vector>
#include <queue>
using namespace std;

typedef pair<int,int> pedge;
typedef vector<vector<pedge>> pgraph;

int mst (const vector<vector<pedge>> &PG) {
    int n = PG.size();
    vector<bool> vtd(n, false);
    vtd[0] = true;
    priority_queue<pedge,vector<pedge>,greater<pedge>> pq;
    for (pedge x : PG[0]) pq.push(x);
    int size = 1;
    int sum = 0;
    while (size < n) {
        int c = pq.top().first;
        int x = pq.top().second;
        pq.pop();
        if(not vtd[x]) {
            vtd[x] = true;
            for (pedge y : PG[x]) pq.push(y);
            sum += c;
            ++size; 
        } 
    }
    return sum; 
}

int main() {
    int n, m;
    while (cin >> n >> m) {
        pgraph PG(n);
        int u, v, d;
        for (int i = 0; i < m; ++i) {
            cin >> u >> v >> d;
            PG[u-1].push_back(pedge(d,v-1));
            PG[v-1].push_back(pedge(d,u-1));
        }
        int min = mst(PG); 
        cout << min << endl;
    }    
}
