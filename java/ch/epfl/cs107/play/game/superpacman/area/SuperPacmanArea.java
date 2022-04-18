package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Ghost;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Keyboard;
import ch.epfl.cs107.play.window.Window;


public abstract class SuperPacmanArea extends Area implements Logic {


    private boolean stop = false;
    private Window window;
    protected SuperPacmanBehavior behavior;

    private boolean hasABonus = false;

    //Logic
    private Logic signal = Logic.FALSE;

    //Calls the method from behavior
    public void removeDiamond() { behavior.removeDiamond(); }



    /**
     * Create the area by adding all its actors
     * called by the begin method, when the area starts to play
     */
    protected void createArea(){
        behavior.registerActors(this);
    }

    @Override
    public boolean begin(Window window, FileSystem fileSystem) {
        this.window = window;
        if (super.begin(window, fileSystem)) {
            // Set the behavior map
            behavior = new SuperPacmanBehavior(window , getTitle());
            setBehavior(behavior);
            createArea();
            return true;
        }
        return false;
    }

    /**
     * Method to pause the game
     * @return boolean
     */
    public boolean isStop(){
        Keyboard keyboard = getKeyboard();
        if(keyboard.get(Keyboard.SPACE).isDown()){
            return false;
        }
        return true;
    }


    public boolean cellIsPossible(DiscreteCoordinates coor) {
        return behavior.cellIsPossible(coor);
    }

    /**
     * Method that get all of the ghost registered (contained in the List)
     * and enables the method respawnGhost located in Ghost
     */
    public void respawnAllGhost(){
        for (Ghost ghost : behavior.getGhostlist()){
            ghost.respawnGhost();
        }
    }


    public void setHasABonus(boolean hasABonus) { this.hasABonus = hasABonus; }

    public boolean isHasABonus() { return hasABonus; }

    @Override
    public float getCameraScaleFactor() { return 25.f;}

    public SuperPacmanBehavior getBehavior() { return behavior; }

    //Logic
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

    public void setSignal(Logic signal) {
        this.signal = signal;
    }
}

