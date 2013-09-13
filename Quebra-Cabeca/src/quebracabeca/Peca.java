
package quebracabeca;

import java.io.Serializable;
import javax.swing.JButton;

/**
 *Classe responsável pelo valor das peças,
 *implementa Serializable para poder ser salva em arquivo.
 */
public class Peca implements Serializable{
    private int Valor;

    /**
     *Construtor default, por decisão de projeto não recebe valores.
     */
    public Peca(){
    }
    /**
     *Metodo que recebe o valor da peça.
     *@param valor  Recebe o valor da peça
     */
    public void setValor(int Valor){
        this.Valor = Valor;
    }
    
    
    /**
     * Metodo que retorna o valor da peça.
     * <p>
     *@return   Retorna o valor da peça.
     */    
    public int getValor(){
        return this.Valor;
    }
    
}
