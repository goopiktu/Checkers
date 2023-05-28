import java.util.*;
public class Game {
    // APPLICABLE SETS
    int[] R = {1, 2, 3, 4, 5, 6, 7}, C = {1, 2, 3, 4, 5};
    HashSet<Coords> P, S, Y, E;

    // SYSTEM VARIABLES
    HashSet<Coords> Alpha, Beta, Free;
    boolean aTurn;
    boolean over;
    boolean ok;

    // USER INPUT
    UI ui;
    Scanner scan = new Scanner(System.in);
    Coords prev, next;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        SetUp();
        SystemInitialization();
        while (!over) {
            System.out.println("\nPlayer " + (aTurn ? "A" : "B") + "'s Turn");
            System.out.println("Alpha: " + Alpha);
            System.out.println("Beta: " + Beta);
            ui.printBoard();
            System.out.println("\nEnter location of piece (x y): ");
            prev.setXY(scan.nextInt(), scan.nextInt());
            System.out.println("Move this piece to (x y): ");
            next.setXY(scan.nextInt(), scan.nextInt());

            if (P.contains(prev) && P.contains(next)) { // FIXED: Overridden equals() and hashCode()
                NextPlayerMove(prev, next);
                Free = UpdateFree();
                ui.updateBoard();
            } else System.out.println("OUT OF BOUNDS");

            if (Alpha.size() == 0 || Beta.size() == 0) over = true;
        }
        GameOver();
    }
    public void SetUp() {
        Coords manager = new Coords();
        P = new HashSet<>();
        S = new HashSet<>();
        Y = new HashSet<>();
        E = new HashSet<>();
        P = manager.multiply(R, C);
        for (Coords xy : P) {
            if (xy.getX()%2 == xy.getY()%2) {
                S.add(xy);
                if (xy.getX() <= 2)
                Y.add(xy);
                if (xy.getX() >= 6)
                E.add(xy);
            }
        }
    }

    public void SystemInitialization() {
        over = false;
        ok = false;
        aTurn = true;
        Alpha = new HashSet<>(E);
        Beta = new HashSet<>(Y);
        Free = new HashSet<>(UpdateFree());
        ui = new UI(this);
        ui.updateBoard();
        prev = new Coords();
        next = new Coords();
    }

    public HashSet<Coords> UpdateFree() {
        HashSet<Coords> temp = new HashSet<>();
        for (Coords xy : P) {
            if (!(Alpha.contains(xy) || Beta.contains(xy)))
                temp.add(xy);
        }
        return temp;
    }
    public void NextPlayerMove(Coords prev, Coords next) {
    /* prev = (a, b)
    * next = (c, d)
    */
        int a = prev.getX();
        int b = prev.getY();
        int c = next.getX();
        int d = next.getY();
        //System.out.println("("+a+", "+b+") ("+c+", "+d+")");
        if (aTurn
        && Alpha.contains(prev)
        && a == c + 1
        && (d == b || d == b + 1 || b == d + 1)) {
            ok = Not(ok);
        }

        if (!aTurn
        && Beta.contains(prev)
        && c == a + 1
        && (d == b || d == b + 1 || b == d + 1)) {
            ok = Not(ok);
        }

        if (ok
        && aTurn
        && Free.contains(next)) {
           RemoveFrom(Alpha, prev);
           AddTo(Alpha, next);
           endTurn();
        //
        }

        if (ok
        && !aTurn
        && Free.contains(next)) {
            RemoveFrom(Beta, prev);
            AddTo(Beta, next);
            endTurn();
        //
        }

        if (ok
        && aTurn
        && Beta.contains(next)
        && !S.contains(next)) {
            ok = Not(ok);
        //
        }

        if (ok
        && aTurn
        && Beta.contains(next)
        && S.contains(next)) {
            RemoveFrom(Beta, next);
            RemoveFrom(Alpha, prev);
            AddTo(Alpha, next);
            endTurn();
        //
        }

        if (ok
        && !aTurn
        && Alpha.contains(next)
        && !S.contains(next)) {
            ok = Not(ok);
        //
        }
        
        if (ok && !aTurn
        && Alpha.contains(next)
        && S.contains(next)) {
            RemoveFrom(Alpha, next);
            RemoveFrom(Beta, prev);
            AddTo(Beta, next);
            endTurn();
        //
        }
    }

    public void GameOver() {
        String[] result = {"Beta Wins", "Alpha Wins"};

        if (Beta.size() == 0 || Alpha.size() != 0) {
            Alpha.removeIf(e -> P.contains(e));

            if (Alpha.size() == 0) { System.out.println(result[1]); }
            return;
        }

        if (Beta.size() != 0 || Alpha.size() == 0) {
            Beta.removeIf(e -> P.contains(e));
        
            if (Beta.size() == 0) { System.out.println(result[0]); }
        }
    }

    public boolean Not(boolean b) {
        if (b)
            return false;
        return true;
    }

    public static void RemoveFrom(HashSet<Coords> set, Coords xy) {
        try {
            set.remove(xy.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public static void AddTo(HashSet<Coords> set, Coords xy) {
        try {
            set.add(xy.clone());
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
    }

    public void endTurn() {
        aTurn = Not(aTurn);
        ok = Not(ok);
    }
}