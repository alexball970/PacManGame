package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;

public class Level2 extends SuperPacmanArea{
    private static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 29);

    public static DiscreteCoordinates getPLAYER_SPAWN_POSITION() {
        return PLAYER_SPAWN_POSITION;
    }

    @Override
    protected void createArea(){

        super.createArea();

        Key key1 =new Key(this, Orientation.UP, new DiscreteCoordinates(3, 16));
        registerActor(key1);
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 14), key1));
        registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(5, 12), key1));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 10), key1));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(8, 8), key1));

        Key key2 =new Key(this, Orientation.UP, new DiscreteCoordinates(26, 16));
        registerActor(key2);
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 14), key2));
        registerActor(new Gate(this, Orientation.DOWN, new DiscreteCoordinates(24, 12), key2));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 10), key2));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(21, 8), key2));

        Key key3 =new Key(this, Orientation.UP, new DiscreteCoordinates(2, 8));
        registerActor(key3);
        Key key4 =new Key(this, Orientation.UP, new DiscreteCoordinates(27, 8));
        registerActor(key4);
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(10, 2), key3, key4));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(19, 2), key3, key4));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(12, 8), key3, key4));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(17, 8), key3, key4));

        //tous les diamants
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), behavior));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), behavior));

    }

    @Override
    public String getTitle() {
        return "superPacman/Level2";
    }
}
