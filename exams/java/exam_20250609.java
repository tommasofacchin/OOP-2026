
public class Dazi{
    public final String nazione;
    private double percentuale;

    public Dazi(String nazione, double percentuale) {
        this.nazione = nazione;
        this.percentuale = percentuale;
    }

    public double getPercentuale() {
        return percentuale;
    }

    public void setPercentuale(double percentuale) {
        this.percentuale = percentuale;
    }

}

public class Confine{
    public final String nazione;
    private final ArrayList<Dazi> dazi;

    public Confine(String nazione, ArrayList<Dazi> dazi) {
        this.nazione = nazione;
        this.dazi = new ArrayList<>(dazi);
    }

    public double getDazio(String nazione){
        for(Dazi d : dazi){
            if(d.getNazione().equals(nazione)){
                return d.getPercentuale();
            }
        }
        throw new IllegalArgumentException("Nazione non trovata");
    }

    public void setDazio(String nazione, double percentuale ){
        for(Dazi d : dazi){
            if(d.getNazione().equals(nazione)){
                d.setPercentuale(percentuale);
            }
        }
    }

}

public class Commercio{
    private final Collection<Confine> confini;

    public Commercio(Collection<Confine> confini) {
        this.confini = confini;
    }    

    public double importazione(String esportatore, String importatore, double valore){
        for(Confine c : confini){
            if(c.getNazione().equals(importatore)){
                double dazio = c.getDazio(esportatore);
                return valore * (1 + dazio);
            }
        }
        throw new IllegalArgumentException("Importatore non trovato");
    }

    public double totaleTransazioni(String nazione1, String nazione2, Collection<Double> valori) {
        double totale = 0;
        for (double valore : valori) {
            if (valore < 0) {
                totale -= importazione(nazione1, nazione2, -valore);
            } else if (valore > 0) {
                totale += importazione(nazione2, nazione1, valore);
            }
        }
        return totale;
    }
}
