package game1;

import utilities.Vector2D;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Bullet extends GameObject {
    private int bulletLife = 0;

    public Vector2D direction;

    public Bullet(Vector2D p, Vector2D v, int radius) {
        super(p, v, radius);
        this.direction = new Vector2D(p).rotate(p.angle());
    }

    public void update() {
        super.update();
        if(bulletLife == 100){
            dead = true;
        }else{
            bulletLife++;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        g2.setColor(Color.green);
        int RADIUS = 5;
        g2.fillOval((int) position.x - RADIUS, (int) position.y - RADIUS, RADIUS, RADIUS);
    }
}
