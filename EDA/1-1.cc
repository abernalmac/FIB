#include <iostream>
#include <map>
using namespace std;

int main() {
    int x;
    map<int,int> m;
    char c;
    while (cin >> c) {
        if (c == 'S') {
            cin >> x;
            auto it = m.find(x);
            if (it != m.end()) it->second++;
            else m.insert({x,1});
        }
        else if (c == 'A') {
            if (m.empty()) cout << "error!" << endl;
            else {
                auto it = m.end();
                --it;
                cout << it->first << endl;
                }
        }
        else if (c == 'R') {
            if (m.empty()) cout << "error!" << endl;
            else {
                auto it = m.end();
                --it;
                if (it->second != 1) it->second--;
                else m.erase(it);
            }
        }
        else if (c == 'I') {
            cin >> x;
            if (m.empty()) cout << "error!" << endl;
            else {
                auto it1 = m.end();
                --it1;
                int a = it1->first + x;
                if (it1->second != 1) it1->second--;
                else m.erase(it1);
                auto it2 = m.find(a);
                if (it2 != m.end()) it2->second++;
                else m.insert({a,1});
            }
        }
        else if (c == 'D') {
            cin >> x;
            if (m.empty()) cout << "error!" << endl;
            else {
                auto it1 = m.end();
                --it1;
                int a = it1->first - x;
                if (it1->second != 1) it1->second--;
                else m.erase(it1);
                auto it2 = m.find(a);
                if (it2 != m.end()) it2->second++;
                else m.insert({a,1});
            }
        }
    }
}
