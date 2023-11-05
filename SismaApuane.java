import java.io.Serializable;
import java.util.ArrayList;

public class SismaApuane implements Serializable {
    protected ArrayList<Regione> regioni;

    public SismaApuane(ArrayList<Regione> regioni) {
        this.regioni = regioni;
    }
    
    public SismaApuane() {
        regioni = new ArrayList<>();
    }

    public void removeRegione(Regione regione) {
        regioni.remove(regione);
    }

    @Override
    public String toString() {
        return "SismaApuane\n{regioni=" + regioni + '}';
    }

    public void addRegione(Regione regione) {
        regioni.add(regione);
    }

    public ArrayList<Regione> getRegioni() {
        return regioni;
    }
    
    public void updateRegione(Regione updatedRegione) {
        for (int i = 0; i < regioni.size(); i++) {
            if (regioni.get(i).getLuogo().equals(updatedRegione.getLuogo())) {
                regioni.set(i, updatedRegione);
                break;
            }
        }
    }

    public void deleteRegione(String luogo) {
        Regione regioneToRemove = null;
        for (Regione regione : regioni) {
            if (regione.getLuogo().equals(luogo)) {
                regioneToRemove = regione;
                break;
            }
        }
        if (regioneToRemove != null) {
            regioni.remove(regioneToRemove);
        }
    }
    
}
