package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tuxrex
 *
 */
public class Analizador {

    private Token token;
    private ArrayList<Token> tokens = new ArrayList<>();
    String id = "[a-zA-Z_][a-zA-Z0-9_]*"; //ya esta
    String numero = "-?\\d+"; //ya esta
    String decimal = "-?\\d+(\\.\\d*)?|-?\\.\\d+"; //ya esta casi
    String coment = "#.*"; //esta ya esta
    String keyWord = "(if|else|for|while|and|as|assert|break|class|continue|def|del|elif|except|False|finally|from|global|import|in|is|lambda|None|nonlocal|not|or|pass|raise|return|True|try|with|yield)";
    String cadena = "\"[^\"]*\"|'[^']*'"; //ya esta
    String opArit = "\\+|-|\\*\\*|/|//|%|\\*";
    String opComp = ">=|<=|>|<|==|!=";
    String opAsig = "=";
    String otros = "\\(|\\)|\\{|\\}|\\[|\\]|,|;|:";
    String indentado = "([ \t]+)(.*)";
    Pattern identifierPattern = Pattern.compile(id);
    Pattern integerPattern = Pattern.compile(numero);
    Pattern decimalPattern = Pattern.compile(decimal);
    Pattern commentPattern = Pattern.compile(coment);
    Pattern palabras = Pattern.compile(keyWord);
    Pattern cadenas = Pattern.compile(cadena);
    Pattern opAritPattern = Pattern.compile(opArit);
    Pattern opCompPattern = Pattern.compile(opComp);
    Pattern opAsignPattern = Pattern.compile(opAsig);
    Pattern otrosPattern = Pattern.compile(otros);
    Pattern indent = Pattern.compile(indentado);

    public static boolean isError = false;
    int linea = 1;
    int column = 1;

    public void analizar(String input) {
        Matcher matcher = Pattern.compile(id + "|" + numero + "|" + decimal + "|" + coment + "|" + keyWord + "|" + cadena + "|" + opArit + "|" + opComp + "|" + opAsig + "|" + otros + "|" + indentado).matcher(input);
       
        while (matcher.find()) {
            String lex;
        
            String lexema = matcher.group();
            lex = lexema.trim();

            System.out.println("Primero: "+lex);
            int start = matcher.start();
            column += start;

            if (Pattern.matches(id, lex)) {
                if (Pattern.matches(keyWord, lex)) {
                    token = new Token(TypeToken.KEY_WORD, lex, "Palabras reservadas", linea, column);
                    tokens.add(token);
                } else {

                    token = new Token(TypeToken.ID, lex, "Identificador", linea, column);
                    tokens.add(token);
                }
            } else if (Pattern.matches(numero, lex)) {
                token = new Token(TypeToken.CONST, lex, "Entero", linea, column);
                tokens.add(token);

            } else if (Pattern.matches(decimal, lex)) {
                token = new Token(TypeToken.CONST, lex, "Decimal", linea, column);
                tokens.add(token);
            } else if (Pattern.matches(coment, lex)) {
                token = new Token(TypeToken.CONST, lex, "Comentario", linea, column);
                tokens.add(token);

            } else if (Pattern.matches(cadena, lex)) {
                token = new Token(TypeToken.CONST, lex, "Cadena", linea, column);
                tokens.add(token);
            } else if (Pattern.matches(opArit, lex)) {
                token = new Token(TypeToken.OP_ARITMETIC, lex, "Operador Aritmetico", linea, column);
                tokens.add(token);
            } else if (Pattern.matches(opComp, lex)) {
                token = new Token(TypeToken.OP_COMPARACION, lex, "Operador de comparacion", linea, column);
                tokens.add(token);
            }else if (Pattern.matches(opAsig, lex)) {
                token = new Token(TypeToken.OP_ASIGN, lex, "Operador de asignacion", linea, column);
                tokens.add(token);
            }else if (Pattern.matches(otros, lex)) {
                token = new Token(TypeToken.OTROS, lex, "Otros", linea, column);
                tokens.add(token);
            }else{
                System.out.println("Error");
            }
            linea += lex.split("\n").length - 1;
            column += lex.endsWith(lex) ? 1 : lex.length() - lex.lastIndexOf("\n");

        }

        for (Token tk : tokens) {
            System.out.println(tk.info());
        }
    }

}
