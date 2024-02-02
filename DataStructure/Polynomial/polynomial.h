#include <iostream>
#include <fstream>
#include <sstream>
#include <vector>

using namespace std;

class Polynomial
{
public:
	vector<int>v;
	vector<int>temp;

	void LoadPolynomial(std::string& filename);   // 인자로 받은 파일명으로 파일에 있는 다항식을 읽어서 멤버 변수에 저장
	
	void PrintPolynomial();    // 다항식을 지수의 내림차순으로 출력
	
	void add(Polynomial a, Polynomial b);
};




