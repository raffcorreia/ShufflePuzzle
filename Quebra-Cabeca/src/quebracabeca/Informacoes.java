
package quebracabeca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
*Metodo que reune as informações usadas pelo jogo, <p>
*  jogadores - Reune todos os jogadores cadastrados no jogo.<p>
*  Ranking   - Reune os 10 melhores pontos já conquistados no jogo.<p>
*Implementa Serializable para poder ser salvo em arquivo. 
*/
public class Informacoes implements Serializable{
    private ArrayList<Pontuacao> ranking = new ArrayList<Pontuacao>(10);
    
    /**
     *Construtor default, pod decisão de projeto não recebe valores.
     */    
    public Informacoes() {
    }

    public void addRanking(double pontos, int tempo){
        System.out.println("Adiciona ao ranking pontos = " + pontos);
        Pontuacao pont = new Pontuacao();
        pont.setPonto(pontos);
        pont.setTempo(tempo);

        if(ranking.size()<10){
            ranking.add(pont);
        }else{
            if(ranking.get(ranking.size()-1).getPonto() < pontos){
                ranking.remove(ranking.size()-1);
                ranking.add(pont);
            }
        }
        Collections.sort(ranking);
    }

    /**
     *
     * @return
     */
    public String getRanking(){
        String retorno = "Pontuação \t Tempo(seg)" ; 
        for(int cont=0; cont<ranking.size();cont++){
            retorno = retorno + "\n" +ranking.get(cont).getPonto() + "\t" + ranking.get(cont).getTempo();
        }
        return retorno;
    }
    public void limpaRanking(){
        //Apaga todos os registros do ranking
        this.ranking.clear();
    }
}