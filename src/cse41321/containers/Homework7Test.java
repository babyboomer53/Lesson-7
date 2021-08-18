package cse41321.containers;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import static org.testng.Assert.*;

public class Homework7Test {

    private final PrintStream originalStdOut = System.out;
    private ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();
    private Homework7.Person[] people = new Homework7.Person[6];

    /*
    The following Strings represent the order in which the Person elements
    should be displayed after they have been sorted accordingly. They will
    be used in the unit tests to compare the output produced by the
    outputSorted method of the Homework7 class.
     */
    final private String alphabeticalOrderByName =
            "Person{name='Dalilah', age=31, height=68.0}\r\n" +
                    "Person{name='Femke', age=21, height=72.0}\r\n" +
                    "Person{name='Gabrielle', age=24, height=70.0}\r\n" +
                    "Person{name='Karsten', age=25, height=74.0}\r\n" +
                    "Person{name='Mary', age=66, height=68.0}\r\n" +
                    "Person{name='Simone', age=24, height=56.0";

    final private String ascendingOrderByAge =
            "Person{name='Femke', age=21, height=72.0}\r\n" +
                    "Person{name='Gabrielle', age=24, height=70.0}\r\n" +
                    "Person{name='Simone', age=24, height=56.0}\r\n" +
                    "Person{name='Karsten', age=25, height=74.0}\r\n" +
                    "Person{name='Dalilah', age=31, height=68.0}\r\n" +
                    "Person{name='Mary', age=66, height=68.0}";

    final private String ascendingOrderByHeight =
            "Person{name='Simone', age=24, height=56.0}\r\n" +
                    "Person{name='Dalilah', age=31, height=68.0}\r\n" +
                    "Person{name='Mary', age=66, height=68.0}\r\n" +
                    "Person{name='Gabrielle', age=24, height=70.0}\r\n" +
                    "Person{name='Femke', age=21, height=72.0}\r\n" +
                    "Person{name='Karsten', age=25, height=74.0}";

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        System.setOut(new PrintStream(this.consoleContent));
        // Initialize the Person array
        people[0] = new Homework7.Person("Karsten", 25, 74);
        people[1] = new Homework7.Person("Dalilah", 31, 68);
        people[2] = new Homework7.Person("Mary", 66, 68);
        people[3] = new Homework7.Person("Simone", 24, 56);
        people[4] = new Homework7.Person("Gabrielle", 24, 70);
        people[5] = new Homework7.Person("Femke", 21, 72);
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        System.setOut(this.originalStdOut);     // Restore original standard out
        // Clear the consoleContent.
        this.consoleContent = new ByteArrayOutputStream();
    }

    @Test
    public void outputSortedByNameAscending() {
        Homework7.outputSorted(people,
                new Comparator<Homework7.Person>() {
                    @Override
                    public int compare(Homework7.Person p1, Homework7.Person p2) {
                        return p2.getName().compareTo(p1.getName());
                    }
                });
        assertTrue(this.consoleContent.toString().contains(alphabeticalOrderByName));
    }

    @Test
    public void outputSortedByAgeAscending() {
        Homework7.outputSorted(people,
                new Comparator<>() {
                    public int compare(Homework7.Person p1, Homework7.Person p2) {
                        return p2.getAge() - p1.getAge();
                    }
                });
        assertTrue(this.consoleContent.toString().contains(ascendingOrderByAge));
    }

    @Test
    public void outputSortedByHeightAscending() {
        Homework7.outputSorted(people,
                new Comparator<>() {
                    public int compare(Homework7.Person p1, Homework7.Person p2) {
                        if (p1.getHeight() < p2.getHeight()) {
                            return 1;
                        } else if (p1.getHeight() > p2.getHeight()) {
                            return -1;
                        }
                        return 0;
                    }
                });
        assertTrue(this.consoleContent.toString().contains(ascendingOrderByHeight));
    }
}