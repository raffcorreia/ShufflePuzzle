
package quebracabeca;

import java.io.Serializable;
import java.util.*;

/**
 * Object resposible for the Business Logic
 * Implements Serializable in order to save on disk.
 */
public class Board implements Serializable{
    private final int Rows = 4;
    private final int Columns = 4;
    //Row and Column of the empyt piece
    private int row0, col0;  
    private boolean winner;
    private Piece matrixPiece[][];
    private boolean paused = true;
        
    public Board() {        
        matrixPiece = new Piece[Rows][Columns];
        
        for(int i=0; i<Rows; i++){
            for(int j=0; j<Columns; j++){
                matrixPiece[i][j] = new Piece();
            }
        }
        reset();
    }

    /**
     * Method to pause or resume the game, 
     * Paused the player can't move the pieces
     *@param    val   receives the paused status, true pauses the game and false resume
     */    
    public void setPausado(boolean val){
        paused = val;
    }
    
    /**
     *Method to reset the game.
     */    
    public void reset(){
        col0=row0=3;
        for(int i=0; i<Rows; i++){
            for(int j=0; j<Columns; j++){
                //Verifica se é a ultima peça
                if(!((i==(Rows-1)) && (j==(Columns-1)))){
                    //Já que não é a ultima peca Calcula o valor
                    matrixPiece[i][j].setValue(i*4+j+1);
                }else{
                    //Ultima peça é sempre igual a 0
                    matrixPiece[i][j].setValue(0);
                }
            }
        }
        winner=false;
        paused=false;
    }
    
    /**
     * Method to print on console the pieces position.
     */    
    public void printBoard(){
        int size;
        for(int i=0; i<Rows; i++){
            for(int j=0; j<Columns; j++){                
                System.out.print(matrixPiece[i][j].getValue());
                size = String.valueOf(matrixPiece[i][j].getValue()).length();
                for(int cont = 1; cont <= (3-size); cont++){
                    System.out.print(" ");
                }
            }
            System.out.println("");
        }     
    }
    
    /**
     * Method to shuffle the pieces
     *@param    tipe    Receives the tipe of the shuffle, 1 or 2, 1 shuffle odd and 2 shuffle even.
     */    
    public void shuffle(int tipe){
        if((tipe == 1) || (tipe == 2)){
            int NumPieces=0;
            if(tipe == 1){      //Shuffle Odd
                NumPieces = 12;
            }
            else if(tipe ==2){  //Shuffle even
                NumPieces = 11;
            }

            ArrayList<Integer> list = new ArrayList<Integer>(16);
            list.clear();
            for(int i = 1; i<16;i++){
                list.add(new Integer(i));
            }

            Collections.shuffle(list);

            for(int i = 0; i<=NumPieces;i=i+2){
                shiftPieces(list.get(i), list.get(i+1));
            }
        }
    }
    /**
     * Method to return the position that the piece "n" will be moved for,
     * if the piece is't moveable returns a invalid value.
     *@param    n   The number of the piece to be moved.
     *@return   Returns: <p> -1 if the piece could not be moved, <p>
     *-2 if the game is fihished because the player won, <p>
     *-3 if the game is paused.
     */    
    public int getMovePeca(int n){
        if(paused==false){
            if(winner==false){
                int c, r;
                c = getCol(n);
                r = getRow(n);

                if(getRealValue(n) != 0){
                    if(((r-1==col0 || r+1==col0) && c==row0) || ((c-1==row0 || c+1==row0)) && r==col0){
                        int emptyPiece;
                        emptyPiece = getPositionN(col0, row0);

                        shiftPieces(getPositionN(r, c), getPositionN(col0, row0));

                        col0 = r;
                        row0 = c;
                        //Returns the n piece to where it was moved to
                        return emptyPiece;
                    }
                }
            }else{
                //Return winner!
                return -2;
            }
        }else{
            return -3;
        }
        //Cant move
        return -1;
    }
    /**
     * Method to return if the player is a winner.
     *@return   return true is is a winner
     */    
    public boolean getWinner(){
        //Look if all pieces are in the correct position
        if(winner == false && paused==false){
            for(int cont=1; cont<((Rows*Columns)); cont++){
                    if(getRealValue(cont)!= cont){
                        return false;
                }
            }
        }
        winner=true;
        return true;
    }
    
    /**
     * Method to return the punctuation
     *@return   Returns the punctuation if it's not a winner returns 0.
     */
    public double getPunctuation(){
        if(winner==true){
            return 1;
        }
        return 0;
    }
    
    /**
     * Method to return the column where the piece is
     *@param    n   Number of the piece to find
     *@return   Returns the column
     */
    public int getCol(int n){
        return ((n - 1) % 4);
    }
    
    /**
     * Method to return the row that a piece is
     *@param    n   Piece to find
     *@return   Reteturn the row where the piece is
     */
    public int getRow(int n){
        return ((n - 1 - getCol(n) )/4);         
    }
    
    /**
     * Method to return the value of a piece
     *@param    n   Number of the piece to find tha value
     *@return   Return the value of the piece.
     */
    public int getRealValue(int n){
        int col, lin;

        col = getCol(n);
        lin = getRow(n);
        
        return matrixPiece[lin][col].getValue();
    }
    
    /**
     * Return the number of the piece by the row and column
     *@param    row piece row.
     *@param    col piece col.
     *@return   Return the number of the piece.
     */
    public int getPositionN(int row, int col){
        return (row * 4 + col + 1);
    }
    
    /**
     *
     *@param    nPiece1  1st piece to be moved
     *@param    nPiece2  2nd piece to be moved.
     */
    private void shiftPieces(int nPiece1, int nPiece2){
        int col1, lin1, col2, lin2, valTemp;

        //Piece1 position on the matrix
        col1 = getCol(nPiece1);
        lin1 = getRow(nPiece1);
        //Piece2 position on the matrix
        col2 = getCol(nPiece2);
        lin2 = getRow(nPiece2);
        
        valTemp = getRealValue(nPiece1);

        matrixPiece[lin1][col1].setValue(matrixPiece[lin2][col2].getValue());
        matrixPiece[lin2][col2].setValue(valTemp);
        
        if(getRealValue(nPiece1)==0){
            row0 = col1;
            col0 = lin1;
        }else if(getRealValue(nPiece2)==0){
            row0 = col2;
            col0 = lin2;
        }
    }
}
