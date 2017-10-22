/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package LectorC;

/**
 *
 * @author Antonio
 */
public class Simbolo {

    private String componenteLexico, lexema, valor;
    private int linea;

    public Simbolo(String componenteLexico, String lexema, String valor, int linea) {
        this.componenteLexico = componenteLexico;
        this.lexema = lexema;
        this.linea = linea;
        this.valor = getValor(componenteLexico, lexema);
    }
    
    
    public String toString(){
        String tab1= "";
        String tab2 = "";
        int tabs = 4;
        int t1 = 0;
        int t2 = 0;
        for(t1 = componenteLexico.length() / 8; t1 < tabs; t1++){
            tab1 += "\t";
        }
        for(t1 = lexema.length() / 8; t1 < tabs; t1++){
            tab2 += "\t";
        }
        return componenteLexico + tab1+lexema + tab2 +valor;
    }
    
    public String getValor(String componenteLexico, String lexema){
        String val = "";
        if(componenteLexico.equals("NUMERO FLOTANTE") 
                || componenteLexico.equals("NUMERO ENTERO")
                || componenteLexico.equals("IDENTIFICADOR")
                || componenteLexico.equals("ESPECIFICADOR DE FORMATO")
                || componenteLexico.equals("CARACTER ESPECIAL")){
            val = lexema;
        }else if(componenteLexico.equals("PALABRA RESERVADA") 
                || componenteLexico.equals("ESTRUCTURA REPETITIVA")
                || componenteLexico.equals("ESTRUCTURA SELECTIVA")
                || componenteLexico.equals("TIPO DE DATO")){
            val = lexema.toUpperCase();
        }else if(componenteLexico.startsWith("OPERADOR")){
            if(lexema.equals("++")){
                val = "INCREMENTO";
            }else if(lexema.equals("--")){
                val = "DECREMENTO";
            }else if(lexema.startsWith("+")){
                val = "SUMA";
            }else if(lexema.startsWith("-")){
                val = "RESTA";
            }else if(lexema.startsWith("/")){
                val = "DIVISION";
            }else if(lexema.startsWith("*")){
                val = "MULTIPLICACION";
            }else if(lexema.startsWith("%")){
                val = "MODULO";
            }else if(lexema.equals("=")){
                val="ASIGNACION";
            }else if(lexema.equals("<")){
                val="MENOR QUE";
            }else if(lexema.equals(">")){
                val="MAYOR QUE";
            }else if(lexema.equals("<=")){
                val="MENOR O IGUAL QUE";
            }else if(lexema.equals(">=")){
                val="MAYOR O IGUAL QUE";
            }else if(lexema.equals("!=")){
                val="DISTINTO DE";
            }else if(lexema.equals("==")){
                val="IGUAL A";
            }else if(lexema.equals("!")){
                val="NEGACION";
            }else if(lexema.equals("&&")){
                val="AND LOGIGO";
            }else if(lexema.equals("||")){
                val="OR LOGIGO";
            }
        }
        return val;
    }
}
