package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import java.util.ArrayList;
import analizador_lexico.analyzer.Token;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tuxrex
 */
public class Analizador {
    private Token token;

    String id = "(_|[a-zA-Z])\\w*(_\\w*)?";
    String numero = "\\d+";
    String comentario = "#[^\\n]*";
    String cadena = "\"[^\"]*\"|'[^']*'";
    String decimal = "\\d+\\.\\d+";
    String opArit = "\\+|-|\\*\\*|/|//|%|\\*";
    String opComp = "==|!=|>|<|>=|<=";
    String opAsig = "=";
    String otros = "\\(|\\)|\\{|\\}|\\[|\\]|,|;|:";
    String keyWord = "\\b(if|else|for|while|and|as|assert|break|class|continue|def|del|elif|except|False|finally|from|global|import|in|is|lambda|None|nonlocal|not|or|pass|raise|return|True|try|with|yield)\\b";

    String expCompleto = "(" + id + "|" + numero + "|" + "|" + comentario + "|" + cadena
            + decimal + "|" + opArit + "|" + "|" + opComp + "|" + opAsig
            + otros + "|" + keyWord + ")";
    Pattern patron = Pattern.compile(expCompleto);

    public static boolean isError = false;

    public void analizar(String input) {
        Matcher analizar=patron.matcher(input);
        while(analizar.find()){
            String lexema=analizar.group();
            if(lexema.contains(id)){
                token=new Token(TypeToken.CONST, lexema, numero, 0, 0);
            }
        }
        
    }

    
}
