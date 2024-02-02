#include <iostream>
#pragma warning(disable: 4996);
int GCD(int, int);

int main()
{
	int a, b;
	scanf("%d %d", &a, &b);
	printf("%d", GCD(a, b));

}

int GCD(int a, int b)
{
	int	r = a % b;

	while (b != 0)
	{
		r = a % b;
		a = b;
		b = r;
	}
	return a;

}