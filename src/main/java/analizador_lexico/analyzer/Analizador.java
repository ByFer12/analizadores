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
    public static boolean isError = false;
    public static ArrayList<Token> tokens = new ArrayList<>();
    public static ArrayList<Token> errores = new ArrayList<>();
    String id = "[a-zA-Z_][a-zA-Z0-9_]*"; //ya esta
    String entero = "-?\\d+"; //ya esta
    String decimal = "-?\\d+(\\.\\d*)?|-?\\.\\d+"; //ya esta casi
    String coment = "#.*"; //esta ya esta
    String keyWord = "(if|else|for|while|and|as|assert|break|class|continue|def|del|elif|except|False|finally|from|global|import|in|is|lambda|None|nonlocal|not|or|pass|raise|return|True|try|with|yield)";
    String cadena = "\"[^\"]*\"|'[^']*'"; //ya esta
    String opArit = "\\+|-|\\*\\*|/|//|%|\\*";
    String opComp = ">=|<=|>|<|==|!=";
    String opAsig = "=";
    String space = "\\s*";
    String salto = "\\n";
    String otros = "\\(|\\)|\\{|\\}|\\[|\\]|,|;|:";
    String indentado = "([ \t]+)(.*)";
    String errorCadena = "\".*|\'.*";
    Pattern identifierPattern = Pattern.compile(id);
    Pattern integerPattern = Pattern.compile(entero);
    Pattern decimalPattern = Pattern.compile(decimal);
    Pattern commentPattern = Pattern.compile(coment);
    Pattern palabras = Pattern.compile(keyWord);
    Pattern cadenas = Pattern.compile(cadena);
    Pattern opAritPattern = Pattern.compile(opArit);
    Pattern opCompPattern = Pattern.compile(opComp);
    Pattern opAsignPattern = Pattern.compile(opAsig);
    Pattern otrosPattern = Pattern.compile(otros);
    Pattern indent = Pattern.compile(indentado);

    int linea = 1;
    int column = 1;

    public void analizar(String input) {
        Matcher matcher = Pattern.compile(id + "|" + entero + "|" + decimal + "|" + coment + "|" + space + "|" + salto + "|" + keyWord + "|" + cadena + "|" + opArit + "|" + opComp + "|" + opAsig + "|" + otros + "|" + indentado).matcher(input);

        while (matcher.find()) {
            String lex;

            String lexema = matcher.group();
            lex = lexema.trim();

            int start = matcher.start();
            column += start;
            if (lex.contains(" ") && !Pattern.matches(coment, lex) && !Pattern.matches(cadena, lex)) {
                analizar(lex);

            } else if (Pattern.matches(id, lex)) {
                if (Pattern.matches(keyWord, lex)) {
                    token = new Token(TypeToken.KY_WD, lex, "reservada", linea, column);
                    tokens.add(token);
                } else {

                    token = new Token(TypeToken.ID, lex, id, linea, column);
                    tokens.add(token);
                }
            } else if (Pattern.matches(entero, lex)) {
                token = new Token(TypeToken.CONS, lex, entero, linea, column);
                tokens.add(token);

            } else if (Pattern.matches(decimal, lex)) {
                token = new Token(TypeToken.CONS, lex, decimal, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(coment, lex)) {
                token = new Token(TypeToken.COM, lex, coment, linea, column);
                tokens.add(token);

            } else if (Pattern.matches(cadena, lex)) {
                token = new Token(TypeToken.CONS, lex, cadena, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(opArit, lex)) {
                token = new Token(TypeToken.OP_ARIT, lex, opArit, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(opComp, lex)) {
                token = new Token(TypeToken.OP_COMP, lex, opComp, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(opAsig, lex)) {
                token = new Token(TypeToken.OP_AS, lex, opAsig, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(otros, lex)) {
                token = new Token(TypeToken.OT, lex, otros, linea, column);
                tokens.add(token);
            } else if (Pattern.matches(space, lex)) {
                token = new Token(TypeToken.SPACE, lex, "Espacio", linea, column);
                tokens.add(token);

            }else if(Pattern.matches(salto, lex)){
                token = new Token(TypeToken.SALT, "Salto Linea", "Salto", linea, column);
                tokens.add(token);           
            
            } else {
                isError = true;
                token = new Token(TypeToken.ERR, lex, "Error", linea, column);
                tokens.add(token);
                System.out.println("Error");
            }
            linea += lex.split("\n").length - 1;
            column += lex.endsWith(lex) ? 1 : lex.length() - lex.lastIndexOf("\n");

        }
        
        for(Token tk:tokens){
            System.out.println(tk.info());
        }

    }

}
