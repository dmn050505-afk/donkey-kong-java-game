package objects;

import java.util.Random;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Room;


public class Bat extends GameObject {
    
    public Bat(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Bat";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public Direction randomHorizontalDirection() {
        int choice = new Random().nextInt(2); // 0 for LEFT, 1 for RIGHT
        return choice == 0 ? Direction.LEFT : Direction.RIGHT;
    }

    public void move(Direction direction, Room room) {
        Point2D newPosition = getPosition().plus(direction.asVector());

        if (room.isOutOfBounds(newPosition) || room.isWall(newPosition)) {
            return; // Invalid move
        }

        // Move to the new position if valid
        setPosition(newPosition);
    }

    public void updateMovement(Room room) {
        if (room.isStairBelow(getPosition())) {
            // Move down if there's a stair below
            move(Direction.DOWN, room);
        } else {
            // Move randomly left or right
            Direction direction = randomHorizontalDirection();
            move(direction, room);
        }
    }
}
    

