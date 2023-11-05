import java.io.Serializable;

public class Comune implements Serializable {
        public String nome;
        public int proprietaPubblica;
        public int proprietaPrivata;
        public Classe classe; // Utilizza la classe Classe
    
        public Comune() {
            classe = new Classe();
        }
    
        public Comune(String nome, int proprietaPubblica, int proprietaPrivata, Classe classe) {
            this.nome = nome;
            this.proprietaPubblica = proprietaPubblica;
            this.proprietaPrivata = proprietaPrivata;
            this.classe = classe;
        }
        
    
        public String getNome() {
            return nome;
        }
    
        public void setNome(String nome) {
            this.nome = nome;
        }
    
        public int getProprietaPubblica() {
            return proprietaPubblica;
        }
    
        public void setProprietaPubblica(int proprietaPubblica) {
            this.proprietaPubblica = proprietaPubblica;
        }
    
        public int getProprietaPrivata() {
            return proprietaPrivata;
        }
    
        public void setProprietaPrivata(int proprietaPrivata) {
            this.proprietaPrivata = proprietaPrivata;
        }
    
        public Classe getClasse() {
            return classe;
        }
    
        public void setClasse(Classe classe) {
            this.classe = classe;
        }
        
        @Override
        public String toString() {
            return "Comune\n{nome='" + nome + "', proprietaPubblica=" + proprietaPubblica + ", proprietaPrivata=" + proprietaPrivata + ", classe=" + classe + '}';
        }
}
