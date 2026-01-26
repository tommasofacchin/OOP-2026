#include <utility>   // std::swap
#include <type_traits>

template <typename A, typename B>
class pair {
public:
    using first_type  = A;
    using second_type = B;
    A first;
    B second;

    pair()
        noexcept(std::is_nothrow_default_constructible_v<A> &&
                 std::is_nothrow_default_constructible_v<B>)
        : first(), second() {}

    pair(const A& a, const B& b)
        : first(a), second(b) {}

    pair(A&& a, B&& b)
        : first(std::move(a)), second(std::move(b)) {}

    pair(const pair& other)
        : first(other.first), second(other.second) {}

    pair(pair&& other) noexcept(std::is_nothrow_move_constructible_v<A> &&
                                std::is_nothrow_move_constructible_v<B>)
        : first(std::move(other.first)), second(std::move(other.second)) {}

    pair& operator=(const pair& other) {
        if (this != &other) {
            first  = other.first;
            second = other.second;
        }
        return *this;
    }

    pair& operator=(pair&& other) noexcept(
        std::is_nothrow_move_assignable_v<A> &&
        std::is_nothrow_move_assignable_v<B>) {
        if (this != &other) {
            first  = std::move(other.first);
            second = std::move(other.second);
        }
        return *this;
    }

    template <typename C, typename D>
    pair(const pair<C, D>& other)
        : first(other.first), second(other.second) {}

    void swap(pair& other) noexcept(
        std::is_nothrow_swappable_v<A> &&
        std::is_nothrow_swappable_v<B>) {
        using std::swap;
        swap(first, other.first);
        swap(second, other.second);
    }

    friend bool operator==(const pair& x, const pair& y) {
        return x.first == y.first && x.second == y.second;
    }

    friend bool operator!=(const pair& x, const pair& y) {
        return !(x == y);
    }

    friend bool operator<(const pair& x, const pair& y) {
        return (x.first < y.first) || (!(y.first < x.first) && x.second < y.second);
    }

    friend bool operator>(const pair& x, const pair& y) {
        return y < x;
    }

    friend bool operator<=(const pair& x, const pair& y) {
        return !(y < x);
    }

    friend bool operator>=(const pair& x, const pair& y) {
        return !(x < y);
    }
};

template <typename A, typename B>
void swap(pair<A,B>& x, pair<A,B>& y) noexcept(noexcept(x.swap(y))) {
    x.swap(y);
}
