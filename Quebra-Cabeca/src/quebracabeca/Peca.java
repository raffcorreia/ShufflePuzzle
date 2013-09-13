
package quebracabeca;

import java.io.Serializable;
import javax.swing.JButton;

/**
 *Classe respons�vel pelo valor das pe�as,
 *implementa Serializable para poder ser salva em arquivo.
 */
public class Peca implements Serializable{
    private int Valor;

    /**
     *Construtor default, por decis�o de projeto n�o recebe valores.
     */
    public Peca(){
    }
    /**
     *Metodo que recebe o valor da pe�a.
     *@param valor  Recebe o valor da pe�a
     */
    public void setValor(int Valor){
        this.Valor = Valor;
    }
    
    
    /**
     * Metodo que retorna o valor da pe�a.
     * <p>
     *@return   Retorna o valor da pe�a.
     */    
    public int getValor(){
        return this.Valor;
    }
    
}
