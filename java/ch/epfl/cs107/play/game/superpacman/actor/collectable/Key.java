package ch.epfl.cs107.play.game.superpacman.actor.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

public class Key extends SuperPacmanCollectable implements Logic {

    private Sprite sprite;
    //Logic signal of the key
    private Logic signal;

    /**
     * Default constructor for Key, sets the sprite
     * @param area
     * @param orientation
     * @param position
     */
    public Key(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        //send a Logic true signal
        this.signal = Logic.TRUE;
        //Draws a key
        sprite = new Sprite("superpacman/key", 1.f, 1.f,this);
    }

    /**
     * Calls the super method to collect the key
     */
    @Override
    public void collect() {
        setSignal(Logic.FALSE);
        super.collect();
    }

    /**
     * Draws in the sprite of the key
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) { ((SuperPacmanInteractionVisitor)v).interactWith(this); }


    //Logic
    public void setSignal(Logic signal) { this.signal = signal; }

    public Logic getSignal() { return signal; }

    @Override
    public boolean isOn() {
        if (signal == Logic.TRUE) { return true; } else { return false; }
    }

    @Override
    public boolean isOff() {
        if (signal == Logic.FALSE) { return true; } else { return false; }
    }

    @Override
    public float getIntensity() {
        if (signal == Logic.TRUE) { return 1.0f; } else { return 0; }
    }
}

