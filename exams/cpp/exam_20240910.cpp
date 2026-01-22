#include <iostream>
#include <functional>
#include <vector>
#include <type_traits>

using namespace std;

template <class InputIterator, class OutputIterator>
void map(InputIterator from, InputIterator to, OutputIterator out, function<typename OutputIterator::value_type(typename InputIterator::value_type)> f)
{
    while (from != to)
        *out++ = f(*from++);
}

template <class A, class B>
vector<B> map(const vector<A>& v, function<B(A)> f)
{
    vector<B> r(v.size());
    map(v.begin(), v.end(), r.begin(), f);
    return r;
}

namespace cpp03 {

    template <class A, class B, class F>
    vector<B> map(const vector<A>& v, F f) 
    {
        vector<B> r(v.size());
        for (int i = 0; i < v.size(); ++i)
            r[i] = f(v[i]);
        return r;
    }

    class myfunction {
    public: 
        bool operator()(int n) { return n > 2; }
    };

    void test()
    {
        vector<int> v1{ 1, 2, 3, 4, 5 };
        vector<bool> v2 = map<int, bool>(v1, myfunction());
    }
}

template <class T>
ostream& operator<<(ostream& os, const vector<T>& v)
{
    os << "[ ";
    for (auto it = v.begin(); it != v.end(); ++it)
        os << *it << ", ";
    os << "\b\b ]";
    return os;
}

int main()
{
    vector<int> v1{ 1, 2, 3, 4, 5 };
    vector<bool> v2(v1.size());
    map(v1.begin(), v1.end(), v2.begin(), [](int n) { return n > 2; });

    v2 = map<int, bool>(v1, [](int n) { return n > 2; });

    cout << v1 << endl << v2 << endl;

    return 0;
}

