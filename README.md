# Java-Chess
Final project for subject PJV (Programming in Java) of CTU FEE.

# Theme of project is Chess game.

Main functions:
1. Singleplayer
2. Multiplayer
3. Continue loaded game
4. Timer

# Chess manual
1) Press **Start** and choose type of game (**Singleplayer** or **Multiplayer**)
2) Type your name
3) Play the game: click on piece to choose it and click on another square to make move.

# Documentation
Project has MVC structure:

Start - main runnable class, create Controller

Controller - class, which controlls View and Model classes

view package
- View (extends Application) - GUI, visualisat of game
- MenuScene (extends Scene) - makes Menu layout Scene
- BoardScene (extends Scene) - makes chess board layout Scene (main playground Scene)

model package
- Model - main Logic class, controlls all data classes
- Player - representation of player
- ComputerPlayer - class, which extendst Player class and represents Computer move, when player playes single game.
- Squares - boxes on Chess board
- Board - representation of chess board

model.piece package
- Piece - abstract class for representation chess pieces
- BishopPiece - representation of Bishop piece
- KingPiece - representation of King piece
- KnightPiece - representation of Knight piece
- PawnPiece - representation of Pawn piece
- QueenPiece - representation of Queen piece
- RookPiece - representatnion of Rook piece

Project [UML diagram](uploads/1c551029dc599cc93fee1c78244c2faf/Chess__1_.pdf)
