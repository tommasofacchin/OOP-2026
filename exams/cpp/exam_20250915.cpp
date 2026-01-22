#include <iostream>
#include <vector>

using namespace std;

template <class InputIterator>
typename InputIterator::value_type average(InputIterator begin, InputIterator end)
{
	typename InputIterator::value_type r{};	

	size_t n = 0;
	for (; begin != end; ++n)
		r += *begin++;	// value_type must have += operator
	if (n == 0)
		throw runtime_error("average of empty range");
	return r / n;	// and the operator / with size_t as second argument
}


int main()
{
	vector<int> v = { 1, 2, 3, 4, 5 };
	cout << average(v.begin(), v.end()) << endl;
	
}

