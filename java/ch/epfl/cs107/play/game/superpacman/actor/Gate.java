package ch.epfl.cs107.play.game.superpacman.actor;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.actor.AreaEntity;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.actor.Sprite;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Key;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanBehavior;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RegionOfInterest;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Collections;
import java.util.List;

public class Gate extends AreaEntity implements Logic {

    private Key key1;
    private Key key2;
    private Sprite sprite;
    private AreaBehavior behavior;
    private Logic signal;


    /**
     * Constructor for a gate that uses a Key's signal
     * @param area (Area) : not null
     * @param orientation (Orientation) : not null orientates the door
     * @param position (DiscreteCoordinates) : not null
     * @param key (Key) : not null for the signal
     */
    public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Key key) {
        super(area, orientation, position);
        this.key1 = key;

        orientateGate(orientation);

    }

    /**
     * Constructor for a gate that uses an AreaBehavior's signal
     * @param area (Area) : not null
     * @param orientation (Orientation) : not null orientates the door
     * @param position (DiscreteCoordinates) : not null
     * @param behavior (AreaBehavior) : not null for the signal (nbrDiamond)
     */
    public Gate(Area area,Orientation orientation, DiscreteCoordinates position, SuperPacmanBehavior behavior) {
        super(area, orientation, position);
        this.behavior = behavior;

        orientateGate(orientation);

    }

    /**
     * Constructor for a gate that uses an AreaBehavior's signal
     * @param area (Area) : not null
     * @param orientation (Orientation) : not null orientates the door
     * @param position (DiscreteCoordinates) : not null
     * @param key (Key) : not null for the signal
     * @param behavior (AreaBehavior) : not null for the signal (nbrDiamond)
     */
    public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Key key, SuperPacmanBehavior behavior) {
        super(area, orientation, position);
        this.key1 = key;
        this.behavior = behavior;

        orientateGate(orientation);
    }

    /**
     * Constructor for a gate that uses an AreaBehavior's signal
     * @param area (Area) : not null
     * @param orientation (Orientation) : not null orientates the door
     * @param position (DiscreteCoordinates) : not null
     * @param key1 (Key) : not null for the signal
     * @param key2 (Key) : not null for the signal
     */
    public Gate(Area area, Orientation orientation, DiscreteCoordinates position, Key key1, Key key2) {
        super(area, orientation, position);
        this.key1 = key1;
        this.key2 = key2;

        orientateGate(orientation);
    }

    /**
     * Orientate the gate is used in the contructors
     * @param orientation (Orientation) : no null
     */
    public void orientateGate(Orientation orientation) {
        if ((orientation == Orientation.DOWN) || (orientation == Orientation.UP)) {
            sprite = new Sprite("superpacman/gate", 1, 1, this, new RegionOfInterest(0, 0, 64, 64));
        } else if ((orientation == Orientation.LEFT) || (orientation == Orientation.RIGHT)) {
            sprite = new Sprite("superpacman/gate", 1.f, 1, this, new RegionOfInterest(0, 64, 64, 64));
        }
    }



    @Override
    public void update(float deltaTime) {

        setSignal();

        if (signal == Logic.FALSE) {
            getOwnerArea().unregisterActor(this);
        }
        super.update(deltaTime);
    }

    @Override
    public void draw(Canvas canvas) {
        sprite.draw(canvas);
    }

    @Override
    public List<DiscreteCoordinates> getCurrentCells() {
        return Collections.singletonList(getCurrentMainCellCoordinates());
    }

    @Override
    public boolean takeCellSpace() {
        return true;
    }

    @Override
    public boolean isCellInteractable() {
        return false;
    }

    @Override
    public boolean isViewInteractable() {
        return false;
    }

    @Override
    public void acceptInteraction(AreaInteractionVisitor v) {

    }


    //Logic

    //using the signal from the key(s) and the area
    public void setSignal() {
        if (key2 == null) {
            if ((key1 == null) && (behavior == null)) {
                signal = Logic.TRUE;
            }

            if ((key1 != null) && (behavior == null)) {
                signal = key1.getSignal();
            }

            if ((key1 == null) && (behavior != null)) {
                signal = ((SuperPacmanBehavior) behavior).getSignal();
            }

            if ((key1 != null) && (behavior != null)) {
                if (key1.isOn() || ((SuperPacmanBehavior) behavior).isOn()) {
                    signal = Logic.TRUE;
                } else {
                    signal = Logic.FALSE;
                }
            }
        } else {
            if (key1.isOn() || key2.isOn()) {
                signal = Logic.TRUE;
            } else {
                signal = Logic.FALSE;
            }
        }

    }


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
