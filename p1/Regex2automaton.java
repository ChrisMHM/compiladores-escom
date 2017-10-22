package LectorC;


public class Regex2automaton {

    public AutomataFD automataDeExpresionRegular(String regex, String nombre, String ok) {
        Conversor conversor = new Conversor(regex);
        conversor.convertir();
        String resultado = null;
        try {
            resultado = conversor.getResultado();
        } catch (Exception ex) {
            System.err.println("Ocurrió una excepción: "+ ex.getMessage());
            System.exit(1);
        }

        AutomataFN AFN = AutomataFN.nuevoAutomataDesdeExpresionRegular(resultado);
        AutomataFD AFD = new AutomataFD(AFN);
        AFD.setNombre(nombre);
        AFD.setError(!ok.equals("OK"));
        return AFD;
    }
}
