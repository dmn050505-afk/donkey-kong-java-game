package pt.iscte.poo.game;

import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;



public class GameEngine implements Observer  {

    private static GameEngine instance;  // Instância única do Singleton
    private Room currentRoom = new Room();
    private int lastTickProcessed = 0;
    
    
    
    
    
    // Construtor privado para garantir que não sejam criadas instâncias diretamente
    private GameEngine() {
        ImageGUI.getInstance().update();
    }

    // Método estático para obter a instância única do GameEngine
    public static GameEngine getInstance() {
        if (instance == null) { // Se a instância não existir, cria uma nova
            instance = new GameEngine();
        }
        return instance; // Retorna a instância única
    }
    
   
    @Override
    public void update(Observed source) {
        if (ImageGUI.getInstance().wasKeyPressed()) {
            int key = ImageGUI.getInstance().keyPressed();
            if (key == 'B' || key == 'b') {
                currentRoom.dropCollectedBomb(lastTickProcessed);
            } else if (Direction.isDirection(key)) {
                currentRoom.moveManel(key);
                currentRoom.handleBombPickup(currentRoom.getManelPosition());
            }
        }

        int ticks = ImageGUI.getInstance().getTicks();
        while (lastTickProcessed < ticks) {
            processTick();
        }

        ImageGUI.getInstance().update();
    }

    private void processTick() {
        currentRoom.applyGravity();
        currentRoom.updateDonkeyKong();
        currentRoom.updateBananas(); 
        currentRoom.updateBat();
        currentRoom.updateBombs(lastTickProcessed); // Atualiza as bombas com o tick atual
        lastTickProcessed++;
    }


}
