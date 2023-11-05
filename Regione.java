import java.io.Serializable;

public class Regione implements Serializable {
    public String luogo;
    public Provincia provincia;

    public Regione() {
    }

    public Regione(String luogo, Provincia provincia) {
        this.luogo = luogo;
        this.provincia = provincia;
    }

    public String getLuogo() {
        return luogo;
    }

    public Provincia getProvince() {
        return provincia;
    }

    public void setLuogo(String luogo) {
        this.luogo = luogo;
    }

    public Provincia getProvincia() {
        return provincia;
    }

    public void setProvincia(Provincia provincia) {
        this.provincia = provincia;
    }

    @Override
    public String toString() {
        return "Regione\n{luogo='" + luogo + "', provincia=" + provincia + '}';
    }
}
