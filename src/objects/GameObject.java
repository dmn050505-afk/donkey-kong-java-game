package objects;

import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;

public abstract class GameObject implements ImageTile {

    private Point2D position; // Posição do objeto no jogo
    
  

    // Construtor para inicializar atributos comuns
    public GameObject(Point2D position) {
        this.position = position;
       
    }

    // Métodos acessores
    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D position) {
        this.position = position;
    }

    public int getLayer() {
        return 0;
    }

    @Override
    public abstract String getName();



    
}