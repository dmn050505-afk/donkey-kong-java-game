# Donkey Kong Java Game

A 2D platform game inspired by the classic Donkey Kong, developed in Java using Object-Oriented Programming principles and design patterns.

The player's objective is to rescue the princess by completing three challenging levels while avoiding enemies, overcoming obstacles, and collecting useful power-ups.

## Features

* Three playable levels
* Princess rescue objective
* Multiple enemy types:

  * Bananas
  * Bats
* Health system
* Collectable items and power-ups:

  * Food for health restoration
  * Sword for increased attack power
  * Fire bombs that can be thrown at enemies
* Interactive obstacles and traps
* Collision detection system
* Level progression
* Graphical user interface
* Observer Pattern implementation

## Technologies

* Java
* Object-Oriented Programming (OOP)
* Observer Design Pattern
* IntelliJ IDEA

## Project Structure

```text
src/
├── objects/
│   ├── Banana.java
│   ├── Bat.java
│   ├── DonkeyKong.java
│   ├── Fire.java
│   ├── GoodMeat.java
│   ├── Princess.java
│   ├── Sword.java
│   └── ...
│
├── pt/iscte/poo/game/
│   ├── Main.java
│   ├── GameEngine.java
│   └── Room.java
│
├── pt/iscte/poo/gui/
│   ├── ImageGUI.java
│   └── ImageTile.java
│
├── pt/iscte/poo/observer/
│   ├── Observer.java
│   └── Observed.java
│
└── pt/iscte/poo/utils/
    ├── Direction.java
    ├── Point2D.java
    └── Vector2D.java

images/
rooms/
```

## Game Elements

### Enemies

* Bats
* Bananas
* Donkey Kong

### Power-Ups

* Sword
* Food
* Fire bombs

### Objectives

* Avoid enemies and traps
* Complete all three levels
* Rescue the princess

## How to Run

1. Open the project in IntelliJ IDEA.
2. Configure a valid JDK.
3. Run:

```java
Main.java
```

located in:

```text
src/pt/iscte/poo/game/Main.java
```

## Screenshots

### Level 1

<img width="720" height="795" alt="image" src="https://github.com/user-attachments/assets/2d626a49-261d-40c6-9e43-77d366c82a80" />


### Level 2

<img width="722" height="789" alt="image" src="https://github.com/user-attachments/assets/1afa0283-8b20-4d56-86f5-27a3f75f6f0a" />


### Level 3/ Princess Rescue

<img width="716" height="788" alt="image" src="https://github.com/user-attachments/assets/6dfc5eb3-d050-49f8-8287-0b569e661172" />


## Authors

* Daniel Nunes
* Eduardo Alexandre

