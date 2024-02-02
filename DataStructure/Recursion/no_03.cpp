#include<iostream>
#pragma warning(disable: 4996)
using namespace std;

void Hanoi(int n, char from, char by, char to);

int main()
{
	int n;
	char from;
	char by;
	char to;

	scanf("%d %c %c %c", &n, &from, &by, &to);
}

void Hanoi(int n, char from, char by, char to)
{

	cout << from << "=>" << to << endl;
}
