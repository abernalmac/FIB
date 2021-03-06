#include <iostream>
#include <vector>

using namespace std;

int seq_num = 1;

int fix_point(vector<int> &s, int a, int i, int j)
{
	if (i <= j) {
		int m = i + (j - i) / 2;
		if (s[m] == (m + 1) - a) {
			int alt = fix_point(s, a, i, m - 1);
			if (alt == -1) return m;
			else return alt;
		}
		if (s[m] > (m + 1) - a) return fix_point(s, a, i, m - 1);
		if (s[m] < (m + 1) - a) return fix_point(s, a, m + 1, j);
	}
	return -1;
}

int main() {
	int n;
	while (cin >> n)
	{
		cout << "Sequence #" << seq_num++ << endl;
		
		vector<int> s(n);
		for (int i = 0; i < n; i++)
			cin >> s[i];
		
		int m; cin >> m;
		for (int i = 0; i < m; i++)
		{
			int a; cin >> a;
			int fixed = fix_point(s, a, 0, n - 1);
			
			if (fixed < 0)
				cout << "no fixed point for " << a << endl;
			else
				cout << "fixed point for " << a << ": " << fixed + 1 << endl;
		}
		cout << endl;
	}
}
