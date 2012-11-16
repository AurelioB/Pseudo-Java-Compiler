/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador;

/**
 *
 * @author KeriosUl
 */
public class Token {
    
    private Lexemas lexema;
    private String valor;

    public Token(){
        
    }
    public Token(String valor, Lexemas lexema){
        this.valor = valor;
        this.lexema = lexema;
    }
    
    public Lexemas getLexema() {
        return lexema;
    }

    public void setLexema(Lexemas lexema) {
        this.lexema = lexema;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
