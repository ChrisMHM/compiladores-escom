package LectorC;

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
            } else if (e.isInicio()) {
                s += e.getDotEtiqueta() + " [shape=\"circle\"]\n";
            }
        }
        s += "}";
        return s;
    }

    public String test(String palabra) {
        String palabraReturn = null;
        if (palabra.equals("")) {
            return null;
        }
       //log("Voy a testear: " + palabra + " en el automata " + nombre);
        StringBuilder sb = new StringBuilder();
        char[] chars = palabra.toCharArray();
        EstadoD eD = estadosD.get(0);
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            ArrayList<TransicionD> transiciones = eD.getTransiciones(c);
            if (transiciones.isEmpty()) {
                break;
            }
            eD = transiciones.get(0).getDestino();
            //log("\tCuando i vale " + i + ", la pila " + transiciones.isEmpty() + " vacia con el simbolo " + c + "  y el estado " + eD.isAceptacion());
            if (eD.isAceptacion()
                    && i == (chars.length - 1)) {
                sb.append(c);
                palabraReturn = sb.toString();
                break;
            }

            if (eD.isAceptacion()
                    && i < (chars.length - 1)) {
                transiciones = eD.getTransiciones(chars[i + 1]);
//                log("\t\tSiguiente caracter " + chars[i + 1]);
                if (transiciones.isEmpty()) {
                    sb.append(c);
                    palabraReturn = sb.toString();
                    break;
                }
            }

            sb.append(c);
        }

        return palabraReturn;
    }

    private void log(String msg) {
        System.out.println(msg);
    }
    private String nombre;

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getNombre(){
        return this.nombre;
    }
    
    private boolean error;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
    
    
}
