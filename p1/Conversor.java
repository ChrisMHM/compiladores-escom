 package LectorC;

import java.util.Stack;

public class Conversor {

    // Definimos los operadores que estaremos utilizando
    public static final char KLEENE = '�', POSITIVA = '�', OR = '�', CONCATENACION = '~', PAR_APERTURA = '�', PAR_CIERRE = '�';
    // Aqu� se almacenar� la expresi�n regular
    Stack<Character> caracteres;
    // Guardaremos la expresi�n regular con la que estaremos trabajando
    private String ExpresionRegular;
    // Aqu� se almacenar� el resultado
    private String Resultado;

    /**
     * Obtiene el resultado de la conversi�n
     *
     * @return
     * @throws Exception Cuando se solicita un resultado sin haber realizado la
     * conversi�n antes
     */
    public String getResultado()
            throws Exception {
        if (Resultado == null) {
            throw new Exception("No se ha convertido ninguna expresi�n");
        }
        return Resultado;
    }

    /**
     * Constructor
     *
     * @param ExpresionRegular La expresi�n regular a convertir
     */
    public Conversor(String ExpresionRegular) {
        this.ExpresionRegular = ExpresionRegular;
        Resultado = null;
        caracteres = new Stack<>();
    }

    /**
     * Declaramos el constructor por default privado para que sea inaccesible
     */
    private Conversor() {
    }

    /**
     * M�todo encargado de realizar la conversi�n de infijo a posfijo
     */
    public void convertir() {
        StringBuilder sb = new StringBuilder();
        char[] chars = ExpresionRegular.toCharArray();
        int index = 0;
        for (char c : chars) {
            index++;
            int precedencia = precedencia(c);
            if (precedencia < 0) { // Si es caracter
                sb.append(c);
            } else if (precedencia == 1) { // Si es par�ntesis de apertura
                caracteres.push(c);
            } else if (precedencia == 0) { // Si es par�ntesis de cierre
                char c1 = caracteres.pop();
                while (precedencia(c1) != 1) { // Mientras no sea par�ntesis de apertura
                    sb.append(c1);
                    c1 = caracteres.pop(); // sacamos de la pila todos los operadores que encontremos
                }
            } else { // Si es un operador
                while (!caracteres.isEmpty() // Mientras la pila no est� vac�a
                        && precedencia(caracteres.peek(), c)) { // y la precedencia del que est� en top sea mayor al que queremos guardar en ella
                    sb.append(caracteres.pop()); // sacamos el caracter que est� debajo
                }
                caracteres.push(c);
            }

            if (index == chars.length) {// Si hemos terminado de recorrer nuestra cadena
                while (!caracteres.isEmpty()) {// vaciamos la pila
                    sb.append(caracteres.pop()); // concatenando todos los operadores que quedaron en ella
                }
            }
        }
        Resultado = sb.toString();
    }

    /**
     * Funcion para determinar qu� caracter tiene mayor precedencia.
     *
     * @param uno Primer caracter a comparar
     * @param dos Segundo caracter a comparar
     * @return <code>true</code> si uno tiena mayor o igual precedencia que dos.
     */
    public static boolean precedencia(char uno, char dos) {
        return (precedencia(uno) >= precedencia(dos));
    }

    /**
     * Funci�n para determinar el grado de precedencia de un s�mbolo
     *
     * @param c
     * @return
     */
    public static int precedencia(char c) {
        if (c == KLEENE) {
            return 4;
        }
        if (c == POSITIVA) {
            return 4;
        }
        if (c == CONCATENACION) {
            return 3;
        }
        if (c == OR) {
            return 2;
        }
        if (c == PAR_APERTURA) {
            return 1;
        }
        if (c == PAR_CIERRE) {
            return 0;
        }
        return -1;
    }
}
