package ch.epfl.cs107.play.game.superpacman;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.rpg.RPG;
import ch.epfl.cs107.play.game.superpacman.actor.SuperPacmanPlayer;
import ch.epfl.cs107.play.game.superpacman.area.Level0;
import ch.epfl.cs107.play.game.superpacman.area.Level1;
import ch.epfl.cs107.play.game.superpacman.area.Level2;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.io.FileSystem;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.window.Window;

public class SuperPacman extends RPG {
    private SuperPacmanPlayer player;

    private void createAreas() {
        addArea(new Level0());
        addArea(new Level1());
        addArea(new Level2());
    }

    /**
     * Update method that willl check if the pacman is invulnerable and then sets the invulnerabilty of the ghost according to that
     * Also checks if the pacman stills has hp, if not then the game restarts
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        ((SuperPacmanArea)getCurrentArea()).setHasABonus(player.isInvulnerable());       
        if(SuperPacmanPlayer.getHp() == 0){
            begin(getWindow(),getFileSystem());
        }
        super.update(deltaTime);
    }

    @Override
    public String getTitle() {
        return "Pac-Man Alex et Marc";
    }

    /**
     * Begin method of the game, sets the area to level0
     * @param window
     * @param fileSystem
     * @return boolean
     */
    public boolean begin(Window window, FileSystem fileSystem) {
        if (super.begin(window , fileSystem)) {

            createAreas();
            //areaIndex = 0;
            Area area = setCurrentArea("superpacman/Level0", true);

            player = new SuperPacmanPlayer(area, Orientation.RIGHT, new DiscreteCoordinates(10,1));
            initPlayer(player);
            return true;
        }
        else return false;

    }
    @Override
    public void end(){}
}
