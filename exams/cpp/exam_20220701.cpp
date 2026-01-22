
#include <iostream>
#include <vector>

using std::string;


template <class T>
class smart_ptr
{
    private:
        T* pt;
        ptrdiff_t offset;
        bool is_array;

        using counter_t = unsigned short;

        counter_t* cnt;

        void dec()
        {
            if (cnt != nullptr && --(*cnt) == 0)
            {
                if (pt != nullptr)
                {
                    if (is_array) delete [] pt;
                    else delete pt;
                }
                delete cnt;
            }
        }

        void inc()
        {
            if (cnt != nullptr) ++(*cnt);
        }

    public:
        using value_type = T;

        smart_ptr() : pt(nullptr), offset(0), is_array(false), cnt(nullptr) {}

        explicit smart_ptr(T* pt_, bool is_array_ = false) : pt(pt_), offset(0), is_array(is_array_), cnt(new counter_t(1)) {}

        smart_ptr(const smart_ptr<T>& p) : pt(p.pt), offset(p.offset), is_array(p.is_array), cnt(p.cnt)
        {
            inc();
        }

        ~smart_ptr()
        {
            dec();
        }

        smart_ptr<T>& operator=(const smart_ptr<T>& p)
        {
            dec();
            pt = p.pt;
            cnt = p.cnt;
            offset = p.offset;
            is_array = p.is_array;
            inc();
            return *this;
        }

        const T& operator[](size_t i) const
        {
            return pt[offset + i];
        }

        const T& operator*() const
        {
            return pt[0];
        }

        const T* operator->() const
        {
            return &*(*this);
        }

        smart_ptr<T>& operator+=(ptrdiff_t off)
        {
            offset += off;
            return *this;
        }
        smart_ptr<T> operator+(ptrdiff_t off) const
        {
            return smart_ptr<T>(*this) += off;	
        }

        smart_ptr<T>& operator-=(ptrdiff_t off)
        {
            offset -= off;	
            return *this;
        }
        smart_ptr<T> operator-(ptrdiff_t off)
        {
            return smart_ptr<T>(*this) -= off;	
        }

        smart_ptr<T>& operator++()
        {
            return *this += 1;
        }
        smart_ptr<T>& operator--()
        {
            return *this -= 1;	
        }

        smart_ptr<T> operator++(int)
        {
            smart_ptr<T> r(*this);	
            ++(*this);				
            return r;				
        }
        smart_ptr<T> operator--(int)
        {
            smart_ptr<T> r(*this);
            --(*this);				
            return r;
        }

};


int main()
{
	int* a = new int[5];
	smart_ptr<int> a1(a, true), a2;
	a2 = a1 + 10;
	a1 -= 3;
	a1 = ++a1 - *a2-- + a1[3];

	smart_ptr<double> b(new double[10], true);
	for (unsigned int i = 0; i < 10; ++i)
		b++;

	string* sp = new string("ciao");
	smart_ptr<string> s1(sp), s2;
	s2 = s1; 
	size_t i = s2->find('a', 0);


}