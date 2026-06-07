package objects;

import pt.iscte.poo.utils.Point2D;

public class Stairs extends GameObject {

    public Stairs(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
    }

    @Override
    public String getName() {
        return "Stairs"; // Nome da imagem associada ao objeto
    }

    // Não é necessário sobrescrever getLayer(), pois o padrão da superclasse (camada 0) já é suficiente.
}
