
package quebracabeca;

import java.io.Serializable;

/**
 * Object responsable for the pices value
 * Implements Serializable in order to save on disk.
 */
public class Piece implements Serializable{
    private int value;

    public Piece(){
    }

    public void setValue(int val){
        this.value = val;
    }
    
    public int getValue(){
        return this.value;
    }
    
}
