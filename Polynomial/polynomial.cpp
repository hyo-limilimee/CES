#include "polynomial.h"

using namespace std;

void Polynomial::LoadPolynomial(std::string& filename)
{
    string temp;
    int temp1;

    ifstream readFile;
    readFile.open(filename);

    if (readFile.is_open())
    {
        while (!readFile.eof())
        {
            getline(readFile, temp);
            stringstream stream1(temp);

            while (stream1 >> temp1)
            {
                v.push_back(temp1);
            }

        }
      
    }
}

void Polynomial::PrintPolynomial()//라이브러리의 정렬을 사용하여 코드 작성
{
   
    int k = 1;

    int temp1, temp2;

    for (int i = 1; i < temp.size(); i += 2)
    {
        for (k = i+2; k < temp.size(); k += 2) {
            if (temp[k] > temp[i])
            {
                temp1 = temp[k];
                temp[k] = temp[i];
                temp[i] = temp1;

                temp2 = temp[k - 1];
                temp[k - 1] = temp[i - 1];
                temp[i - 1] = temp2;

            }

        }

    }
    for (int m = 1; m < temp.size(); m += 2)
    {
        cout << temp[m - 1] << " " << temp[m] << endl;

    }
}




void Polynomial::add(Polynomial a, Polynomial b)
{
 
    int i;
    int j;
    int k;
    int cnt = 0;

    for (int i = 2; i < a.v.size(); i += 2)
    {
        int cnt = 0;

        for (int j = 2; j < b.v.size(); j += 2)
        {
            if (a.v[i] == b.v[j])
            {
                k = a.v[i - 1] + b.v[j - 1];
                cnt++;

                if (k != 0)
                {
                    temp.push_back(k);
                    temp.push_back(a.v[i]);

                }
            }
        }
        if (cnt == 0)
        {
            temp.push_back(a.v[i - 1]);
            temp.push_back(a.v[i]);
        }
    }

    for (int i = 2; i < b.v.size(); i += 2)
    {
        int cnt = 0;

        for (int j = 2; j < a.v.size(); j += 2)
        {
            if (b.v[i] == a.v[j])
            {
                cnt++;
            }
        }

        if (cnt == 0)
        {
            temp.push_back(b.v[i - 1]);
            temp.push_back(b.v[i]);

        }

    }

   // cout << "배열출력 " << endl;

    //for (int x = 0; x < temp.size(); x++)
    //{

        //cout << temp[x] << " ";

   // }
    //정상출력
}
    
