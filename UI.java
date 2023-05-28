public class UI {
    char[][] board;
    Game game;

    // ONLY FOR TEXT COLORING
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_WHITE = "\u001B[37m";

    public UI(Game game) {
        board = new char[5][7];
        this.game = game;
    }

    public void updateBoard() {
        for (Coords xy: game.P) {
            if (game.Alpha.contains(xy)) {setTile(xy, 'A'); continue;}
            if (game.Beta.contains(xy)) {setTile(xy, 'B'); continue;}
            setTile(xy, 'O');
        }
    }
    public void printBoard() {
        int i = 1;
        System.out.println(TEXT_YELLOW + " 1 2 3 4 5 6 7" + TEXT_RESET);
        for (char[] row: board) {
            System.out.println(TEXT_YELLOW + i + TEXT_RESET + " " +
            RowToString(row));
            i++;
        }
    }
    public String RowToString(char[] arr) {
    String s = "";
    for (char c: arr)
        s += (c == 'A' 
                ? TEXT_RED 
                : (c == 'B' 
                        ? TEXT_BLUE
                        : TEXT_WHITE))
            + c + TEXT_RESET + " ";
        return s;
    }
    
    public void setTile(Coords xy, char c) {
        board[xy.getY()-1][xy.getX()-1] = c; // y then x, for the horizontal layout
    }
}
    