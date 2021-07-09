#include <iostream>
#include <set> 
using namespace std;

int main() {
    string s; 
    int n;
    multiset<int> bag;
    cout.setf(ios::fixed);
	cout.precision(4);
    int sum = 0; 
    while (cin >> s) {
        if (s == "number") {
            cin >> n;
            bag.insert(n);
            sum += n;
        }
        else if (s == "delete" and not bag.empty()) {
            auto it = bag.begin();
            sum -= *it;
            bag.erase(it);
        }
        if (bag.empty()) cout << "no elements" << endl;
        else {
            auto it = bag.begin();
            cout << "minimum: " << *it << ", maximum: ";
            it = bag.end();
            --it;
            cout << *it << ", average: ";
            int sz = bag.size();
            double avg = double(sum)/double(sz);
            cout.precision(4);
            cout << avg << endl;    
        }            
    }
    
    
}
