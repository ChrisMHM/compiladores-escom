package LectorC;

import java.util.ArrayList;

public class EstadoD {

    // Un estado determinista contiene n estados no-deterministas
    private ArrayList<EstadoN> estados;
    // Se un estado contiene transiciones
    private ArrayList<TransicionD> transiciones;
    // Indica que el estado es de inicio
    private boolean inicio;
    // Indica si el estado es de aceptación
    private boolean aceptacion;
    // La etiqueta del estado
    private int etiqueta;
    // Para apliicar el algoritmo, necesitamos saber marcarlo
    private boolean marcado;

    /**
     * Indica si el estado es de aceptación
     *
     * @return
     */
    public boolean isAceptacion() {
        return aceptacion;
    }

    /**
     * Establece si el estado es o no de aceptacion
     *
     * @param aceptacion
     */
    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }

    /**
     * Establece las trancisiones de este estado
     *
     * @param transiciones
     */
    public void setTransiciones(ArrayList<TransicionD> transiciones) {
        this.transiciones = transiciones;
    }

    /**
     * Obtene las trancisiones de este estado hacia otros
     *
     * @return
     */
    public ArrayList<TransicionD> getTransiciones() {
        return transiciones;
    }

    public ArrayList<TransicionD> getTransiciones(char c) {
        ArrayList<TransicionD> T = new ArrayList<>();
        for (TransicionD tr : transiciones) {
            if (tr.getCaracterDeTransicion() == c) {
                T.add(tr);
            }
        }
        return T;
    }

    /**
     * Agrega una trancisión hacia {@code destino}, mediante
     * {@code caracterDeTrancision}
     *
     * @param destino
     * @param caracterDeTransicion
     */
    public void addTransiciones(EstadoD destino, char caracterDeTransicion) {
        this.transiciones.add(new TransicionD(destino, caracterDeTransicion));
    }

    /**
     * Obtiene la etiqueta del estado, formateada correspondientemente
     *
     * @return
     */
    public String getEtiqueta() {
        if (aceptacion) {
            return etiqueta + "**";
        } else if (inicio) {
            return etiqueta + "*";
        }
        return String.valueOf(etiqueta);
    }

    /**
     * Obtiene la etiqueta del estado, para la impresión de dot
     *
     * @return
     */
    public String getDotEtiqueta() {
        return String.valueOf(etiqueta);
    }

    /**
     * Establece la etiqueta del estaddo
     *
     * @param etiqueta
     */
    public void setEtiqueta(int etiqueta) {
        this.etiqueta = etiqueta;
    }

    /**
     * Indica si el {@code estado D} contiene o no {@code estados N}
     *
     * @return
     */
    public boolean isEmpty() {
        return estados.isEmpty();
    }

    /**
     * Añade los {@code estados N} a la lista de los estados
     *
     * @param estadoNs
     */
    public void addEstadosN(ArrayList<EstadoN> estadoNs) {
        for (EstadoN e : estadoNs) {
            if (!estados.contains(e)) {
                if (e.isAceptacion()) {
                    aceptacion = true;
                } else if (e.isInicio()) {
                    inicio = true;
                }
                estados.add(e);
            }
        }
    }

    /**
     * Indica si este estado ya ha sido visitado
     *
     * @return
     */
    public boolean isMarcado() {
        return marcado;
    }

    /**
     * Establece la propiedad marcado
     *
     * @param marcado
     */
    public void setMarcado(boolean marcado) {
        this.marcado = marcado;
    }

    /**
     * Obtiene los {@code estados} que contiene el estado
     *
     * @return
     */
    public ArrayList<EstadoN> getEstados() {
        return estados;
    }

    /**
     * Indica si este es el estado de inicio del autómata
     *
     * @return
     */
    public boolean isInicio() {
        return inicio;
    }

    public void setInicio(boolean inicio) {
        this.inicio = inicio;
    }

    @Override
    public boolean equals(Object obj) {

        EstadoD e = (EstadoD) obj;
        for (EstadoN d : e.estados) {
            if (!this.estados.contains(d)) {
                return false;
            }
        }
        for (EstadoN d : this.estados) {
            if (!e.estados.contains(d)) {
                return false;
            }
        }
        return true;
    }

    public EstadoD() {
        estados = new ArrayList<>();
        transiciones = new ArrayList<>();
        marcado = false;
        aceptacion = false;

    }

    public void printEstados() {
        System.out.print("=======\nEstados de " + getEtiqueta() + "\n");
        for (EstadoN e : estados) {
            System.out.println(e);
        }
    }

    @Override
    public String toString() {
        char anterior = '\n';
        StringBuilder sb = new StringBuilder(String.valueOf(etiqueta));
        for (TransicionD T : transiciones) {
            sb.append(T.toString(anterior));
            anterior = T.getCaracterDeTransicion();
        }
        return sb.toString();
    }

    public String toDotString() {
        StringBuilder sb = new StringBuilder();
        for (TransicionD T : transiciones) {
            sb.append(getDotEtiqueta()).append(T.toDotString()).append("\n");
        }
        return sb.toString();
    }
}
