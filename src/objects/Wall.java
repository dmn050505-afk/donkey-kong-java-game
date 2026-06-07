package objects;


import pt.iscte.poo.utils.Point2D;

public class Wall extends GameObject {
	
    public Wall(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Wall"; // Nome associado à imagem do objeto
    }

	
}
