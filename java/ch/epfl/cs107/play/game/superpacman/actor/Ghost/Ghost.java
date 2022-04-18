package ch.epfl.cs107.play.game.superpacman.actor.Ghost;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.*;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.GhostInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Canvas;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Ghost extends MovableAreaEntity implements Interactor, Interactable {

    protected SuperPacmanPlayer player = null;

    private static final int ANIMATION_DURATION = 6;
    //For the interaction
    private final Ghost.ghostHandler Handler;
    //For the drawing
    private Orientation[] tabOrientation = new Orientation[] {Orientation.UP, Orientation.RIGHT , Orientation.DOWN,Orientation.LEFT };
    private Animation[] animation;
    public Sprite[][] spriteAfraid ;
    public Sprite[][] sprites ;
    //movement of the afraid sprite
    private int i = 0;

    private int SPEED = 12;
    private DiscreteCoordinates posRefuge;
    private DiscreteCoordinates coordinates;
    private boolean afraid;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */

    public Ghost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        afraid = false;
        coordinates = position;
        posRefuge = position;
        spriteAfraid = RPGSprite.extractSprites("superpacman/ghost.afraid", 2, 1, 1,
                this, 16, 16,tabOrientation);
        animation = Animation.createAnimations(ANIMATION_DURATION/2, spriteAfraid);
        Handler = new Ghost.ghostHandler();
    }

    //getter and setter
    public DiscreteCoordinates getPosRefuge() { return posRefuge; }


    public void setAfraid(boolean invulnerable){ afraid = invulnerable; }

    public boolean isAfraid() { return afraid; }

    /**
     * Method update that, in the most part, controls the movement
     * of the ghosts and changes the sprite if afraid
     * @param deltaTime
     */
    public void update(float deltaTime){
        if(((SuperPacmanArea)getOwnerArea()).isStop()){
            setAfraid(((SuperPacmanArea)getOwnerArea()).isHasABonus());
            if(!isDisplacementOccurs()){
                if (getOwnerArea().canEnterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates()
                        .jump(getNextOrientation().toVector())))){
                    orientate(getNextOrientation());
                    move(SPEED);
                }
            }else if(isAfraid()){
                for(int i = 0 ; i < 2 ; ++i){
                    animation[i].update(deltaTime);
                }
            }
            if(isAfraid()){
                SPEED = 8;
            }else{
                SPEED = 12;
            }
            super.update(deltaTime);
        }
    }

    /**
     * Creates the getNextOrientatiom() method so that the classes that extend Ghost can use it
     */
    public abstract Orientation getNextOrientation();

    /**
     * Draws in the animation of the afraid Sprite
     * @param canvas target, not null
     */
    @Override
    public void draw(Canvas canvas) { animation[i].draw(canvas); }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    /**
     * Creates a Array List that will store the cells that are in a certain radius
     * (actually in a square around the ghost and not a circle)
     * @return discreteCoordinates (List<DiscreteCoordinates>)
     */
    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        List<DiscreteCoordinates> discreteCoordinates = new ArrayList<>();
            for(int i = -4 ; i < 4 ; ++i){
                for (int j = -4 ; j< 4 ; ++j){
                    discreteCoordinates.add(new DiscreteCoordinates(getCurrentMainCellCoordinates().x + i , getCurrentMainCellCoordinates().y + j));
                }
            }
        return discreteCoordinates;
    }

    /**
     * method that respawns the ghosts to their "refuge" position
     */
    public void respawnGhost(){
        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        if (getOwnerArea().getTitle() == "superPacman/Level1") {
            setCurrentPosition(coordinates.toVector());
        } else if (getOwnerArea().getTitle() == "superPacman/Level2") {
            setCurrentPosition(coordinates.toVector());
        }
        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
        player = null;
    }



    @Override
    public boolean wantsCellInteraction() { return false; }

    @Override
    public boolean wantsViewInteraction() { return true; }

    @Override
    public void interactWith(Interactable other) {
        other.acceptInteraction(Handler);
    }

    @Override
    public boolean takeCellSpace() { return false; }

    @Override
    public boolean isCellInteractable() {
        return true;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((SuperPacmanInteractionVisitor)v).interactWith(this);
    }

    /**
     * if ghost touches then the ghost will respawn
     */
    public void touchesPacman(){
        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        this.setCurrentPosition(coordinates.toVector());
        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
        player = null;
    }

    /**
     * Handler for the ghost, handles the interactions that can have the ghosts
     */
    private class ghostHandler implements GhostInteractionVisitor {

        public void interactWith(SuperPacmanPlayer superPacmanPlayer) {
            //tracks the pacman
            player = superPacmanPlayer;
        }
    }
}