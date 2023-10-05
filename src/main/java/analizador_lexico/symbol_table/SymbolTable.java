/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.symbol_table;

import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {

    HashMap<String, Simbolo> tablaSimbolos;

    public SymbolTable() {
        tablaSimbolos = new HashMap<>();
    }

    public void agregarSimbolo(String nombre, String tipo, String valor) {
        Simbolo symbol = new Simbolo(nombre, tipo, valor);
        tablaSimbolos.put(nombre, symbol);
    }

    public Simbolo obtenerSimbolo(String nombre) {
        return tablaSimbolos.get(nombre);
    }

    public ArrayList<Simbolo> obtenerTodosSimbolos() {
        return new ArrayList<>(tablaSimbolos.values());
    }

}
