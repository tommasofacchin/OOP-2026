#include <iostream>
#include <utility>   
#include <iterator>  
#include <cmath>      
#include <functional> 

template <typename X, typename Y>
class fun_seq {
public:
    using value_type      = std::pair<X, Y>;
    using function_type   = Y(*)(X);       

    fun_seq(X a, X b, function_type f, X step)
        : a_(a), b_(b), f_(f), step_(step)
    {}

    class iterator {
    public:
        using iterator_category = std::input_iterator_tag;
        using value_type        = std::pair<X, Y>;
        using difference_type   = std::ptrdiff_t;
        using pointer           = value_type*;
        using reference         = value_type&;

        iterator() : owner_(nullptr), end_(true) {}

        iterator(const fun_seq* owner, X x, bool end)
            : owner_(owner), x_(x), end_(end)
        {}

        value_type operator*() const {
            return { x_, owner_->f_(x_) };
        }

        iterator& operator++() {
            x_ = x_ + owner_->step_;
            if (!(x_ < owner_->b_)) {
                end_ = true;
            }
            return *this;
        }

        iterator operator++(int) {
            iterator tmp = *this;
            ++(*this);
            return tmp;
        }

        bool operator==(const iterator& other) const {
            if (end_ && other.end_) return true;
            return owner_ == other.owner_ && x_ == other.x_ && end_ == other.end_;
        }

        bool operator!=(const iterator& other) const {
            return !(*this == other);
        }

    private:
        const fun_seq* owner_;
        X x_;
        bool end_;
    };

    iterator begin() const {
        if (a_ < b_) {
            return iterator(this, a_, false);
        } else {
            return end();
        }
    }

    iterator end() const {
        return iterator(this, X{}, true);
    }

private:
    X a_, b_;
    function_type f_;
    X step_;
};

double parabola(double x) {
    return x * x + 2 * x - 1;
}

int main() {
    const double a    = -2.0;
    const double b    =  2.0;
    const double step =  0.1;

    fun_seq<double, double> seq(a, b, parabola, step);

    for (const auto& p : seq) {
        double x = p.first;
        double y = p.second;
        std::cout << "f(" << x << ") = " << y << '\n';
    }

    return 0;
}
