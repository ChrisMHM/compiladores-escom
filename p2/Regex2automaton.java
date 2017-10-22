import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Regex2automaton {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
//            Conversor conversor = new Conversor("a*");
            String outputAFN = null, outputAFD = null, regex;
            if (args.length > 0) {
				//log(args[0]);
                regex = args[0];
                if (args.length == 3) {
                    outputAFN = args[1];
                    outputAFD = args[2];
                }else if(args.length == 4){
                    
                }
            }
            else{
                return;
            }
            Conversor conversor = new Conversor(regex);
            conversor.convertir();
            String resultado = conversor.getResultado();
            
            AutomataFN AFN = AutomataFN.nuevoAutomataDesdeExpresionRegular(resultado);
            
            FileWriter fw;
            PrintWriter pw;
            if(outputAFN != null){
                // Escritura archivo
                fw = new FileWriter(outputAFN);
                pw = new PrintWriter(fw);
                pw.print(AFN);
                fw.close();
            }
            else{
                log("AFN:\n");
                log(AFN.toString());
            }
            AutomataFD AFD = new AutomataFD(AFN);
            if(outputAFD != null){
                // Escritura archivo
                
                fw = new FileWriter(outputAFD);
                pw = new PrintWriter(fw);
                pw.print(AFD);
                fw.close();
                        
            }
            else{
                log("AFD:\n");
                log(AFD.toDotString());
            }

        } catch (Exception ex) {
            Logger.getLogger(Regex2automaton.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void log(String create_automata_uno) {
        System.out.println(create_automata_uno);
    }
}
