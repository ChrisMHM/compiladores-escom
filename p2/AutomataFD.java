import java.util.ArrayList;

public final class AutomataFD {

    private ArrayList<EstadoD> estadosD;

    public ArrayList<EstadoD> getEstadosD() {
        return estadosD;
    }

    public void setEstadosD(ArrayList<EstadoD> estadosD) {
        this.estadosD = estadosD;
    }

    public EstadoD getSiguienteNoMarcado() {
        for (EstadoD e : estadosD) {
            if (!e.isMarcado()) {
                return e;
            }
        }
        return null;
    }

    public AutomataFD(AutomataFN afn) {
        int label = 0;
        estadosD = new ArrayList<>();
        EstadoD ed = new EstadoD();
        ed.addEstadosN(AutomataFD.cerraduraEpsilon(afn.inicio));
        ed.setEtiqueta(label++);
        estadosD.add(ed);
        EstadoD e = getSiguienteNoMarcado();
        
        while (e != null) {
            e.setMarcado(true);
            for (Character c : afn.simbolos) {
                ed = new EstadoD();
                if (c == '@') {
                    continue;
                }
                for (EstadoN en : e.getEstados()) {
                    EstadoN n = mueve(en, c);
                    if (n != null) {
                        ed.addEstadosN(AutomataFD.cerraduraEpsilon(n));
                    }
                }
                if (!estadosD.contains(ed) && !ed.isEmpty()) {
                    ed.setEtiqueta(label++);
                    estadosD.add(ed);
                    e.addTransiciones(ed, c);
                } else if (!ed.isEmpty()) {
                    ed = estadosD.get(estadosD.indexOf(ed));
                    e.addTransiciones(ed, c);
                }
            }

            e = getSiguienteNoMarcado();
        }
    }

    public static EstadoN mueve(EstadoN estadoN, char simbolo) {
        for (TransicionN t : estadoN.getTransiciones(simbolo)) {
            return t.getDestino();
        }
        return null;
    }

    public static ArrayList<EstadoN> cerraduraEpsilon(EstadoN estado) {
        ArrayList<EstadoN> e = new ArrayList<>();
        e.add(estado);
        for (TransicionN T : estado.getTransiciones('@')) {
            e.addAll(cerraduraEpsilon(T.getDestino()));

        }
        return e;
    }

    @Override
    public String toString() {
        ArrayList<EstadoD> estadosImpresos = new ArrayList<>();
        String s = "";
        for (EstadoD e : this.estadosD) {
            if (!estadosImpresos.contains(e) && !e.getTransiciones().isEmpty()) {
                s += e.toString() + "\n";
                estadosImpresos.add(e);
            }
        }
        return s;
    }

    public String toDotString() {
        ArrayList<EstadoD> estadosImpresos = new ArrayList<>();
        String s = "";
        s += "digraph g{\n";
        for (EstadoD e : this.estadosD) {
            if (!estadosImpresos.contains(e)) {
                s += e.toDotString();
                estadosImpresos.add(e);
            }
        }
        for (EstadoD e : estadosImpresos) {
            if (e.isAceptacion()) {
                s += e.getDotEtiqueta() + " [shape=\"doublecircle\"]\n";
            }
            else if (e.isInicio()){
                s += e.getDotEtiqueta() + " [shape=\"circle\"]\n";
            }
        }
        s += "}";
        return s;
    }
}
