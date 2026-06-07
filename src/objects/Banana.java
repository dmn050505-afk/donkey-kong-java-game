package objects;

import pt.iscte.poo.utils.Point2D;


public class Banana extends GameObject {
    
    public Banana(Point2D position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Banana"; 
    }

    @Override
    public int getLayer() {
        return 2; 
    }

  
}
