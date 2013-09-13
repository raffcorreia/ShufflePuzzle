package quebracabeca;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import javax.swing.JOptionPane;
/**
 *Classe responsável pelo controle do jogo
 *Implementa Serializable para poder ser salvo em arquivo
 */
public class Jogo implements Serializable{
    private Tabuleiro tab = new Tabuleiro();
    private Informacoes info;
    private int tipoEmbaralhamento = 0;
    String pastaImg = new String();
/** 
  *Construtor default, por decisão de projeto não recebe valores.
 */
    public Jogo() {
        recuperaInfo();
    }
/**
 *Método que define o início do jogo, tirando do pause o primeiro jogador.
 */
    public void iniciaJogo(){
        tab = new Tabuleiro();
        tab.embaralha(tipoEmbaralhamento);
    }
    
    /**
     *Método que define o tipo de embaralhamento, se ímpar ou par.
     *@param tipo Recebe o valor correspondente ao tipo de embaralhamento um se impar
     * e dois se par.
     */
    public void settipoEmbaralhamento(int tipo){
        tipoEmbaralhamento = tipo;
    }
    /**
     *Método que retorna o ranking.
     *@return Retorna uma string com todo o ranking.
     */
    public String getRanking(){
        recuperaInfo();
        return info.getRanking();
    }
    /**
     *Método que guarda as informações em um arquivo.
     *@throws 
     */
    public void salvaInfo(){
        //Salva QCTab em disco
        try {
            String url;
            url = System.getProperty("user.dir");
            File arquivo = new File(url + "GuardaInfo.txt");
            FileOutputStream fOut = new FileOutputStream(arquivo);
            ObjectOutputStream objOut = new ObjectOutputStream(fOut);
            objOut.writeObject(info);
            System.out.println("Salvou");
        } catch (Exception e) {
            System.out.println("Erro na Gravação :" + e.getMessage());
            System.out.println(e.getCause());
        }    
    }
    /**
     *Método que recupera as informações do arquivo.
     */
    public void recuperaInfo(){
        //Recupera QCTab gravada previamente em disco
        try {
            String url;
            url = System.getProperty("user.dir");
            File arquivo = new File(url + "GuardaInfo.txt");
            FileInputStream fIn = new FileInputStream(arquivo);
            ObjectInputStream objIn = new ObjectInputStream(fIn);
            info=(Informacoes)objIn.readObject();
            System.out.println("Recuperou");
        } catch (Exception e) {
            info = new Informacoes();
            System.out.println("Erro na Leitura :" + e.getMessage());
            System.out.println(e.getCause());
        }    
    }
    
    //#########################################################################
    //Declaração dos metodos do quebra cabeça!
    /**
     *Método que embaralha o jogo para o jogador atual.
     *@param tipo Recebe o tipo de embaralhamento, se ímpar(1) ou par(2).
     */
    public void embaralha(int tipo){
        tab.embaralha(tipo);
    }
    /**
     *MÁTODO QUE MOVIMENTA A PEÇA.
     *@param n recebe 
     *
     */
    public int getMovePeca(int n){
        int movePeca;

        movePeca = tab.getMovePeca(n);

        if(movePeca>=0){
            if(getVencedor()){
                JOptionPane.showMessageDialog(null, "Parabens você venceu! \n Tempo = " + tab.getTempo() + "\n Pontuação = " + tab.getPontuacao(), "PARABÉNS", 1);
            }else{
                //passaJogador();
            }
        }
        return movePeca;
    }
    
    /**
     *Método que retorna 
     *@return Retorna 
     */
    public boolean getVencedor(){
        boolean venc;
        venc = tab.getVencedor();

        if(venc==true){
            info.addRanking(getPontuacao(), getTempo());
            salvaInfo();
            return  true;
        }
        return false;
    }

    public int getTempo(){        
        return tab.getTempo();
    }

    public double getPontuacao(){
        return tab.getPontuacao();
    }
    
    //Retorna a coluna da peca n
    public int getCol(int n){
        return tab.getCol(n);
    }
    
    //Retorna a linha da peca n
    public int getLin(int n){
        return tab.getLin(n);
    }
    
    //Retorna o valor da n(esima) peca
    public int getValorReal(int n){
        return tab.getValorReal(n);
    }
    
    //Retorna a posicao n de uma peca na matriz
    public int getPoscaoN(int lin, int col){
        return tab.getPoscaoN(lin, col);
    }
    
    public void limpaRanking(){
        info.limpaRanking();
        salvaInfo();
    }
    
    public void setPastaImg(String pasta){
        pastaImg = pasta;
    }
    
    public String getPastaImg(){
        return pastaImg;
    }
}
