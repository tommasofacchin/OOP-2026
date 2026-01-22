
#include <iostream>
#include <iterator>
#include <cstddef>
#include <vector>

using std::vector;

template <class T>
class tree_node
{
public:
	T data;
	tree_node<T>* left, * right;

	static bool are_equal(const tree_node<T>* a, const tree_node<T>* b)
	{
		return a == b || (a != nullptr && b != nullptr && *a == *b);
	}

public:

	tree_node() = default;
	tree_node(const tree_node<T>& t) = default;
	tree_node<T>& operator=(const tree_node<T>& t) = default;

	~tree_node()
	{
		if (left != nullptr)
		{
			delete left;
			left = nullptr;
		}
		if (right != nullptr)
		{
			delete right;
			right = nullptr;
		}
	}

	tree_node(const T& v, tree_node<T>* l, tree_node<T>* r) : data(v), left(l), right(r)
	{
		prepopulate();
	}


	bool operator==(const tree_node<T>& t) const
	{
		return data == t.data && are_equal(left, t.left) && are_equal(right, t.right);
	}


	using value_type = T;
	using const_iterator = typename vector<T>::const_iterator;
	using iterator = typename vector<T>::iterator;

private:
	vector<T> children;

	void dfs(vector<T>& v)
	{	
		v.push_back(data);
		if (left != nullptr) left->dfs(v);
		if (right != nullptr) right->dfs(v);
	}

	void prepopulate()
	{
		dfs(children);
	}

public:

	const_iterator begin() const
	{
		return children.begin();
	}

	const_iterator end() const
	{
		return children.end();
	}

	iterator begin()
	{
		return children.begin();
	}

	iterator end()
	{
		return children.end();
	}


};

template <class T>
tree_node<T>* lr(const T& v, tree_node<T>* l, tree_node<T>* r)
{
	return new tree_node<T>(v, l, r);
}

template <class T>
tree_node<T>* l(const T& v, tree_node<T>* n)
{
	return new tree_node<T>(v, n, nullptr);
}

template <class T>
tree_node<T>* r(const T& v, tree_node<T>* n)
{
	return new tree_node<T>(v, nullptr, n);
}

template <class T>
tree_node<T>* v(const T& v)
{
	return new tree_node<T>(v, nullptr, nullptr);
}


template <class T>
std::ostream& operator<<(std::ostream& os, const tree_node<T>& t)
{
	os << t.data;
	if (t.left != nullptr) os << "(" << *t.left << ")";
	if (t.right != nullptr) os << "[" << *t.right << "]";
	return os;
}

using namespace std;


int main()
{
	auto t1 =
		shared_ptr<tree_node<int>>(	
			lr(1,						
				lr(2,					
					v(3),
					v(4)),
				r(5,
					lr(6,
						v(7),
						v(8)))));

	auto t2 =
		shared_ptr<tree_node<int>>(
			lr(1,
				r(5,					
					lr(6,				
						v(7),
						v(8))),
				lr(2,
					v(3),
					v(4))));

	cout << "pretty printer: " << endl
		<< "t1: " << *t1 << endl	
		<< "t2: " << *t2 << endl;

	cout << "equality: " << (*t1 == *t2) << ", " << (*t1->left == *t2->right) << endl;	

	cout << "iterator: ";
	for (tree_node<int>::iterator it = t1->begin(); it != t1->end(); ++it)
	{
		int& n = *it;		
		n *= 2;				
		cout << n << " ";
	}
	cout << endl << "t1 modificato: " << *t1 << endl;

	cout << "const iterator: ";
	for (tree_node<int>::const_iterator it = t1->begin(); it != t1->end(); ++it)	
	{
		const int& n = *it;
		cout << n << " ";
	}
	cout << endl;

}


