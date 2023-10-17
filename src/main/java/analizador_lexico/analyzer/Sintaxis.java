package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import analizador_lexico.errors.ErrorSyntaxIndexOut;
import java.util.ArrayList;
import analizador_lexico.symbol_table.SymbolTable;
import java.util.function.ToLongFunction;
import javax.swing.JOptionPane;

public class Sintaxis {

    private boolean hayIf = false, hayElif = false, hayFor = false, hayDef = false;
    private boolean haySpace = false;
    private ArrayList<Token> token;
    private int index = 0;
    private SymbolTable sybolTable;
    boolean pasa = false;
    private int conteoIndentado = 0;

    public Sintaxis(ArrayList<Token> token) {
        this.token = token;
        this.sybolTable = new SymbolTable();

    }

    public void syntaxAnalyer() {
        try {
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.ID)) {
                    idAnalyzer();
                } else if (this.token.get(index).getLexema().equals("break")) {
                    index++;
                    syntaxAnalyer();

                } else if (this.token.get(index).getToken().equals(TypeToken.COM)) {

                    comentAnalyzer();

                } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();

                } else if (this.token.get(index).getLexema().equals("print")) {
                    print();
                } else if (this.token.get(index).getLexema().equals("if")) {
                    hayIf = true;
                    condicionIf();
                } else if (this.token.get(index).getLexema().equals("else")) {

                    if (hayIf || hayElif || hayFor) {
                        index++;
                        hayIf = false;
                        hayElif = false;
                        if (this.token.get(index).getLexema().equals(":")) {
                            index++;
                            syntaxAnalyer();
                        } else {
                            JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));
                            index += 2;
                            syntaxAnalyer();

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis, ha escrito un else sin el if en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));
                        index += 2;
                        syntaxAnalyer();

                    }
                } else if (this.token.get(index).getLexema().equals("elif")) {
                    if (hayIf) {
                        hayElif = true;
                        condicionIf();

                    } else {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis, ha escrito un else sin el if en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));
                        index += 2;
                        syntaxAnalyer();

                    }

                    //AQUI VA EL METODO PARA RECONOCER LA SENTENCIA ELIF
                } else if (this.token.get(index).getLexema().equals("while")) {
                    //aqui va todo lo del while:
                    condicionIf();
                } else if (this.token.get(index).getLexema().equals("for")) {
                    cicloFor();

                } else if (token.get(index).getLexema().equals("def")) {
                    hayDef = true;
                    index++;
                    indentado();
                    funcionDef();
                } else if (token.get(index).getLexema().equals("return")) {
                    index++;
                    indentado();
                    espacioReturn();
                    syntaxAnalyer();

                } else if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    indentado();
                    syntaxAnalyer();
                }
            }

        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void comentAnalyzer() {
        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
            index++;
            syntaxAnalyer();
        }
    }

    public void espacioReturn() {
        if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
            index++;

            indentado();
            if (token.get(index).getToken().equals(TypeToken.OP_ARIT)) {
                index++;
                indentado();
                if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    index++;

                } else {
                    JOptionPane.showMessageDialog(null, "Error de sintaxis, no tiene el operando, liena: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));

                }

            } else {
                JOptionPane.showMessageDialog(null, "Error de sintaxis, no tiene operador aritmetico, linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));

            }
        } else if (token.get(index).getToken().equals(TypeToken.CONS)) {
            index++;
        } else if (token.get(index).getLexema().equals("len")) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
                indentado();
                if (token.get(index).getToken().equals(TypeToken.ID)) {
                    index++;
                    indentado();
                    if (token.get(index).getLexema().equals(")")) {
                        index++;

                    }
                }

            }

        } else if (token.get(index).getLexema().equals("list")) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
                indentado();
                if (token.get(index).getToken().equals(TypeToken.ID)) {
                    index++;
                    indentado();
                    if (token.get(index).getLexema().equals(")")) {
                        index++;

                    }
                }

            }
        }
    }

    public void funcionDef() {
        if (token.get(index).getToken().equals(TypeToken.ID)) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
                indentado();
                if(token.get(index).getLexema().equals("*")){
                    index++;
                    indentado();
                }
                
                if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    index++;
                    indentado();
                    if (token.get(index).getLexema().equals(",")) {
                        index++;
                        indentado();
                        if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
                            index++;
                            indentado();
                            if (token.get(index).getLexema().equals(")")) {
                                index++;
                                if (token.get(index).getLexema().equals(":")) {
                                    index++;
                                    syntaxAnalyer();
                                }

                            }
                        }
                    }

                }
                if (token.get(index).getLexema().equals(")")) {
                    index++;
                    indentado();
                    if (token.get(index).getLexema().equals(":")) {
                        index++;
                        syntaxAnalyer();

                    }

                }

            }
        }

    }

    public void idAnalyzer() {
        if (this.token.get(index).getToken().equals(TypeToken.ID)) {
            String nombreVar = this.token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_AS)) {
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }

                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.BOOL) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
                    String tipoDato = token.get(index).getToken().name();
                    String valorVariable = token.get(index).getLexema();
                    index++;
                    if (index < token.size()) {

                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            index++;

                        }
                        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                            index++;

                        }
                    }

                    this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);
                    if (index < token.size()) {

                        if (this.token.get(index).getToken().equals(TypeToken.OP_ARIT)) {
                            //OPERADOR ARITMETICO
                            opAritmetico(nombreVar, tipoDato, valorVariable);

                        } else if (this.token.get(index).getLexema().equals("is")) {

                            //OPERADOR DE IDENTIDAD
                            operadorIdentidad(nombreVar, tipoDato, valorVariable);

                        } else if (this.token.get(index).getLexema().equals("if")) {
                            tipoDato = token.get(index).getToken().name();
                            valorVariable += token.get(index).getLexema();
                            //OPERADOR TERNARIO
                            operadorTernario(nombreVar, tipoDato, valorVariable);

                        } else if (this.token.get(index).getLexema().equals("in") || this.token.get(index).getLexema().equals("not")) {

                            //AQUI VA OPERADOR DE PERTENENCIA
                            valorVariable += token.get(index).getLexema();
                            tipoDato = token.get(index).getToken().name();
                            operadorPertenencia(nombreVar, tipoDato, valorVariable);

                        } else if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {

                            //OPERADOR DE COMPARACION
                            operadorComparacion(nombreVar, tipoDato, valorVariable);

                        }
                    }

                    if (index < token.size()) {
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                            index++;
                        }

                        if (index < token.size()) {

                            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                index++;
                                syntaxAnalyer();
                            } else {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));
                                if (token.get(index).getToken().equals(TypeToken.OP_AS)) {
                                    index += 4;
                                } else {

                                    index++;
                                    index++;
                                }
                                syntaxAnalyer();
                            }
                        }
                    }
//
//                    if (index < token.size()) {
//                        syntaxAnalyer();
//                    }

                    //Aqui va la logica para una lista
                } else if (this.token.get(index).getToken().equals(TypeToken.OT) && this.token.get(index).getLexema().equals("[")) {
                    String tipo = this.token.get(index).getToken().name();
                    String valor = this.token.get(index).getLexema();
                    listas(nombreVar, tipo, valor);

                } else if (this.token.get(index).getToken().equals(TypeToken.OT) && this.token.get(index).getLexema().equals("{")) {
                    String tipo = this.token.get(index).getToken().name();
                    String valor = this.token.get(index).getLexema();
                    diccionario(nombreVar, tipo, valor);

                } else if (this.token.get(index).getToken().equals(TypeToken.OP_LOG) && this.token.get(index).getLexema().equals("not")) {

                    //DESPUES DE ASIGNAR INICIA CON OPERADOR LOGICO NOT
                    operadorLogicoNot(nombreVar);

                } else {
                    System.out.println("Error sintaxis de tipo de dato token: " + index);
                }
            } else if (this.token.get(index).getToken().equals(TypeToken.OP_RE_AS)) {
                //Aqui va operador de reasignacion
                opReAsignacion(nombreVar);
                if (index < token.size()) {

                    syntaxAnalyer();
                }

            } else {
                System.out.println("Error de sintaxis falta el simbolo igual token: " + index);
            }
        } else {
            System.out.println("Error de sintaxis no declaro variable o identificador token: " + index);
        }
    }

    public void diccionario(String nombreVar, String tipo, String valor) {
        boolean enviarTabla = false;
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valor += this.token.get(index).getLexema();
            index++;
        }
        try {

            while (!this.token.get(index).getLexema().equals("}")) {
                System.out.println("Estoy dentro del while de diccionario");
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getLexema().equals("}")) {
                        valor += this.token.get(index).getLexema();
                    }

                    if (this.token.get(index).getLexema().equals(":")) {
                        valor += this.token.get(index).getLexema();
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            valor += this.token.get(index).getLexema();
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                            valor += this.token.get(index).getLexema();
                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                            }
                            if (this.token.get(index).getLexema().equals(",")) {
                                valor += this.token.get(index).getLexema();
                                index++;
                                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                    JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));

                                    enviarTabla = true;
                                    break;
                                }
                                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                    index++;
                                }
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                valor += this.token.get(index).getLexema();
                                index++;
                            } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getLexema().equals("}")) {
                                valor += this.token.get(index).getLexema();
                            }
                        }
                    }

                }
            }
            if (!enviarTabla) {
                this.sybolTable.agregarSimbolo(nombreVar, "Lista", "Lista");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error en la sintaxis de excepcion");
        }
        index++;
        this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
        if (index < token.size()) {
            if (this.token.get(index).getLexema().equals(",")) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                index++;
            }
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();
                } else if (token.get(index).getToken().equals(TypeToken.ID)) {
                    syntaxAnalyer();
                }
            }
        }

    }

    public String diccionarioLista(String nombreVar, String tipo, String valor) {
        boolean enviarTabla = false;
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valor += this.token.get(index).getLexema();
            index++;
        }
        try {

            while (!this.token.get(index).getLexema().equals("}")) {
                System.out.println("Estoy dentro del while de diccionario");
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getLexema().equals("}")) {
                        valor += this.token.get(index).getLexema();
                    }

                    if (this.token.get(index).getLexema().equals(":")) {
                        valor += this.token.get(index).getLexema();
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            valor += this.token.get(index).getLexema();
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                            valor += this.token.get(index).getLexema();
                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                            }
                            if (this.token.get(index).getLexema().equals(",")) {
                                valor += this.token.get(index).getLexema();
                                index++;
                                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                    JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));

                                    enviarTabla = true;
                                    break;
                                }
                                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                    index++;
                                }
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                valor += this.token.get(index).getLexema();
                                index++;
                            } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getLexema().equals("]")) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }

                            if (this.token.get(index).getLexema().equals("}")) {
                                valor += this.token.get(index).getLexema();
                            }
                        }
                    }

                }
            }

        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error en la sintaxis de excepcion");
        }
        index++;
        //this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
        if (index < token.size()) {
            if (this.token.get(index).getLexema().equals(",")) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                index++;
            }
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();
                } else if (token.get(index).getToken().equals(TypeToken.ID)) {
                    syntaxAnalyer();
                }
            }
        }
        return valor;

    }

    public void opAritmetico(String nombre, String tipo, String valorVariable) {
        pasa = true;
        valorVariable += token.get(index).getLexema();
        index++;

        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
            valorVariable += token.get(index).getLexema();
            index++;
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                    index++;
                }
            }

        }
        this.sybolTable.agregarSimbolo(nombre, tipo, valorVariable);

    }

    public void opReAsignacion(String nombreVar) {
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            String tipoDato = token.get(index).getToken().name();
            String valorVariable = token.get(index).getLexema();
            index++;
            this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);

            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();
                }
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                    index++;
                }
            }
        }
    }

    public void operadorIdentidad(String nombreVar, String tipo, String valorVariable) {
        valorVariable += token.get(index).getLexema();
        index++;
        pasa = true;

        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valorVariable += token.get(index).getLexema();
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valorVariable += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                valorVariable += token.get(index).getLexema();
                index++;

            }

        } else if (this.token.get(index).getLexema().equals("not")) {
            valorVariable += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                valorVariable += token.get(index).getLexema();
                index++;

            }
        }
        this.sybolTable.agregarSimbolo(nombreVar, tipo, valorVariable);
    }

    public void listas(String nombreVar, String tipo, String valor) {
        boolean enviarTabla = false;
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valor += this.token.get(index).getLexema();
            index++;
        }
        try {

            while (!this.token.get(index).getLexema().equals("]")) {
                System.out.println("Estoy dentro del while");
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1));

                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getLexema().equals("]")) {
                        valor += this.token.get(index).getLexema();
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.OT)) {
                        if (this.token.get(index).getLexema().equals(",")) {
                            valor += this.token.get(index).getLexema();

                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1));

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                            }
                        }
                    }
                } else if (this.token.get(index).getLexema().equals("{")) {
                    valor += token.get(index).getLexema();
                    valor = diccionarioLista(nombreVar, tipo, valor);
                }
            }
            if (!enviarTabla) {
                this.sybolTable.agregarSimbolo(nombreVar, "Lista", "Lista");
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Error en la sintaxis de excepcion");
        }
        index++;
        this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
        if (index < token.size()) {
            syntaxAnalyer();
        }
    }

    //OPERADORES DE ASIGNACION
    public void operadorComparacion(String nombreVar, String tipoDato, String valorVariable) {
        valorVariable += token.get(index).getLexema();
        index++;

        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valorVariable += token.get(index).getLexema();
            tipoDato = TypeToken.BOOL.name();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_LOG)) {
                valorVariable += token.get(index).getLexema();
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    valorVariable += token.get(index).getLexema();
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valorVariable += token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            valorVariable += token.get(index).getLexema();
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                            valorVariable += token.get(index).getLexema();
                            index++;
                        }
                    }
                }

            }

        }

        this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);
    }

    public void operadorLogicoNot(String nombreVar) {
        String tipo = token.get(index).getToken().name();
        String valor = token.get(index).getLexema();
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valor += token.get(index).getLexema();
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valor += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                valor += token.get(index).getLexema();
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += token.get(index).getLexema();
                    index++;
                }

            }

        }
        this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);

        if (index < token.size()) {
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                index++;
            }
            if (index < token.size()) {

                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();
                }
            }
        }
    }

    public void operadorPertenencia(String nombreVar, String tipoDato, String valorVariable) {

        if (this.token.get(index).getLexema().equals("not")) {

            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;

            }
            if (this.token.get(index).getLexema().equals("in")) {
                valorVariable += token.get(index).getLexema();
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;

                }
                if (this.token.get(index).getLexema().equals("[")) {
                    valorVariable += token.get(index).getLexema();
                    listas(nombreVar, tipoDato, valorVariable);

                }

            }

        } else {
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;
            }
            if (this.token.get(index).getLexema().equals("[")) {
                valorVariable += token.get(index).getLexema();
                listas(nombreVar, tipoDato, valorVariable);

            }

        }
    }

    public void operadorTernario(String nombreVar, String tipoDato, String valorVariable) {
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valorVariable += token.get(index).getLexema();
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
            valorVariable += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                valorVariable += token.get(index).getLexema();
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {

                    valorVariable += token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                    }
                    if (this.token.get(index).getLexema().equals("else")) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            valorVariable += token.get(index).getLexema();
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
                            valorVariable += token.get(index).getLexema();
                            index++;
                        }
                    }
                }
            }
        }
        this.sybolTable.agregarSimbolo(nombreVar, "Operador Ternario", valorVariable);
    }

    //OPERADORES DE SALIDA PRINT
    public void print() {
        String prueba = "";
        if (this.token.get(index).getLexema().equals("print")) {
            prueba += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                prueba += token.get(index).getLexema();
                index++;
            }
            if (this.token.get(index).getLexema().equals("(")) {
                prueba += token.get(index).getLexema();
                index++;
                if (token.get(index).getLexema().equals("f")) {
                    index++;
                }
                try {
                    while (!this.token.get(index).getLexema().equals(")")) {
                        System.out.println("Dentro del while print");
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            prueba += token.get(index).getLexema();
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {

                            prueba += token.get(index).getLexema();
                            index++;
                            indentado();
                            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna()));

                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index - 1).getColumna()));

                                break;
                            }
                            if (this.token.get(index).getLexema().equals("+") || this.token.get(index).getLexema().equals(",")) {
                                prueba += token.get(index).getLexema();
                                index++;

                            }
                        }
                    }
                    System.out.println("Palabra correcta");
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }

        index++;
        //this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
        if (index < token.size()) {
            indentado();
            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                index++;

            }
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();
                } else if (token.get(index).getToken().equals(TypeToken.ID)) {
                    syntaxAnalyer();
                }
            }
        }
    }

    //CONDICIONALES IF
    public void condicionIf() {

        try {
            if (this.token.get(index).getLexema().equals("if") || this.token.get(index).getLexema().equals("elif") || this.token.get(index).getLexema().equals("while")) {
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }
                if (this.token.get(index).getToken().equals(TypeToken.BOOL) || this.token.get(index).getToken().equals(TypeToken.OP_LOG)) {
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        index++;

                    }
                    if (this.token.get(index).getToken().equals(TypeToken.ID)) {
                        index++;

                    }
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        index++;

                    }
                    if (this.token.get(index).getLexema().equals(":")) {
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {

                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                                conteoIndentado++;
                                indentado();
                                System.out.println("Indentado registrado");
                                if (this.token.get(index).getToken().equals(TypeToken.ID)) {
                                    idAnalyzer();
                                } else if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                    syntaxAnalyer();
                                }

                            } else {
                                System.out.println("Error no tiene indentado");
                            }

                        }
                    } else {
                        index++;
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna()));
                    }
                } else if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.CONS)) {
                    opCompIf();
                    if (this.token.get(index).getLexema().equals(":")) {
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                            index++;
                        }
                        if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                                indentado();
                                System.out.println("Indentado registrado");
                                syntaxAnalyer();
                            } else {
                                System.out.println("Error no tiene indentado");
                            }

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna()));

                    }

                }
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void indentado() {
        while (index < token.size() && token.get(index).getToken().equals(TypeToken.SPACE)) {
            System.out.println("Espacio");
            index++;
            conteoIndentado++;
        }

    }

    public void cicloFor() {
        hayFor = true;
        index++;
        indentado();
        if (token.get(index).getToken().equals(TypeToken.ID)) {
            index++;
        }
        indentado();
        if (token.get(index).getLexema().equals("in")) {
            index++;
        }
        indentado();
        if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getLexema().equals("range")) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
            }
            indentado();
            if (token.get(index).getToken().equals(TypeToken.ENTERO)) {
                index++;
                indentado();
                if (token.get(index).getLexema().equals(",")) {
                    index++;
                    indentado();
                    if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
                        index++;
                        indentado();
                        if (token.get(index).getLexema().equals(")")) {
                            index++;
                            if (token.get(index).getLexema().equals(":")) {
                                index++;
                                syntaxAnalyer();
                            }

                        }
                    }
                }

            }
            if (token.get(index).getLexema().equals(")")) {
                index++;
                indentado();
                if (token.get(index).getLexema().equals(":")) {
                    index++;
                    syntaxAnalyer();

                }

            }

        }

        indentado();
        if (token.get(index).getLexema().equals(":")) {
            index++;
        }
        indentado();
        syntaxAnalyer();
    }

    public void opCompIf() {
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
            index++;

            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.CONS)) {
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }

            }
        } else if (this.token.get(index).getLexema().equals("in")) {
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                index++;
            }
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.CONS)) {
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                }

            }

        }

    }

    public SymbolTable getSybolTable() {
        return sybolTable;
    }
}
