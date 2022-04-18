package ch.epfl.cs107.play.game.superpacman.actor.Ghost;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;


import java.util.Queue;

public class Inky extends SmartGhost {
    private static final int ANIMATION_DURATION = 6;
    private int MAX_DISTANCE_WHEN_SCARED = 5;
    private int MAX_DISTANCE_WHEN_NOT_SCARED = 10;
    private Animation[] animation;
    private Orientation[] tabOrientation = new Orientation[] {Orientation.UP, Orientation.RIGHT , Orientation.DOWN,Orientation.LEFT};

    private DiscreteCoordinates coordinates;
    private Area area;

    public DiscreteCoordinates targetPos;
    public DiscreteCoordinates cell;
    public Orientation orientation;
    public Queue<Orientation> path = null;



    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    public Inky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        this.coordinates = position;
        this.area = area;
        sprites = RPGSprite.extractSprites("superpacman/ghost.inky", 2, 1, 1,
                this, 16, 16, tabOrientation);
        animation = Animation.createAnimations(ANIMATION_DURATION/2, sprites);
    }

    /**
     * Method update that manages the animation of the Pinky according to the right sprite
     * @param deltaTime
     */
    public void update(float deltaTime){
        if(isDisplacementOccurs()){
            animation[getOrientation().ordinal()].update(deltaTime);
        }else{
            animation[getOrientation().ordinal()].reset();
        }
        super.update(deltaTime);
    }

//

    /**
     * According to the State of the ghost Inky (if it is Afraid, or that he has seen the player),
     * the inky will call the setTargetPos located in the super class SmartGhost
     * @return
     */
    @Override
    public DiscreteCoordinates setTargetPos() {
        if(player != null){
            if(isAfraid()){
                do {
                    super.setTargetPos();
                } while (DiscreteCoordinates.distanceBetween(getPosRefuge(), getTargetPos()) < MAX_DISTANCE_WHEN_SCARED);
            }else{
                super.setTargetPos();
            }
        }
        else{
            do {
                super.setTargetPos();
            } while (DiscreteCoordinates.distanceBetween(getPosRefuge(), getTargetPos()) < MAX_DISTANCE_WHEN_NOT_SCARED);
        }
        return targetPos;
    }

    /**
     * Method that
     * @return super.getNextOrientation() (Orientation)
     */
    @Override
    public Orientation getNextOrientation() {
        return super.getNextOrientation();
    }

    /**
     * Draw method, checks if ghost is afraid, if so,
     * then it will draw the afraid sprite which is in the super class Ghost
     * @param canvas
     */
    @Override
    public void draw(Canvas canvas) {
        if(isAfraid()){
            super.draw(canvas);
        }else{
            animation[getOrientation().ordinal()].draw(canvas);
        }
    }
}
