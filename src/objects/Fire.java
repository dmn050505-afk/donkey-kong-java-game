// Fire.java
package objects;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageGUI;
import pt.iscte.poo.game.Room;

import java.util.ArrayList;
import java.util.List;


public class Fire extends GameObject {

 private int startTick; // Tick em que a bomba foi largada
 private boolean isDropped; // Indica se a bomba foi largada
 private Room room;
 private ImageGUI gui = ImageGUI.getInstance();

 public Fire(Point2D position, Room room) {
     super(position);
     this.startTick = -1; // Não está ativa inicialmente
     this.isDropped = false;
     this.room = room;
 }

 @Override
 public String getName() {
     return "Fire";
 }

 @Override
 public int getLayer() {
     return 1; // Camada gráfica
 }

 public void collect() {
     setPosition(null); // Remove a bomba do mapa
 }

 public void drop(Point2D position, int currentTick) {
     setPosition(position);
     this.isDropped = true;
     this.startTick = currentTick; // Registra o tick inicial
 }

 public boolean isDropped() {
     return isDropped;
 }

 public boolean shouldExplode(int currentTick) {
     return isDropped && startTick > 0 && (currentTick - startTick) >= 5;
 }

 public void explode() {
     gui.setStatusMessage("Boom! A bomba explodiu!");
     for (Point2D point : getExplosionArea()) {
         if (room.isWall(point) || room.isStairs(point)) continue; // Ignora paredes e escadas

         GameObject obj = room.findObjectAtPosition(point);
         if (obj != null) {
             room.removeObject(obj);
         }
     }

     if (room.isManelAtPosition(getPosition()) || room.isManelNear(getExplosionArea())) {
         gui.setStatusMessage("Manel morreu na explosão!");
         room.restartLevel(); // Reinicia o nível se Manel for atingido
     }

     room.removeObject(this); // Remove a bomba da sala
 }

 private List<Point2D> getExplosionArea() {
     List<Point2D> area = new ArrayList<>();
     Point2D position = getPosition();
     area.add(position);
     area.addAll(position.getNeighbourhoodPoints());
     return area;
 }
}
