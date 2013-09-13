package quebracabeca;


import java.io.Serializable;

/**
 *Classe respons�vel pela pontua��o dos jogadores.
 *Implementa Serializable para poder ser salvo em arquivo e Comparable para facilitar a ordena��o
 * em caso de um ArrayList.
 */
public final class Pontuacao implements Serializable, Comparable<Pontuacao>{
    private double ponto;
    private int tempo;

    /*
     *Construtor default, por decis�o de projeto n�o recebe valores. 
     */
    public Pontuacao() {
    }
  
    /**
     *Metodo que define a pontua��o deste jogador.
     *@param    ponto   recebe a pontua��o deste jogador.
     */    
    public void setPonto(double ponto){
        this.ponto = ponto;
    }
    
    /**
     *Metodo que retorna a pontua��o deste jogador.
     *@return   Retorna a pontua��o deste jogador.
     */    
    public double getPonto(){
        return this.ponto;
    }
    
    /**
     *Metodo que define o tempo gasto por este jogador para vencer o jogo.
     *@param    tempo   Recebe o tempo gasto por este jogador.
     */    
    public void setTempo(int tempo){
        this.tempo = tempo;
    }
    
    /**
     *Metodo que retorna o tempo gasto por este jogador.
     *@return   Retorna o tempo gasto por este jogador.
     */    
    public int getTempo(){
        return this.tempo;
    }    

    /**
     *Metodo de compara��o, criado exclusivamente para atender as necessidades do "Comparable".
     *@param    outraPontuacao  Recebe a pontua��o que ser� comparada.
     */    
    public int compareTo(Pontuacao outraPontuacao) {
        if(outraPontuacao.ponto==this.ponto){
            return 0;
        }else if(outraPontuacao.ponto<this.ponto){
            return -1;
        }else{
            return 1;
        }
    }
}
