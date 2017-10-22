package LectorC;

public class ErrorLexico {

    private int linea;
    private String lexema;

    public ErrorLexico(int linea, String lexema) {
        this.linea = linea;
        this.lexema = lexema;
    }
    
    public String toString(){
        
        return lexema + "\t\t" +linea;
    }
}
