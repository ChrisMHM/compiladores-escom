public class TransicionN {

    private EstadoN Destino;
    private char CaracterDeTransicion;

    public TransicionN(EstadoN Destino, char CaracterDeTransicion) {
        this.Destino = Destino;
        this.CaracterDeTransicion = CaracterDeTransicion;
    }

    public char getCaracterDeTransicion() {
        return CaracterDeTransicion;
    }

    public EstadoN getDestino() {
        return this.Destino;
    }

    public String toString(char anterior) {
        if(CaracterDeTransicion == anterior){
            return "," + Destino.getEtiqueta();
        }else {
            return ":" +CaracterDeTransicion + "," + Destino.getEtiqueta();
        }
    }
    
    public String toDotString() {
        return " -> " + Destino.getEtiqueta() + " [label=\"" + CaracterDeTransicion + "\"]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof TransicionN) {
            TransicionN o1 = (TransicionN) o;
            if (o1.getDestino().equals(this.getDestino())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + this.CaracterDeTransicion;
        return hash;
    }
}
