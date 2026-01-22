#include <cstddef> // size_t, ptrdiff_t
#include <vector>
#include <functional>

// === TYPE ALIASES ===

// simple class template + type alias
template <class T>
class C {
public:
    using value_type = T;   // alias for the stored type
};

// simple type alias
using counter_t = unsigned short;  // compact integer type


// === NEW / DELETE ===

void new_delete_example() {
    int* p = new int(42);   // allocate single int, value-initialized to 42
    delete p;               // delete single object

    int* a = new int[10];   // allocate array of 10 ints (default-initialized)
    delete[] a;             // delete array (must use delete[])
}


// === MINIMAL REF-COUNTED HANDLE ===

template <class T>
class Mini {
public:
    T*          ptr;   // raw pointer to managed object/array
    counter_t*  cnt;   // reference counter on heap

    Mini() : ptr(nullptr), cnt(nullptr) {}                     // default

    explicit Mini(T* p) : ptr(p), cnt(new counter_t(1)) {}     // from T*

    Mini(const Mini& other) : ptr(other.ptr), cnt(other.cnt) { // copy ctor
        if (cnt) ++(*cnt);                                     // share ownership
    }

    ~Mini() {                                                  // destructor
        if (cnt && --(*cnt) == 0) {                            // last owner
            delete ptr;                                        // delete managed
            delete cnt;                                        // delete counter
        }
    }

    Mini& operator=(const Mini& other) {                       // copy assignment
        if (this == &other) return *this;                      // self-assignment guard
        if (cnt && --(*cnt) == 0) {                            // release old
            delete ptr;
            delete cnt;
        }
        ptr = other.ptr;                                       // share new
        cnt = other.cnt;
        if (cnt) ++(*cnt);
        return *this;
    }

    // basic operators
    T&       operator*()       { return *ptr; }                // dereference
    const T& operator*() const { return *ptr; }

    T*       operator->()       { return ptr; }                // member access
    const T* operator->() const { return ptr; }

    T&       operator[](size_t i)       { return ptr[i]; }     // index access
    const T& operator[](size_t i) const { return ptr[i]; }
};

int mini_example() {
    Mini<int> m(new int(5));   // manage a single int
    int x = *m;                // uses operator*
    m[0] = 10;                 // uses operator[]
    (void)x;
    return 0;
}


// === VECTOR INITIALIZATION ===

void vector_initialization_examples() {
    std::vector<int> v1;                   // empty vector
    std::vector<int> v2(5);                // 5 default-initialized ints (0)
    std::vector<int> v3(5, 42);            // 5 copies of 42
    std::vector<int> v4{1, 2, 3, 4};       // list-initialization
    std::vector<int> v5(v4);               // copy ctor
    std::vector<int> v6 = v4;              // copy assignment syntax
}


// === TYPEDEF (LEGACY ALIAS) ===

// legacy alias syntax (equivalent to using)
typedef unsigned short counter_typedef_t;  // like using counter_t = unsigned short;


// === WHEN TO USE 'typename' ===

// 'typename' is required before dependent nested types
template <class Container>
void sum_example(const Container& c) {
    // Container::value_type depends on template parameter Container
    typename Container::value_type acc{};  // without 'typename' this is a parse error
    for (typename Container::const_iterator it = c.begin(); it != c.end(); ++it) {
        acc += *it;
    }
}

// not needed when the type is not dependent on a template parameter
void no_typename_needed() {
    std::vector<int>::iterator it;         // here 'typename' is not required
}


// === ITERATORS ===

void iterator_examples() {
    std::vector<int> v{1, 2, 3};

    // mutable iterator: can modify elements
    for (std::vector<int>::iterator it = v.begin(); it != v.end(); ++it) {
        *it *= 2;                          // modify elements in-place
    }

    // const iterator: read-only access
    for (std::vector<int>::const_iterator it = v.cbegin(); it != v.cend(); ++it) {
        int x = *it;                       // read-only access
        (void)x;
    }
}


// === std::function ===

using real      = double;
using unary_fun = std::function<real(const real&)>;

real apply_twice(const unary_fun& f, real x) {
    // takes any callable with signature real(const real&)
    return f(f(x));                        // calls f twice on x
}

void function_example() {
    unary_fun square = [](const real& x) { return x * x; }; // lambda stored in std::function
    real r = apply_twice(square, 2.0);                      // (2^2)^2 = 16
    (void)r;
}


// === WHEN TO USE '&' (REFERENCE) ===

// 1. Parameters: avoid copies (especially for large / complex types)
void takes_by_value(std::vector<int> v) {
    // v is a copy: modifications do not affect caller
    v.push_back(42);
}

void takes_by_ref(std::vector<int>& v) {
    // v is a reference: modifications affect caller
    v.push_back(42);
}

void takes_by_const_ref(const std::vector<int>& v) {
    // v is a reference, but read-only; no copy and no modification allowed
    if (!v.empty()) {
        int x = v[0];
        (void)x;
    }
}

// 2. Return by reference: allow aliasing existing objects (must not return reference to local)
int& front_ref(std::vector<int>& v) {
    return v[0];                           // returns reference to first element
}

void reference_examples() {
    std::vector<int> v{1, 2, 3};
    takes_by_value(v);                     // copies v
    takes_by_ref(v);                       // modifies v in place
    takes_by_const_ref(v);                 // reads v

    int& ref = front_ref(v);               // ref aliases v[0]
    ref = 100;                             // changes v[0] to 100
}

