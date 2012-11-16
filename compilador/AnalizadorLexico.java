/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

import compilador.exceptions.ErrorLexico;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author KeriosUl
 */
public final class AnalizadorLexico {

    private String RegexDig = "\\d+";
    private String RegexDigReal = "\\d*\\.\\d+";
    private String RegexID = "[a-zA-Z]([a-zA-z]|[0-9]|(_))*";
    private String RegexChain = "[\"]([\\\\]([n]|[r]|[t]|[b]|[f]|[\']|[\"])|[^\\\\\"])*[\"]";
    private String RegexChar = "[\']([\\\\]([r]|[n]|[b]|[f]|[t]|[\']|[\"]))[\']|[\'][^\\'][\']";
    private List<Token> tokens;
    private List<ErrorLexico> erroresLexicos;
    private int contadorToken;

    public AnalizadorLexico() {
        contadorToken = 0;
        tokens = new ArrayList<Token>();
        erroresLexicos = new ArrayList<ErrorLexico>();
    }

    public AnalizadorLexico(String[] texto) {
        this();
        analizarTexto(texto);
    }

    public List<ErrorLexico> getListaErrores() {
        return erroresLexicos;
    }

    public Token getNextToken() {
        if (contadorToken >= tokens.size()) {
            return null;
        } else {
            return tokens.get(contadorToken++);
        }
    }

    private void limpiarTokens() {
        tokens.clear();
        erroresLexicos.clear();
        contadorToken = 0;
    }
    

    public void analizarTexto(String[] texto) {

        limpiarTokens();
        

        boolean comillaSimple = false;
        boolean comillaDoble = false;
        boolean comentario = false;
        String buffer = "";

        for (int i = 0; i < texto.length; i++) {
            comillaSimple = false;
            comillaDoble = false;
            comentario = false;

            String lineaActual = texto[i];
            lineaActual = lineaActual.trim();
            buffer = "";
            for (int j = 0; j < lineaActual.length(); j++) {

                char caracterActual = lineaActual.charAt(j);
                if (!comentario) {
                    if (comillaDoble) {
                        if (caracterActual == '"') {
                            tokens.add(construirToken(buffer + '"', i + 1));
                            buffer = "";
                            comillaDoble = false;
                        } else {
                            buffer += caracterActual;
                        }
                    } else if (comillaSimple) {
                        if (caracterActual == '\'') {
                            tokens.add(construirToken(buffer + '\'', i + 1));
                            buffer = "";
                            comillaSimple = false;
                        } else {
                            buffer += caracterActual;
                        }
                    } else {
                        switch (caracterActual) {
                            case '"':
                                comillaDoble = true;
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "\"";
                                break;
                            case '\'':
                                comillaSimple = true;
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "'";
                                break;
                            case ' ':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer.toString(), i + 1));
                                }
                                buffer = "";
                                break;
                            case '+':
                                if (buffer.equals("+")) {
                                    buffer += caracterActual;
                                    tokens.add(construirToken(buffer, i + 1));
                                    buffer = "";
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "" + caracterActual;
                                }
                                break;
                            case '-':
                                if (buffer.equals("-")) {
                                    buffer += caracterActual;
                                    tokens.add(construirToken(buffer, i + 1));
                                    buffer = "";
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "" + caracterActual;
                                }
                                break;
                            case '*':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "*";
                                break;
                            case '/':
                                if (buffer.equals("/")) {
                                    buffer += '/';
                                    comentario = true;
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "/";
                                }
                                break;
                            case '%':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "%";
                                break;
                            case '!':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "!";
                                break;
                            case '&':
                                if (buffer.equals("&")) {
                                    tokens.add(construirToken("&&", i + 1));
                                    buffer = "";
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "&";
                                }
                                break;
                            case '|':
                                if (buffer.equals("|")) {
                                    tokens.add(construirToken("||", i + 1));
                                    buffer = "";
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "|";
                                }
                                break;
                            case '<':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "<";
                                break;
                            case '>':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = ">";
                                break;
                            case '.':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = ".";
                                break;
                            case ';':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = ";";
                                break;
                            case ',':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = ",";
                                break;
                            case '(':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "(";
                                break;
                            case ')':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = ")";
                                break;
                            case '{':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "{";
                                break;
                            case '}':
                                if (buffer.length() > 0) {
                                    tokens.add(construirToken(buffer, i + 1));
                                }
                                buffer = "}";
                                break;
                            case '=':
                                if (buffer.equals("=")
                                        || buffer.equals("!")
                                        || buffer.equals("<")
                                        || buffer.equals(">")
                                        || buffer.equals("*")
                                        || buffer.equals("/")
                                        || buffer.equals("%")
                                        || buffer.equals("+")
                                        || buffer.equals("-")) {
                                    tokens.add(construirToken(buffer + "=", i + 1));
                                    buffer = "";
                                } else {
                                    if (buffer.length() > 0) {
                                        tokens.add(construirToken(buffer, i + 1));
                                    }
                                    buffer = "=";
                                }
                                break;
                            default:
                                if (buffer.equals("=")
                                        || buffer.equals("!")
                                        || buffer.equals("<")
                                        || buffer.equals(">")
                                        || buffer.equals("*")
                                        || buffer.equals("/")
                                        || buffer.equals("%")
                                        || buffer.equals("+")
                                        || buffer.equals("-")
                                        || buffer.equals("(")
                                        || buffer.equals(")")
                                        || buffer.equals(";")
                                        || buffer.equals(",")
                                        || buffer.equals(".")) {
                                    tokens.add(construirToken(buffer, i + 1));
                                    buffer = "";
                                }
                                buffer += caracterActual;
                                break;
                        }
                    }
                } else {
                    buffer += caracterActual;
                }
            }
            if (buffer.length() > 0) {
                if (comentario) {
                    Token tockenComentario = new Token();
                    tockenComentario.setValor(buffer);
                    tockenComentario.setLexema(Lexemas.COMENTARIO);
                    tokens.add(tockenComentario);
                } else { //errores lexicos
                    if (comillaDoble) { //no cerro comillas
                        erroresLexicos.add(new ErrorLexico("No se cerraron comillas dobles.", i + 1));
                    } else if (comillaSimple) {
                        erroresLexicos.add(new ErrorLexico("No se cerraron comillas simples.", i + 1));
                    } else if (buffer.equals("&")) {
                        erroresLexicos.add(new ErrorLexico("& no reconocido.", i + 1));
                    } else if (buffer.equals("|")) {
                        erroresLexicos.add(new ErrorLexico("| no reconocido.", i + 1));
                    }
                    Token lastToken = construirToken(buffer, i + 1);
                    tokens.add(lastToken);
                }
            }
        }
    }

    private Token construirToken(String palabra, int lineaActual) {
        Token nuevoToken = new Token();
        nuevoToken.setValor(palabra);
        nuevoToken.setLexema(obtenerTipoLexema(palabra));
        if (nuevoToken.getLexema() == Lexemas.UNDEFINED) {//error lexico
            erroresLexicos.add(new ErrorLexico("Cadena de caracacteres no válida: " + palabra, lineaActual));
        }
        return nuevoToken;
    }

    private Lexemas obtenerTipoLexema(String palabra) {
        if (esClass(palabra)) {
            return Lexemas.CLASS;
        } else if (esPublic(palabra)) {
            return Lexemas.PUBLIC;
        } else if (esStatic(palabra)) {
            return Lexemas.STATIC;
        } else if (esVoid(palabra)) {
            return Lexemas.VOID;
        } else if (esMain(palabra)) {
            return Lexemas.MAIN;
        } else if (esWhile(palabra)) {
            return Lexemas.WHILE;
        } else if (esFor(palabra)) {
            return Lexemas.FOR;
        } else if (esIf(palabra)) {
            return Lexemas.IF;
        } else if (esElse(palabra)) {
            return Lexemas.ELSE;
        } else if (esBreak(palabra)) {
            return Lexemas.BREAK;
        } else if (esContinue(palabra)) {
            return Lexemas.CONTINUE;
        } else if (esReturn(palabra)) {
            return Lexemas.RETURN;
        } else if (esSystem(palabra)) {
            return Lexemas.SYSTEM;
        } else if (esOut(palabra)) {
            return Lexemas.OUT;
        } else if (esIn(palabra)) {
            return Lexemas.IN;
        } else if (esPrintln(palabra)) {
            return Lexemas.PRINTLN;
        } else if (esRead(palabra)) {
            return Lexemas.READ;
        } else if (esString(palabra)) {
            return Lexemas.TIPO_STRING;
        } else if (esInt(palabra)) {
            return Lexemas.TIPO_INT;
        } else if (esBoolean(palabra)) {
            return Lexemas.TIPO_BOOLEAN;
        } else if (esFloat(palabra)) {
            return Lexemas.TIPO_FLOAT;
        } else if (esSuma(palabra)) {
            return Lexemas.OP_SUMA;
        } else if (esResta(palabra)) {
            return Lexemas.OP_RESTA;
        } else if (esMulti(palabra)) {
            return Lexemas.OP_MULT;
        } else if (esDivi(palabra)) {
            return Lexemas.OP_DIV;
        } else if (esMod(palabra)) {
            return Lexemas.OP_MOD;
        } else if (esInc(palabra)) {
            return Lexemas.OP_INC;
        } else if (esDec(palabra)) {
            return Lexemas.OP_DEC;
        } else if (esIgual(palabra)) {
            return Lexemas.OP_IGUAL;
        } else if (esDif(palabra)) {
            return Lexemas.OP_DIF;
        } else if (esMenorQ(palabra)) {
            return Lexemas.OP_MENOR;
        } else if (esMayorQ(palabra)) {
            return Lexemas.OP_MAYOR;
        } else if (esMenIg(palabra)) {
            return Lexemas.OP_MENIG;
        } else if (esMayIg(palabra)) {
            return Lexemas.OP_MAYIG;
        } else if (esNot(palabra)) {
            return Lexemas.OP_NOT;
        } else if (esAnd(palabra)) {
            return Lexemas.OP_AND;
        } else if (esOr(palabra)) {
            return Lexemas.OP_OR;
        } else if (esAsign(palabra)) {
            return Lexemas.OP_ASIGNACION;
        } else if (esAsigMul(palabra)) {
            return Lexemas.OP_ASIG_MUL;
        } else if (esAsigDiv(palabra)) {
            return Lexemas.OP_ASIG_DIV;
        } else if (esAsigMod(palabra)) {
            return Lexemas.OP_ASIG_MOD;
        } else if (esAsigSum(palabra)) {
            return Lexemas.OP_ASIG_SUMA;
        } else if (esAsigRes(palabra)) {
            return Lexemas.OP_ASIG_RESTA;
        } else if (esPunto(palabra)) {
            return Lexemas.PUNTO;
        } else if (esPYC(palabra)) {
            return Lexemas.PYC;
        } else if (esComa(palabra)) {
            return Lexemas.COMA;
        } else if (esParIz(palabra)) {
            return Lexemas.PAR_IZQ;
        } else if (esParDer(palabra)) {
            return Lexemas.PAR_DER;
        } else if (esLlaveIz(palabra)) {
            return Lexemas.LLAVE_IZQ;
        } else if (esLlaveDer(palabra)) {
            return Lexemas.LLAVE_DER;
        } else if (esLogica(palabra)) {
            return Lexemas.CONST_LOGICA;
        } else if (esID(palabra)) {
            return Lexemas.ID;
        } else if (esEntero(palabra)) {
            return Lexemas.CONST_ENTERA;
        } else if (esReal(palabra)) {
            return Lexemas.CONST_REAL;
        } else if (esConstCaracter(palabra)) {
            return Lexemas.CONST_CARACTER;
        } else if (esCadena(palabra)) {
            return Lexemas.CONST_CADENA;
        } else {
            return Lexemas.UNDEFINED;
        }
    }

    public boolean esClass(String palabra) {
        return palabra.equalsIgnoreCase("class");
    }

    public boolean esPublic(String palabra) {
        return palabra.equalsIgnoreCase("public");
    }

    public boolean esStatic(String palabra) {
        return palabra.equalsIgnoreCase("static");
    }

    public boolean esVoid(String palabra) {
        return palabra.equalsIgnoreCase("void");
    }

    public boolean esMain(String palabra) {
        return palabra.equalsIgnoreCase("main");
    }

    public boolean esWhile(String palabra) {
        return palabra.equalsIgnoreCase("while");
    }

    public boolean esFor(String palabra) {
        return palabra.equalsIgnoreCase("for");
    }

    public boolean esIf(String palabra) {
        return palabra.equalsIgnoreCase("if");
    }

    public boolean esElse(String palabra) {
        return palabra.equalsIgnoreCase("else");
    }

    public boolean esBreak(String palabra) {
        return palabra.equalsIgnoreCase("break");
    }

    public boolean esContinue(String palabra) {
        return palabra.equalsIgnoreCase("continue");
    }

    public boolean esReturn(String palabra) {
        return palabra.equalsIgnoreCase("return");
    }

    public boolean esSystem(String palabra) {
        return palabra.equals("System");
    }

    public boolean esOut(String palabra) {
        return palabra.equalsIgnoreCase("out");
    }

    public boolean esIn(String palabra) {
        return palabra.equalsIgnoreCase("in");
    }

    public boolean esPrintln(String palabra) {
        return palabra.equalsIgnoreCase("println");
    }

    public boolean esRead(String palabra) {
        return palabra.equalsIgnoreCase("read");
    }

    public boolean esString(String palabra) {
        return palabra.equals("String");
    }

    public boolean esInt(String palabra) {
        return palabra.equalsIgnoreCase("int");
    }

    public boolean esBoolean(String palabra) {
        return palabra.equalsIgnoreCase("boolean");
    }

    public boolean esFloat(String palabra) {
        return palabra.equalsIgnoreCase("float");
    }

    public boolean esSuma(String palabra) {
        return palabra.equalsIgnoreCase("+");
    }

    public boolean esResta(String palabra) {
        return palabra.equalsIgnoreCase("-");
    }

    public boolean esMulti(String palabra) {
        return palabra.equalsIgnoreCase("*");
    }

    public boolean esDivi(String palabra) {
        return palabra.equalsIgnoreCase("/");
    }

    public boolean esMod(String palabra) {
        return palabra.equalsIgnoreCase("%");
    }

    public boolean esMenorQ(String palabra) {
        return palabra.equalsIgnoreCase("<");
    }

    public boolean esMayorQ(String palabra) {
        return palabra.equalsIgnoreCase(">");
    }

    public boolean esNot(String palabra) {
        return palabra.equalsIgnoreCase("!");
    }

    public boolean esAsign(String palabra) {
        return palabra.equalsIgnoreCase("=");
    }

    public boolean esPunto(String palabra) {
        return palabra.equalsIgnoreCase(".");
    }

    public boolean esPYC(String palabra) {
        return palabra.equalsIgnoreCase(";");
    }

    public boolean esComa(String palabra) {
        return palabra.equalsIgnoreCase(",");
    }

    public boolean esParIz(String palabra) {
        return palabra.equalsIgnoreCase("(");
    }

    public boolean esParDer(String palabra) {
        return palabra.equalsIgnoreCase(")");
    }

    public boolean esLlaveIz(String palabra) {
        return palabra.equalsIgnoreCase("{");
    }

    public boolean esLlaveDer(String palabra) {
        return palabra.equalsIgnoreCase("}");
    }

    public boolean esInc(String palabra) {
        return palabra.equalsIgnoreCase("++");
    }

    public boolean esDec(String palabra) {
        return palabra.equalsIgnoreCase("--");
    }

    public boolean esIgual(String palabra) {
        return palabra.equalsIgnoreCase("==");
    }

    public boolean esDif(String palabra) {
        return palabra.equalsIgnoreCase("!=");
    }

    public boolean esMenIg(String palabra) {
        return palabra.equalsIgnoreCase("<=");
    }

    public boolean esMayIg(String palabra) {
        return palabra.equalsIgnoreCase(">=");
    }

    public boolean esAnd(String palabra) {
        return palabra.equalsIgnoreCase("&&");
    }

    public boolean esOr(String palabra) {
        return palabra.equalsIgnoreCase("||");
    }

    public boolean esAsigMul(String palabra) {
        return palabra.equalsIgnoreCase("*=");
    }

    public boolean esAsigDiv(String palabra) {
        return palabra.equalsIgnoreCase("/=");
    }

    public boolean esAsigMod(String palabra) {
        return palabra.equalsIgnoreCase("%=");
    }

    public boolean esAsigSum(String palabra) {
        return palabra.equalsIgnoreCase("+=");
    }

    public boolean esAsigRes(String palabra) {
        return palabra.equalsIgnoreCase("-=");
    }

    public boolean esEntero(String palabra) {
        return palabra.matches(RegexDig);
    }

    public boolean esReal(String palabra) {
        return palabra.matches(RegexDigReal);
    }

    public boolean esID(String palabra) {
        return palabra.matches(RegexID);
    }

    public boolean esLogica(String palabra) {
        return palabra.equalsIgnoreCase("true") || palabra.equalsIgnoreCase("false");
    }

    public boolean esConstCaracter(String palabra) {
        return palabra.matches(RegexChar);
    }

    public boolean esCadena(String palabra) {
        return palabra.matches(RegexChain);
    }
}
