package ch.epfl.cs107.play.game.superpacman.actor.Ghost;

import ch.epfl.cs107.play.game.areagame.Area;
import ch.epfl.cs107.play.game.areagame.actor.Orientation;
import ch.epfl.cs107.play.game.superpacman.area.SuperPacmanArea;
import ch.epfl.cs107.play.math.DiscreteCoordinates;
import ch.epfl.cs107.play.math.RandomGenerator;
import ch.epfl.cs107.play.window.Canvas;

import java.util.Queue;

public class SmartGhost extends Ghost {
    private DiscreteCoordinates targetPos;
    private Orientation orientation;
    private Queue<Orientation> path;
    private int SPEED = 18;
    private boolean first = true;

    /**
     * Default MovableAreaEntity constructor
     *
     * @param area        (Area): Owner area. Not null
     * @param orientation (Orientation): Initial orientation of the entity. Not null
     * @param position    (Coordinate): Initial position of the entity. Not null
     */
    public SmartGhost(Area area, Orientation orientation, DiscreteCoordinates position) {
        super(area, orientation, position);
        setTargetPos();
    }


    /**
     * Method that sets the target position
     *
     * @return targetPos (Discrete Coordinates): the targeted position for the ghost
     */
    

    public DiscreteCoordinates setTargetPos(){
        SuperPacmanArea area = (SuperPacmanArea) getOwnerArea();
        if (player != null && !isAfraid()) {
            targetPos = player.getCurrentCells().get(0);
        }else{
                if(first) {
                first = false;
                do {
                    int x;
                    int y;
                    DiscreteCoordinates discreteCoordinates;
                    x = RandomGenerator.getInstance().nextInt(area.getWidth() - 1);
                    y = RandomGenerator.getInstance().nextInt(area.getHeight() - 1);
                    discreteCoordinates = new DiscreteCoordinates(x, y);

                    targetPos = discreteCoordinates;
                } while (getPosRefuge().equals(targetPos) || getCurrentMainCellCoordinates().equals(targetPos)
                        || ((SuperPacmanArea) getOwnerArea()).cellIsPossible(targetPos));
            }
            first = true;
        }
        return targetPos;
    }

    public DiscreteCoordinates getTargetPos() { return targetPos; }

    @Override
    public void update (float deltaTime){
        if ((getCurrentMainCellCoordinates() == setTargetPos()) ) { setTargetPos(); }
        super.update(deltaTime);
    }

    /**
     *
     * @return orientation (Orientation): the orientation (or the path of orientations)
     *                                      that the ghost has to take to get to the targetPos
     */
    @Override
    public Orientation getNextOrientation() {
        path = ((SuperPacmanArea) getOwnerArea()).getBehavior().calculatePath(getCurrentMainCellCoordinates(), targetPos);
        if(path!=null){
            orientation = path.poll();
            if (orientation != null ) {
                return orientation;
            }
            return Orientation.fromInt(RandomGenerator.getInstance().nextInt(4));

        }
        return Orientation.fromInt(RandomGenerator.getInstance().nextInt(4));
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
    }
}
