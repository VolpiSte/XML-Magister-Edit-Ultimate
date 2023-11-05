import java.io.Serializable;

public class Classe implements Serializable {
    public String nome;

    public Classe() {
    }

    public Classe(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Classe\n{nome='" + nome + "'}";
    }
}
