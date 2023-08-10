/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;


public class Token {
    private TypeToken token;
    private String lexema;
    private int fila;
    private int columna;

    public Token() {
    }

    public Token(TypeToken token, String lexema) {
        this.token = token;
        this.lexema = lexema;
    }

    public Token(TypeToken token, String lexema, int fila, int columna) {
        this.token = token;
        this.lexema = lexema;
        this.fila = fila;
        this.columna = columna;
    }

    public TypeToken getToken() {
        return token;
    }

    public void setToken(TypeToken token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }
    
}
