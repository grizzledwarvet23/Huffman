import java.util.ArrayList;

public class PriorityQueue314<E extends Comparable<E>> {
    private ArrayList<E> con;

    public PriorityQueue314() {
        con = new ArrayList<>();
    }

    public void enqueue(E item) {
        con.add(findPos(item), item);
    }

    public E dequeue() {
        return con.isEmpty() ? null : con.remove(0);
    }

    public E top() {
        return con.isEmpty() ? null : con.get(0);
    }

    public int size() {
        return con.size();
    }

    private int findPos(E target) {
        int low = 0;
        int high = con.size() - 1;
        while (low <= high) {
            int mid = low + ((high - low) / 2);
            int compVal = con.get(mid).compareTo(target);
            if (compVal == 0) {
                while(mid < con.size() && (con.get(mid).compareTo(target) == 0)) {
                    mid++;
                }
                //one above the last duplicate
                return mid;
            }
            else if (compVal < 0)
                low = mid + 1;
            else
                high = mid - 1;
        }
        return low;
    }



}
