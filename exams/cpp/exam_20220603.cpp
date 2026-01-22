
#include <iostream>
#include <vector>

using namespace std;

template <class T>
class matrix
{
private:
	size_t cols;
	vector<T> v;

public:
	matrix() : cols(0), v() {}
	matrix(size_t rows, size_t cols_) : cols(cols_), v(rows * cols) {}
	matrix(size_t rows, size_t cols_, const T& v) : cols(cols_), v(rows* cols, v) {}
	matrix(const matrix<T>& m) : cols(m.cols), v(m.v) {}

	typedef T value_type;
	typedef typename vector<T>::iterator iterator;
	typedef typename vector<T>::const_iterator const_iterator;

	matrix<T>& operator=(const matrix<T>& m)
	{
		v = m.v;
		return *this;
	}

	T& operator()(size_t i, size_t j)
	{
		return v[i * cols + j];
	}

	const T& operator()(size_t i, size_t j) const
	{
		return (*this)(i, j);
	}

	iterator begin()
	{
		return v.begin();
	}

	iterator end()
	{
		return v.end();
	}

	const_iterator begin() const
	{
		return begin();
	}

	const_iterator end() const
	{
		return end();
	}
};


int main()
{
	matrix<double> m1;         
	matrix<double> m2(10, 20); 
	matrix<double> m3(m2);      
	m1 = m2;                   
	m3(3, 1) = 11.23;        

	for (typename matrix<double>::iterator it = m1.begin(); it != m1.end(); ++it) {
		typename matrix<double>::value_type& x = *it;	
		x = m2(0, 2);									
	}
	
	matrix<string> ms(5, 4, "ciao"); 
	for (typename matrix<string>::const_iterator it = ms.begin(); it != ms.end(); ++it)
		cout << *it;				
}
