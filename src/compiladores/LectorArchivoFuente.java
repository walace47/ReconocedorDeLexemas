/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author js298678
 */
public class LectorArchivoFuente {

    private FileReader file;
    private BufferedReader reader;

    public LectorArchivoFuente(String archivo) throws FileNotFoundException {
        file  = new FileReader(archivo);
        reader  = new BufferedReader(file);
    }
    
    public String retornarLinea() throws IOException{
        String cadena,respuesta;
        
        if((cadena = reader.readLine()) != null){
            respuesta = cadena;
        }else{
            reader.close();
            respuesta = "Fin de archivo";
        }
        return respuesta;
    }
}
