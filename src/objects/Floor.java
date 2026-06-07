package objects;


import pt.iscte.poo.utils.Point2D;

public class Floor extends GameObject {
	
    public Floor(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Floor"; // Nome associado à imagem do objeto
    }

	
}
