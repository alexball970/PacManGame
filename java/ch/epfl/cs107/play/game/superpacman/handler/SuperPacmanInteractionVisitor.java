package ch.epfl.cs107.play.game.superpacman.handler;

import ch.epfl.cs107.play.game.rpg.handler.RPGInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.*;

public interface SuperPacmanInteractionVisitor extends RPGInteractionVisitor {

    /**
     * Simulate and interaction between Superpacman Interactor and a Door
     * @param superPacmanPlayer (SuperPacmanPlayer), not null
     */
    default void interactWith(SuperPacmanPlayer superPacmanPlayer){/* by default the interaction is empty*/ }

    default void interactWith(SuperPacmanCollectable superPacmanCollectable) {/* by default the interaction is empty*/}

    default void interactWith(Bonus bonus) {/* by default the interaction is empty*/}

    default void interactWith(Cherry cherry) {/* by default the interaction is empty*/}

    default void interactWith(Diamond diamond) {/* by default the interaction is empty*/}

    default void interactWith(Key key) {/* by default the interaction is empty*/}

    default void interactWith(Ghost ghost) {/* by default the interaction is empty*/}

}
