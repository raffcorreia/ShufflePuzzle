
package quebracabeca;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
* Object that maintain the game ranking history
* Implements Serializable to save information on disk 
*/
public class Ranking implements Serializable{
    private ArrayList<Point> list = new ArrayList<Point>(10);
    
    public Ranking() {
    }

    public void addRanking(double points){
        addRanking(points, new Date());
    }
    
    public void addRanking(double pointVal, Date datetime){
        Point point = new Point(pointVal, datetime);

        if(list.size()<10){
            list.add(point);
        } else {
            Point min = Collections.min(list);
            if(min.getPoint() < pointVal){
                list.remove(min);
                list.add(point);
            }
        }
        Collections.sort(list);
    }

    public String getRanking(){
        String retorno = "Punctuation \t When" ; 
        for(int cont=0; cont<list.size();cont++){
            retorno = retorno + "\n" +list.get(cont).getPoint() + "\t" + list.get(cont).getTempo().toString();
        }
        return retorno;
    }
    public void clearRanking(){
        this.list.clear();
    }
}