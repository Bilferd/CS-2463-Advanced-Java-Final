//tramy hoang registered person

import java.io.Serializable;

public class RegisteredPerson extends Person implements Serializable{
    
    private String governmentID;

    // full constructor
    public RegisteredPerson(String firstName, String lastName, String governmentID) {
        super(firstName, lastName);
        this.governmentID = governmentID;
    }

    // constructor using existing Person
    public RegisteredPerson(Person p, String governmentID) {
        super(p);
        this.governmentID = governmentID;
    }

    // copy constructor
    public RegisteredPerson(RegisteredPerson p) {
        super(p);
        this.governmentID = p.governmentID;
    }

    
    // getter
    public String getGovernmentID() {
        return governmentID;
    }

    // setter
    public void setGovernmentID(String governmentID) {
        this.governmentID = governmentID;
    }

    // toString
    @Override
    public String toString() {
        return super.toString() + " [" + governmentID + "]";
    }
}
