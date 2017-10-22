public class TransicionD {

    private EstadoD Destino;
    private char CaracterDeTransicion;

    public TransicionD(EstadoD Destino, char CaracterDeTransicion) {
        this.Destino = Destino;
        this.CaracterDeTransicion = CaracterDeTransicion;
    }

    public char getCaracterDeTransicion() {
        return CaracterDeTransicion;
    }

    public EstadoD getDestino() {
        return this.Destino;
    }

    public String toString(char anterior) {
        if (CaracterDeTransicion == anterior) {
            return "," + Destino.getEtiqueta();
        } else {
            return ":" + CaracterDeTransicion + "," + Destino.getEtiqueta();
        }
    }

    public String toDotString() {
        return " -> " + Destino.getDotEtiqueta() + " [label=\"" + CaracterDeTransicion + "\"]";
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
        int hash = 7;
        hash = 97 * hash + this.CaracterDeTransicion;
        return hash;
    }
}
