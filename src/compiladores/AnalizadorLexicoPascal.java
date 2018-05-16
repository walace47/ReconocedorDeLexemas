/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.util.HashMap;

/**
 *
 * @author js298678
 */
public class AnalizadorLexicoPascal {

    /**
     * @param args the command line arguments
     */
    private final static String expresionRegularID = "[a-zA-Z_][a-zA-Z_0-9]*";
    private static HashMap<String, String> mapa_ids = new HashMap<>();

    private static String cadena;

    private static String tokenID = "token_ID";
    private static final String tokenNumero = "token_numero";

    private static String errorTokenID = "ID_No_Valido";
    private static String errorTokenNumero = "Numero_no_valido";

    private static String tokenMenor = "token_menor";
    private static String tokenMayor = "token_mayor";
    private static String tokenIgual = "token_igual";
    private static String tokenMenorIgual = "token_menor_igual";
    private static String tokenMayorIgual = "token_mayor_igual";
    private static String tokenSuma = "token_suma";
    private static String tokenResta = "token_resta";
    private static String tokenMultiplicacion = "token_multiplicacion";
    private static String tokenDivision = "token_division";
    private static String tokenAsignacion = "token_asignacion";
    private static String tokenFinSentencia = "token_fin_sentencia";
    private static String tokenFinPrograma = "token_fin_programa";
    private static String tokenComa = "token_coma";
    private static String tokenPunto = "token_punto";
    private static String tokenDosPuntos = "token_dos_puntos";
    private static String tokenPuntoComa = "token_punto_y_coma";
    private static String tokenParetesisAbre = "token_parentesis_que_abre";
    private static String tokenParetesisCierra = "token_parentesis_que_cierra";
    private static String tokenDistinto = "token_distinto";

    public static void llenarPatronesYTokens() {
        mapa_ids.put("program", "token_program");
        mapa_ids.put("procedure", "token_procedure");
        mapa_ids.put("function", "token_function");
        mapa_ids.put("boolean", "token_boolean");
        mapa_ids.put("integer", "token_integer");
        mapa_ids.put("var", "token_var");
        mapa_ids.put("begin", "token_begin");
        mapa_ids.put("end", "token_end");
        mapa_ids.put("if", "token_if");
        mapa_ids.put("then", "token_then");
        mapa_ids.put("else", "token_else");
        mapa_ids.put("while", "token_while");
        mapa_ids.put("do", "token_do");
        mapa_ids.put("and", "token_and");
        mapa_ids.put("or", "token_or");
        mapa_ids.put("true", "token_true");
        mapa_ids.put("false", "token_false");
        mapa_ids.put("not", "token_not");

    }

    public static String automata() {
        //La cadena analizar y por donde comienza el analisis
        String cadenaRespuesta = "";
        String respuesta;
        char puntero;
        int longitud = cadena.length();
        if (!cadena.equals("")) {
            puntero = cadena.charAt(0);
            //Verifico si la primer palabra es letra, considerando letra como lo definido por nuestro alfabeto
            if (esLetra(puntero)) {
                cadenaRespuesta = sospechaId() + " " + automata();
                //Verifico si la primer letra es un digito
            } else if (Character.isDigit(puntero)) {
                cadenaRespuesta = sospechaNumero() + " " + automata();
            } else {
                switch (puntero) {
                    //salteo espacios en blanco
                    case ' ':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = automata();
                        break;
                    //se consume toda la cadena hasta el cierre del comentario    
                    case '{':
                        consumirComentario();
                        break;
                    //copio salto de linea    
                    case '\n':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = "\n" + automata();
                        break;
                    //token_suma	
                    case '+':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenSuma + " " + automata();
                        break;
                    //token_Resta	
                    case '-':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenResta + " " + automata();
                        break;
                    //Token_multiplicacion	
                    case '*':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenMultiplicacion + " " + automata();
                        break;
                    //Token_Division	
                    case '/':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenDivision + " " + automata();
                        break;
                    //Token_parentesis que abre	
                    case '(':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenParetesisAbre + " " + automata();
                        break;
                    //token_parentesis_que_cierra	
                    case ')':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenParetesisCierra + " " + automata();
                        break;
                    //token igual	
                    case '=':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenIgual + " " + automata();
                        break;
                    //token_coma	
                    case ',':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenComa + " " + automata();
                        break;
                    //token_fin_sentencia	
                    case ';':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenPuntoComa + " " + automata();
                        break;
                    //token_fin_programa	
                    case '.':
                        cadena = cadena.substring(1);
                        cadenaRespuesta = tokenPunto + " " + automata();
                        break;
                    //sospecha de token mayor		
                    case '>':
                        //Verifico que halla un caractermas en la cadena y si lo hay verifico si el siguiente es un igual
                        if (longitud > 1 && cadena.charAt(1) == '=') {
                            //Si es un igual se consume dos letras, y retorna token_mayor_igual
                            cadena = cadena.substring(2);
                            cadenaRespuesta = tokenMayorIgual + " " + automata();
                        } else {
                            //sino token mayor
                            cadena = cadena.substring(1);
                            cadenaRespuesta = tokenMayor + " " + automata();
                        }
                        break;
                    case '<':
                        if (longitud > 1) {
                            if (cadena.charAt(1) == '=') {
                                cadena = cadena.substring(2);
                                cadenaRespuesta = tokenMenorIgual + " " + automata();

                            } else if (cadena.charAt(1) == '>') {
                                cadena = cadena.substring(2);
                                cadenaRespuesta = tokenDistinto + " " + automata();
                            } else {
                                cadena = cadena.substring(1);
                                cadenaRespuesta = tokenMenor + " " + automata();
                            }

                        } else {
                            cadena = cadena.substring(1);
                            cadenaRespuesta = tokenMenor + " " + automata();
                        }
                        break;
                    case ':':
                        if (longitud > 1 && cadena.charAt(1) == '=') {
                            cadena = cadena.substring(2);
                            cadenaRespuesta = tokenAsignacion + " " + automata();
                        } else {
                            cadena = cadena.substring(1);
                            cadenaRespuesta = tokenDosPuntos + " " + automata();
                        }
                        break;

                }
            }
        }

        return cadenaRespuesta;
    }

    public static void consumirComentario() {
        //Te consume la cadena hasta e fin del comentario
        //Sino esta se consume toda la cadena y se llama al automata
        int posicion;
        if ((posicion = cadena.indexOf("}")) == -1) {
            cadena = "";
        } else {
            cadena = cadena.substring(posicion);
        }
        automata();
    }

    public static String sospechaNumero() {
        //Si se encontro un numero hay sospecha de numero
        //Se consumen todos los numeros, cuando lo encuentra retorna el token_numero_lexema
        int longitud, i = 1;
        char puntero;
        String token;
        longitud = cadena.length();
        String cadenaConsumida = "" + String.valueOf(cadena.charAt(0));
        boolean tokenEncontrado = false;
        while (i < longitud && !tokenEncontrado) {
            puntero = cadena.charAt(i);
            if (!Character.isDigit(puntero)) {
                tokenEncontrado = true;
            } else {
                cadenaConsumida = cadenaConsumida + puntero;
            }
            i++;
        }
        token = tokenNumero + "_" + cadenaConsumida;
        cadena = cadena.substring(cadenaConsumida.length(), cadena.length());
        return token;
    }

    public static String sospechaId() {
        //Si se encontro una letra hay sospecha de id
        int longitud, i = 1;
        char puntero;
        String token;
        longitud = cadena.length();
        String cadenaConsumida = "" + String.valueOf(cadena.charAt(0));
        boolean tokenEncontrado = false;
        while (i < longitud && !tokenEncontrado) {
            puntero = cadena.charAt(i);
            if (!esLetra(puntero) && !Character.isDigit(puntero)) {
                tokenEncontrado = true;
            } else {
                cadenaConsumida = cadenaConsumida + puntero;
            }
            i++;
        }
        //Se llama a esta funcion para saber si hay palabras reservadas
        token = identificarTipoID(cadenaConsumida);

        cadena = cadena.substring(cadenaConsumida.length(), cadena.length());
        return token;
    }

    public static String identificarTipoID(String cadena) {
        //Identifica si la cadena es una palabra reservada o un id
        String token;
        token = mapa_ids.get(cadena.toLowerCase());
        if (token == null) {
            token = tokenID;
        } else {
            //Si es un id, retorna token_id_lexema
            token = token + "_" + cadena.toLowerCase();
        }
        return token;
    }

    public static boolean esLetra(char puntero) {
        boolean respuesta = false;
        //Verifico si es una letra segun definido en nuestro alfabeto comparando con los valores de la tabla ascii
        if ((puntero >= 65 && puntero <= 90) || (puntero >= 97 && puntero <= 122) || puntero == 95) {
            respuesta = true;
        }
        return respuesta;
    }

    public static void main(String[] args) {
        /*if (args.length < 1) {
         System.err.println("Porfavor ingrese el archivo de lectura como parametro");
         }*/
        //Recorta la cadena en espacios en blanco
        //String cadena = args[0];
        cadena = "program _algunid ";
        String cadenaResultante = "";
        //tiene que tener almenos un elemento la cadena
        llenarPatronesYTokens();
        if (cadena.length() >= 1) {
            cadenaResultante = automata();
        }
        System.out.println(cadenaResultante);

    }

}
