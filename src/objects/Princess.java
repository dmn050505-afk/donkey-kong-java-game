package objects;


import pt.iscte.poo.utils.Point2D;

public class Princess extends GameObject {
	
    public Princess(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Princess"; // Nome associado à imagem do objeto
    }
    @Override
    public int getLayer() {
        return 1;
    }
	
}
