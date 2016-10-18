// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package publications;

public class PaperPublication {
	private double price;
	private int numberOfPages;

	public PaperPublication(){
		this.price = 0;
		this.numberOfPages = 0;
	}

	public PaperPublication(double price, int numberOfPages){
		this.price = price;
		this.numberOfPages = numberOfPages;
	}

	public PaperPublication(PaperPublication publication){
		this(publication.price, publication.numberOfPages);
	}

	public double getPrice(){
		return this.price;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public int getNumberOfPages(){
		return this.numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages){
		this.numberOfPages = numberOfPages;
	}

	public String toString(){
		return "This paper publication has "+this.numberOfPages+" pages and costs "+this.price+"$.";
	}

	public boolean equals(PaperPublication publication){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(publication == null || this == null || this.getClass() != publication.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the PaperPublication being compared is just an object
			PaperPublication comparingPublication = (PaperPublication) publication;

			return this.price == comparingPublication.price && this.numberOfPages == comparingPublication.numberOfPages;
		}
	}
}

