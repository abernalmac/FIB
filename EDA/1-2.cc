#include <iostream>
#include <string>
#include <map>
using namespace std;

int main() {
    string name, action;
    map<string, int> m;
    while (cin >> name >> action) {
        if (action == "enters") {
            if (m.find(name) != m.end())
                cout << name << " is already in the casino" << endl;
            else
                m.insert({name,0});
        }
        else if (action == "wins") {
            int prize;
            cin >> prize;
            auto it = m.find(name);
            if ((it) == m.end())
                cout << name << " is not in the casino" << endl;
            else
            it->second += prize;
            
        }
        else if (action == "leaves") {
            auto it = m.find(name);
            if (it == m.end())
                cout << name << " is not in the casino" << endl;
            else {
                cout << name << " has won " << it->second << endl;
                m.erase(it);
            }
        }
    }
    cout << "----------" << endl;
    for (auto it = m.begin(); it != m.end(); ++it) {
        cout << it->first << " is winning " << it->second << endl;
    }
        
}
