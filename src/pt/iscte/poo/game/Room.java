package pt.iscte.poo.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import objects.*;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.gui.ImageTile;
import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.utils.Direction;


public class Room {
	
	private List<GameObject> objectsInRoom;
	private  Point2D position;
	private Manel manel;
	private char[][] map;
	private int mapWidth;
    private int mapHeight;
    private String nextRoom; 
    private String Room; 
    ImageGUI gui = ImageGUI.getInstance();
    private List<Banana> bananas;
    private Fire collectedBomb; 
    private List<Fire> activeBombs = new ArrayList<>();
    
   

   
	
    public Room() {
        objectsInRoom = new ArrayList<>();
        bananas = new ArrayList<>();
        nextRoom = "rooms/room0.txt"; 
        Room=nextRoom; 
        loadRoom(nextRoom); 
    }

    private void loadRoom(String fileName) {
    	ImageGUI.getInstance().clearImages();
        
        objectsInRoom.clear();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            boolean isFinalMap = false;
            String firstLine = null;

            if (scanner.hasNextLine()) {
                firstLine = scanner.nextLine();

               
                if (firstLine.contains("P")) {
                    isFinalMap = true; 
                     
                } else if (firstLine.contains(";")) {
                    
                    firstLine = firstLine.trim(); 
                    String[] roomInfo = firstLine.split(";");
                    if (roomInfo.length > 1) {
                        nextRoom = "rooms/" + roomInfo[1]; 
                    } else {
                        
                        return;
                    }
                } else {
                    
                    return;
                }
            }

            int y = 0; 
            map = new char[100][100];
            int maxWidth = 0;
           
            
            if (isFinalMap && firstLine != null) {
                processMapLine(firstLine, y++);
            }
            
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                
                // Remove o último caractere se for um espaço
                if (!line.isEmpty() && line.charAt(line.length() - 1) == ' ') {
                    line = line.substring(0, line.length() - 1);
                }
                
                processMapLine(line, y++);
                if (line.length() > maxWidth) {
                    maxWidth = line.length();
                }
            }

            mapWidth = maxWidth;
            mapHeight = y;

            
            for (int i = 0; i < mapHeight; i++) {
                for (int j = 0; j < mapWidth; j++) {
                    if (map[j][i] == '\0') { // Substituir '\0' por ' ' para espaços não inicializados
                        map[j][i] = ' ';
                    }
                }
            }

        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado: " + fileName);
        } catch (Exception e) {
            System.err.println("Erro ao carregar o mapa: " + e.getMessage());
        }
        
    }
    
    private void processMapLine(String line, int y) {
    	
    	for (int x = 0; x < line.length(); x++) {
            char tileChar = line.charAt(x);
            position = new Point2D(x, y);
            map[x][y] = tileChar;
           
            ImageGUI.getInstance().addImage(new Floor(position));
           
            ImageTile image = create(tileChar, position);
            if (image != null) {
                ImageGUI.getInstance().addImage(image);
            }
        }
    }
    
    
    private GameObject create(char type, Point2D position) {
        switch (type) {
            case 'H':
                manel = new Manel(position);
                return manel;
            case 'W':
                return new Wall(position);
            case 'S':
                return new Stairs(position);
            case 'P':
                Princess p=new Princess(position);
                objectsInRoom.add(p);
                return p;
            case 'G':
                DonkeyKong c=  new DonkeyKong(position);
                objectsInRoom.add(c);
                return c;
            case 't':
                Trap d= new Trap(position);
                objectsInRoom.add(d);
                return d;
            case 'b':
            	Bat m = new Bat(position);
            	objectsInRoom.add(m);
            	return m;
            case 'F': // Representação de Fire/Bomba no mapa
                Fire f = new Fire(position, this);
                objectsInRoom.add(f);
                return f;
            case 'm':        	
                GoodMeat b = new GoodMeat(position);
                objectsInRoom.add(b);
                return b;
            case '0':
                
                DoorClosed e = new DoorClosed(position);
                objectsInRoom.add(e);
                return e;
            case 's':
                Sword a= new Sword(position);
                objectsInRoom.add(a);
                return a;
            case ' ':
            	return new Floor(position);
            default: 
                return new Floor(position);
        }
    }
	
	 
    public boolean isWall(Point2D position) {
        int x = position.getX();
        int y = position.getY();
        
       
        if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
        	return map[x][y] == 'W' ;  
        }
        return false;
    }

    
    public boolean isOutOfBounds(Point2D position) {
        int x = position.getX();
        int y = position.getY();
        return x < 0 || x >= mapWidth || y < 0 || y >= mapHeight;
    }
    public boolean isFloor(Point2D position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < mapWidth && y >= 0 && y < mapHeight && map[x][y] == ' ';
    }
    
    public boolean isTrap(Point2D position) {
        int x = position.getX();
        int y = position.getY();
        
       
        if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
            return map[x][y] == 't'; 
        }
        return false;
    }
    
    public boolean isStairBelow(Point2D position) {
        Point2D below = position.plus(Direction.DOWN.asVector());
        return !isOutOfBounds(below) && map[below.getX()][below.getY()] == 'S';
    }
    
    
    
    
    
    
    
    
    
    
    

    public void moveManel(int keyCode) {
        Direction direction = Direction.directionFor(keyCode);  

        Point2D newPosition = manel.getPosition().plus(direction.asVector());

        GameObject objectAtNewPosition = findObjectAtPosition(newPosition);

        if (isDonkeyKongAtPosition(newPosition)) {
            gui.setStatusMessage("Manel atacou Donkey Kong!");
            DonkeyKong donkeyKong = getDonkeyKongAtPosition(newPosition); 
            donkeyKong.sofrerDano(manel.getDano());  

            if (donkeyKong.getVida() == 0) {
                donkeyKong.desaparecer();  
                objectsInRoom.remove(donkeyKong);  
                ImageGUI.getInstance().removeImage(donkeyKong);  
            }

            return;  
        }

        if(isTrap(manel.getPosition())){
            manel.sofrerDano(20);
            gui.setStatusMessage("AI AI AI!");
            if (manel.getVida() <= 0) {
                restartLevel();  
                return; 
            }
        }
        
       

        if (isBanana(newPosition)) {
            gui.setStatusMessage("AI banana!");
            manel.sofrerDano(10);

            Banana bananaToRemove = null;
            for (Banana banana : bananas) {
                if (banana.getPosition().equals(newPosition)) {
                    bananaToRemove = banana;
                    break;
                }
            }
            if (bananaToRemove != null) {
                bananas.remove(bananaToRemove);
                ImageGUI.getInstance().removeImage(bananaToRemove); 
            }

            if (manel.getVida() <= 0) {
                restartLevel();
                return;
            }
        }

        if (objectAtNewPosition instanceof Sword) {
            Sword sword = (Sword) objectAtNewPosition;
            sword.desaparecer();
            manel.aumentarDano(20);
            objectsInRoom.remove(sword); 
            ImageGUI.getInstance().removeImage(sword); 
        }

        if (objectAtNewPosition instanceof GoodMeat) {
            GoodMeat meat = (GoodMeat) objectAtNewPosition;
            meat.desaparecer();
            manel.aumentarVida();
            objectsInRoom.remove(meat); 
            ImageGUI.getInstance().removeImage(meat); 
        }
        
        if (objectAtNewPosition instanceof Princess) {
            int ticks = ImageGUI.getInstance().getTicks();
            ImageGUI.getInstance().showMessage("Parabéns!", "BOA, CHEGASTE AO FIM! Demoraste: " + ticks + " segundos");
            ImageGUI.getInstance().dispose(); // Fecha o jogo
            return;
        }

        if (isOutOfBounds(newPosition)) {
            return;
        } else if (isWall(newPosition)) {
            return;
        } else if (direction == Direction.UP && !isStairs(manel.getPosition())) {
            return;
        } else {
            manel.move(direction); 
            ImageGUI.getInstance().update();
        }

        if (objectAtNewPosition instanceof DoorClosed) {
            gui.setStatusMessage("Boa, passaste o nivel!");
            loadRoom(nextRoom);
        }
    }

    
    public boolean isBanana(Point2D position) {
        for (Banana banana : bananas) {
            if (banana.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }
    
    
    public void addBanana(Banana banana) {
    	 bananas.add(banana); // Adiciona a banana à lista
    	    ImageGUI.getInstance().addImage(banana); // Adiciona a imagem à interface gráfica
    	    ImageGUI.getInstance().update(); 
    }

    public void updateBananas() {
        List<Banana> toRemove = new ArrayList<>();
        for (Banana banana : bananas) {
            Point2D below = banana.getPosition().plus(Direction.DOWN.asVector());

            if (isOutOfBounds(below)) {
                toRemove.add(banana);
                ImageGUI.getInstance().removeImage(banana);
            } else if (isManelAtPosition(below) ) {
                manel.sofrerDano(10);
                gui.setStatusMessage("AI banana!");
                toRemove.add(banana);
                ImageGUI.getInstance().removeImage(banana);

                if (manel.getVida() <= 0) {
                    restartLevel();
                    return;
                }
            } else {
                banana.setPosition(below);
            }
        }

        bananas.removeAll(toRemove);
        ImageGUI.getInstance().update(); 
    }
    
    
    
    
    

    
    
    
  
    
    
    
    
    
    public boolean isDonkeyKongAtPosition(Point2D position) {
        for (GameObject object : objectsInRoom) {
            if (object instanceof DonkeyKong && object.getPosition().equals(position)) {
                return true;  // Se o Donkey Kong estiver na posição
            }
        }
        return false;  // Caso contrário
    }

    // Método para obter o Donkey Kong na posição fornecida
    public DonkeyKong getDonkeyKongAtPosition(Point2D position) {
        for (GameObject object : objectsInRoom) {
            if (object instanceof DonkeyKong && object.getPosition().equals(position)) {
                return (DonkeyKong) object;  // Retorna a instância do Donkey Kong
            }
        }
        return null;  // Caso não haja Donkey Kong na posição
    }
    
    
    
    
    
    
    public void restartLevel() {
        // Recarrega o nível atual
        loadRoom(Room);  // Usa o mesmo nível em que o Manel está

       
        manel.aumentarVida();  // Restaura a vida inicial de Manel

       
        ImageGUI.getInstance().update();
        gui.setStatusMessage("jogo reiniciado!");
    }
    
    
    
    
    
    public void applyGravity() {
        Point2D below = manel.getPosition().plus(Direction.DOWN.asVector());

        // Verifica se o Manel pode cair
        if (!isOutOfBounds(below) && !isWall(below) && !isStairs(below)) {
            manel.move(Direction.DOWN); // Move o Manel um passo para baixo
        }
        
        if (isTrap(below)) {
        	gui.setStatusMessage("AI AI AI! Manel caiu numa armadilha!");
            manel.sofrerDano(20); 
            if (manel.getVida() <= 0) {
                
                restartLevel();  // Reinicia o nível
                return;  // Impede o movimento de Manel após a reinicialização
            }
        }
    }
    
    
    
    
    
   
    
    
    public void addObject(GameObject object) {
        objectsInRoom.add(object);
        ImageGUI.getInstance().addImage(object);
        
    }
    
    
    
    
    public boolean isStairs(Point2D position) {
        int x = position.getX();
        int y = position.getY();
        
        // Verifica se a posição está dentro dos limites do mapa
        if (x >= 0 && x < mapWidth && y >= 0 && y < mapHeight) {
            // Retorna true se a célula na posição contém 'S' (escada)
            return map[x][y] == 'S';
        }
        return false; // Caso contrário, retorna false
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
   
    public GameObject findObjectAtPosition(Point2D position) {
        for (GameObject obj : objectsInRoom) {
            if (obj.getPosition() != null && obj.getPosition().equals(position)) {
                return obj;
            }
        }
        return null;
    }
    
    
    public Point2D getManelPosition() {
        return manel.getPosition();
    }
    
    
    public void updateDonkeyKong() {
        for (GameObject obj : objectsInRoom) {
            if (obj instanceof DonkeyKong) {
                DonkeyKong donkey = (DonkeyKong) obj;
                Direction direction = donkey.randomDirection();  
                Point2D newPosition = donkey.getPosition().plus(direction.asVector()); 
                

       if (!isOutOfBounds(newPosition) && !isWall(newPosition) ) {

                    if (isManelAtPosition(newPosition)) {
                        manel.sofrerDano(20);  
                        if (manel.getVida() <= 0) {
                            gui.setStatusMessage("A vida do Manel chegou a 0!");
                            restartLevel();  
                            return;  
                        }
                        gui.setStatusMessage("Manel sofreu dano!");
                    } else {
                        donkey.move(direction, this);
                    }
                }
            }
        }
    }
    
    public void updateBat() {
        List<Bat> batsToRemove = new ArrayList<>();

        for (GameObject obj : objectsInRoom) {
            if (obj instanceof Bat) {
                Bat bat = (Bat) obj;

                
                bat.updateMovement(this);

                
                if (isManelAtPosition(bat.getPosition())) {
                    manel.sofrerDano(20); 
                    batsToRemove.add(bat); 
                    gui.setStatusMessage("O morcego atacou o Manel!");

                    if (manel.getVida() <= 0) {
                        gui.setStatusMessage("A vida do Manel chegou a 0!");
                        restartLevel();
                        return;
                    }
                }
            }
        }

        // Remove bats that attacked Manel
        for (Bat bat : batsToRemove) {
            objectsInRoom.remove(bat);
            ImageGUI.getInstance().removeImage(bat);
        }

        ImageGUI.getInstance().update(); // Update the GUI
    }
    
    
    

    // Método para verificar se o Manel está na posição fornecida
    public boolean isManelAtPosition(Point2D position) {
        return manel.getPosition().equals(position);  // Verifica se o Manel está na posição fornecida
    }
    
  
    
    
    
    
    
    
    
 
    
        public boolean isManelNear(List<Point2D> explosionArea) {
            for (Point2D point : explosionArea) {
                if (manel.getPosition().equals(point)) {
                    return true;
                }
            }
            return false;
        }

        public void removeObject(GameObject object) {
            objectsInRoom.remove(object);
            ImageGUI.getInstance().removeImage(object);
        }

    


        

        public void handleBombPickup(Point2D position) {
            GameObject obj = findObjectAtPosition(position);
            if (obj instanceof Fire) {
                Fire bomb = (Fire) obj;
                bomb.collect();
                collectedBomb = bomb;
                ImageGUI.getInstance().removeImage(bomb);
                gui.setStatusMessage("Manel apanhou a bomba!");
            }
        }

        public void dropCollectedBomb(int currentTick) {
            if (collectedBomb != null) {
                collectedBomb.drop(manel.getPosition(), currentTick);
                addObject(collectedBomb);
                activeBombs.add(collectedBomb); 
                collectedBomb = null; // Limpa a bomba coletada
                gui.setStatusMessage("Bomba largada!");
            }
        }

        public void updateBombs(int currentTick) {
            List<Fire> bombsToExplode = new ArrayList<>();
            for (Fire Fire : activeBombs) {
                if (Fire.shouldExplode(currentTick)) {
                    bombsToExplode.add(Fire);
                }
            }

            for (Fire Fire : bombsToExplode) {
                Fire.explode();
                activeBombs.remove(Fire);
            }
        }

}
    
    
    
    
    
    
    
    
    

   

