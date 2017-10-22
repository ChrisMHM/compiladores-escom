/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LectorC;

/**
 *
 * @author Antonio
 */
public class Linea {
    private String [] simbolos;
    private int longitud;
    private int numeroDeLinea;
    private boolean vacia;
    
    public Linea(int longitud, String[] simbolos,int numeroDeLinea){
        this.longitud = longitud;
        this.simbolos = simbolos;
        this.numeroDeLinea = numeroDeLinea;
        vacia = true;
        for(String s:simbolos){
            vacia &= "".equals(s);
            if(!vacia)
                break;
        }
    }

    public String[] getSimbolos() {
        return simbolos;
    }

    public int getLongitud() {
        return longitud;
    }

    public int getNumeroDeLinea() {
        return numeroDeLinea;
    }

    public boolean isVacia() {
        return vacia;
    }
    
}
