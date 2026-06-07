package objects;


import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class Sword extends GameObject {
	
	ImageGUI gui = ImageGUI.getInstance();
	
    public Sword(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Sword"; // Nome associado à imagem do objeto
    }

    public int getLayer() {
        return 1;
    }
	public void desaparecer() {
		setPosition(null);  // A espada não tem mais uma posição válida
		gui.setStatusMessage("Espada coletada!");
    }

	
}