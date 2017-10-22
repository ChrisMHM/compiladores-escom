package LectorC;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Lector {
    
    int lineas;
    String route;
    File file;
    BufferedReader in;
    
    public Lector(String route)
            throws FileNotFoundException {
        this.route = route;
        file = new File(this.route);
        
        FileInputStream fstream = new FileInputStream(file);
        DataInputStream dis = new DataInputStream(fstream);
        in = new BufferedReader(new InputStreamReader(dis));
        
    }
    
    public ArrayList<Linea> read()
            throws IOException {
        String strLine;
        ArrayList<Linea> Lineas = new ArrayList<>();
        lineas = 0;
        while ((strLine = in.readLine()) != null) {
            String[] lineSymbols = limpiaEspacios(strLine.split("\\ "));
            Linea linea = new Linea(strLine.length(), lineSymbols, (++lineas));
            Lineas.add(linea);
        }
        return Lineas;
    }
    
    private String[] limpiaEspacios(String[] simbolos){
        int i = 0;
        for(int y = 0; y < simbolos.length; y++){
            simbolos[y] = simbolos[y].replaceAll("\t", "");
        }
        for(String s : simbolos){
            if(!"".equals(s)){
                i++;
            }
        }
        String[] x = new String[i];
        i = 0;
        for(String s : simbolos){
            if(!"".equals(s)){
                x[i++] = s;
            }
        }
        return x;
    }
}

