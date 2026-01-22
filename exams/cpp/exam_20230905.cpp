#include <iostream>
#include <vector>

using namespace std;

template <class Container>
ostream& operator<<(ostream& os, const Container& c) 
{
	os << "[";
	for (typename Container::const_iterator it = c.begin(); it != c.end(); ++it)
		os << " " << *it;
	os << "]";
	return os;
}

template <class Container>
typename Container::value_type sum(const Container& c)
{
	typename Container::value_type r;
	for (typename Container::const_iterator it = c.begin(); it != c.end(); ++it)
	{
		r += *it;
	}
	return r;
}


