package objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import java.util.Random;
import pt.iscte.poo.game.Room;

public class DonkeyKong extends GameObject {

    private int vida;
    ImageGUI gui = ImageGUI.getInstance();
   
    
    public DonkeyKong(Point2D position) {
        super(position);
        this.vida = 100;
        
        
    }

    @Override
    public String getName() {
        return "DonkeyKong";
    }

    @Override
    public int getLayer() {
        return 2;
    }

    public int getVida() {
        return this.vida;
    }

    public void sofrerDano(int danoRecebido) {
        this.vida -= danoRecebido;
        if (this.vida < 0) {
            this.vida = 0;
        }
    }
    
    public void desaparecer() {
        // Remove o DonkeyKong da sala e da interface gráfica
    	setPosition(null);
    	gui.setStatusMessage("DonkeyKong foi derrotado!");
        
        
    }

    public Direction randomDirection() {
        int choice = new Random().nextInt(2); 
        switch (choice) {
            case 0:
                return Direction.LEFT;  
            case 1:
                return Direction.RIGHT;    	
            default:
                return Direction.LEFT;   
        }
    }
    
    

    public void move(Direction direction, Room room) {
        Point2D newPosition = getPosition().plus(direction.asVector());
        if (!room.isOutOfBounds(newPosition) && !room.isWall(newPosition)) {
            setPosition(newPosition);

            // Chance de lançar banana
            if (new Random().nextInt(100) < 62) { 
                Point2D bananaPosition = newPosition.plus(Direction.DOWN.asVector());
                if (!room.isOutOfBounds(bananaPosition)) {
                    room.addBanana(new Banana(bananaPosition));
                    ImageGUI.getInstance().update(); 
                }
            }
        }
    }
    
    

   
}
