import java.io.Serializable;

public class Provincia implements Serializable {
    public String proa;
    public Comune comune;

    public Provincia() {
    }
    
    public Provincia(String proa, Comune comune) {
        this.proa = proa;
        this.comune = comune;
    }
    

    public String getProa() {
        return proa;
    }

    public Comune getComuni() {
        return comune;
    }

    public Comune getComune() {
        return comune;
    }

    @Override
    public String toString() {
        return "Provincia\n{proa='" + proa + "', comune=" + comune + '}';
    }

    public void setProa(String nomeProvincia) {
        this.proa = nomeProvincia;
    }
}
