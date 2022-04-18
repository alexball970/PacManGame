package ch.epfl.cs107.play.game.superpacman.actor.collectable;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;

public class Bonus extends SuperPacmanCollectable {
    private static final int ANIMATION_DURATION = 6;
    private Orientation[] tabOrientation = new Orientation[] {Orientation.UP, Orientation.RIGHT , Orientation.DOWN,Orientation.LEFT };
    private Animation[] animation;
    public Sprite[][] sprites ;
    private int i = 0;

    /**
     * Default constructor de Bonus that will initialise the sprite for the coin
     * @param area
     * @param orientation
     * @param position
     */
    public Bonus(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);

        sprites = RPGSprite.extractSprites("superpacman/coin", 4, 1, 1,
                this, 15, 15, tabOrientation);
        animation = Animation.createAnimations(ANIMATION_DURATION/2, sprites);
    }

    /**
     * Update method that manages the animations of the coin
     * @param deltaTime
     */
    public void update(float deltaTime){
        if(((SuperPacmanArea)getOwnerArea()).isStop()){
        for(int i = 0 ; i < 4 ; ++i){
            animation[i].update(deltaTime);
        }
        super.update(deltaTime);
        }
    }

    @Override
    public void collect() { super.collect(); }

    @Override
    public void draw(Canvas canvas) {
        animation[i].draw(canvas);
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

}
