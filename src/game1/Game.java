package game1;

import utilities.JEasyFrame;

import javax.swing.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static final int N_INITIAL_ASTEROIDS = 5;
    public static View view;
    public Keys ctrl;
    public Ship ship;
    List<GameObject> objects;

    private static int score;
    public static boolean endGame;
    public int asteroidCount;

    public Game() {
        endGame = false;
        objects = new ArrayList<>();

        ctrl = new Keys();
        ship = new Ship(ctrl);
        objects.add(ship);
        for (int i = 0; i < N_INITIAL_ASTEROIDS; i++) {
            objects.add(Asteroid.makeRandomAsteroid());
        }
    }

    public void incScore(GameObject object){
        if(object instanceof Asteroid){
            Asteroid obj = (Asteroid) object;
            if(obj.size == 3){
                addScore(100);
            }
            else if(obj.size ==2){
                addScore(200);
            }
            else if(obj.size == 1){
                addScore(300);
            }
        }
    }

    public int getScore(){
        return score;
    }

    public void addScore(int amount){
        score += amount;
        if(score % 10000 == 0){
            ship.addLife(1);
        }
    }

    public static void main(String[] args) throws Exception {
        Game game = new Game();
        view = new View(game);
        new JEasyFrame(view, "Basic Game").addKeyListener(game.ctrl);
        // run the game
        while (true) {
            if(!endGame) {
                game.update();
                view.repaint();
                Thread.sleep(Constants.DELAY);
            } else {
                JFrame parent = new JFrame();
                JOptionPane.showMessageDialog(parent, "Score: " + game.getScore());
                System.out.println("Finished");
                System.exit(1);
            }
        }
    }

    public void update() {
        asteroidCount = 0;
        List<GameObject> alive = new ArrayList<>();
        for (GameObject gameObject : objects) {
            gameObject.update();
            if (!gameObject.dead) {
                if(gameObject instanceof Asteroid){
                    asteroidCount++;
                }
                alive.add(gameObject);
            } else {
                if (!ship.dead) {
                    incScore(gameObject);
                }
                if (gameObject instanceof Asteroid) {
                    Asteroid asteroid = (Asteroid) gameObject;
                    if (asteroid.asteroids != null) {
                        alive.addAll(asteroid.asteroids);
                        asteroid.asteroids = null;
                    }
                }
            }
        }
        if(ship.bullet != null){
            alive.add(ship.bullet);
            ship.bullet = null;
        }

        synchronized (Game.class){
            objects.clear();
            objects.addAll(alive);
        }

        if(asteroidCount <=4){
            objects.add(Asteroid.makeRandomAsteroid());
            objects.add(Asteroid.makeRandomAsteroid());
        }

        for(int i=0; i<alive.size()-1; i++){
            for(int j=i+1; j<alive.size(); j++){
                if (!(alive.get(i).getClass() == alive.get(j).getClass())) {
                    if (!alive.get(i).equals(alive.get(j))) {
                        alive.get(i).collisionHandling(alive.get(j));
                    }
                }
            }
        }
    }
}