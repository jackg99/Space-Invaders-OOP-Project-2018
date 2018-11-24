import java.awt.*;
import java.util.ArrayList;

public abstract class Character {

    int x;
    int y;
    int speed;
    //Color colour;
    //int width;
    //int height;

    public Character(){

    }


    public Character(int x, int y, int speed){
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

}
