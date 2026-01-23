import java.util.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Biglietto{
  private final String serie;
  private final int numero;

  public Biglietto(String serie, int numero){

    if(numero > 999999 || numero < 0)
      throw new IllegalArgumentException("number not valid");
    if (serie == null || serie.length() != 2) 
      throw new IllegalArgumentException("Serie not valid");
    
    this.serie = serie;
    this.numero = numero;
  }

  public String getSerie() { return serie; }
  public int getNumero() { return numero; }
  
  @Override
  public int hashCode(){
    return Objects.hash(serie, numero);
  }
  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Biglietto that = (Biglietto) o;
    return numero == that.numero && serie.equals(that.serie);
  }
}

public class BigliettoVenduto extends Biglietto{
  private final String luogo;
  private final Data dataVendita;

  public BigliettoVenduto(String serie, int numero, String luogo, Data dataVendita){
    super(serie, numero);
    if (luogo == null) 
      throw new IllegalArgumentException("Luogo not valid");
    if (dataVendita == null) 
      throw new IllegalArgumentException("Date not valid");
        
    this.luogo = luogo;
    this.dataVendita = dataVendita;
  }
  
  public String getLuogo(){ return luogo; }
  public Data getDataVendita(){ return dataVendita;  }
  
  @Override
  public int hashCode(){
    return Objects.hash(super.hashCode(), luogo, dataVendita);
  }
  @Override
  public boolean equals(Object o){
    if (this == o) return true;
    if (!super.equals(o)) return false;
    if (getClass() != o.getClass()) return false;
    BigliettoVenduto that = (BigliettoVenduto) o;
    return luogo.equals(that.luogo) && dataVendita.equals(that.dataVendita);
  }
}

public class Lotteria {
    private final Set<BigliettoVenduto> biglietti;   
    private boolean estrazioneEseguita;              
    private final Random random;

    public Lotteria() {
        this.biglietti = new HashSet<>();
        this.estrazioneEseguita = false;
        this.random = new Random();
    }

    public BigliettoVenduto aggiungi(Biglietto b, Data dataVendita, String luogo) {
        if (estrazioneEseguita) {
            throw new IllegalStateException("Impossible to add tickets after extraction");
        }
        if (b == null || dataVendita == null || luogo == null) {
            throw new IllegalArgumentException("Argomenti non validi");
        }
        BigliettoVenduto bv = new BigliettoVenduto(
                b.getSerie(),
                b.getNumero(),
                luogo,
                dataVendita
        );
        biglietti.add(bv);
        return bv;
    }
  
    public BigliettoVenduto[] estrazione() {
        if (biglietti.size() < 5) {
            throw new IllegalStateException("Not enough tickets");
        }

        List<BigliettoVenduto> lista = new ArrayList<>(biglietti);
        Collections.shuffle(lista, random);

        BigliettoVenduto[] estratti = new BigliettoVenduto[5];
        for (int i = 0; i < 5; i++) {
            BigliettoVenduto bv = lista.get(i);
            estratti[i] = bv;
            biglietti.remove(bv);      
        }

        estrazioneEseguita = true;
        return estratti;
    }
}
