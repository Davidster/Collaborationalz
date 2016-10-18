// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package magazines;

import publications.PaperPublication;

public class Magazine extends PaperPublication{
	public enum PaperQuality {High, Normal, Low};
	public enum IssuingFrequency {Weekly, Monthly, Yearly};
	private PaperQuality paperQuality;
	private IssuingFrequency issuingFrequency;

	public Magazine(){
		this.paperQuality = PaperQuality.Normal;
		this.issuingFrequency = IssuingFrequency.Monthly;
	}

	public Magazine(double price, int numberOfPages, PaperQuality quality, IssuingFrequency frequency){
		super.setPrice(price);
		super.setNumberOfPages(numberOfPages);

		this.paperQuality = quality;
		this.issuingFrequency = frequency;
	}

	public Magazine(Magazine magazine){
		this(magazine.getPrice(), magazine.getNumberOfPages(), magazine.paperQuality, magazine.issuingFrequency);
	}

	public String toString(){
		return "This magazine has "+super.getNumberOfPages()+" pages and costs "+super.getPrice()
		+"$.\nThe paper quality is "+this.paperQuality+" and the issuing frequency is "+this.issuingFrequency;
	}

	public boolean equals(Object magazine){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(magazine == null || this == null || this.getClass() != magazine.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the Magazine being compared is just an object
			Magazine comparingMagazine = (Magazine) magazine;

			return super.getPrice() == comparingMagazine.getPrice() && super.getNumberOfPages() == comparingMagazine.getNumberOfPages()
					&& this.paperQuality == comparingMagazine.paperQuality && this.issuingFrequency == comparingMagazine.issuingFrequency;
		}
	}
}
