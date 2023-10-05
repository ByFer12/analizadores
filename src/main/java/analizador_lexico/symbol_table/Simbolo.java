/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package analizador_lexico.symbol_table;

public class Simbolo {
    private String nombreVar;
    private String tipoVar;
    private String valueVar;

    public Simbolo(String nombreVar, String tipoVar, String valueVar) {
        this.nombreVar = nombreVar;
        this.tipoVar = tipoVar;
        this.valueVar = valueVar;
    }

    public String getNombreVar() {
        return nombreVar;
    }

    public String getValueVar() {
        return valueVar;
    }

    public String getTipoVar() {
        return tipoVar;
    }

    
    
    
    
}
