package LectorC;

import java.util.ArrayList;

public final class AutomataFN {

    public EstadoN inicio, aceptacion;
    public ArrayList<EstadoN> estados;
    public ArrayList<Character> simbolos;

    public AutomataFN() {
        
    }

    public static AutomataFN nuevoAutomataDesdeExpresionRegular(String regexp) {
        ArrayList<Character> sim = new ArrayList<>();
        ArrayList<AutomataFN> automatasAFN = new ArrayList<>();
        for (char c : regexp.toCharArray()) {

            int op = Conversor.precedencia(c);
            int index = automatasAFN.size();
            AutomataFN automata = null;
            sim.add('@');
            if (op < 0) {// Caracter
                if (!sim.contains(c)) {
                    sim.add(c);
                }
                automata = AutomataFN.nuevoAutomataSimple(c);
                automatasAFN.add(index, automata);
            } else if (op == 2) {// OR
                automata = AutomataFN.nuevoAutomataOR(automatasAFN.remove(index - 2), automatasAFN.remove(index - 2));
                automatasAFN.add(automatasAFN.size(), automata);
            } else if (op == 3) {// Concatenación
                automata = AutomataFN.nuevoAutomataConcatenado(automatasAFN.remove(index - 2), automatasAFN.remove(index - 2));
                automatasAFN.add(automatasAFN.size(), automata);
            } else if (op == 4) {// Kleene o positiva
                if (Conversor.POSITIVA == c) {
                    automata = AutomataFN.nuevoAutomataPositiva(automatasAFN.remove(index - 1));
                } else {
                    automata = AutomataFN.nuevoAutomataKleene(automatasAFN.remove(index - 1));
                }
                automatasAFN.add(automatasAFN.size(), automata);
            }
        }
        if (automatasAFN.size() == 1) {
            AutomataFN a = automatasAFN.get(0);
            a.simbolos = sim;
            return a;
        }
        return null;
    }

    public static AutomataFN nuevoAutomataSimple(char transicion) {
        AutomataFN nuevo = new AutomataFN();
        nuevo.inicio = EstadoN.newEstado();
        nuevo.inicio.setInicio(true);
        nuevo.aceptacion = EstadoN.newEstado();
        nuevo.aceptacion.setAceptacion(true);
        nuevo.inicio.addTransicion(transicion, nuevo.aceptacion);
        nuevo.estados = new ArrayList<>();
        nuevo.estados.add(0, nuevo.inicio);
        nuevo.estados.add(nuevo.estados.size(), nuevo.aceptacion);
        return nuevo;
    }

    public static AutomataFN nuevoAutomataConcatenado(AutomataFN uno, AutomataFN dos) {
        AutomataFN nuevo = new AutomataFN();
        nuevo.inicio = uno.inicio;
        nuevo.inicio.setInicio(true);
        nuevo.aceptacion = dos.aceptacion;
        for (TransicionN t : dos.inicio.getTransiciones()) {
            uno.aceptacion.addTransicion(t);
        }
        dos.inicio.clearTransiciones();
        dos.inicio.setInicio(false);
        dos.estados.remove(dos.inicio);
        dos.inicio = uno.aceptacion;


        uno.aceptacion.setAceptacion(false);
        nuevo.aceptacion.setAceptacion(true);

        nuevo.estados = new ArrayList<>();
        nuevo.estados.add(0, nuevo.inicio);
        nuevo.estados.addAll(nuevo.estados.size(), uno.estados);
        nuevo.estados.addAll(nuevo.estados.size(), dos.estados);
        nuevo.estados.add(nuevo.estados.size(), nuevo.aceptacion);

        return nuevo;
    }

    public static AutomataFN nuevoAutomataOR(AutomataFN uno, AutomataFN dos) {
        AutomataFN nuevo = new AutomataFN();
        nuevo.inicio = EstadoN.newEstado();
        nuevo.inicio.setInicio(true);
        nuevo.aceptacion = EstadoN.newEstado();

        nuevo.inicio.addTransicion('@', uno.inicio);
        nuevo.inicio.addTransicion('@', dos.inicio);

        uno.inicio.setInicio(false);
        dos.inicio.setInicio(false);

        uno.aceptacion.addTransicion('@', nuevo.aceptacion);
        dos.aceptacion.addTransicion('@', nuevo.aceptacion);

        uno.aceptacion.setAceptacion(false);
        dos.aceptacion.setAceptacion(false);
        nuevo.aceptacion.setAceptacion(true);

        nuevo.estados = new ArrayList<>();
        nuevo.estados.add(0, nuevo.inicio);
        nuevo.estados.addAll(nuevo.estados.size(), uno.estados);
        nuevo.estados.addAll(nuevo.estados.size(), dos.estados);
        nuevo.estados.add(nuevo.estados.size(), nuevo.aceptacion);

        return nuevo;
    }

    public static AutomataFN nuevoAutomataPositiva(AutomataFN uno) {
        //Generamos el nuevo autómata
        AutomataFN nuevo = new AutomataFN();
        nuevo.inicio = EstadoN.newEstado();
        nuevo.inicio.setInicio(true);
        nuevo.aceptacion = EstadoN.newEstado();

        uno.aceptacion.addTransicion('@', uno.inicio);
        uno.inicio.setInicio(false);
        nuevo.inicio.addTransicion('@', uno.inicio);
        uno.aceptacion.addTransicion('@', nuevo.aceptacion);


        nuevo.aceptacion.setAceptacion(true);
        uno.aceptacion.setAceptacion(false);

        nuevo.estados = new ArrayList<>();
        nuevo.estados.add(0, nuevo.inicio);
        nuevo.estados.addAll(nuevo.estados.size(), uno.estados);
        nuevo.estados.add(nuevo.estados.size(), nuevo.aceptacion);

        return nuevo;
    }

    public static AutomataFN nuevoAutomataKleene(AutomataFN uno) {
        AutomataFN nuevo = new AutomataFN();
        nuevo.inicio = EstadoN.newEstado();
        nuevo.inicio.setInicio(true);
        nuevo.aceptacion = EstadoN.newEstado();

        uno.aceptacion.addTransicion('@', uno.inicio);
        nuevo.inicio.addTransicion('@', uno.inicio);
        uno.aceptacion.addTransicion('@', nuevo.aceptacion);
        nuevo.inicio.addTransicion('@', nuevo.aceptacion);
        uno.inicio.setInicio(false);

        nuevo.aceptacion.setAceptacion(true);
        uno.aceptacion.setAceptacion(false);

        nuevo.estados = new ArrayList<>();
        nuevo.estados.add(0, nuevo.inicio);
        nuevo.estados.addAll(nuevo.estados.size(), uno.estados);
        nuevo.estados.add(nuevo.estados.size(), nuevo.aceptacion);

        return nuevo;
    }

    @Override
    public String toString() {
        ArrayList<EstadoN> estadosImpresos = new ArrayList<>();
        String s = "";
        for (EstadoN e : this.estados) {
            if (!e.isAceptacion() && !estadosImpresos.contains(e)) {
                s += e.toString() + "\n";
                estadosImpresos.add(e);
            }
        }
        return s;
    }

    public String toDotString() {
        ArrayList<EstadoN> estadosImpresos = new ArrayList<>();
        String s = "";
        for (EstadoN e : this.estados) {
            if (!e.isAceptacion() && !estadosImpresos.contains(e)) {
                s += e.toDotString();
                estadosImpresos.add(e);
            }
        }
        return s;
    }
}
