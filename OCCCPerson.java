//Jayden Wyatt
//CS 2463
//Description: This is the child class OCCCPerson. It is a child of the Registered Person class. 

import java.io.Serializable;

public class OCCCPerson extends RegisteredPerson implements Serializable {
	private static final long serialVersionUID = 1L;
    private String studentID;

    public OCCCPerson(String firstName, String lastName, OCCCDate dob,
                      String govID, String studentID) {
        super(firstName, lastName, dob, govID);
        this.studentID = studentID;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OCCCPerson)) return false;

        OCCCPerson p = (OCCCPerson) obj;
        return super.equals(p) && studentID.equals(p.studentID);
    }

    @Override
    public String toString() {
        return super.toString() + " [" + studentID + "]";
    }
}