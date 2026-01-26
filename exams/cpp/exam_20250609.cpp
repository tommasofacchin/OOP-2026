#include <algorithm> // std::swap

template <typename A, typename B>
class pair {
public:
    typedef A first_type;
    typedef B second_type;

    A first;
    B second;

    pair() : first(), second() {}

    pair(const A& a, const B& b) : first(a), second(b) {}

    pair(const pair& other) : first(other.first), second(other.second) {}

    template <typename C, typename D>
    pair(const pair<C,D>& other) : first(other.first), second(other.second) {}

    pair& operator=(const pair& other) {
        if (this != &other) {
            first  = other.first;
            second = other.second;
        }
        return *this;
    }

    void swap(pair& other) {
        std::swap(first,  other.first);
        std::swap(second, other.second);
    }
};

template <typename A, typename B>
void swap(pair<A,B>& x, pair<A,B>& y) {
    x.swap(y);
}

template <typename A, typename B>
bool operator==(const pair<A,B>& x, const pair<A,B>& y) {
    return x.first == y.first && x.second == y.second;
}

template <typename A, typename B>
bool operator!=(const pair<A,B>& x, const pair<A,B>& y) {
    return !(x == y);
}

template <typename A, typename B>
bool operator<(const pair<A,B>& x, const pair<A,B>& y) {
    return (x.first < y.first) ||
           (!(y.first < x.first) && x.second < y.second);
}

template <typename A, typename B>
bool operator>(const pair<A,B>& x, const pair<A,B>& y) {
    return y < x;
}

template <typename A, typename B>
bool operator<=(const pair<A,B>& x, const pair<A,B>& y) {
    return !(y < x);
}

template <typename A, typename B>
bool operator>=(const pair<A,B>& x, const pair<A,B>& y) {
    return !(x < y);
}
