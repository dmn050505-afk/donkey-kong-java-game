package objects;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.game.Room;

public class Manel extends GameObject {

    private int vida; // Vida do personagem
    private int dano; // Dano causado pelo personagem
    ImageGUI gui = ImageGUI.getInstance();
    private Fire heldFire;
    
    public Manel(Point2D position) {
        super(position); // Inicializa a posição usando o construtor da superclasse
        this.vida = 100; // Vida inicial
        this.dano = 10; 
        this.heldFire = null; 
    }
    
    public void collectFire(Fire fire) {
        this.heldFire = fire;
    }

    @Override
    public String getName() {
        return "JumpMan"; // Nome da imagem associada ao objeto
    }

    @Override
    public int getLayer() {
        return 2; 
    }

    // Método para mover o Manel na direção especificada
    public void move(Direction direction) {
        setPosition(getPosition().plus(direction.asVector())); // Atualiza a posição usando a superclasse
    }

    
    public void sofrerDano(int danoRecebido) {
        this.vida -= danoRecebido;
        if (this.vida < 0) {
            this.vida = 0; // Vida não pode ser negativa
        }
        System.out.println("vida está a " + this.vida);
    }

    // Método para obter o dano que Manel pode causar
    public int getDano() {
        return this.dano;
    }

    // Método para obter a vida atual de Manel
    public int getVida() {
        return this.vida;
    }

    // Método para aumentar o dano de Manel
    public void aumentarDano(int incremento) {
        this.dano = Math.min(this.dano + incremento, 100);
        gui.setStatusMessage("Dano aumentado para: " + this.dano);// O dano não pode ultrapassar 100
    }
    
    public void aumentarVida() {
        this.vida= 100;
        gui.setStatusMessage("Vida a 100!");
    }
    
    

    public void dropFire(Room room) {
        if (heldFire != null) {
            Point2D dropPosition = getPosition();
            heldFire.setPosition(dropPosition);
            room.addObject(heldFire);
            heldFire = null; // Remove referência após o drop
        }
    }

    public boolean hasFire() {
        return heldFire != null;
    }
}

