package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.actor.Door;
import ch.epfl.cs107.play.game.superpacman.actor.Gate;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;

public class Level1 extends SuperPacmanArea{
    private static final DiscreteCoordinates PLAYER_SPAWN_POSITION = new DiscreteCoordinates(15, 6);

    public static DiscreteCoordinates getPLAYER_SPAWN_POSITION() {
        return PLAYER_SPAWN_POSITION;
    }

    @Override
    protected void createArea(){
        super.createArea();
        registerActor(new Door("superPacman/Level2", Level2.getPLAYER_SPAWN_POSITION(), Logic.TRUE, this, Orientation.DOWN,
                new DiscreteCoordinates(14,0), new DiscreteCoordinates(15,0)));

        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(14, 3), behavior));
        registerActor(new Gate(this, Orientation.RIGHT, new DiscreteCoordinates(15, 3), behavior));
    }

    @Override
    public String getTitle() {
        return "superPacman/Level1";
    }
}
