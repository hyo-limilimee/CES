#include <iostream>
using namespace std;

int BinarySearch(int* a, const int m, const int first, const int last);
int cnt = 0;

int main()
{
	int n;

	cin >> n;

	int* a = new int[n];

	for (int i = 0; i < n; i++)
	{
		cin >> a[i];
	}

	int m;

	cin >> m;


	//BinarySearch(a, m, 0, n);

	//printf("index number: %d\n", BinarySearch(a, m, 0, n));
	printf("%d\n", BinarySearch(a, m, 0, n-1));
	printf("%d\n", cnt);
	return 0;

}

int BinarySearch(int* a, const int m, const int first, const int last)
{
	//정렬된 배열 a[first]에서 a[last]에서 m 탐색

	
	//printf("zero count : %d\n", count);

	//강의자료 ppt 순환 이원 탐색 코드 참고하여 작성함

	if (first <= last) // 끝까지 탐색했는지 확인
	{
		int mid = (first + last) / 2;
		cnt++;

		if (m < a[mid])
		{
			//printf("under\n");
			return BinarySearch(a, m, first, mid - 1);
		}


		else if (m > a[mid])
		{
			//printf("over\n");
			return BinarySearch(a, m, mid + 1, last);
		}

		//printf("found\n");
		//printf("%d\n", count);
		return mid;
	}
	//printf("unfound\n");

	return -1;

}


