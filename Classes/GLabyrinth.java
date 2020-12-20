package Classes;

import java.util.Vector;
import java.util.Random;
import Extras.GBoundary;
public class GLabyrinth {
    public static final int SCALER = 10;

    public int SIZE;
    public Cell[][] contents;
    public GLabyrinth(int size){
        this.SIZE = size;
        GGame.MAX_DIST = this.SIZE * SCALER;
        
        initializeContent();
        createLabyrinth();
        createBoundaries();
    }
    private void initializeContent(){
        this.contents = new Cell[this.SIZE][this.SIZE];
        for(int x = 0; x < this.SIZE; x++){
            for(int y = 0; y < this.SIZE; y++){
                this.contents[x][y] = new Cell();
            }
        }
    }

    private void createLabyrinth(){
        this.contents[0][0]._visited = true;
        Pos lastPos = new Pos(0, 0);
        Vector<Pos> history = new Vector<>();
        history.add(new Pos(0, 0));

        while(history.size() > 0){
            //First we get a random neighbor
            lastPos = history.lastElement();
            Pos nextPos = getRandomNeighbor(lastPos);
            if(nextPos == null){ //No neighbor found
                history.remove(history.size() - 1); //Removes last Pos
            }else{
                this.contents[nextPos._x][nextPos._y]._visited = true;
                history.add(nextPos); //Adds it to the historial
                if(nextPos._x != lastPos._x){  //X
                    if(nextPos._x > lastPos._x){ //Right
                        this.contents[lastPos._x][lastPos._y]._right = false;
                        this.contents[nextPos._x][nextPos._y]._left = false;
                    }else{ //Left
                        this.contents[lastPos._x][lastPos._y]._left = false;
                        this.contents[nextPos._x][nextPos._y]._right = false;
                    }
                }else{//Y
                    if(nextPos._y > lastPos._y){ //Down
                        this.contents[lastPos._x][lastPos._y]._down = false;
                        this.contents[nextPos._x][nextPos._y]._up = false;
                    }else{ //Up
                        this.contents[lastPos._x][lastPos._y]._up = false;
                        this.contents[nextPos._x][nextPos._y]._down = false;
                    }
                }
            }
        }
    }

    private Pos getRandomNeighbor(Pos lastPos){
        int x = lastPos._x;
        int y = lastPos._y;
        Vector<Pos> neighbors = new Vector<>();

        if((y - 1) >= 0){ //Up
            if(!this.contents[x][y - 1]._visited){
                neighbors.add(new Pos(x, y - 1));
            }
        }
        if((y + 1) < SIZE){ //Down
            if(!this.contents[x][y + 1]._visited){
                neighbors.add(new Pos(x, y + 1));
            }
        }
        if((x - 1) >= 0){ //Left
            if(!this.contents[x - 1][y]._visited){
                neighbors.add(new Pos(x - 1, y));
            }
        }
        if((x + 1) < SIZE){ //Right
            if(!this.contents[x + 1][y]._visited){
                neighbors.add(new Pos(x + 1, y));
            }
        }

        if(neighbors.size() == 0){
            return null;
        }

        int index = new Random().nextInt(neighbors.size());
        return neighbors.elementAt(index);
    }

    private void createBoundaries(){
        GGame.boundaries = new Vector<>();

        for(int x = 0; x < this.SIZE; x++){
            for(int y = 0; y < this.SIZE; y++){
                Cell currentCell = this.contents[x][y];

                int X = x * SCALER;
                int Y = y * SCALER;
                if(currentCell._up){ //Upper wall
                    GGame.boundaries.add(new GBoundary(X, Y, X + SCALER, Y));
                }
                if(currentCell._right){ //Right wall
                    GGame.boundaries.add(new GBoundary(X + SCALER, Y, X + SCALER, Y + SCALER));
                }
                if(currentCell._down){ //Bottom wall
                    GGame.boundaries.add(new GBoundary(X + SCALER, Y + SCALER, X, Y + SCALER));
                }
                if(currentCell._left){ //Bottom wall
                    GGame.boundaries.add(new GBoundary(X, Y + SCALER, X, Y));
                }
            }
        }
    }
    
    private class Pos{
        public int _x, _y;
        public Pos(int x, int y){
            this._x = x;
            this._y = y;
        }
    }
    private class Cell{
        public boolean _up, _left, _down, _right, _visited;
        public Cell(){
            this._up = true;
            this._left = true;
            this._down = true;
            this._right = true;
            this._visited = false;
        }
    }
}
