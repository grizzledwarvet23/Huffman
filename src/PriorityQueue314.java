/*  Student information for assignment:
 *
 *  On OUR honor, Zayaan Rahman and Aman Rajeshkumar Modi, this programming assignment is OUR
 *  own work
 *  and WE have not provided this code to any other student.
 *
 *  Number of slip days used:
 *
 *  Student 1 (Student whose Canvas account is being used)
 *  UTEID: zer235
 *  email address: zayaanr@utexas.edu
 *  Grader name: Pranav Chandupatla
 *
 *  Student 2
 *  UTEID: arm6433
 *  email address: aman.modi1763@gmail.com
 *
 */

import java.util.ArrayList;

/**
 * A PriorityQueue class that queues the elements based on their priority.
 * A PriorityQueue structure adds elements with priority.
 * The element with the highest priority is at the front of the queue,
 * the lowest priority at the end of the queue.
 * @param <E> must extend comparable in order to determine the element's priority
 */
public class PriorityQueue314<E extends Comparable<E>> {
    private final ArrayList<E> con;

    /**
     * Constructor for the PriorityQueue314 class that initializes
     * the instance variables.
     */
    public PriorityQueue314 () {
        con = new ArrayList<>();
    }

    /**
     * Adds the item E into the priority queue.
     * @param item, the item to insert into the PriorityQueue314
     * pre: item != null
     * post: size = size() + 1
     */
    public void enqueue (E item) {
        //Check preconditions
        if (item == null) {
            throw new IllegalArgumentException("Cannot insert a null value into the" +
                    "PriorityQueue314.");
        }

        con.add(findPos(item), item);
    }

    /**
     * Removes and returns the element at the front of the PriorityQueue314.
     * If there are no elements in the PriorityQueue to dequeue, return
     * null.
     * pre: none
     * post: size = size() - 1, if the size() > 1.
     * @return The element at the front of the queue
     */
    public E dequeue () {
        return con.isEmpty() ? null : con.remove(0);
    }

    /**
     * Return the total size of the PriorityQueue314.
     * pre: none
     * post: none
     * @return The size of the priority-queue.
     */
    public int size () {
        return con.size();
    }

    /**
     * A helper method to find the position to insert an element in the priority-queue.
     * @param target The element to insert. target != null, already checked under the method
     *               using the helper method
     * @return An integer value to indicate where to insert the target element
     */
    private int findPos (E target) {
        int indexToInsert = 0;

        while (indexToInsert < con.size()) {
            int comparisonVal = con.get(indexToInsert).compareTo(target);

            //We know the target element has a lower frequency.
            //The lower the frequency, the higher the priority.
            //This is our place to insert!
            if (comparisonVal > 0) {
                return indexToInsert;
            }

            indexToInsert++;
        }

        return indexToInsert;
    }

}
