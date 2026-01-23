import java.util.ArrayList;
import java.util.List;

public class Stanza {
    private final double lato1;     
    private final double lato2; 
    private final double altezza;  

    public Stanza(double lato1, double lato2, double altezza) {
        if (lato1 <= 0 || lato2 <= 0 || altezza <= 0) {
            throw new IllegalArgumentException("Dimensioni non valide");
        }
        this.lato1 = lato1;
        this.lato2 = lato2;
        this.altezza = altezza;
    }

    public double getSuperficie() {
        return lato1 * lato2;
    }

    public double getVolume() {
        return getSuperficie() * altezza;
    }
}

public class Appartamento {
    private final List<Stanza> stanze;
    private final double valore;   

    public Appartamento(List<Stanza> stanze, double valore) {
        if (stanze == null || stanze.isEmpty()) {
            throw new IllegalArgumentException("L'appartamento deve avere almeno una stanza");
        }
        if (valore < 0) {
            throw new IllegalArgumentException("Valore non valido");
        }
        this.stanze = new ArrayList<>(stanze);
        this.valore = valore;
    }

    public double getValore() {
        return valore;
    }

    public double getSuperficieTotale() {
        double s = 0;
        for (Stanza st : stanze) {
            s += st.getSuperficie();
        }
        return s;
    }

    public double getVolumeTotale() {
        double v = 0;
        for (Stanza st : stanze) {
            v += st.getVolume();
        }
        return v;
    }
}

public class MercatoImmobiliare {

    private final List<Appartamento> appartamenti;

    public MercatoImmobiliare() {
        this.appartamenti = new ArrayList<>();
    }

    public void aggiungi(Appartamento a) {
        if (a == null) {
            throw new IllegalArgumentException("Appartamento nullo");
        }
        appartamenti.add(a);
    }
  
    public double valoreStimato(Appartamento a) {
        if (appartamenti.isEmpty()) {
            throw new IllegalStateException("Mercato vuoto: impossibile stimare");
        }

        double sommaValoriMq = 0.0;
        int n = appartamenti.size();

        for (Appartamento app : appartamenti) {
            double superficie = app.getSuperficieTotale();
            double valore = app.getValore();
            if (superficie <= 0) {
                throw new IllegalStateException("Appartamento con superficie non valida nel mercato");
            }
            double valoreMq = valore / superficie; 
            sommaValoriMq += valoreMq;
        }

        double valoreMedioMq = sommaValoriMq / n;
        double superficieApp = a.getSuperficieTotale();
        return superficieApp * valoreMedioMq;
    }
}
