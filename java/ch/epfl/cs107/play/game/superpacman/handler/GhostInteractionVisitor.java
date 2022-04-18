package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;

public interface GhostInteractionVisitor extends AreaInteractionVisitor {

    default void interactWith(SuperPacmanPlayer superPacmanPlayer){/* by default the interaction is empty*/ }
}
