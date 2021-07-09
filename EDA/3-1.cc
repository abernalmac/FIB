#include <iostream>
#include <vector>
using namespace std;

typedef vector<vector<char>> mapa;

bool dfs(mapa &M, int x, int y) {
    if (M[x][y] == 'X') return false;
    if (M[x][y] == 't') return true;
    M[x][y] = 'X';
    return dfs(M,x+1,y) or dfs(M,x-1,y) or dfs(M,x,y+1) or dfs(M,x,y-1);
}

int main() {
    int n, m;
    cin >> n >> m;
    mapa M(n+2,vector<char>(m+2,'X'));
    char c;
    for (int i = 1; i <= n; ++i) {
        for (int j = 1; j <= m; ++j) {
            cin >> M[i][j];
        }
    }
    int x, y;
    cin >> x >> y; 
    if (dfs(M,x,y)) cout << "yes" << endl;
    else cout << "no" << endl;
}
