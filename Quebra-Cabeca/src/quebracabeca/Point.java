package quebracabeca;


import java.io.Serializable;
import java.util.Date;

/**
 *  Object that represents points for the ranking
 */
public final class Point implements Serializable, Comparable<Point>{
    private double point;
    private Date dateTime;

    public Point() {
    }
    
    public Point(double pointVal, Date datetime){
        setPoint(pointVal);
        setDateTime(datetime);
    }
    
    public void setPoint(double ponto){
        this.point = ponto;
    }
    
    public double getPoint(){
        return this.point;
    }
    
    public void setDateTime(Date datetime){
        this.dateTime = datetime;
    }
      
    public Date getTempo(){
        return this.dateTime;
    }    

    public int compareTo(Point other) {
        if(other.point==this.point){
            return 0;
        }else if(other.point > this.point){
            return 1;
        }else{
            return -1;
        }
    }
}
