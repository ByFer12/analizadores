/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import java.util.ArrayList;

/**
 *
 * @author tuxrex
 */
public class Analizador {

    private StringBuilder lexemaActual = new StringBuilder();
    private Token token;
    public static boolean isEmpty;
    private int row = 1, column = 0;
    private boolean coment=false;
    public ArrayList<Token> tokens = new ArrayList();
    private String[] OP_ASIGN = {"="};
    private String[] OP_ARIT = {"+", "-", "*", "/", "**", "//", "%"};
    private String[] OP_COMP = {"==", "!=", ">", "<", "<=", ">="};
    private String[] OP_LOG = {"and", "not", "or"};

    public void analizar(String input) {

        if (input.isEmpty()) {
            isEmpty = true;
        }
            int it=0;
        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == 10) {
                row++;
                column = 0;
            } else {
                column++;
            }
            if (!(input.charAt(i) == 10)&&!(input.charAt(i) == 32)) {
                lexemaActual.append(input.charAt(i));
                it++;
            }
           
            if (input.charAt(i) == 32 || (i == input.length() - 1) || input.charAt(i+1) == 10) {
                if(lexemaActual.length()==0){
                    continue;
                }else

//analiza si el caracter anterior o dos anteriores es " entonces termina la lectura y es reconocido como cadena
                if(lexemaActual.charAt(it-1)==34 ||lexemaActual.charAt(it-1)==39){
                    coment=true;
                    //verifica si la posicion cero del lexema actual es comilla asi omite los espacios y sigue verificando 
                }else if((lexemaActual.charAt(0)==34)|| (lexemaActual.charAt(0)==39)){
                    continue;
                }

                String lexer = lexemaActual.toString();
                //si el caracter anterior es comilla entonces se reconoce el token comentario
                if(coment){
                     token = new Token(TypeToken.COMENTARIO, lexer,"Comentario", row, column);
                    tokens.add(token);
                    //verifica si es un identificador
                }else if (isIdentificator(lexer)) {
                    token = new Token(TypeToken.ID, lexer,"Identificador", row, column);
                    tokens.add(token);

                } else if (isNumber(lexer)) {
                    token = new Token(TypeToken.CONST, lexer,"Entero", row, column);
                    tokens.add(token);
                } else if(isDecimal(lexer)){
                    token = new Token(TypeToken.CONST, lexer,"Decimal", row, column);
                    tokens.add(token);
                }else if(isCadena(lexer)){
                    token = new Token(TypeToken.CONST, lexer,"Cadena", row, column);
                    tokens.add(token);
                }else{
                    System.out.println("No hay tokens");
                }
                lexemaActual.setLength(0);
                it=0;
                coment=false;

            }
            

        }

        for (Token op : tokens) {
            System.out.println("Lexema: " + op.infor());
        }

    }

    //funcion que determina si es identificador
    public boolean isIdentificator(String lexema) {
        if (lexema.isEmpty()) {
            return false;
        }
        char letter = lexema.charAt(0);
        if (!isLetter(letter) && letter != 95) {
            return false;
        }

        for (int i = 1; i < lexema.length() - 1; i++) {
            char letr = lexema.charAt(i);
            if (!isLetter(letr) && !isDigit(letr) && letr != 95) {
                return false;
            }
        }
        return true;
    }

    //verifica si el caracter es numero
    private static boolean isLetter(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }
    
//verifica si el caracter es letra mayuscula o minuscula

    private static boolean isDigit(char c) {
        return (c >= 48 && c <= 57);
    }

    //verifica si el lexema es un token
    public boolean isNumber(String lexema) {

        char number = lexema.charAt(0);
        if (!isDigit(number)) {
            return false;
        }
        for (int i = 1; i < lexema.length() - 1; i++) {
            char digit = lexema.charAt(i);
            if (!isDigit(digit)) {
                return false;
            }
        }
        return true;
    }
    

// metodo que comprueba si es decimal
    public boolean isDecimal(String lexema) {
        boolean hasDecimalPoint = false;
        boolean hasDigits = false;

        for (int i = 0; i < lexema.length()-1; i++) {
            char c = lexema.charAt(i);

            if (c == '.') {
                if (hasDecimalPoint || !hasDigits) {
                    return false; // Más de un punto decimal o punto al principio
                }
                hasDecimalPoint = true;
            } else if (isDigit(c)) {
                hasDigits = true;
            } else {
                return false; // Carácter no permitido
            }
        }

        return hasDigits; // Debe haber al menos un dígito
    }
    
    //metodo para reconocer cadenas
    public boolean isCadena(String lexema){
        return (lexema.startsWith("\"") && lexema.endsWith("\"")) ||
               (lexema.startsWith("'") && lexema.endsWith("'"));
    }
}
