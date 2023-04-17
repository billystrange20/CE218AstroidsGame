package game1;

import utilities.Vector2D;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static game1.Constants.*;

public class Ship extends GameObject {
    public static final int RADIUS = 8;
    public static final int DRAWING_SCALE = 10;

    // rotation velocity in radians per second
    public static final double STEER_RATE = 2* Math.PI;
    // acceleration when thrust is applied
    public static final double MAG_ACC = 200;
    // constant speed loss factor
    public static final double DRAG = 0.01;

    public static final Color COLOR = Color.cyan;

    // direction in which the nose of the ship is pointing
    // this will be the direction in which thrust is applied
    // it is a unit vector representing the angle by which the ship has rotated
    public Vector2D direction;

    // controller which provides an Action object in each frame
    private Controller ctrl;
    private int life;

    public Bullet bullet = null;

    public Ship(Controller ctrl) {
        super(new Vector2D(FRAME_WIDTH/2,FRAME_HEIGHT/2),
                new Vector2D(),RADIUS);
        this.life = 5;

        this.ctrl = ctrl;
        direction = new Vector2D(0,-1);
    }

    private void mkBullet(){
        bullet = new Bullet(new Vector2D(position),new Vector2D(velocity),RADIUS);
        bullet.position.add((direction.x*25),(direction.y*25));
        bullet.velocity.addScaled(direction,MAG_ACC);
    }

    public void addLife(int amount) {
        this.life += amount;
    }
    public int getLife(){
        return life;
    }

    public void hit(){
        super.hit();
        if(this.life > 1){
            this.life -= 1;
            this.dead = false;
        }
        else{
            Game.endGame = true;
        }
    }

    public void draw(Graphics2D g) {
        int[] XP = {0,2,0,-2};
        int[] YP = {-2,2,0,2};
        int[] XPTHRUST = {-1,0,1};
        int[] YPTHRUST = {0,4,0};
        Action action = ctrl.action();
        AffineTransform at = g.getTransform();
        g.translate(position.x, position.y);
        double rot = direction.angle() + Math.PI / 2;
        g.rotate(rot);
        g.scale(DRAWING_SCALE, DRAWING_SCALE);
        g.setColor(COLOR);
        g.fillPolygon(XP, YP, XP.length);
        if (action.thrust == 1) {
            g.setColor(Color.red);
            g.fillPolygon(XPTHRUST, YPTHRUST, XPTHRUST.length);
        }
        g.setTransform(at);
    }

    public void update() {
        super.update();
        direction.rotate((STEER_RATE * ctrl.action().turn) * DT);
        velocity.addScaled(direction, (MAG_ACC * DT) * ctrl.action().thrust);
        velocity.mult(1 - DRAG);

        if(ctrl.action().shoot) {
            mkBullet();
            ctrl.action().shoot = false;
        }
    }
}