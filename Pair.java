import java.util.Comparator;

/**
 * Kien Do 300163370
 * CSI2510 - Structures de données et algorithms
 *
 * Devoir Programmation
 */
public class Pair implements Comparable<Pair> {

    private int employerRanking;
    private int student;

    public Pair() {

    }

    public Pair(int employerRanking, int student) {
        this.employerRanking = employerRanking;
        this.student = student;
    }


    /** =============  Méthodes  ============= */
    public int getEmployerRanking() {
        return employerRanking;
    }

    public void setEmployerRanking(int employerRanking) {
        this.employerRanking = employerRanking;
    }

    public int getStudent() {
        return student;
    }

    public void setStudent(int student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "(" + employerRanking + ", " + student + ")";
    }


    @Override
    public int compareTo(Pair otherEmployer) {
        return Integer.compare(this.employerRanking, otherEmployer.employerRanking);
    }
}