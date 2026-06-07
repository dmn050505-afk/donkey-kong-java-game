package objects;


import pt.iscte.poo.utils.Point2D;

public class Trap extends GameObject {
	
    public Trap(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Trap"; // Nome associado à imagem do objeto
    }

	
}
