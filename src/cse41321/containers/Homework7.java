package cse41321.containers;

import java.util.Comparator;

public class Homework7 {

    static class Person {
        String name;
        int age;
        double height;

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

    public static class Heap<E> {
        Object[] tree;  // Left-balanced binary tree

        private Comparator<? super E> comparator;

        public Heap(Comparator<? super E> comparator) {
            this.comparator = comparator;
        }

        public int getSize() {
            return tree == null ? 0 : tree.length;
        }

        public boolean isEmpty() {
            return getSize() == 0;
        }

        @SuppressWarnings("unchecked")
        public void insert(E data) {
            // Grow tree by 1 to hold new node
            Object[] temp;
            if (tree == null) {
                temp = new Object[1];
            } else {
                temp = new Object[tree.length + 1];
                System.arraycopy(tree, 0, temp, 0, tree.length);
            }
            tree = temp;

            // Insert data as right-most node in last level
            tree[getSize() - 1] = data;

            // Push node upward to correct position
            int childIndex = getSize() - 1;
            int parentIndex = getParentIndex(childIndex);
            while (childIndex > 0
                    && comparator.compare(
                    (E) tree[parentIndex],
                    (E) tree[childIndex]) < 0) {
                // Parent and child out of order, swap
                swapNodes(parentIndex, childIndex);

                // Move up one level in the tree
                childIndex = parentIndex;
                parentIndex = getParentIndex(parentIndex);
            }
        }

        @SuppressWarnings("unchecked")
        public E extract() throws IllegalStateException {
            if (isEmpty()) {
                throw new IllegalStateException("Cannot extract from empty heap");
            }

            // Extract data at top of heap
            E extracted = (E) tree[0];

            // If extracting last node, set tree to empty and return extracted data
            if (getSize() == 1) {
                tree = null;
                return extracted;
            }

            // Save and remove right-most node from last level
            E save = (E) tree[tree.length - 1];
            Object[] temp = new Object[tree.length - 1];
            System.arraycopy(tree, 0, temp, 0, tree.length - 1);
            tree = temp;

            // Set saved node as the new root
            tree[0] = save;

            // Push root down to correct position
            int parentIndex = 0;
            while (true) {
                int leftChildIndex = getLeftChildIndex(parentIndex);
                int rightChildIndex = getRightChildIndex(parentIndex);

                // Determine whether parent, left child, or right child is largest
                int maxIndex = parentIndex;
                if (leftChildIndex < tree.length && comparator.compare(
                        (E) tree[leftChildIndex],
                        (E) tree[maxIndex]) > 0) {
                    maxIndex = leftChildIndex;
                }
                if (rightChildIndex < tree.length && comparator.compare(
                        (E) tree[rightChildIndex],
                        (E) tree[maxIndex]) > 0) {
                    maxIndex = rightChildIndex;
                }

                // If parent is largest, root has been pushed to correct position
                if (parentIndex == maxIndex) {
                    break;
                } else {
                    // Parent and a child out of order, swap with larger child
                    swapNodes(parentIndex, maxIndex);

                    // Move down one level in the tree
                    parentIndex = maxIndex;
                }
            }

            return extracted;
        }

        private static int getParentIndex(int childIndex) {
            return (childIndex - 1) / 2;
        }

        private static int getLeftChildIndex(int parentIndex) {
            return parentIndex * 2 + 1;
        }

        private static int getRightChildIndex(int parentIndex) {
            return parentIndex * 2 + 2;
        }

        private void swapNodes(int index1, int index2) {
            Object temp = tree[index1];
            tree[index1] = tree[index2];
            tree[index2] = temp;
        }


    }

    public static void outputSorted(Person[] people, Comparator<Person> comparator) {
        Heap<Person> heap = new Heap<>(comparator);
        for (Person person : people) heap.insert(person);
        while (!heap.isEmpty()) {
            System.out.println(heap.extract());
        }
    }

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

}
