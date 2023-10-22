package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import java.util.ArrayList;
import analizador_lexico.symbol_table.SymbolTable;
import javax.swing.JOptionPane;

public class Sintaxis {

    private boolean hayIf = false, hayElif = false, hayFor = false, hayDef = false;
    private boolean haySpace = false;
    private ArrayList<Token> token;
    private int index = 0;
    private SymbolTable sybolTable;
    boolean pasa = false;
    private int conteoIndentado = 0;
    String err;
    public ArrayList<String> errores = new ArrayList<>();
    public boolean syntaxErr = false;

    public Sintaxis(ArrayList<Token> token) {
        this.token = token;
        this.sybolTable = new SymbolTable();

    }

    public void syntaxAnalyer() {
        try {
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.ID)) {
                    idAnalyzer();
                    syntaxAnalyer();
                } else if (this.token.get(index).getLexema().equals("break")) {
                    index++;
                    syntaxAnalyer();

                } else if (this.token.get(index).getToken().equals(TypeToken.COM)) {

                    comentAnalyzer();
                    syntaxAnalyer();

                } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                    syntaxAnalyer();

                } else if (this.token.get(index).getLexema().equals("print")) {
                    print();
                    syntaxAnalyer();
                } else if (this.token.get(index).getLexema().equals("if")) {
                    hayIf = true;
                    condicionIf();
                    syntaxAnalyer();
                } else if (this.token.get(index).getLexema().equals("else")) {

                    if (hayIf || hayElif || hayFor) {
                        index++;
                        hayIf = false;
                        hayElif = false;
                        if (this.token.get(index).getLexema().equals(":")) {
                            index++;

                            syntaxAnalyer();
                        } else {
                            syntaxErr = true;
                            err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                            errores.add(err);
                            //index += 2;
                            syntaxAnalyer();

                        }
                    } else {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                        errores.add(err);
                        index += 2;
                        syntaxAnalyer();

                    }
                } else if (this.token.get(index).getLexema().equals("elif")) {
                    if (hayIf) {
                        hayElif = true;
                        condicionIf();
                        syntaxAnalyer();

                    } else {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                        errores.add(err);
                        index += 2;
                        syntaxAnalyer();

                    }

                    //AQUI VA EL METODO PARA RECONOCER LA SENTENCIA ELIF
                } else if (this.token.get(index).getLexema().equals("while")) {
                    //aqui va todo lo del while:
                    condicionIf();
                    syntaxAnalyer();
                } else if (this.token.get(index).getLexema().equals("for")) {
                    cicloFor();
                    syntaxAnalyer();
                } else if (token.get(index).getLexema().equals("def")) {
                    hayDef = true;
                    index++;
                    indentado();
                    funcionDef();
                    syntaxAnalyer();
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
                    indentado();
                    if (token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                        index++;
                        indentado();
                        if (token.get(index).getToken().equals(TypeToken.ENTERO)) {
                            index++;

                        } else {
                            index++;
                            syntaxErr = true;
                            err = "Error de sintaxis en liena: " + (token.get(index).getFila() - 2) + " y columna: " + (token.get(index - 2).getColumna());
                            errores.add(err);
                            indentado();

                        }

                    }

                } else {
                    index++;
                    syntaxErr = true;
                    err = "Error de sintaxis en liena: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                    errores.add(err);
                }

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

                    } else {
                        index++;
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                        errores.add(err);
                    }
                } else {
                    index++;
                    syntaxErr = true;
                    err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                    errores.add(err);
                }

            } else {
                index++;
                syntaxErr = true;
                err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                errores.add(err);
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

                    } else {
                        index++;
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                        errores.add(err);
                    }
                } else {
                    index++;
                    syntaxErr = true;
                    err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                    errores.add(err);
                }

            } else {
                index++;
                syntaxErr = true;
                err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
                errores.add(err);
            }
        } else {
            index++;
            syntaxErr = true;
            err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 1).getColumna());
            errores.add(err);
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
                indentado();

                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.BOOL) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
                    String tipoDato = token.get(index).getToken().name();
                    String valorVariable = token.get(index).getLexema();
                    index++;
                    if (index < token.size()) {

                        indentado();
                        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                            index++;

                        }
                    }

                    this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);
                    if (index < token.size()) {

                        if (this.token.get(index).getToken().equals(TypeToken.OP_ARIT)) {
                            //OPERADOR ARITMETICO
                            opAritmetico(nombreVar, tipoDato, valorVariable);

                        } else if (this.token.get(index).getLexema().equals("(")) {
                            index++;
                            tipoDato = "funcion";
                            valorVariable += token.get(index).getLexema();
                            operadorFuncion(nombreVar, tipoDato, valorVariable);

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

                            } else {
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                                errores.add(err);
                                if (token.get(index).getToken().equals(TypeToken.OP_AS)) {
                                    index += 4;
                                } else {

                                    index++;
                                    index++;
                                }

                            }
                        }
                    }

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
                }
            } else if (this.token.get(index).getToken().equals(TypeToken.OP_RE_AS)) {
                //Aqui va operador de reasignacion
                opReAsignacion(nombreVar);
                if (index < token.size()) {

                    syntaxAnalyer();
                }

            } else {
            }
        } else {
        }
    }

    public void operadorFuncion(String nombreVar, String tipo, String valor) {
        indentado();
        if (this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valor += token.get(index).getLexema();
            index++;
            indentado();
            if (this.token.get(index).getLexema().equals(",")) {
                valor += token.get(index).getLexema();
                index++;
                indentado();
                if (this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += token.get(index).getLexema();
                    index++;
                    indentado();
                    if (this.token.get(index).getLexema().equals(")")) {
                        valor += token.get(index).getLexema();
                        index++;
                        indentado();
                    } else {
                        index++;
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 2).getFila()) + " y columna: " + (token.get(index - 2).getColumna());
                        errores.add(err);
                    }
                } else {
                    index++;
                    syntaxErr = true;
                    err = "Error de sintaxis en linea: " + (token.get(index - 2).getFila()) + " y columna: " + (token.get(index - 2).getColumna());
                    errores.add(err);
                }

            } else {
                index++;
                syntaxErr = true;
                err = "Error de sintaxis en linea: " + (token.get(index - 2).getFila()) + " y columna: " + (token.get(index - 2).getColumna());
                errores.add(err);
            }
        } else {
            index++;
            syntaxErr = true;
            err = "Error de sintaxis en linea: " + (token.get(index - 2).getFila()) + " y columna: " + (token.get(index - 2).getColumna());
            errores.add(err);
        }

        this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
    }

    public void diccionario(String nombreVar, String tipo, String valor) {
        boolean enviarTabla = false;
        index++;
        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            valor += this.token.get(index).getLexema();
            index++;
        }
        try {
            int iterador = 0;
            while (!this.token.get(index).getLexema().equals("}")) {
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        String err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna());
                        errores.add(err);
                        enviarTabla = true;
                        break;
                    }
                    iterador++;
                    if (iterador == 20) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna());
                        errores.add(err);
                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna());
                        errores.add(err);
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
                                    syntaxErr = true;
                                    err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                                    errores.add(err);
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
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                                errores.add(err);
                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                                errores.add(err);
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
            int iterador = 0;
            while (!this.token.get(index).getLexema().equals("}")) {
                iterador++;
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                        errores.add(err);
                        enviarTabla = true;
                        break;
                    }
                    if (iterador == 20) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                        errores.add(err);
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                        errores.add(err);
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
                                    syntaxErr = true;
                                    err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                                    errores.add(err);
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
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                                errores.add(err);
                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                                errores.add(err);
                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getLexema().equals("]")) {
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                                errores.add(err);
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
//Aqui he modificado y he quirado el if de salto de space y com encerrado en un size

    public void opReAsignacion(String nombreVar) {
        index++;
        indentado();
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            String tipoDato = token.get(index).getToken().name();
            String valorVariable = token.get(index).getLexema();
            index++;
            this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);

        } else {
            index++;
            syntaxErr = true;
            err = "Error de sintaxis en linea: " + (token.get(index - 2).getFila()) + " y columna: " + (token.get(index - 2).getColumna());
            errores.add(err);

        }
    }

    public void operadorIdentidad(String nombreVar, String tipo, String valorVariable) {
        valorVariable += token.get(index).getLexema();
        index++;
        pasa = true;

        indentado();
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valorVariable += token.get(index).getLexema();
            index++;
            indentado();
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                valorVariable += token.get(index).getLexema();
                index++;

            }

        } else if (this.token.get(index).getLexema().equals("not")) {
            valorVariable += token.get(index).getLexema();
            index++;
            indentado();
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
            indentado();
        }
        try {

            while (!this.token.get(index).getLexema().equals("]")) {
                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valor += this.token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                        errores.add(err);
                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index - 1).getFila()) + " y columna: " + (token.get(index - 1).getColumna() - 1);
                        errores.add(err);
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
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index).getFila()) + " y columna: " + (token.get(index).getColumna() - 1);
                                errores.add(err);
                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
                                indentado();
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
        }
        index++;
        this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
    }

    //OPERADORES DE ASIGNACION
    public void operadorComparacion(String nombreVar, String tipoDato, String valorVariable) {
        valorVariable += token.get(index).getLexema();
        index++;

        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
            indentado();
        }
        if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
            valorVariable += token.get(index).getLexema();
            tipoDato = TypeToken.BOOL.name();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;
                indentado();
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_LOG)) {
                valorVariable += token.get(index).getLexema();
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    valorVariable += token.get(index).getLexema();
                    index++;
                    indentado();
                }
                if (this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    valorVariable += token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                        indentado();
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                            valorVariable += token.get(index).getLexema();
                            index++;
                            indentado();
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
                indentado();

            }
            if (this.token.get(index).getLexema().equals("in")) {
                valorVariable += token.get(index).getLexema();
                index++;
                indentado();
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
                indentado();
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
            indentado();
        }
        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {
            valorVariable += token.get(index).getLexema();
            index++;
            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                valorVariable += token.get(index).getLexema();
                index++;
                indentado();
            }
            if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {
                valorVariable += token.get(index).getLexema();
                index++;

                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                    indentado();
                }
                if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ID)) {

                    valorVariable += token.get(index).getLexema();
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valorVariable += token.get(index).getLexema();
                        index++;
                        indentado();
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
        int resta = 1;
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
                        indentado();

                        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID)) {

                            prueba += token.get(index).getLexema();
                            index++;
                            indentado();
                            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {

                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                                resta = 0;
                                break;
                            }

                            if (this.token.get(index).getLexema().equals("+") || this.token.get(index).getLexema().equals(",")) {
                                prueba += token.get(index).getLexema();
                                index++;

                            }

                        }
                    }
                } catch (IndexOutOfBoundsException e) {

                }
            }
        }
        if (this.token.get(index).getLexema().equals(")")) {
            index++;
        } else {
            syntaxErr = true;
            err = "Error de sintaxis en lineaaa: " + (token.get(index).getFila() - resta) + " y columna: " + (token.get(index - 1).getColumna());
            errores.add(err);
            //index++;
        }
        //this.sybolTable.agregarSimbolo(nombreVar, tipo, valor);
        if (index < token.size()) {
            indentado();
            if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                index++;

            }
            if (index < token.size()) {
                if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                    index++;
                }
            }
        }
    }

    //AQUI VA LA LOGICA DE LA FUNCION DEF
    public void funcionDef() {
        if (token.get(index).getToken().equals(TypeToken.ID)) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
                indentado();
                if (token.get(index).getLexema().equals("*")) {
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
                            if (token.get(index).getLexema().equals(",")) {
                                index++;
                                indentado();
                            }
                            if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getToken().equals(TypeToken.ENTERO)) {
                                index++;
                                indentado();
                            }
                            if (token.get(index).getLexema().equals(")")) {
                                index++;
                                if (token.get(index).getLexema().equals(":")) {
                                    index++;
                                    // syntaxAnalyer();
                                } else {
                                    syntaxErr = true;
                                    err = "Error de sintaxis linea: " + (token.get(index).getFila() - 1) + " y columna: " + token.get(index - 1).getColumna();
                                    errores.add(err);
                                }

                            } else {
                                syntaxErr = true;
                                err = "Error de sintaxis linea: " + token.get(index).getFila() + " y columna: " + token.get(index).getColumna();
                                errores.add(err);
                            }
                        } else {
                            syntaxErr = true;
                            err = "Error de sintaxis linea: " + token.get(index).getFila() + " y columna: " + token.get(index).getColumna();
                            errores.add(err);
                        }
                    } else if (token.get(index).getLexema().equals(")")) {
                        index++;
                        indentado();
                        if (token.get(index).getLexema().equals(":")) {
                            index++;
                            // syntaxAnalyer();

                        } else {
                            syntaxErr = true;
                            err = "Error de sintaxis linea: " + (token.get(index).getFila() - 1) + " y columna: " + token.get(index - 1).getColumna();
                            errores.add(err);
                        }

                    } else {
                        syntaxErr = true;
                        err = "Error de sintaxis linear: " + (token.get(index).getFila()) + " y columna: " + token.get(index - 1).getColumna();
                        errores.add(err);
                    }

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

                        indentado();
                    }
                    if (this.token.get(index).getLexema().equals(":")) {
                        index++;
                        // syntaxAnalyer();
                    } else {
                        index++;
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 2).getColumna());
                        errores.add(err);
                    }
                } else if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.CONS)) {
                    opCompIf();
                    if (this.token.get(index).getLexema().equals(":")) {
                        index++;
                        //syntaxAnalyer();
                    } else {
                        index++;
                        syntaxErr = true;
                        err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 2).getColumna());
                        errores.add(err);
                    }

                }
            }
        } catch (IndexOutOfBoundsException e) {

        }
    }

    public void indentado() {
        while (index < token.size() && token.get(index).getToken().equals(TypeToken.SPACE)) {
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
            indentado();
        }
        if (token.get(index).getLexema().equals("in")) {
            index++;
            indentado();
        }
        if (token.get(index).getToken().equals(TypeToken.ID) || token.get(index).getLexema().equals("range")) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals("(")) {
                index++;
            }
            indentado();
            if (token.get(index).getToken().equals(TypeToken.ENTERO) || token.get(index).getToken().equals(TypeToken.ID)) {
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

                            } else {
                                index++;
                                syntaxErr = true;
                                err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 2).getColumna());
                                errores.add(err);

                            }

                        } else {
                            index++;
                            syntaxErr = true;
                            err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 2).getColumna());
                            errores.add(err);

                        }
                    }
                }

            }
            if (token.get(index).getLexema().equals(")")) {
                index++;
                indentado();
                if (token.get(index).getLexema().equals(":")) {
                    index++;

                }

            }

        } else if (token.get(index).getToken().equals(TypeToken.ID)) {
            index++;
            indentado();
            if (token.get(index).getLexema().equals(":")) {
                index++;
                indentado();
            } else {
                index++;
                syntaxErr = true;
                err = "Error de sintaxis en linea: " + (token.get(index).getFila() - 1) + " y columna: " + (token.get(index - 2).getColumna());
                errores.add(err);
            }
        }

        //syntaxAnalyer();
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
                indentado();
            }
            if (this.token.get(index).getToken().equals(TypeToken.ENTERO) || this.token.get(index).getToken().equals(TypeToken.ID) || this.token.get(index).getToken().equals(TypeToken.CONS)) {
                index++;
                if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                    index++;
                    indentado();
                }

            }

        }

    }

    public SymbolTable getSybolTable() {
        return sybolTable;
    }
}
