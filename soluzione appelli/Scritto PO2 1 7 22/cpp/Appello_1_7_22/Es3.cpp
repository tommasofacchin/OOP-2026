
// Questo sorgente contiene le soluzioni dell'esame scritto di PO2 del 1/7/2022 per ci� che riguarda il quesito 3, ovvero la domanda che coinvolge C++.
// I quesiti 1-2 riguardanti Java sono in un progetto IntelliJ a parte, non qui.
// Il codice qui esposto � C++14 per qualche piccolo particolare, ma in gran parte � essenzialmente vanilla.

#include <iostream>
#include <vector>

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
			if (pt != nullptr)	// se non punta a nulla non c'� niente da liberare
			{
				if (is_array) delete pt;
				else delete [] pt;
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

	// subscript
	// l'operatore di subscript � l'unica implementazione reale; tutti gli altri operatori sono implementati in funzione di questo
	// questo approccio � poco error-prone perch� concentra solamente qui il calcolo esatto dell'indirizzo usando l'offset
	// in altre parole, l'operatore di subscript funge da API interna a basso livello; tutto il resto � costruito sopra di essa
	const T& operator[](size_t i) const
	{
		return pt[offset + i];
	}
	T& operator[](size_t i)
	{
		// capita spesso che l'implementazione const e l'implementazione non-const siano identiche
		// in questi casi � necessario duplicare il codice, che � una pratica inelegante ed error-prone
		// questo trucco sfrutta un giro di const cast per rimandare questa implementazione a quella const appena sopra, che � l'unica che implementiamo davvero
		// ATTENZIONE: tutto questo NON � richiesto dal tema d'esame, lo mostriamo solamente a scopo didattico per insegnare una tecnica avanzata di non-duplicazione del codice
		return const_cast<T&>(const_cast<const smart_ptr<T>&>(*this).operator[](i));
	}

	// de-reference
	const T& operator*() const
	{
		// usa l'operatore di subscript
		return pt[0];
	}
	T& operator*()
	{
		// stessa tecnica per non duplicare: chiamiamo la versione const di questo operatore definita qui sopra, che � l'unica che implementiamo davvero
		return const_cast<T&>(const_cast<const smart_ptr<T>&>(*this).operator*());
	}

	// field access
	const T* operator->() const
	{
		// anche questa implementazione sfrutta altri operatori scritti sopra: in particolare usa de-reference che a sua volta usa il subscript, in questo modo non richiede manutenzione
		return &*(*this);
		//      ^
		// si faccia attenzione a un particolare: solo l'operatore * indicato dalla freccetta invoca l'overload definito da noi
		// il de-reference di this dentro le parentesi tonde e l'operatore & pi� esterno invocano gli operatori nativi di C++, non i nostri overload
	}
	T* operator->()
	{
		// altro uso della tecnica avanzata per non duplicare l'implementazione non-const
		// cerchiamo di capirla: lo scopo � chiamare l'implementazione const di questo operatore senza duplicare il codice
		// 1) trasformiamo *this (che in questo scope � di tipo smart_ptr<T>&) in un const smart_ptr<T>&
		// 2) ora che *this � castato a const, invochiamo l'operatore o il metodo che ci interessa: la risoluzione dell'overload risolver� l'implementazione const, non far� una ricorsione!
		// 3) siccome stiamo invocando la versione const, il risultato � const: ma il nostro tipo di ritorno deve essere un T* non-const, pertanto bisogna const-castare per TOGLIERE il const
		return const_cast<T*>(const_cast<const smart_ptr<T>&>(*this).operator->());
	}

	// plus
	smart_ptr<T>& operator+=(ptrdiff_t off)
	{
		offset += off;
		return *this;
	}
	smart_ptr<T> operator+(ptrdiff_t off) const
	{
		// questa implementazione usa il copy-constructor e l'operator+= definito qui sopra
		return smart_ptr<T>(*this) += off;	
	}

	// minus
	smart_ptr<T>& operator-=(ptrdiff_t off)
	{
		offset -= off;	// analogo al +=
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

using std::string;

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
