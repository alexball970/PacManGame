package ch.epfl.cs107.play.game.superpacman.area;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.AreaBehavior;
import ch.epfl.cs107.play.game.areagame.AreaGraph;
import ch.epfl.cs107.play.game.areagame.Cell;
import ch.epfl.cs107.play.game.areagame.actor.Interactable;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.areagame.handler.AreaInteractionVisitor;
import ch.epfl.cs107.play.game.superpacman.actor.*;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Blinky;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Ghost;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Inky;
import ch.epfl.cs107.play.game.superpacman.actor.Ghost.Pinky;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Bonus;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Cherry;
import ch.epfl.cs107.play.game.superpacman.actor.collectable.Diamond;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.signal.logic.Logic;
import ch.epfl.cs107.play.window.Window;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class SuperPacmanBehavior extends AreaBehavior implements Logic {

    public enum SuperPacmanCellType {
        NONE(0), // never used as real content
        WALL ( -16777216), //black
        FREE_WITH_DIAMOND(-1), //white
        FREE_WITH_BLINKY (-65536), //red
        FREE_WITH_PINKY ( -157237), //pink
        FREE_WITH_INKY ( -16724737), //cyan
        FREE_WITH_CHERRY (-36752), //light red
        FREE_WITH_BONUS ( -16478723), //light blue
        FREE_EMPTY ( -6118750); // sort of gray

        final int type;


        SuperPacmanCellType(int type) {
            this.type = type;
        }

        public static SuperPacmanBehavior.SuperPacmanCellType toType(int type) {
            for (SuperPacmanBehavior.SuperPacmanCellType ict : SuperPacmanBehavior.SuperPacmanCellType.values()) {
                if (type == ict.type) {
                    return ict;
                }
            }
            return NONE;
        }
    }

    //Logic
    private Logic signal = Logic.TRUE;

    //Counts the diamonds
    private int nbDiamonds = 0;
    public void removeDiamond() {
        --nbDiamonds;
        System.out.println(nbDiamonds);
        if (nbDiamonds == 0) { setSignal(Logic.FALSE); }

    }

    // Area graph
    private AreaGraph graph;

    /**
     * Creates an AreaGraph that will create nodes if a cell is not a wall
     * The ghosts will be  moving according to this graph
     */
    public void graphAssocie(){
        graph = new AreaGraph();
        boolean hasLeftEdge;
        boolean hasUpEdge;
        boolean hasRightEdge;
        boolean hasDownEdge;


        for(int i = 0 ; i < getWidth(); ++i){
            for(int j = 0 ; j < getHeight() ; ++j){

                    if(((SuperPacmanCell) getCell(i,j)).getType() != SuperPacmanCellType.WALL) {

                        hasLeftEdge = ((i > 0) && (((SuperPacmanCell) getCell(i-1, j)).getType())  != SuperPacmanCellType.WALL);
                        hasDownEdge = ((j > 0) && (((SuperPacmanCell) getCell(i,j-1)).getType())  != SuperPacmanCellType.WALL);
                        hasRightEdge = ((i < getWidth()-1) && (((SuperPacmanCell) getCell(i+1, j)).getType())  != SuperPacmanCellType.WALL);
                        hasUpEdge = ((j < getHeight()-1) && (((SuperPacmanCell) getCell(i,j+1)).getType())  != SuperPacmanCellType.WALL);

                        this.graph.addNode((new DiscreteCoordinates(i, j)), hasLeftEdge, hasUpEdge, hasRightEdge, hasDownEdge);
                    }


            }
        }
    }

    public AreaGraph getGraph() { return graph; }

    /**
     * method that gives the shortest path in a Queue of orientations for a given starting point and finish point
     * @param start
     * @param finish
     * @return isAfraid (boolean)
     */
    public Queue <Orientation> calculatePath(DiscreteCoordinates start, DiscreteCoordinates finish){
        Queue <Orientation> path = graph.shortestPath(start,finish);
        return path;
    }

    private boolean isAfraid;

    public void setAfraid(boolean afraid) {
        isAfraid = afraid;
    }

    public boolean getAfraid(){
        return isAfraid;
    }

    /**
     * checks if the cell is usable, used in other method
     * @param coor
     * @return
     */
    public boolean cellIsPossible(DiscreteCoordinates coor) {
        return ((getRGB(getHeight()-1-coor.y, coor.x)) == SuperPacmanCellType.WALL.type && coor.x >= 0 && coor.y >= 0
                && coor.x < getWidth() && coor.y < getHeight());
    }

    private List<Ghost> ghostlist = new ArrayList<>();

    public List<Ghost> getGhostlist() { return ghostlist; }

    /**
     * Registers all the actors in the area
     * @param area
     */
    protected void registerActors(Area area) {
        for (int x = 0; x < area.getWidth(); ++x) {
            for (int y = 0; y < area.getHeight(); ++y) {
                //goes through the area

                switch (((SuperPacmanCell) getCell(x, y)).type) {
                    case WALL:
                        registerWall(area, x, y);
                        break;
                    case FREE_WITH_BLINKY:
                        Blinky blinky = new Blinky(area, Orientation.UP, new DiscreteCoordinates(x,y));
                        area.registerActor(blinky);
                        ghostlist.add(blinky);
                        break;
                    case FREE_WITH_INKY:
                        Inky inky = new Inky(area, Orientation.UP, new DiscreteCoordinates(x,y));
                        area.registerActor(inky);
                        ghostlist.add(inky);
                        break;
                    case FREE_WITH_PINKY:
                        Pinky pinky = new Pinky(area, Orientation.UP, new DiscreteCoordinates(x,y));
                        area.registerActor(pinky);
                        ghostlist.add(pinky);
                        break;
                    case FREE_WITH_BONUS:
                        //to be modified
                        area.registerActor(new Bonus(area, Orientation.UP, new DiscreteCoordinates(x, y)));
                        break;

                    case FREE_WITH_CHERRY:
                        area.registerActor(new Cherry(area, Orientation.UP, new DiscreteCoordinates(x, y)));
                        break;

                    case FREE_WITH_DIAMOND:
                        area.registerActor(new Diamond(area, Orientation.UP, new DiscreteCoordinates(x, y)));
                        ++nbDiamonds;
                        break;
                }
            }
        }
    }
    /**
     * Registers the walls in the area
     * @param area
     * @param x
     * @param y
     */
    private void registerWall(Area area, int x, int y) {
        boolean[][] neighborhood = new boolean[3][3];

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                try {
                    if (((SuperPacmanCell) getCell(x-1+i, y+1-j)).type == SuperPacmanCellType.WALL) {
                        neighborhood[i][j] = true;
                    } else {
                        neighborhood[i][j] = false;
                    }
                } catch (Exception e) {
                    neighborhood[i][j] = false;
                }
            }
        }
        area.registerActor(new Wall(area, new DiscreteCoordinates(x, y), neighborhood));
    }

    /**
     * Default constructor for the SuperPacmanBehavior
     * @param window
     * @param name
     */
    public SuperPacmanBehavior(Window window, String name) {
        super(window, name);
        int width = getWidth();
        int height = getHeight();

        for (int y = 0; y < height; ++y){
            for (int x = 0; x < width; ++x) {
                SuperPacmanBehavior.SuperPacmanCellType color = SuperPacmanBehavior.SuperPacmanCellType.toType(getRGB(height -1-y, x));
                setCell(x, y, new SuperPacmanBehavior.SuperPacmanCell(x, y, color));
            }
        }
       graphAssocie();
    }

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

    public Logic getSignal() { return signal; }





    public class SuperPacmanCell extends Cell {
        /// Type of the cell following the enum
        private final SuperPacmanBehavior.SuperPacmanCellType type;

        /**
         * Default  Constructor
         * @param x (int): x coordinate of the cell
         * @param y (int): y coordinate of the cell
         * @param type (EnigmeCellType), not null
         */
        public  SuperPacmanCell(int x, int y, SuperPacmanBehavior.SuperPacmanCellType type){
            super(x, y);
            this.type = type;
        }

        public SuperPacmanCellType getType(){
            return type;
        }

        @Override
        protected boolean canLeave(Interactable entity) {
            return true;
        }

        @Override
        protected boolean canEnter(Interactable entity) {return !this.hasNonTraversableContent(); }

        @Override
        public boolean isCellInteractable() {
            return true;
        }

        @Override
        public boolean isViewInteractable() {
            return false;
        }

        @Override
        public void acceptInteraction(AreaInteractionVisitor v) { }

    }
}
