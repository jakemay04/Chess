package ui;

public class GameUI {



    private void help() {
        System.out.println("Possible options: ");
        System.out.println("Redraw Board - draw the current board in the terminal");
        System.out.println("Leave - leave the current game");
        System.out.println("Make Move - declare a move against your opponent");
        System.out.println("Resign - forfeit the current game let your opponent win");
        System.out.println("Highlight Legal Moves - redraw the board with the highlighted possible moves");
    }
}
