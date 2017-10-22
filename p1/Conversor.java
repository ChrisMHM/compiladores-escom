 package LectorC;

import java.util.Stack;

public class Conversor {

    // Definimos los operadores que estaremos utilizando
    public static final char KLEENE = '¥', POSITIVA = '†', OR = '‡', CONCATENACION = '~', PAR_APERTURA = '«', PAR_CIERRE = '»';
    // Aquí se almacenará la expresión regular
    Stack<Character> caracteres;
    // Guardaremos la expresión regular con la que estaremos trabajando
    private String ExpresionRegular;
    // Aquí se almacenará el resultado
    private String Resultado;

    /**
     * Obtiene el resultado de la conversión
     *
     * @return
     * @throws Exception Cuando se solicita un resultado sin haber realizado la
     * conversión antes
     */
    public String getResultado()
            throws Exception {
        if (Resultado == null) {
            throw new Exception("No se ha convertido ninguna expresión");
        }
        return Resultado;
    }

    /**
     * Constructor
     *
     * @param ExpresionRegular La expresión regular a convertir
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
     * Método encargado de realizar la conversión de infijo a posfijo
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
            } else if (precedencia == 1) { // Si es paréntesis de apertura
                caracteres.push(c);
            } else if (precedencia == 0) { // Si es paréntesis de cierre
                char c1 = caracteres.pop();
                while (precedencia(c1) != 1) { // Mientras no sea paréntesis de apertura
                    sb.append(c1);
                    c1 = caracteres.pop(); // sacamos de la pila todos los operadores que encontremos
                }
            } else { // Si es un operador
                while (!caracteres.isEmpty() // Mientras la pila no esté vacía
                        && precedencia(caracteres.peek(), c)) { // y la precedencia del que esté en top sea mayor al que queremos guardar en ella
                    sb.append(caracteres.pop()); // sacamos el caracter que esté debajo
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
     * Funcion para determinar qué caracter tiene mayor precedencia.
     *
     * @param uno Primer caracter a comparar
     * @param dos Segundo caracter a comparar
     * @return <code>true</code> si uno tiena mayor o igual precedencia que dos.
     */
    public static boolean precedencia(char uno, char dos) {
        return (precedencia(uno) >= precedencia(dos));
    }

    /**
     * Función para determinar el grado de precedencia de un símbolo
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
