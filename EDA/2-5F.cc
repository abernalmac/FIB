#include <iostream>
#include <vector>
using namespace std;


int main() {
    int n, m;
    int count = 0;
    while (cin >> n) {
        ++count;
        cout << "Sequence #" << count << endl;
        vector<int> v(n);
        for (int i = 1; i <= n; ++i) cin >> v[i];
        cin >> m;
        int a_i;
        bool b;
        for (int i = 0; i < m; ++i) {
            cin >> a_i;
            int test;
            b = false;
            for (int j = 1; j <= n and not b; ++j) {
                test=a_i+v[j];
                if (test == j) {
                    b = true;
                    cout << "fixed point for " << a_i << ": " << j << endl;
                }
            }
            if (not b) cout << "no fixed point for " << a_i << endl;
        }
    cout << endl;     
    }
}
