package objects;


import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Point2D;

public class GoodMeat extends GameObject {
	
	ImageGUI gui = ImageGUI.getInstance();
	
	   
    public GoodMeat(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    
    
    public String getName() {
        return "GoodMeat"; // Nome associado à imagem do objeto
    }

    public int getLayer() {
        return 1;
    }
    public void desaparecer() {
		setPosition(null); 
		gui.setStatusMessage("Carne coletada!");
    }
    
   
}
