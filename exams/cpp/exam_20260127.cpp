#include <vector>
#include <cstddef>
#include <iostream>

template <class T>
class Array2d
{
private:
    std::size_t cols;
    std::vector<T> v;

public:
    Array2d() : cols(0), v() {}

    Array2d(std::size_t rows, std::size_t cols_)
        : cols(cols_), v(rows * cols_) {}

    Array2d(std::size_t rows, std::size_t cols_, const T& value)
        : cols(cols_), v(rows * cols_, value) {}

    Array2d(const Array2d<T>& m)
        : cols(m.cols), v(m.v) {}

    typedef T value_type;
    typedef typename std::vector<T>::iterator iterator;
    typedef typename std::vector<T>::const_iterator const_iterator;

    Array2d<T>& operator=(const Array2d<T>& m)
    {
        if (this != &m) {
            cols = m.cols;
            v = m.v;
        }
        return *this;
    }

    T& operator()(std::size_t i, std::size_t j)
    {
        return v[i * cols + j];
    }

    const T& operator()(std::size_t i, std::size_t j) const
    {
        return v[i * cols + j];
    }
    
    iterator begin() { return v.begin(); }
    iterator end()   { return v.end(); }

    const_iterator begin() const { return v.begin(); }
    const_iterator end()   const { return v.end(); }

    const_iterator cbegin() const { return v.cbegin(); }
    const_iterator cend()   const { return v.cend(); }
};


    
int main() {
    Array2d<int> m(3, 4);

    for (std::size_t i = 0; i < 3; ++i) {
        for (std::size_t j = 0; j < 4; ++j) {
            m(i, j) = static_cast<int>(i * j);
        }
    }

    for (std::size_t i = 0; i < 3; ++i) {
        for (std::size_t j = 0; j < 4; ++j) {
            std::cout << m(i, j) << ' ';
        }
        std::cout << '\n';
    }
}
