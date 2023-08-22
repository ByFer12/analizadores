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

    private boolean isKey = false;
    private StringBuilder lexemaActual = new StringBuilder();
    private Token token;
    public static boolean isEmpty;
    private int row = 1, column = 0;
    private boolean caden = false, coment = false;
    public static ArrayList<Token> tokens = new ArrayList();
    public static ArrayList<Token> errors = new ArrayList();
    private String[] keysWords = {"and ", "not", "or", "True", "False", "and", "as", "assert", "break", "class", "continue", "def ", "del", "elif", "else", "except", "finally", "for ", "from", "global", "if ", "import", "i", "is", "lamda", "None", "nonlocal", "pass", "raise", "return", "try", "while ", "with", "yield"};
    private String[] others = {")", "( ", ": ", "; ", ", ", ". ", "{ ", "} ", "[ ", "] "};
    public static boolean isError = false;

    public void analizar(String input) {

        if (input.isEmpty()) {
            isEmpty = true;
        }
        int it = 0;
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

            //validar para clasificar tokens
            if (input.charAt(i) == 32 || (i == input.length() - 1) || input.charAt(i + 1) == 10) {
                try {
                    if (lexemaActual.length() == 0) {
                        continue;
                    } else if ((lexemaActual.charAt(0) == 34 || lexemaActual.charAt(0) == 39) && input.charAt(i) == 32) {
                        if ((lexemaActual.charAt(it - 1) == 34 || lexemaActual.charAt(it - 1) == 39) && (lexemaActual.charAt(0) == 34 || lexemaActual.charAt(0) == 39)) {
                            caden = true;

                        } else if ((lexemaActual.charAt(it - 2) == 34 || lexemaActual.charAt(it - 2) == 39) && (lexemaActual.charAt(0) == 34 || lexemaActual.charAt(0) == 39)) {
                            caden = true;

                        } else {
                            continue;
                        }
                    } else if ((lexemaActual.charAt(it - 1) == 34 || lexemaActual.charAt(it - 1) == 39) && (lexemaActual.charAt(0) == 34 || lexemaActual.charAt(0) == 39)) {
                        caden = true;

                    } else if ((i == input.length() - 1 || input.charAt(i + 1) == 10) && lexemaActual.charAt(0) == 35) {
                        coment = true;
                    } else if (lexemaActual.charAt(0) == 35) {
                        continue;

                    }
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("Error");
                }

                String lexer = lexemaActual.toString();
                if (coment) {
                    token = new Token(TypeToken.CONST, lexer, "Comentario", row, column);
                    tokens.add(token);
                } else //si el caracter anterior es comilla entonces se reconoce el token comentario
                if (caden) {
                    token = new Token(TypeToken.CONST, lexer, "Cadena", row, column);

                    tokens.add(token);
                    //verifica si es un identificador
                } else if (isIdentificator(lexer)) {
                    if (isKeyWord(lexer)) {
                        token = new Token(TypeToken.KEY_WORD, lexer, "Palabra reservada", row, column);
                        tokens.add(token);
                    } else {
                        token = new Token(TypeToken.ID, lexer, "Identificador", row, column);
                        tokens.add(token);
                    }
                } else if (isNumber(lexer)) {
                    token = new Token(TypeToken.CONST, lexer, "Entero", row, column);
                    tokens.add(token);
                } else if (isDecimal(lexer)) {
                    token = new Token(TypeToken.CONST, lexer, "Decimal", row, column);
                    tokens.add(token);
                } else if (opAritmetico(lexer)) {
                    token = new Token(TypeToken.OP_ARITMETIC, lexer, "Operador Aritmetico", row, column);
                    tokens.add(token);

                } else if (opComparacion(lexer)) {
                    token = new Token(TypeToken.OP_COMPARACION, lexer, "Operador Comparacion", row, column);
                    tokens.add(token);
                } else if (isOther(lexer)) {
                    token = new Token(TypeToken.OTROS, lexer, "Otros", row, column);
                    tokens.add(token);

                } else {
                    token = new Token(TypeToken.ERRORS, lexer, "Error", row, column);
                    errors.add(token);
                    isError = true;

                }
                lexemaActual.setLength(0);
                it = 0;
                coment = false;
                caden = false;

            }

        }
        if (isError) {
            for (Token op : errors) {
                System.out.println(op.infor());
            }

        } else {
            for (Token op : tokens) {
                System.out.println(op.infor());
            }


        }
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public ArrayList<Token> getErrors() {
        return errors;
    }

    public void setErrors(ArrayList<Token> errors) {
        this.errors = errors;
    }

    public boolean isIsError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
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

    public boolean isKeyWord(String lexer) {
        for (String key : keysWords) {
            if (key.equals(lexer)) {
                return true;

            }
        }

        return false;
    }

    //verifica si el caracter es numero
    private static boolean isLetter(char c) {
        return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
    }

//verifica si el caracter es letra mayuscula o minuscula
    private static boolean isDigit(char c) {
        return (c >= 48 && c <= 57);
    }

    public static boolean opAritmetico(String c) {
        if (c.equals("** ")) {
            return true;
        } else if (c.equals("// ")) {
            return true;
        } else if (c.equals("* ")) {
            return true;
        } else if (c.equals("/ ")) {
            return true;
        } else if (c.equals("+ ")) {
            return true;
        } else if (c.equals("- ")) {
            return true;
        } else if (c.equals("% ")) {
            return true;
        }

        return false;
    }

    public boolean isOther(String lexer) {
        for (String ot : others) {
            if (ot.equals(lexer)) {
                return true;
            }
        }
        return false;
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

    public boolean opComparacion(String c) {
        if (c.equals("<")) {
            return true;
        } else if (c.equals(">")) {
            return true;
        } else if (c.equals("=")) {
            return true;

        } else if (c.equals("!=")) {
            return true;

        } else if (c.equals(">=")) {
            return true;

        } else if (c.equals("<=")) {
            return true;
        }

        return false;
    }

// metodo que comprueba si es decimal
    public boolean isDecimal(String lexema) {
        boolean hasDecimalPoint = false;
        boolean hasDigits = false;

        for (int i = 0; i < lexema.length() - 1; i++) {
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

    


}
