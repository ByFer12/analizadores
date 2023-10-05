package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import java.util.ArrayList;
import analizador_lexico.symbol_table.SymbolTable;

public class Sintaxis {

    private ArrayList<Token> token;
    private int index = 0;
    private SymbolTable sybolTable;

    public Sintaxis(ArrayList<Token> token) {
        this.token = token;
        this.sybolTable = new SymbolTable();

    }

    public void declaracionVar() {
        if (this.token.get(index).getToken().equals(TypeToken.ID)) {
            String nombreVar = this.token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.OP_AS)) {
                index++;

                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.KY_WD)) {
                    if (this.token.get(index).getToken().equals(TypeToken.KY_WD)) {
                        if (this.token.get(index).getLexema().equals("True") || this.token.get(index).getLexema().equals("False")) {

                            String tipoDato = token.get(index).getToken().name();
                            String valorVariable = token.get(index).getLexema();
                            index++;
                            this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);
                        }
                    } else {
                        String tipoDato = token.get(index).getToken().name();
                        String valorVariable = token.get(index).getLexema();
                        index++;
                        this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);
                    }
                    

                    //Aqui va la logica para una lista
                }else if(this.token.get(index).getToken().equals(TypeToken.OT)&&this.token.get(index).getLexema().equals("[")){
                    index++;
                    while(!this.token.get(index).getLexema().equals("]")){
                        System.out.println("Estoy dentro del while");
                        if(this.token.get(index).getToken().equals(TypeToken.CONS)){
                            System.out.println("Tk: "+this.token.get(index).getLexema());
                            index++;
                            if(this.token.get(index).getToken().equals(TypeToken.OT)){
                                if(this.token.get(index).getLexema().equals(",")){
                                    
                                index++;
                                 System.out.println("Tk: "+this.token.get(index).getLexema());
                                }
                            }
                        }
                    }
                    
                }
                
                
                
                else {
                    System.out.println("Error sintaxis de tipo de dato ");
                }
            } else {
                System.out.println("Error de sintaxis falta el simbolo igual");
            }
        } else {
            System.out.println("Error de sintaxis no declaro variable o identificador");
        }
        System.out.println("Expresion correcta ");

    }

    public SymbolTable getSybolTable() {
        return sybolTable;
    }
}
