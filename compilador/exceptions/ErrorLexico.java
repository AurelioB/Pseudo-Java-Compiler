/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package compilador.exceptions;

/**
 *
 * @author KeriosUl
 */
public class ErrorLexico extends Exception{
    
    private int linea = 0;
    private String mensaje;
    
    public ErrorLexico(String mensaje, int linea){
        super(mensaje);
        this.linea = linea;
        this.mensaje = mensaje;
    }
    
    public int getLinea(){
        return linea;
    }
    
    public String getMensaje(){
        return mensaje;
    }    

    @Override
    public String toString() {
        return "ErrorLexico{" + "linea=" + linea + ", mensaje=" + mensaje + '}';
    }
    
}
