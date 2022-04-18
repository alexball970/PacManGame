package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.actor.Graphics;
import ch.epfl.cs107.play.game.actor.ImageGraphics;
import ch.epfl.cs107.play.game.actor.TextGraphics;
import ch.epfl.cs107.play.game.areagame.io.ResourcePath;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.math.Vector;
import ch.epfl.cs107.play.window.Canvas;

import java.awt.*;


public class SuperPacmanPlayerStatusGUI implements Graphics {

    private static SuperPacmanPlayer player;


    /**
     * Constructor for the gui using its player
     * @param player
     */
    protected SuperPacmanPlayerStatusGUI(SuperPacmanPlayer player) {
        this.player = player;
    }



    /**
     * Draws in the GUI
     */
    @Override
    public void draw(Canvas canvas) {
        float height = canvas.getScaledHeight();
        float width = canvas.getScaledWidth();
        Vector anchor = canvas.getTransform().getOrigin().sub(new Vector(width/2, height/2));
        int DEPTH = 0;
        //To have every thing aligned
        int shift = 0;
        //Change when i is higher than hp
        int m = 0;
        for (int i = 0; i < player.getMaxHp(); i++) {
            if (i >= player.getHp()) { m = 64; }
            ImageGraphics life = new
                    ImageGraphics(ResourcePath.getSprite("superpacman/lifeDisplay"),
                    1f, 1f, new RegionOfInterest(m, 0, 64, 64),
                    anchor.add(new Vector(shift, height - 1.375f)), 1,  DEPTH);
            life.draw(canvas);
            shift += 1f;
        }

        TextGraphics score = new TextGraphics ("SCORE:" + player.getScore(), 1.f, Color.YELLOW, Color.BLACK, 0f, true, false,
                anchor.add(new Vector(shift +0.5f, height - 1.375f)));
        score.draw(canvas);
    }
}
