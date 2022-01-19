import java.io.*;
import java.util.Scanner;
import java.lang.*;

/**
 * Kien Do 300163370
 * CSI2510 - Structures de données et algorithms
 *
 * Devoir Programmation
 */

public class GaleShapley {

    /* ============== Attributes ============== */

    // variables globales
    public static Sue sue = new Sue(); // stack of unmatched employers

    // information on students and employers
    public static int n; // number of employers (and subsequently, students)

    public static String[] studentNames;
    public static String[] employerNames;
    public static int[] students; // represents the students' rankings of the employers
    public static int[] employers; // represents the employers' rankings of the students

    // 2D matrix A[n][n] with A[s][e] representing the rank given by the student s to employer e
    public static int[][] A;

    // queue[i] represents employer[i]'s rankings of the students
    public static PQ[] queueEmployer;


    /* ============== Helper methods ============== */

    /**
     * Sets total students/employers.
     * Reads the first line (line 0) of the provided file in order to find out the total number of students/employers.
     * n is the number found on the first line (line 0) of the file and is initialized in the constructor.
     */
    public static void setTotalStudentsFromFile(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);

            n = Integer.parseInt(read.nextLine()); // reads and saves the first line into an integer variable

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Sets employers' names.
     * Reads n lines starting from the second line (line 1) of the file.
     * n is the number found on the first line (line 0) of the file and is initialized in the constructor.
     */
    public static void setEmployersNamesFromFile(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);

            read.nextLine(); // skip first line of the file
            int lineCounter = 0;

            while (read.hasNextLine() && lineCounter < n) {
                employerNames[lineCounter++] = read.nextLine(); // add the employer names to the data structure
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Sets students' names.
     * Reads n lines starting from line 1 + n of the file.
     * n is the number found on the first line (line 0) of the file and is initialized in the constructor.
     */
    public static void setStudentsNamesFromFile(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);

            read.nextLine(); // skip first line of the file
            for (int i = 0; i < n; i++) {
                read.nextLine(); // skip the next n lines in the file
            }

            int lineCounter = 0;

            while (read.hasNextLine() && lineCounter < n) {
                studentNames[lineCounter++] = read.nextLine(); // add the student names to the data structure
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
            e.printStackTrace();
        }
    }

    /**
     * Sets students' rankings from the text file
     */
    public static void setStudentEmployerRankingsFromFile(String fileName) {
        try {
            File f = new File(fileName);
            Scanner read = new Scanner(f);

            read.nextLine(); // skip first line of the file
            for (int i = 0; i < 2 * n; i++) {
                read.nextLine(); // skip the next 2*n lines in the file, thereby skipping all the student and employer names
            }

            // begin to read the matrix of pairs in the text file
            for (int i = 0; i < n; i++) {
                String employerRow = read.nextLine();
                String[] employerStrPairs = employerRow.split(" "); // example: 2,2 1,2 3,3 <- split spaces
                for (int j = 0; j < n; j++) {
                    String[] ranking = employerStrPairs[j].split(","); // example: 1,2 becomes ranking[0] = 1, ranking[1] = 2
                    A[j][i] = Integer.parseInt(ranking[1]); // initialise A[s][e] with student rankings
                    queueEmployer[i].insert(Integer.parseInt(ranking[0]), j); // PQ[e].insert(employer_ranking,s)
                }
            }

            read.close();
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found.");
            e.printStackTrace();
        }
    }

    /* ============== Create file methods ============== */

    /**
     * Creates a new file and adds the file to the project
     *
     * @param fileName Name of the file to be created
     */
    public static void createFile(String fileName) {
        try {
            File f = new File(fileName);
            if (f.createNewFile()) {
                System.out.println("File created");
            } else {
                System.out.println("File already exists");
            }
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred at createFile() in file handling functions");
            e.printStackTrace();
        }
    }

    /**
     * Writes text in the form of a String to an existing file
     *
     * @param fileName Name of the file to be written to
     * @param data     A String of data to be written to the file
     */
    public static void writeToFile(String fileName, String data) {
        try {
            FileWriter w = new FileWriter(fileName, true); // Help on how to add to file here: https://stackoverflow.com/a/1625266
            w.write(data + "\n");
            w.close();
            System.out.println("Data added successfully.");
        } catch (IOException e) {
            System.out.println("ERROR: An error occurred at writeToFile() in file handling functions");
            e.printStackTrace();
        }
    }

    /* ============== Required methods ============== */

    public static void initialize(String filename) {
        // n = first number in the text file
        setTotalStudentsFromFile(filename);
        System.out.println("Total students is: " + n);

        // initialize class attributes
        studentNames = new String[n];
        employerNames = new String[n];
        students = new int[n];
        employers = new int[n];
        A = new int[n][n];
        queueEmployer = new PQ[n];

        // ======== initialisation ========

        for (int i = 0; i < n; i++) {
            sue.push(i); // mets tous les employeurs dans cette pile en commençant avec employeur[0]
            queueEmployer[i] = new PQ(); // initialise PQ[i]
        }

        // initialize the tables that pair the students and employers together
        for (int i = 0; i < n; i++) {
            students[i] = -1;
            employers[i] = -1;
        }

        // initialize students[] and employers[]
        setStudentEmployerRankingsFromFile(filename);

        // initialize student and employer names
        setEmployersNamesFromFile(filename);
        setStudentsNamesFromFile(filename);

    } // end of initialize()

    public static void execute() {
        // procedure
        int e = 0;
        int s = 0;
        int eN = 0;
        while (!sue.empty()) {
            e = sue.pop(); // employer # who is looking for a student
            s = queueEmployer[e].removeMin(); // gets the most preferred student of employer #
            eN = students[s]; // gets the employer who is matched with the current student;
            // -1 means no matched employer
            if (students[s] == -1) {
                students[s] = e;
                employers[e] = s; // match (currEmployer, currStudent)
            }
            // current student prefers current employer to matched employer
            else if (A[s][e] < A[s][eN]) {
                students[s] = e;
                employers[e] = s; // replace the match
                employers[eN] = -1; // unmatch the previously matched employer
                sue.push(eN); // push the previously matched employer back into the stack of unmatched employers
            }
            // current student rejects offer from current employer
            else {
                sue.push(e);
            }
        } // end of while loop

    } // end of execute()

    public static void save(String filename) {

        // create a new file and write the number of students/employers into the first line
        String newFileName = "matches_" + filename;
        createFile(newFileName);

        // write the stable matches in employers[] and students[] into the newly created file
        //  write everything to a string then write that string to the new file
        String textFinal = "";
        for (int i = 0; i < n; i++) {
            textFinal += "Match " + i + ": " + employerNames[i] + " - " + studentNames[employers[i]] + "\n";
        }

        writeToFile(newFileName, textFinal);

    } // end of save()


    /* ============== Main method to test GaleShapley.java ============== */
    public static void main(String[] args) {

        // takes the user's input
        System.out.println("SVP entrez le nom du fichier, en incluant son extension (ex: name_N10.txt)");
        System.out.print("Nom du fichier: ");
        Scanner scanner = new Scanner(System.in);
        String fileNameInput = scanner.nextLine();

        initialize(fileNameInput);

        execute();

        save(fileNameInput);


    } // end of main
}
