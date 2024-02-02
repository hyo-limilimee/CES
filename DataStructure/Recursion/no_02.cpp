#include <iostream>
#pragma warning(disable: 4996)
using namespace std;

int Fibo(int);

int main()
{
	int n;
	scanf("%d", &n);
	cout << ("%d", Fibo(n)) << endl;
}

int Fibo(int n)
{
	if (n == 1)
		return 1;
	else if (n == 2)
		return 1;
	else
		return Fibo(n - 1) + Fibo(n - 2);

}