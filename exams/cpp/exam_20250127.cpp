#include <iterator>

template <class InputIterator, class OutputIterator, class UnaryFunction>
OutputIterator map(InputIterator from, InputIterator to,
                   OutputIterator result, UnaryFunction f)
{
    for (; from != to; ++from, ++result) {
        *result = f(*from);
    }
    return result;
}
