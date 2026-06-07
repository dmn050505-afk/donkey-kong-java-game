package objects;


import pt.iscte.poo.utils.Point2D;

public class DoorClosed extends GameObject {
	
    public DoorClosed(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "DoorClosed"; // Nome associado à imagem do objeto
    }

	
}
