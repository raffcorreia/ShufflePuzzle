
package quebracabeca;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.*;
import javax.swing.Timer;

/**
 *Classe responsável por todo o processamento do quebra cabeça,
 *implementa Serializable para poder ser salva em arquivo.
 */
public class Tabuleiro implements Serializable{
    private final int Linhas = 4;
    private final int Colunas = 4;
    //Guarda os Linha e Coluna da Peça Vazia
    private int col0, lin0;  
    private Timer t = new Timer(1000, new TimeListener());
    private int tempo;
    private boolean vencedor;
    private Peca matrizPeca[][];
    private boolean pausado = true;
    
    /**
     *Construtor default, por decisão de projeto não recebe valores.
     */    
    public Tabuleiro() {        
        matrizPeca = new Peca[Linhas][Colunas];
        
        for(int i=0; i<Linhas; i++){
            for(int j=0; j<Colunas; j++){
                matrizPeca[i][j] = new Peca();
            }
        }
        reseta();
    }

    /**
     *Metodo que pausa ou tira do pausa o jogo, 
     * Quanto o jogo está pausado não conta tempo e é possível mover as peças.
     *@param    valor   Recebe o status em que o jogo será colocado, caso true pausa o jogo
     *e caso false tira do pause.
     */    
    public void setPausado(boolean valor){
        pausado = valor;
    }
    
    /**
     *Metodo que reseta o jogo,
     *Qando este método eh chamado, as peças voltam a sua posição original.
     */    
    public void reseta(){
        lin0=col0=3;
        for(int i=0; i<Linhas; i++){
            for(int j=0; j<Colunas; j++){
                //Verifica se é a ultima peça
                if(!((i==(Linhas-1)) && (j==(Colunas-1)))){
                    //Já que não é a ultima peca Calcula o valor
                    matrizPeca[i][j].setValor(i*4+j+1);
                }else{
                    //Ultima peça é sempre igual a 0
                    matrizPeca[i][j].setValor(0);
                }
            }
        }
        tempo=0;
        vencedor=false;
        pausado=false;
        t.start();
    }
    
    /**
     *Metodo que retorna no console a posição das peçcas.
     */    
    public void imprimeTabuleiro(){
        int tam;
        for(int i=0; i<Linhas; i++){
            for(int j=0; j<Colunas; j++){                
                System.out.print(matrizPeca[i][j].getValor());
                tam = String.valueOf(matrizPeca[i][j].getValor()).length();
                for(int cont = 1; cont <= (3-tam); cont++){
                    System.out.print(" ");
                }
                //+ " (" + tam + ") "
            }
            System.out.println("");
        }     
    }
    
    /**
     *Metodo que embaralha as peças do tabuleiro.
     *@param    tipo    Recebe o tipo de embaralhamento, 1 ou 2, 1 embaralha impar e 2 embaralha par.
     */    
    public void embaralha(int tipo){
        if((tipo == 1) || (tipo == 2)){
            int QtdPeca=0;
            if(tipo == 1){      //Caso o Embraralhamento seja IMPAR
                QtdPeca = 12;
                System.out.println("Embaralhamento IMPAR");
            }
            else if(tipo ==2){  //Caso o Embraralhamento seja PAR
                QtdPeca = 11;
                System.out.println("Embaralhamento PAR");
            }

            ArrayList<Integer> lista = new ArrayList<Integer>(16);
            lista.clear();
            for(int i = 1; i<16;i++){
                lista.add(new Integer(i));
            }

            Collections.shuffle(lista);

            for(int i = 0; i<=QtdPeca;i=i+2){
                trocaPecas(lista.get(i), lista.get(i+1));
            }
            System.out.println("lin0 =" + lin0 + " col0 =" + col0);
        }
    }
    /**
     *Metodo que retorna a posição que a peça passada no parametro será movida,
     *caso a peça passada não faça fonteira com a peça vazia retorna valor inválido.
     *@param    n   Recebe o numero da peça a ser movida (o numero relativo a posição da peça no tabuleiro de 1..16).
     *@return   Retorna: <p> -1 se a peça passada não se encontra em uma posição que possa ser movida, <p>
     *-2 se o jogador já é vencedor, <p>
     *-3 se o jogo está parado.
     */    
    public int getMovePeca(int n){
        if(pausado==false){
            if(vencedor==false){
                int col, lin;
                col = getCol(n);
                lin = getLin(n);

                if(getValorReal(n) == 0){
                    System.out.println("A peça vazia não pode ser movida desta forma");
                }else{
                    if(((lin-1==lin0 || lin+1==lin0) && col==col0) || ((col-1==col0 || col+1==col0)) && lin==lin0){
                        int pecaVazia;
                        pecaVazia = getPoscaoN(lin0, col0);

                        trocaPecas(getPoscaoN(lin, col), getPoscaoN(lin0, col0));

                        lin0 = lin;
                        col0 = col;
                        //Retorna a pessa para onde foi movida
                        return pecaVazia;
                    }else{
                        System.out.println("Esta peça não pode ser movida!");
                    }
                }
            }else{
                //Retorna vencedor!
                return -2;
            }
        }else{
            return -3;
        }
        //Retorna não foi possível mover
        return -1;
    }
    /**
     *Metodo que retorna se o jogador venceu ou não o jogo.
     *@return   Retorna true caso o jogador tenha vencido ou false caso contrario.
     */    
    public boolean getVencedor(){
        //Verifica se todos os valores estão no lugar certo, só não verifica o Vazio pq ja verificou todos os outros
        if(vencedor == false && pausado==false){
            for(int cont=1; cont<((Linhas*Colunas)); cont++){
                    if(getValorReal(cont)!= cont){
                        return false;
                }
            }
        }
        vencedor=true;
        t.stop();
        return true;
    }
    
    /**
     *Classe privada, incrementa o tempo a cada segundo de jogo passado,
     *não considera o tempo pausado.
     */
    private class TimeListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event){
            if(vencedor==false && pausado==false){
                tempo++;
            }
        }
    }

    /**
     *Metodo que retorna o tempo em segundos de jogo decorrido, sem considerar o tempo pausado.
     *@return   Retorna o tempo de jogo sem considerar o tempo pausado.
     */
    public int getTempo(){
        return tempo;
    }
    
    /**
     *Metodo que retorna a pontuação do jogador vencedor, 
     * pontuação calculara exclusivamtente em relação ao tempo
     *@return   Retorna a pontuação do jogador caso ele tenha vencido, caso contrário retorna 0.
     */
    public double getPontuacao(){
        if(vencedor==true){
            return ((60000) / (getTempo()+1));
        }
        return 0;
    }
    
    /**
     *Metodo que rerotna a coluna em que a peça passada como parametro se encontra.
     *@param    n   Numero da peça que deseja-se saber a coluna, 
     * (numero relativo a posição da peça no tabuleiro entre 1..16).
     *@return   Retorna a coluna em que a peça se encontra.
     */
    public int getCol(int n){
        return ((n - 1) % 4);
    }
    
    /**
     *Metodo que retorna a linha em que a peça passada como parametro se encontra
     *@param    n Numero da peça que deseja-se saber a coluna,
     * (numero relativo a posição da peça no tabuleiro entre 1..16).
     *@return   Retorna a linha em que a peça se encontra.
     */
    public int getLin(int n){
        return ((n - 1 - getCol(n) )/4);         
    }
    
    /**
     *Metodo que retorna o valor da peça passada como parâmetro.
     *@param    n   Numero da peça que deseja-se saber o valor,
     * (numero relativo a posição da peça no tabuleiro entre 1..16).
     *@return   Retorna o valor da peça passada como parâmetro.
     */
    public int getValorReal(int n){
        int col, lin;

        col = getCol(n);
        lin = getLin(n);
        
        return matrizPeca[lin][col].getValor();
    }
    
    /**
     *Retorna o numero da peça em relação a sua linha e coluna.
     *@param    lin recebe a linha da peça.
     *@param    col recebe a coluna da peça.
     *@return   Retorna o numero da peça,
     * (numero relativo a posição da peça no tabuleiro entre 1..16).
     */
    public int getPoscaoN(int lin, int col){
        return (lin * 4 + col + 1);
    }
    
    /**
     *Metodo privado, criado para dividir o código de movimentação da peça e torna-lo mais legivel
     *@param    nPeca1  Recebe a 1º peça que será movida.
     *@param    nPeca2  recebe a 2º peça que será movida.
     */
    private void trocaPecas(int nPeca1, int nPeca2){
        int col1, lin1, col2, lin2, valTemp;

        //Pega a posição da peca1 na Matriz
        col1 = getCol(nPeca1);
        lin1 = getLin(nPeca1);
        //Pega a posição da peca2 na Matriz
        col2 = getCol(nPeca2);
        lin2 = getLin(nPeca2);
        
        System.out.println("Troca as pecas " + nPeca1 + " e " + nPeca2);
        
        valTemp = getValorReal(nPeca1);

        matrizPeca[lin1][col1].setValor(matrizPeca[lin2][col2].getValor());
        matrizPeca[lin2][col2].setValor(valTemp);
        
        if(getValorReal(nPeca1)==0){
            col0 = col1;
            lin0 = lin1;
        }else if(getValorReal(nPeca2)==0){
            col0 = col2;
            lin0 = lin2;
        }
    }
}
