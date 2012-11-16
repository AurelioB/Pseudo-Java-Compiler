/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

//import com.sun.org.apache.bcel.internal.generic.ALOAD;

/**
 *
 * @author KeriosUl
 */
public class Sintactico {

    private AnalizadorLexico analisadorLexico;

    public Sintactico(AnalizadorLexico lexico) {
        analisadorLexico = lexico;



    }

    public boolean modifier() {
        Token tokenActual = analisadorLexico.getNextToken();

        return tokenActual.getLexema() == Lexemas.PUBLIC || tokenActual.getLexema() == Lexemas.STATIC;
    }

    public boolean modifiers() {

        modifier();
        Token tokenActual = analisadorLexico.getNextToken();
        if (tokenActual.getLexema() == Lexemas.PUBLIC || tokenActual.getLexema() == Lexemas.STATIC) {
            return true;
        } else {
            return false;
        }
    }

    public boolean compilationUnit() {

        return typeDeclaration();
    }

    public boolean typeDeclaration() {
        return classDeclaration();
    }

    public boolean classDeclaration() {
        modifiers();
        Token tokenActual = analisadorLexico.getNextToken();
        if (tokenActual.getLexema() == Lexemas.CLASS) {
            tokenActual = analisadorLexico.getNextToken();
            if (tokenActual.getLexema() == Lexemas.ID) {
                tokenActual = analisadorLexico.getNextToken();
                if (tokenActual.getLexema() == Lexemas.LLAVE_IZQ) {
                    declarationsOpc();
                    tokenActual = analisadorLexico.getNextToken();
                    if (tokenActual.getLexema() == Lexemas.LLAVE_DER) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean declarationsOpc() {
        return declarations();
    }

    public boolean declarations() {

        if (modifiers()) {
            if (type()) {
               // return declaration();
                return true; //Quitar esto
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean type() {
        return type_specifier();
    }

    public boolean type_specifier() {
        Token tokenActual = analisadorLexico.getNextToken();
        if (tokenActual.getLexema() == Lexemas.TIPO_INT || tokenActual.getLexema() == Lexemas.TIPO_BOOLEAN
                || tokenActual.getLexema() == Lexemas.TIPO_FLOAT || tokenActual.getLexema() == Lexemas.TIPO_STRING
                || tokenActual.getLexema() == Lexemas.VOID) {
            return true;
        } else {
            return false;
        }
    }

 
}