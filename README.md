# Data Structures and Algorithms in Java (CSE-41321)
## University of California San Diego Extension
## Summer 2021 (157162)
## Raymond Mitchell III (Instructor)
## Assignment #7
### Heaps
### Problem Statement
Implement a method called *outputSorted* which utilizes a **Heap** to sort an array of **Person** objects. An object of
type **Person** is defined as follows:
```java
class Person {
    String name;
    int age;
    double height;
}
```
The *outputSorted* method expects two arguments; an array of **Person** objects, and a **Comparator**.
```java
    outputSorted(Person[] person, Comparator<Person> comparator)
```
It will use the
**Comparator** parameter to insert the **Person** objects into a **Heap** in the prescribed order. Then it extracts the
**Person** objects from the **Heap** and lists them on the console. The original array of **Person** objects is not 
modified.

The _outputSorted_ method must be able to display the contents of an array of **Person** objects in order by _name_, 
_age_ or _height_.
### Implementation
The first thing I decided was that the **Person** class needed a little sprucing up. Toward that end, I added 
accessor and mutator methods, as well as a *toString* method. The result was as follows:
```java
static class Person {
    private String name;
    private int age;
    private double height;

    public Person(String name, int age, double height) {
        this.name = name;
        this.age = age;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }
}

```
Probably a bit of overkill for this exercise, but I definitely needed the constructor, accessor and *toString* methods.

Next, I implemented the *outputSorted* method. It was fairly straightforward, and was composed of only a few lines:
```java
public static void outputSorted(Person[] people, Comparator<Person> comparator) {
    Heap<Person> heap = new Heap<>(comparator);
    for (Person person : people) heap.insert(person);
    while (!heap.isEmpty()) {
        System.out.println(heap.extract());
    }
}

```
Next I created a suite of unit tests. Once again I elected to use the TestNG framework. Conspicuously absent from the
suite were any tests of the **Person** class's methods!? I'm not sure what happened there.
```java
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

```
It was during development of the unit tests that I established the convention of passing the second argument of the 
*outputSorted* method as an anonymous inner class. I hope I'm correctly identifying that construct. Anyway, whatever 
it is, it stuck.

It is the **Comparator** that controls the behavior of the *outputSorted* method (i.e., the sort order). However, I
noticed that the algorithms I developed for the *compare* method seemed counterintuitive. The logic seemed inverted.
Considering how a heap works, I guess it makes sense.

To provide a user interface, I created the **Driver** class. The **Driver** class contains a *main* method which 
calls the *outputSorted* method several times to sort an array of **Person** objects either by name, age or height.
```java
    public static class Driver {

        public static void main(String[] arguments) {
            Person[] people = new Person[9]; // A 9-element array of Person objects
            people[0] = new Homework7.Person("Karsten", 25, 74);
            people[1] = new Homework7.Person("Dalilah", 31, 68);
            people[2] = new Homework7.Person("Andre", 26, 70);
            people[3] = new Homework7.Person("Mary", 66, 68);
            people[4] = new Homework7.Person("Grant", 23, 74);
            people[5] = new Homework7.Person("Simone", 24, 56);
            people[6] = new Homework7.Person("Gabrielle", 24, 70);
            people[7] = new Homework7.Person("Kathryn", 30, 68);
            people[8] = new Homework7.Person("Femke", 21, 72);
            System.out.println("\n… sorted by name:\n");
            outputSorted(people,
                    new Comparator<>() {
                        @Override
                        public int compare(Homework7.Person thisPerson, Homework7.Person thatPerson) {
                            return thatPerson.getName().compareTo(thisPerson.getName());
                        }
                    });
            System.out.println("\n… sorted by age:\n");
            outputSorted(people,
                    new Comparator<>() {
                        @Override
                        public int compare(Homework7.Person thisPerson, Homework7.Person thatPerson) {
                            return thatPerson.getAge() - thisPerson.getAge();
                        }
                    });
            System.out.println("\n… sorted by height:\n");
            outputSorted(people,
                    new Comparator<>() {
                        @Override
                        public int compare(Person thisPerson, Person thatPerson) {
                            if (thisPerson.getHeight() < thatPerson.getHeight()) {
                                return 1;
                            } else if (thisPerson.getHeight() > thatPerson.getHeight()) {
                                return -1;
                            }
                            return 0;
                        }
                    });
        }
    }

```
### Deliverables
#### Homework7
```java

```
#### Homework7Test
```java

```
### Epilogue
This project went relatively quickly. Considering that I waited until the last minute to get started, I got lucky.
What's really pathetic is that I had extra time to work on this project (two weeks instead of one) and I didn't take
advantage of it. During that time, I *did* move from one city to another, and started a new job, but those are just
excuses. Simply put, I procrastinated. This could have been a real disaster. However, after watching the lecture
twice and doing some supplemental research, I found the assignment fairly intuitive.