import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.List;

public final class Materiale {
    private final String nome;
    private final double pesoSpecifico;
    private final boolean ignifugo;

    public Materiale(String nome, double pesoSpecifico, boolean ignifugo) {
        this.nome = nome;
        this.pesoSpecifico = pesoSpecifico;
        this.ignifugo = ignifugo;
    }

    public String getNome() {
        return nome;
    }

    public double getPesoSpecifico() {
        return pesoSpecifico;
    }

    public boolean isIgnifugo() {
        return ignifugo;
    }
}

public class Stanza {

    private final String nome;
    private final Map<Materiale, Integer> materiali;

    public Stanza(String nome) {
        this.nome = Objects.requireNonNull(nome);
        this.materiali = new HashMap<>();
    }

    public String getNome() {
        return nome;
    }

    public Map<Materiale, Integer> getMateriali() {
        return materiali;
    }

    public void aggiungiMateriale(Materiale materiale, int quantita) {
        materiali.merge(materiale, quantita, Integer::sum);
    }

    public void rimuoviMateriale(Materiale materiale) {
        materiali.remove(materiale);
    }
}

public interface CertificazioneRistretta {
    boolean isCertificata(Stanza stanza);
}

public class CertificazioneSicurezza implements CertificazioneRistretta {

    private final int soglia;

    public CertificazioneSicurezza(int soglia) {
        this.soglia = soglia;
    }

    @Override
    public boolean isCertificata(Stanza stanza) {
        int sum = 0;
        Set<Map.Entry<Materiale, Integer>> set = new HashSet<>(stanza.getMateriali().entrySet());

        for (Map.Entry<Materiale, Integer> e : set) {
            if (e.getKey().isIgnifugo()) {
                sum += e.getValue();
            }
        }
        return sum < soglia;
    }

}


public class Controllo<T extends CertificazioneRistretta> {

    private final T certificazione;
    private final List<Stanza> stanze;

    public Controllo(T certificazione, List<Stanza> stanze) {
        this.certificazione = Objects.requireNonNull(certificazione);
        this.stanze = Objects.requireNonNull(stanze);
    }

    public boolean isConforme() {
        for (Stanza stanza : stanze) {
            if (!certificazione.isCertificata(stanza)) {
                return false;
            }
        }
        return true;
    }

}
