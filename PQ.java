import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * Kien Do 300163370
 * CSI2510 - Structures de données et algorithms
 * Devoir Programmation
 *
 * This Priority Queue represents who the employer ranks the highest.
 *
 * Implementation based on the class HeapPriorityQueue from the textbook Data Structures and Algorithms
 *  by Goodrich, page 377.
 */
public class PQ extends PriorityQueue<Pair> {


    /**
     * primary collection of priority queue entries
     */
    protected ArrayList<Pair> heap = new ArrayList<>();

    /**
     * Creates an empty priority queue based on the natural ordering of its keys.
     */
    public PQ() {
        super();
    }

    /**
     * Creates an empty priority queue using the given comparator to order keys.
     */
    public PQ(Comparator<Pair> comp) {
        super(comp);
    }

    // protected utilities
    protected int parent(int j) {
        return (j-1)/2;
    } // truncating division


    protected int left(int j) {
        return 2 * j + 1;
    }

    protected int right(int j) {
        return 2 * j + 2;
    }

    protected boolean hasLeft(int j) {
        return left(j) < heap.size();
    }

    protected boolean hasRight(int j) {
        return right(j) < heap.size();
    }

    /**
     * Exchanges the entries at indices i and j of the array list.
     */
    protected void swap(int i, int j) {
        Pair temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    /**
     * Moves the entry at index j higher, if necessary, to restore the heap property.
     */
    protected void upheap(int j) {
        while (j > 0) { // continue until reaching root (or break statement)
            int p = parent(j);
            //if (compare(heap.get(j), heap.get(p)) >= 0) break; // heap property verified
            if (heap.get(j).getEmployerRanking() >= heap.get(p).getEmployerRanking()) break; // heap property verified
            swap(j, p);
            j = p; // continue from the parent's location

        }
    }

    /**
     * Moves the entry at index j lower, if necessary, to restore the heap property.
     */
    protected void downheap(int j) {
        while (hasLeft(j)) { // continue to bottom (or break statement)
            int leftIndex = left(j);
            int smallChildIndex = leftIndex; // although right may be smaller
            if (hasRight(j)) {
                int rightIndex = right(j);
                //if (compare(heap.get(leftIndex), heap.get(rightIndex)) > 0)
                if (heap.get(leftIndex).getEmployerRanking() >= heap.get(rightIndex).getEmployerRanking())
                    smallChildIndex = rightIndex; // right child is smaller

            }
            if (heap.get(smallChildIndex).getEmployerRanking() >= heap.get(j).getEmployerRanking())
                break; // heap property has been restored
            swap(j, smallChildIndex);
            j = smallChildIndex; // continue at position of the child
        }
    }

    // public methods

    /**
     * Returns the number of items in the priority queue.
     */
    public int size() {
        return heap.size();
    }

    /**
     * Returns (but does not remove) an entry with minimal key (if any).
     */
    @Override
    public Pair peek() {
        if (heap.isEmpty()) return null;
        return heap.get(0);
    }

    /**
     * Inserts a key-value pair and returns the entry created.
     */
    public void insert(int employer_ranking, int student) throws IllegalArgumentException {
        //checkKey(key); // auxiliary key-checking method (could throw exception)
        Pair newest = new Pair(employer_ranking, student);
        heap.add(newest); // add to the end of the list
        upheap(heap.size() - 1); // upheap newly added entry
        //return newest;
    }

    /**
     * Removes and returns an entry with minimal key (if any).
     */
    @Override
    public Pair poll() {
        if (heap.isEmpty()) return null;
        Pair answer = heap.get(0);
        swap(0, heap.size() - 1); // put minimum item at the end
        heap.remove(heap.size() - 1); // and remove it from the list;
        downheap(0); // then fix new root
        return answer;
    }

    /**
     * Removes the minimum Pair and returns the employer ranking (integer).
     * @return The ranking of the employer for the student in this Pair.
     */
    public int removeMin() {
        return Objects.requireNonNull(poll()).getStudent();
    }



}
