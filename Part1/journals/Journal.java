// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package journals;

import magazines.Magazine;
import publications.PaperPublication;

public class Journal extends PaperPublication {
	private int issueNumber;
	private String specialityType;

	public Journal(){
		this.issueNumber = 0;
		this.specialityType = "";
	}

	public Journal(double price, int numberOfPages, int issueNumber, String specialityType){
		super.setPrice(price);
		super.setNumberOfPages(numberOfPages);

		this.issueNumber = issueNumber;
		this.specialityType = specialityType;
	}

	public Journal(Journal journal){
		this(journal.getPrice(), journal.getNumberOfPages(), journal.issueNumber, journal.specialityType);
	}

	public String toString(){
		return "This journal has "+super.getNumberOfPages()+" pages and costs "+super.getPrice()
		+"$.\nThe issue number is "+this.issueNumber+" and the speciality type is "+this.specialityType;
	}

	public boolean equals(Object journal){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(journal == null || this == null || this.getClass() != journal.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the Journal being compared is just an object
			Journal comparingJournal = (Journal) journal;

			return super.getPrice() == comparingJournal.getPrice() && super.getNumberOfPages() == comparingJournal.getNumberOfPages()
					&& this.issueNumber == comparingJournal.issueNumber && this.specialityType == comparingJournal.specialityType;
		}
	}
}
