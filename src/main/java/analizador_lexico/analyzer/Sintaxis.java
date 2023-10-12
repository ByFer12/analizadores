package analizador_lexico.analyzer;

import analizador_lexico.enums.TypeToken;
import analizador_lexico.errors.ErrorSyntaxIndexOut;
import java.util.ArrayList;
import analizador_lexico.symbol_table.SymbolTable;
import javax.swing.JOptionPane;

public class Sintaxis {

    private ArrayList<Token> token;
    private int index = 0;
    private SymbolTable sybolTable;
    boolean pasa = false;

    public Sintaxis(ArrayList<Token> token) {
        this.token = token;
        this.sybolTable = new SymbolTable();

    }

    public void syntaxAnalyer() {
        if (this.token.get(index).getToken().equals(TypeToken.ID)) {
            idAnalyzer();
        } else if (this.token.get(index).getToken().equals(TypeToken.COM)) {
            comentAnalyzer();

        } else if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
            index++;
            syntaxAnalyer();

        } else if (this.token.get(index).getToken().equals(TypeToken.KY_WD)) {
            kwAnalyzer();
        }

    }

    public void comentAnalyzer() {
        if (this.token.get(index).getToken().equals(TypeToken.COM)) {
            index++;
            syntaxAnalyer();
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

                if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.BOOL) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
                    String tipoDato = token.get(index).getToken().name();
                    String valorVariable = token.get(index).getLexema();
                    index++;

                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        index++;

                    }
                    if (this.token.get(index).getToken().equals(TypeToken.COM)) {
                        index++;

                    }
                    this.sybolTable.agregarSimbolo(nombreVar, tipoDato, valorVariable);

                    if (this.token.get(index).getToken().equals(TypeToken.OP_ARIT)) {
                        //OPERADOR ARITMETICO
                        opAritmetico(nombreVar, tipoDato, valorVariable);

                    } else if (this.token.get(index).getLexema().equals("is")) {

                        //OPERADOR DE IDENTIDAD
                        operadorIdentidad(nombreVar, tipoDato, valorVariable);

                    } else if (this.token.get(index).getToken().equals(TypeToken.OP_COMP)) {

                        //OPERADOR DE COMPARACION
                        operadorComparacion(nombreVar, tipoDato, valorVariable);

                    } else {
                        JOptionPane.showMessageDialog(null, "Error de sintaxis en linea: " + token.get(index).getFila() + " y columna: " + token.get(index).getColumna());
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
                            }
                        }
                    }

                    if (index < token.size()) {
                        syntaxAnalyer();
                    }

                    //Aqui va la logica para una lista
                } else if (this.token.get(index).getToken().equals(TypeToken.OT) && this.token.get(index).getLexema().equals("[")) {
                    String tipo = this.token.get(index).getToken().name();
                    String valor = this.token.get(index).getLexema();
                    listas(nombreVar, tipo, valor);

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

    public void opAritmetico(String nombre, String tipo, String valorVariable) {
        pasa = true;
        valorVariable += token.get(index).getLexema();
        index++;

        if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
            index++;
        }
        if (this.token.get(index).getToken().equals(TypeToken.CONS) || this.token.get(index).getToken().equals(TypeToken.ENTERO)) {
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
                    System.out.println("Tk: " + this.token.get(index).getLexema());
                    index++;
                    if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                        valor += this.token.get(index).getLexema();
                        index++;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                        enviarTabla = true;
                        break;
                    }
                    if (this.token.get(index).getToken().equals(TypeToken.OT)) {
                        if (this.token.get(index).getLexema().equals(",")) {
                            valor += this.token.get(index).getLexema();

                            index++;
                            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {

                                enviarTabla = true;
                                break;
                            }
                            if (this.token.get(index).getToken().equals(TypeToken.SPACE)) {
                                index++;
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
            if (this.token.get(index).getToken().equals(TypeToken.SALT)) {
                index++;
                syntaxAnalyer();
            }
        }
    }

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

    public void kwAnalyzer() {
        System.out.println("Estoy en analyzer");
    }

    public SymbolTable getSybolTable() {
        return sybolTable;
    }
}
