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

    private String lexemaActual;
    private Token token;
    public static boolean isEmpty;
    private int row=1, column=0;
    public ArrayList<Token> tokens = new ArrayList();
    private String[] OP_ASIGN = {"="};
    private String[] OP_ARIT = {"+", "-", "*", "/", "**", "//", "%"};
    private String[] OP_COMP = {"==", "!=", ">", "<", "<=", ">="};
    private String[] OP_LOG = {"and", "not", "or"};

    public void analizar(String input) {
        if (input.isEmpty()) {
            isEmpty = true;
        }
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == 10) {
                row++;
                column=0;
            } else {
                column++;
            }
            lexemaActual += input.charAt(i);
            if (input.charAt(i) == 32 || i == input.length() - 1) {

                if (isIdentificator(input)) {
                    token = new Token(TypeToken.ID, lexemaActual, row, column);
                    tokens.add(token);

                }
                lexemaActual = "";
            }

        }

        for (Token op : tokens) {
            System.out.println("Lexema: " + op.infor());
        }

    }

    public boolean isIdentificator(String lexema) {
        boolean correct = true;
        for (int i = 0; i < lexema.length(); i++) {
            if (lexema.charAt(0) == 95 || isLetter(lexema.charAt(0))) {
                correct = true;

            }
            if (isNumber(lexema.charAt(0))) {
                correct = false;
                break;
            }
            if (i > 0) {
                if (lexema.charAt(i) == 95 || isLetter(lexema.charAt(i)) || isNumber(lexema.charAt(i))) {
                    correct = true;
                }
            }

        }

        return correct;
    }

    public boolean isLetter(char character) {

        return ((character >= 65 && character <= 90) || (character >= 97 && character <= 122));
    }

    public boolean isNumber(char character) {
        return (character >= 48 && character <= 57);
    }

}
