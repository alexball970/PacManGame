package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Background;
import ch.epfl.cs107.play.game.areagame.actor.Foreground;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Key;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level0 extends SuperPacmanArea {
    private static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(10, 1);

    public static DiscreteCoordinates getPLAYER_SPAWN_POSITION() {
        return PLAYER_SPAWN_POSITION;
    }



    @Override
    protected void createArea(){
        super.createArea();

        registerActor(new Door("superPacman/Level1", Level1.getPLAYER_SPAWN_POSITION(), Logic.TRUE, this, Orientation.UP,
                new DiscreteCoordinates(5,9), new DiscreteCoordinates(6,9)));

        Key key =new Key(this, Orientation.UP, new DiscreteCoordinates(3, 4));
        registerActor(key);


        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(5, 8), key));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(6, 8), key));
    }

    @Override
    public String getTitle() {
        return "superpacman/Level0";
    }
}

