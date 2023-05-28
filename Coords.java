import java.util.*;
public class Coords implements Cloneable{
    private int x;
    private int y;

    public Coords() {
        x = 0;
        y = 0;
    }

    public Coords(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Coords getXY() {
        return this;
    }

    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public HashSet<Coords> multiply(int[] a, int[] b) {
        HashSet<Coords> c = new HashSet<>();
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b.length; j++) {
                c.add(new Coords(a[i], b[j]));
            }
        }
        return c;
    }

    public boolean isMod2Equal(Coords xy) {
        if (xy.getX() % 2 == xy.getY() % 2)
            return true;
        return false;
    }

    public boolean isAnElementOf(Coords xy, ArrayList<Coords> arr) {
        for (int i = 0; i < arr.size(); i++)
            if (xy.getX() == arr.get(i).getX() && xy.getY() == arr.get(i).getY())
                return true;
            return false;
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Coords other = (Coords) obj;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public Coords clone() throws CloneNotSupportedException {
        Coords c = (Coords)super.clone();
        c.x = x;
        c.y = y;
        return c;
    }
}