package game1;

import utilities.Vector2D;

import java.awt.*;

import static game1.Constants.*;

public abstract class GameObject {
    protected static final double MAX_SPEED = 160;

    protected Vector2D position;
    protected Vector2D velocity;
    protected double radius;
    protected boolean dead = false;

    public GameObject(Vector2D position, Vector2D velocity, double radius) {
        this.position = position;
        this.velocity = velocity;
        this.radius = radius;
    }

    public void hit() {
        this.dead = true;
    }

    public void update() {
        position.addScaled(velocity, DT);
        position.wrap(FRAME_WIDTH, FRAME_HEIGHT);
    }

    public boolean overlap(GameObject other){
        double distanceBetweenCenters = new Vector2D(position).subtract(other.position).mag();
        return !((this.radius + other.radius) < distanceBetweenCenters);
    }

    public void collisionHandling(GameObject other){
        if(this.getClass() != other.getClass() && this.overlap(other)){

            if(this instanceof Asteroid && other instanceof Ship){
                this.dead = true;
                other.hit();
            }
            else{
                this.hit();
                other.hit();
            }
        }


    }

    public abstract void draw(Graphics2D g);
}