// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package books;

public class EducationalBook extends Book {
	private int editionNumber;
	private String specialityType;

	public EducationalBook(){
		this.editionNumber = 0;
		this.specialityType = "";
	}

	public EducationalBook(double price, int numberOfPages, long ISBN, int issueYear, String title, String authorName, int editionNumber, String specialityType){
		super.setPrice(price);
		super.setNumberOfPages(numberOfPages);
		super.setISBN(ISBN);
		super.setIssueYear(issueYear);
		super.setTitle(title);
		super.setAuthorName(authorName);

		this.editionNumber = editionNumber;
		this.specialityType = specialityType;
	}

	public EducationalBook(EducationalBook book){
		this(book.getPrice(), book.getNumberOfPages(), book.getISBN(), book.getIssueYear(), book.getTitle(), book.getAuthorName(), book.editionNumber, book.specialityType);
	}

	public int getEditionNumber(){
		return this.editionNumber;
	}

	public void setEditionNumber(int editionNumber){
		this.editionNumber = editionNumber;
	}

	public String getSpecialityType(){
		return this.specialityType;
	}

	public void setSpecialityType(String specialityType){
		this.specialityType = specialityType;
	}
	public String toString(){
		return "This educational book is titled "+this.title+" and was written by "+this.authorName+". "
				+"\nThe edition number is "+this.editionNumber+" and the speciality type is "+this.specialityType+"."
				+ "\nThis book has "+super.getNumberOfPages()+" pages and costs "+super.getPrice()
				+"$.\nThe ISBN "+this.ISBN+" and the issue year is "+this.issueYear+".";
	}

	public boolean equals(Object educationalBook){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(educationalBook == null || this == null || this.getClass() != educationalBook.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the EducationalBook being compared is just an object
			EducationalBook comparingBook = (EducationalBook) educationalBook;

			return this.getPrice() == comparingBook.getPrice() && this.getNumberOfPages() == comparingBook.getNumberOfPages()
					&& this.ISBN == comparingBook.ISBN && this.issueYear == comparingBook.issueYear
					&& this.authorName == comparingBook.authorName && this.title == comparingBook.title
					&& this.editionNumber == comparingBook.editionNumber && this.specialityType == comparingBook.specialityType;
		}
	}
}

