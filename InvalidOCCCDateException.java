//Jayden Wyatt
//CS 2463
//Description: This is my Custom Exception. 

import java.io.Serializable;

public class InvalidOCCCDateException extends IllegalArgumentException implements Serializable {
	private static final long serialVersionUID = 1L;
	public InvalidOCCCDateException(String message){
		super(message);
	}
}