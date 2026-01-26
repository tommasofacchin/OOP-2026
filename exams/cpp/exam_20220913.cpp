#include <iostream>
#include <memory>
#include <utility>
#include <stack>
#include <iterator>

template <typename T>
class tree_node {
public:
    using value_type      = T;

private:
    T value;
    std::unique_ptr<tree_node> left;
    std::unique_ptr<tree_node> right;

public:
    explicit tree_node(const T& v)
        : value(v), left(nullptr), right(nullptr) {}

    tree_node(const T& v,
              std::unique_ptr<tree_node> l,
              std::unique_ptr<tree_node> r)
        : value(v), left(std::move(l)), right(std::move(r)) {}

    tree_node(const tree_node& other)
        : value(other.value),
          left(other.left ? std::make_unique<tree_node>(*other.left) : nullptr),
          right(other.right ? std::make_unique<tree_node>(*other.right) : nullptr) {}

    tree_node(tree_node&&) noexcept = default;

    // Copy assignment (copy-and-swap)
    tree_node& operator=(tree_node other) noexcept {
        swap(other);
        return *this;
    }

    tree_node& operator=(tree_node&&) noexcept = default;

    ~tree_node() = default;

    void swap(tree_node& other) noexcept {
        using std::swap;
        swap(value, other.value);
        swap(left, other.left);
        swap(right, other.right);
    }

    static tree_node leaf(const T& v) {
        return tree_node(v);
    }

    static tree_node left_child(const T& v, const tree_node& l) {
        return tree_node(
            v,
            std::make_unique<tree_node>(l),
            nullptr
        );
    }

    static tree_node right_child(const T& v, const tree_node& r) {
        return tree_node(
            v,
            nullptr,
            std::make_unique<tree_node>(r)
        );
    }

    static tree_node both(const T& v, const tree_node& l, const tree_node& r) {
        return tree_node(
            v,
            std::make_unique<tree_node>(l),
            std::make_unique<tree_node>(r)
        );
    }

    const tree_node* left_ptr() const noexcept { return left.get(); }
    const tree_node* right_ptr() const noexcept { return right.get(); }

    tree_node* left_ptr() noexcept { return left.get(); }
    tree_node* right_ptr() noexcept { return right.get(); }

    const T& get() const noexcept { return value; }
    T& get() noexcept { return value; }


    friend bool operator==(const tree_node& a, const tree_node& b) {
        if (!(a.value == b.value)) return false;

        const bool left_eq =
            (!a.left && !b.left) ||
            (a.left && b.left && *a.left == *b.left);

        if (!left_eq) return false;

        const bool right_eq =
            (!a.right && !b.right) ||
            (a.right && b.right && *a.right == *b.right);

        return right_eq;
    }

    friend bool operator!=(const tree_node& a, const tree_node& b) {
        return !(a == b);
    }

    class iterator {
    public:
        using iterator_category = std::forward_iterator_tag;
        using value_type        = T;
        using difference_type   = std::ptrdiff_t;
        using pointer           = T*;
        using reference         = T&;

    private:
        std::stack<tree_node*> st;

    public:
        iterator() = default;

        explicit iterator(tree_node* root) {
            if (root) st.push(root);
        }

        reference operator*() const {
            return st.top()->value;
        }

        pointer operator->() const {
            return &st.top()->value;
        }

        iterator& operator++() {
            tree_node* curr = st.top();
            st.pop();
            if (curr->right) st.push(curr->right.get());
            if (curr->left)  st.push(curr->left.get());
            return *this;
        }

        iterator operator++(int) {
            iterator tmp = *this;
            ++(*this);
            return tmp;
        }

        friend bool operator==(const iterator& a, const iterator& b) {
            return a.st.empty() && b.st.empty()
                   || (!a.st.empty() && !b.st.empty() && a.st.top() == b.st.top());
        }

        friend bool operator!=(const iterator& a, const iterator& b) {
            return !(a == b);
        }
    };

    class const_iterator {
    public:
        using iterator_category = std::forward_iterator_tag;
        using value_type        = T;
        using difference_type   = std::ptrdiff_t;
        using pointer           = const T*;
        using reference         = const T&;

    private:
        std::stack<const tree_node*> st;

    public:
        const_iterator() = default;

        explicit const_iterator(const tree_node* root) {
            if (root) st.push(root);
        }

        reference operator*() const {
            return st.top()->value;
        }

        pointer operator->() const {
            return &st.top()->value;
        }

        const_iterator& operator++() {
            const tree_node* curr = st.top();
            st.pop();
            if (curr->right) st.push(curr->right.get());
            if (curr->left)  st.push(curr->left.get());
            return *this;
        }

        const_iterator operator++(int) {
            const_iterator tmp = *this;
            ++(*this);
            return tmp;
        }

        friend bool operator==(const const_iterator& a, const const_iterator& b) {
            return a.st.empty() && b.st.empty()
                   || (!a.st.empty() && !b.st.empty() && a.st.top() == b.st.top());
        }

        friend bool operator!=(const const_iterator& a, const const_iterator& b) {
            return !(a == b);
        }
    };

    iterator begin() { return iterator(this); }
    iterator end()   { return iterator(); }

    const_iterator begin() const { return const_iterator(this); }
    const_iterator end()   const { return const_iterator(); }

    const_iterator cbegin() const { return const_iterator(this); }
    const_iterator cend()   const { return const_iterator(); }
};

template <typename T>
std::ostream& operator<<(std::ostream& os, const tree_node<T>& t) {
    os << t.get();
    if (t.left_ptr()) {
        os << "(" << *t.left_ptr() << ")";
    }
    if (t.right_ptr()) {
        os << "[" << *t.right_ptr() << "]";
    }
    return os;
}
