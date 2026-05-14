//tramy hoang registered person

import java.io.Serializable;

public class RegisteredPerson extends Person implements Serializable {

    // Required for Serializable classes
    private static final long serialVersionUID = 1L;

    // Stores government-issued identification number
    private String governmentID;

    // Main constructor
    // Creates a RegisteredPerson using name, DOB, and government ID
    public RegisteredPerson(String firstName,
                            String lastName,
                            OCCCDate dob,
                            String governmentID) {

        // Call parent constructor from Person
        super(firstName, lastName, dob);

        // Store government ID
        this.governmentID = governmentID;
    }

    // Conversion constructor
    // Converts a Person into a RegisteredPerson
    public RegisteredPerson(Person p, String governmentID) {

        // Copy Person data into superclass
        super(p);

        // Add government ID
        this.governmentID = governmentID;
    }

    // Copy constructor
    // Creates a deep copy of another RegisteredPerson
    public RegisteredPerson(RegisteredPerson p) {

        // Copy parent class data
        super(p);

        // Copy government ID
        this.governmentID = p.governmentID;
    }

    // Returns government ID
    public String getGovernmentID() {
        return governmentID;
    }

    // Updates government ID
    public void setGovernmentID(String governmentID) {
        this.governmentID = governmentID;
    }

    // Determines if two RegisteredPerson objects are equal
    @Override
    public boolean equals(Object obj) {

        // Make sure object is correct type
        if (!(obj instanceof RegisteredPerson)) {
            return false;
        }

        RegisteredPerson p = (RegisteredPerson) obj;

        // Compare inherited fields and government ID
        return super.equals(p) &&
               governmentID.equals(p.governmentID);
    }

    // Returns string representation of object
    @Override
    public String toString() {

        // Append government ID to Person string
        return super.toString() + " [" + governmentID + "]";
    }
}
