package ch.epfl.cs107.play.game.superpacman.actor.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Diamond extends SuperPacmanCollectable{

    private Sprite sprite;

    /**
     * Default constructor of Diamond, sets the sprite
     * @param area
     * @param orientation
     * @param position
     */
    public Diamond(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        sprite = new Sprite("superpacman/diamond", 1.f, 1.f,this);
    }

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
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }

    @Override
    public void collect() {
        ((SuperPacmanArea) getOwnerArea()).removeDiamond();
        super.collect();
    }
}
