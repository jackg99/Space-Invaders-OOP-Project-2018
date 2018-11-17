public class Alien extends Character{


    boolean moveRight;
    boolean moveLeft;
    boolean isVisible;
    boolean destroyed;
    //String direction;

    public Alien(int x, int y, int speed){
        super(x,y,speed);

        moveLeft=false;
        moveRight=true;
        isVisible=true;
    }


}
