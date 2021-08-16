package cse41321.containers;

import org.testng.annotations.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import static org.testng.Assert.*;

public class Homework7Test {

    private final PrintStream originalStdOut = System.out;
    private ByteArrayOutputStream consoleContent = new ByteArrayOutputStream();
    private Homework7.Person[] people;

    @org.testng.annotations.BeforeMethod
    public void setUp() {
        System.setOut(new PrintStream(this.consoleContent));
        Homework7.Person[] people = {new Homework7.Person("Karsten", 25, 74),
                new Homework7.Person("Dalilah", 31, 68),
                new Homework7.Person("William", 68, 83),
                new Homework7.Person("Simone", 24, 56),
                new Homework7.Person("Gabrielle", 24, 70),
                new Homework7.Person("Femke", 21, 72)};
    }

    @org.testng.annotations.AfterMethod
    public void tearDown() {
        System.setOut(this.originalStdOut);     // Restore original standard out
        // Clear the consoleContent.
        this.consoleContent = new ByteArrayOutputStream();
    }

    @Test
    public void outputSortedByNameAscending() {
        Homework7.outputSorted(people, new Comparator<Homework7.Person>() {
            public int compare(Homework7.Person p1, Homework7.Person p2) {
                return p1.getName().compareTo(p2.getName());
            }
        });
        assertTrue(this.consoleContent.toString().contains("Dalilah"));
    }

    @Test
    public void outputSortedByAgeAscending() {

    }

    @Test
    public void outputSortedByHeightAscending() {

    }
}