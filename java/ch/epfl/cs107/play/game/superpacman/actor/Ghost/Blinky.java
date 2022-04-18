package ch.epfl.cs107.play.game.superpacman.actor.Ghost;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

public class Blinky extends Ghost {
    private static final int ANIMATION_DURATION = 6;
    private Animation[] animation;
    private Orientation[] tabOrientation = new Orientation[] {Orientation.UP, Orientation.RIGHT , Orientation.DOWN,Orientation.LEFT};
    private final int MAX = 4;
    private Orientation nextOrientation;
    protected DiscreteCoordinates posrefuge;
    

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    public Blinky(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        posrefuge = position;
        sprites = RPGSprite.extractSprites("superpacman/ghost.blinky", 2, 1, 1,
                this, 16, 16, tabOrientation);
        animation = Animation.createAnimations(ANIMATION_DURATION/2, sprites);
    }

    /**
     * Method update that manages the animation of the Pinky according to the right sprite
     * @param deltaTime
     */

    @Override
    public void update(float deltaTime){
        //draws in the right orientation
        if(isDisplacementOccurs()){
            animation[getOrientation().ordinal()].update(deltaTime);
        }else{
            animation[getOrientation().ordinal()].reset();
        }
        super.update(deltaTime);
    }

    /**
     * Method that generates a random orientation
     * @return nextOrientation
     */
    @Override
    public Orientation getNextOrientation() {
        int randomInt = RandomGenerator.getInstance().nextInt(MAX);
        nextOrientation = tabOrientation[randomInt];
        return nextOrientation;
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
        }else if(!isAfraid()){
            animation[getOrientation().ordinal()].draw(canvas);
        }

    }
}

