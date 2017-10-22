package LectorC;

import java.util.ArrayList;

public class EstadoN  {

    public static int EstadoActual = 0;
    private int etiqueta;
    private boolean aceptacion;
    private boolean inicio;

    private ArrayList<TransicionN> transiciones;

    public EstadoN(int etiqueta, boolean aceptacion) {
        this.etiqueta = etiqueta;
        this.aceptacion = aceptacion;
        this.transiciones = new ArrayList<>();
    }

    public void clearTransiciones() {
        transiciones.clear();
    }
    public ArrayList<TransicionN> getTransiciones() {
        return transiciones;
    }

    public ArrayList<TransicionN> getTransiciones(char simbolo) {
        ArrayList<TransicionN> trans = new ArrayList<>();
        for (TransicionN T : transiciones) {
            if (T.getCaracterDeTransicion() == simbolo) {
                trans.add(T);
            }
        }
        return trans;
    }

    public String getEtiqueta() {
        if (aceptacion) {
            return etiqueta + "**";
        } else if (inicio) {
            return etiqueta + "*";
        }
        return String.valueOf(etiqueta);
    }
    public void addTransicion(char caracter, EstadoN e) {
        this.addTransicion(new TransicionN(e, caracter));
    }

    public void addTransicion(TransicionN T) {
        transiciones.add(T);
    }

    public boolean removeTransicion(EstadoN e) {
        return transiciones.remove(new TransicionN(e, '@'));
    }
    
    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    @Override
    public String toString() {
        char anterior = '\n';
        StringBuilder sb = new StringBuilder(getEtiqueta());
        for (TransicionN T : transiciones) {
            sb.append(T.toString(anterior));
            anterior = T.getCaracterDeTransicion();
        }
        return sb.toString();
    }

    public String toDotString() {
        StringBuilder sb = new StringBuilder();
        for (TransicionN T : transiciones) {
            sb.append(getEtiqueta()).append(T.toDotString() + "\n");
        }
        return sb.toString();
    }

    public static EstadoN newEstado() {
        EstadoN e = new EstadoN(EstadoActual++, false);
        return e;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o instanceof EstadoN) {
            EstadoN o1 = (EstadoN) o;
            if (o1.etiqueta == this.etiqueta) {
                return true;
            }
        }
        return false;
    }

}
