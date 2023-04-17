package game1;

import javax.swing.*;
import java.awt.*;
import static game1.Constants.*;

public class View extends JComponent {
    // background colour
    public static final Color BG_COLOR = Color.black;

    private Game game;

    public View(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g0) {
        Graphics2D g = (Graphics2D) g0;
        // paint the background
        g.setColor(BG_COLOR);
        g.fillRect(0, 0, getWidth(), getHeight());

        for (GameObject object : game.objects)
            object.draw(g);

        g.setColor(Color.WHITE);
        g.drawString("Score: "+ game.getScore(),10,20);

        g.drawString("Lives: "+ game.ship.getLife(),FRAME_WIDTH -50,20);
    }

    @Override
    public Dimension getPreferredSize() {
        return Constants.FRAME_SIZE;
    }
}
