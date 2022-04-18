package ch.epfl.cs107.play.game.superpacman.actor;


import java.util.*;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Animation;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.rpg.actor.Player;
import ch.epfl.cs107.play.game.rpg.actor.RPGSprite;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.*;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.game.superpacman.handler.GhostInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.handler.SuperPacmanInteractionVisitor;
import ch.epfl.cs107.play.math.DiscreteCoordinates;



import ch.epfl.cs107.play.window.Canvas;
import ch.epfl.cs107.play.window.Keyboard;


import java.util.List;


public class SuperPacmanPlayer extends Player {

    private int SPEED = 6;
    private final int GHOST_SCORE = 500;
    private float TIMER;
    private AreaInteractionVisitor Handler;
    private Orientation desiredOrientation;
    private final int ANIMATION_DURATION = 6;
    private Animation[] animation;
    private Orientation[] tabOrientation = new Orientation[] {Orientation.DOWN, Orientation.LEFT, Orientation.UP,Orientation.RIGHT };
    Sprite[][] sprites ;
    private boolean Invulnerable;
    public DiscreteCoordinates pacmanSpawnPosition;

    //Hit-Point and score
    private static final int MAX_HP = 5;
    private static int hp;
    private int score;
    private SuperPacmanPlayerStatusGUI superPacmanPlayerStatusGUI;



    /**
     * Default Player constructor
     *
     * @param area        (Area): Owner Area, not null
     * @param orientation (Orientation): Initial player orientation, not null
     * @param coordinates (Coordinates): Initial position, not null
     */

    public SuperPacmanPlayer(Area area, Orientation orientation, DiscreteCoordinates coordinates) {
        super(area, orientation, coordinates);
        this.Invulnerable = false;
        this.TIMER = 0;
        pacmanSpawnPosition = coordinates;
        sprites = RPGSprite.extractSprites("superpacman/pacman", 4, 1, 1,
                this, 64, 64, tabOrientation);
        animation = Animation.createAnimations(ANIMATION_DURATION/2, sprites);
        Handler = new SuperPacmanPlayerHandler();
        this.hp = 3;
        this.score = 0;
        this.superPacmanPlayerStatusGUI = new SuperPacmanPlayerStatusGUI(this);
    }


    /**
     * Method that sets the speed if the pacman is invulnerable or not
     * manages the animations, the movement and the orientation of the pacman aswell as a timer and a check for the hp
     * @param deltaTime
     */
    public void update(float deltaTime) {
        if(((SuperPacmanArea)getOwnerArea()).isStop()) {
            if (isInvulnerable()) {
                SPEED = 3;
            } else {
                SPEED = 6;
            }
            setDesiredOrientation();

            if (isDisplacementOccurs()) {
                animation[getOrientation().ordinal()].update(deltaTime);
            } else {
                animation[getOrientation().ordinal()].reset();
            }

            if (!isDisplacementOccurs()) {
                try {
                    if (getOwnerArea().enterAreaCells(this, Collections.singletonList(getCurrentMainCellCoordinates()
                            .jump(desiredOrientation.toVector())))) ;
                    {
                        orientate(desiredOrientation);
                    }
                } catch (Exception e) {
                }
                move(SPEED);
            }
            if (isInvulnerable()) {
                TIMER = TIMER - deltaTime;
            }
            if (TIMER < 0) {
                TIMER = 0.f;
            }
            if (hp == 0) {
                hp = 3;
            }
            super.update(deltaTime);
        }

    }


    /**
     * Sets the desired orientation if button is pressed
     */
    private void setDesiredOrientation() {
        Keyboard keyboard = getOwnerArea().getKeyboard();

        if (keyboard.get(Keyboard.LEFT).isDown()) {
            desiredOrientation = Orientation.LEFT;
        } else if (keyboard.get(Keyboard.UP).isDown()) {
            desiredOrientation = Orientation.UP;
        }else if (keyboard.get(Keyboard.RIGHT).isDown()) {
            desiredOrientation = Orientation.RIGHT;
        } else if (keyboard.get(Keyboard.DOWN).isDown()) {
            desiredOrientation = Orientation.DOWN;
        } else { }
    }

    public static int getMaxHp() {
        return MAX_HP;
    }

    public int getScore() { return  this.score; }

    public static int getHp() { return hp; }

    public static void setHp(int hp) { SuperPacmanPlayer.hp = hp; }

    public void setScore(int score) { this.score = score; }

    @Override
    public void draw(Canvas canvas){
        animation[getOrientation().ordinal()].draw(canvas);
        superPacmanPlayerStatusGUI.draw(canvas);
    }

    public boolean isInvulnerable (){
        if (TIMER == 0) {
            Invulnerable = false;
        }
        return Invulnerable;
    }

    protected void setInvulnerable(boolean invulnerable) {
        setTIMER(10.f);
        Invulnerable = invulnerable;
    }

    public void setTIMER(float timer) { this.TIMER = timer; }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() { return Collections.singletonList(getCurrentMainCellCoordinates()); }

    @Override
    public List<DiscreteCoordinates> getFieldOfViewCells() {
        return null;
    }

    @Override
    public boolean wantsCellInteraction() {
        return true;
    }

    @Override
    public boolean wantsViewInteraction() {
        return false;
    }

    @Override
    public void interactWith(Interactable other) { other.acceptInteraction(Handler); }

    @Override
    public boolean takeCellSpace() {
        return false;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return true;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {
        ((GhostInteractionVisitor)v).interactWith(this);
    }

    /**
     * Method that will make pacman go to his spawn if he is not invulnerable and if he touches a ghost
     */
    public void touchesGhost(){
        getOwnerArea().leaveAreaCells(this, this.getEnteredCells());
        if (getOwnerArea().getTitle() == "superPacman/Level1") {
            setCurrentPosition(Level1.getPLAYER_SPAWN_POSITION().toVector());
        } else if (getOwnerArea().getTitle() == "superPacman/Level2") {
            setCurrentPosition(Level2.getPLAYER_SPAWN_POSITION().toVector());
        }
        hp = hp - 1;

        getOwnerArea().enterAreaCells(this, this.getCurrentCells());
        resetMotion();
    }

    private class SuperPacmanPlayerHandler implements SuperPacmanInteractionVisitor {
        @Override
        public void interactWith(Door door) {setIsPassingADoor(door);}

        @Override
        public void interactWith(SuperPacmanCollectable superPacmanCollectable) { superPacmanCollectable.collect(); }

        @Override
        public void interactWith(Bonus bonus) {
            setInvulnerable(true);
            bonus.collect();
        }
        @Override
        public void interactWith(Diamond diamond) {
            setScore(getScore() + 10);
            diamond.collect();
        }

        /**
         * If pacman is invulnerable and if a pacman touches a ghost then the pacman
         * goes back to his spawn and the player wins 500 points
         * else enable the method that if ghost touches a vulnerable pacman then the pacman
         * will go to spawn as well as all the ghosts
         * @param ghost
         */
        @Override
        public void interactWith(Ghost ghost) {
            if(isInvulnerable()){
                ghost.touchesPacman();
                setScore(getScore()+ GHOST_SCORE);
            }else{
                touchesGhost();
                ((SuperPacmanArea)getOwnerArea()).respawnAllGhost();
            }
        }

        @Override
        public void interactWith(Cherry cherry) {
            setScore(getScore() +200);
            cherry.collect();
        }

        @Override
        public void interactWith(Key key) { key.collect(); }
    }
}


