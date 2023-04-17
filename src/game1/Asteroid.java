package game1;

import static game1.Constants.*;
import utilities.Vector2D;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Asteroid extends GameObject {
    public static final int RADIUS = 8;
    public static final double MAX_SPEED = 100;
    public int size;
    public ArrayList<GameObject> asteroids = null;
    private int asteroidLife = 0;

    private Vector2D v;

    public Asteroid(Vector2D v, Vector2D p, int size) {
        super(v, p, RADIUS);
        this.v = v;
        this.size = size;

    }

    public void spawnAsteroids() {
        if(this.size == 2) {
            Asteroid mediumAsteroid1 = new Asteroid( new Vector2D(this.position.x + 5, this.position.y + 5),
                    new Vector2D((Math.random() - 0.5) *2*(MAX_SPEED + 1), (Math.random() - 0.5) *2*(MAX_SPEED + 1)),
                    this.size);
            Asteroid mediumAsteroid2 = new Asteroid( new Vector2D(this.position.x + 5, this.position.y + 5),
                    new Vector2D((Math.random() - 0.5) *2*(MAX_SPEED + 1), (Math.random() - 0.5) *2*(MAX_SPEED + 1)),
                    this.size);
            asteroids = new ArrayList<>();
            asteroids.add(mediumAsteroid1);
            asteroids.add(mediumAsteroid2);
        }
        else if(this.size == 1){
            Asteroid smallAsteroid1 = new Asteroid(new Vector2D(this.position.x + 5, this.position.y + 5),
                    new Vector2D((Math.random() - 0.5) *2*(MAX_SPEED + 1), (Math.random() - 0.5) *2*(MAX_SPEED + 1)),
                    this.size);
            Asteroid smallAsteroid2 = new Asteroid(new Vector2D(this.position.x + 5, this.position.y + 5),
                    new Vector2D((Math.random() - 0.5) *2*(MAX_SPEED + 1), (Math.random() - 0.5) *2*(MAX_SPEED + 1)),
                    this.size);            asteroids = new ArrayList<>();
            asteroids.add(smallAsteroid1);
            asteroids.add(smallAsteroid2);
        }
    }

	public static Asteroid makeRandomAsteroid() {
        int size = 3;

        return new Asteroid(new Vector2D(Math.random() * FRAME_WIDTH, Math.random() * FRAME_HEIGHT),
                new Vector2D((Math.random() * (MAX_SPEED - -MAX_SPEED)) + -MAX_SPEED, (Math.random() * (MAX_SPEED - -MAX_SPEED)) + -MAX_SPEED), size);
    }

    public void hit(){
        super.hit();
        this.size-=1;
        spawnAsteroids();
    }

    public void draw(Graphics2D g) {
        g.setColor(Color.red);
        if(size == 3){
            g.fillOval((int) v.x - RADIUS, (int) v.y - RADIUS, 3 * RADIUS, 3 * RADIUS);
        } else if (size == 2) {
            g.fillOval((int) v.x - RADIUS, (int) v.y - RADIUS, 2 * RADIUS, 2 * RADIUS);
        }else if (size == 1) {
            g.fillOval((int) v.x - RADIUS, (int) v.y - RADIUS, RADIUS, RADIUS);
        }
    }

    public void update() {
        super.update();
        if (size == 3) {
            if(asteroidLife == 500){
                size = 2;
            }else{
                asteroidLife++;
            }
        }
        if (size == 2) {
            if (asteroidLife == 500) {
                size = 1;
            } else {
                asteroidLife++;
            }
        }
    }
}
