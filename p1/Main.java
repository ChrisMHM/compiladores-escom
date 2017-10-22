package LectorC;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {
        //<editor-fold desc="Declaramos nuestras expresiones regulares en el orden que las deseamos" defaultstate="collapsed">
        String[][] regexes = {{
                "«0‡1‡2‡3‡4‡5‡6‡7‡8‡9»†~«a‡b‡c‡d‡e‡f‡g‡h‡i‡j‡k‡l‡m‡n‡o‡p‡q‡r‡s‡t‡u‡v‡w‡x‡y‡z‡A‡B‡C‡D‡E‡F‡G‡H‡I‡J‡K‡L‡M‡N‡O‡P‡Q‡R‡S‡T‡U‡V‡W‡X‡Y‡Z‡_»†~«a‡b‡c‡d‡e‡f‡g‡h‡i‡j‡k‡l‡m‡n‡o‡p‡q‡r‡s‡t‡u‡v‡w‡x‡y‡z‡A‡B‡C‡D‡E‡F‡G‡H‡I‡J‡K‡L‡M‡N‡O‡P‡Q‡R‡S‡T‡U‡V‡W‡X‡Y‡Z‡_‡0‡1‡2‡3‡4‡5‡6‡7‡8‡9»¥"
                ,"«0‡1‡2‡3‡4‡5‡6‡7‡8‡9»†~«.~«0‡1‡2‡3‡4‡5‡6‡7‡8‡9»†»"
                ,"«0‡1‡2‡3‡4‡5‡6‡7‡8‡9»†"
                , "«m~a~i~n»‡«g~o~t~o»‡«r~e~t~u~r~n»‡«s~i~g~n~e~d»‡«s~i~z~e~o~f»‡«t~y~p~e~d~e~f»‡«u~n~i~o~n»‡«u~n~s~i~g~n~e~d»"
                , "«f~o~r»‡«b~r~e~a~k»‡«c~o~n~t~i~n~u~e»‡«d~o»‡«w~h~i~l~e»"
                , "«s~w~i~t~c~h»‡«c~a~s~e»‡«e~l~s~e»‡«i~f»‡«d~e~f~a~u~l~t»"
                , "«c~h~a~r»‡«i~n~t»‡«l~o~n~g»‡«s~h~o~r~t»‡«f~l~o~a~t»‡«d~o~u~b~l~e»‡«v~o~i~d»" 
                ,"«a‡b‡c‡d‡e‡f‡g‡h‡i‡j‡k‡l‡m‡n‡o‡p‡q‡r‡s‡t‡u‡v‡w‡x‡y‡z‡A‡B‡C‡D‡E‡F‡G‡H‡I‡J‡K‡L‡M‡N‡O‡P‡Q‡R‡S‡T‡U‡V‡W‡X‡Y‡Z‡_»†~«a‡b‡c‡d‡e‡f‡g‡h‡i‡j‡k‡l‡m‡n‡o‡p‡q‡r‡s‡t‡u‡v‡w‡x‡y‡z‡A‡B‡C‡D‡E‡F‡G‡H‡I‡J‡K‡L‡M‡N‡O‡P‡Q‡R‡S‡T‡U‡V‡W‡X‡Y‡Z‡_‡0‡1‡2‡3‡4‡5‡6‡7‡8‡9»¥"
                ,"%~«d‡i‡u‡o‡x‡X‡f‡F‡e‡E‡g‡G‡a‡A‡c‡s‡p‡n‡%»"
                , "«+~+»‡«-~-»‡«+‡-‡*‡/‡%»~«=»"
                , "«+‡-‡*‡/‡%»"
                , "<‡>‡«>~=»‡«<~=»‡«!~=»‡«=~=»"
                , "="
                , "!‡«&~&»‡«|~|»"
                , "{‡}‡[‡]‡(‡)‡;‡,‡."
            }, {
                "ERROR"
                ,"NUMERO FLOTANTE"
                ,"NUMERO ENTERO"
                ,"PALABRA RESERVADA"
                ,"ESTRUCTURA REPETITIVA"
                ,"ESTRUCTURA SELECTIVA"
                ,"TIPO DE DATO" 
                ,"IDENTIFICADOR"
                ,"ESPECIFICADOR DE FORMATO"
                ,"OPERADOR INCREMENTO"
                ,"OPERADOR ARITMETICO"
                ,"OPERADOR RELACIONAL"
                ,"OPERADOR ASIGNACION"
                ,"OPERADOR LOGICO"
                ,"CARACTER ESPECIAL"
            },{
                "NO"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
                ,"OK"
            }};
        //</editor-fold>
        //<editor-fold desc="Creacion de los automatas" defaultstate="collapsed">
        int numero_de_automatas = regexes[0].length;
        int ix = 0;
        AutomataFD[] automatas = new AutomataFD[regexes[0].length];
        Regex2automaton r = new Regex2automaton();
        for (ix = 0; ix < numero_de_automatas; ix++) {
            //(log(""+ ix);
            automatas[ix] = r.automataDeExpresionRegular(regexes[0][ix], regexes[1][ix],regexes[2][ix]);
        }
        //</editor-fold>
        
        // Leemos las lineas de nuestro archivo
        Lector l;
        ArrayList<Linea> ls = null;
        // Declaramos los arreglos en los que guardaremos los lexemas (componentes y errores)
        ArrayList<Simbolo> simbolos = new ArrayList<>();
        ArrayList<ErrorLexico> errores = new ArrayList<>();
        
        
        try {
            l = new Lector(args[0]);
            ls = l.read();
        } catch (FileNotFoundException ex) {
            System.err.println("Hubo un error tratando de encontrar tu archivo");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println("Hubo una excepción: " + ex.getMessage());
            System.exit(1);
        }
        for (Linea ll : ls) {
            if (!ll.isVacia()) {
                for (String s : ll.getSimbolos()) {
                    String cadenaATrabajar = new String(s);
                    for (ix = 0; ix < numero_de_automatas;) {
                        AutomataFD evaluando = automatas[ix];
                        String cEvaluada = evaluando.test(cadenaATrabajar);
                        if (cEvaluada != null) {
                            if(evaluando.isError()){
                                errores.add(new ErrorLexico(ll.getNumeroDeLinea(), cEvaluada));
                            }else{
                            simbolos.add(new Simbolo(evaluando.getNombre(), cEvaluada, null, ll.getNumeroDeLinea()));
                            }
                            if (cEvaluada.length() == cadenaATrabajar.length()) {
                                break;
                            }
                            cadenaATrabajar = cadenaATrabajar.substring(cEvaluada.length());
                            ix = 0;
                            continue;
                        }else if(ix == (numero_de_automatas - 1)){
                            // Llegamos al ultimo automata
                            String no_reconocido = cadenaATrabajar.substring(0,1);
                            errores.add(new ErrorLexico(ll.getNumeroDeLinea(), no_reconocido));
                            if(cadenaATrabajar.length() == 1){
                                break;
                            }
                            cadenaATrabajar = cadenaATrabajar.substring(1);
                            ix = 0;
                            continue;
                        }
                        ix++;
                    }
                }
            }
        }
        try {
            imprime(errores, simbolos,args[2], args[1]);
        } catch (IOException ex) {
            System.err.println("Hubo una excepción: " + ex.getMessage());
            System.exit(1);
        }
    }
    
    
    private static void imprime(ArrayList<ErrorLexico> errores, ArrayList<Simbolo> simbolos, String err, String sim) 
            throws IOException{
        FileWriter fw;
            PrintWriter pw;
                // Escritura archivo
                fw = new FileWriter(err);
                pw = new PrintWriter(fw);
                pw.println("Linea del error\t\tSimbolo");
                for(ErrorLexico el : errores){
                pw.println(el.toString());}
                fw.close();
                
                // Escritura archivo
                fw = new FileWriter(sim);
                pw = new PrintWriter(fw);
                pw.println("Componente lexico\t\tLexema\t\t\tValor");
                for(Simbolo s : simbolos){
                pw.println(s.toString());}
                fw.close();
    }

    private static void log(String m) {
        System.out.println(m);
    }
}
