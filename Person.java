//Jayden Wyatt
//CS 2463
//Description: This is the parent class.

import java.io.Serializable;

public class Person implements Comparable<Person>, Serializable {
	private static final long serialVersionUID = 1L;
    protected String firstName;
    protected String lastName;
    protected OCCCDate dob;  

    // Constructor
    public Person(String firstName, String lastName, OCCCDate dob) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dob = dob;
    }

    // Copy constructor
    public Person(Person p) {
        this.firstName = p.firstName;
        this.lastName = p.lastName;
        this.dob = new OCCCDate(p.dob);
    }

    // Getters
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public OCCCDate getDOB() { return dob; }

    // Setters
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDOB(OCCCDate dob) { this.dob = dob; }

    //required for sorting
    @Override
    public int compareTo(Person other) {
        int last = this.lastName.compareToIgnoreCase(other.lastName);
        if (last != 0) return last;

        int first = this.firstName.compareToIgnoreCase(other.firstName);
        if (first != 0) return first;

        return this.dob.compareTo(other.dob); // uses your OCCCDate
    }

    // Proper equals override
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Person)) return false;
        Person p = (Person) obj;
        return firstName.equalsIgnoreCase(p.firstName)
            && lastName.equalsIgnoreCase(p.lastName)
            && dob.equals(p.dob);
    }

    @Override
    public String toString() {
        return lastName + ", " + firstName + " (" + dob + ")";
    }

    // Extra methods (unchanged)
    public void eat() {
        System.out.println(getClass().getName() + " " + toString() + " is eating!");
    }

    public void sleep() {
        System.out.println(getClass().getName() + " " + toString() + " is sleeping!");
    }

    public void play() {
        System.out.println(getClass().getName() + " " + toString() + " is playing.");
    }

    public void run() {
        System.out.println(getClass().getName() + " " + toString() + " is running.");
    }
}