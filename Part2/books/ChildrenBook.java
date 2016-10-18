// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package books;

public class ChildrenBook extends Book {
	private int minimumAge;
	
	public ChildrenBook(){
		this.minimumAge = 0;
	}
	
	public ChildrenBook(double price, int numberOfPages, long ISBN, int issueYear, String title, String authorName, int minimumAge){
		super.setPrice(price);
		super.setNumberOfPages(numberOfPages);
		super.setISBN(ISBN);
		super.setIssueYear(issueYear);
		super.setTitle(title);
		super.setAuthorName(authorName);
		
		this.minimumAge = minimumAge;
	}
	
	public ChildrenBook(ChildrenBook book){
		this(book.getPrice(), book.getNumberOfPages(), book.getISBN(), book.getIssueYear(), book.getTitle(), book.getAuthorName(), book.minimumAge);
	}
	
	public String toString(){
		return "This children book is titled "+this.getTitle()+" and was written by "+this.getAuthorName()+". "
				+"\nIt is suitable for age "+this.minimumAge+ " and up."
				+ "\nThis book has "+this.getNumberOfPages()+" pages and costs "+this.getPrice()
				+"$.\nThe ISBN "+this.getISBN()+" and the issue year is "+this.getIssueYear()+".";
	}
	
	public boolean equals(Object childrenBook){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(childrenBook == null || this == null || this.getClass() != childrenBook.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the ChildrenBook being compared is just an object
			ChildrenBook comparingBook = (ChildrenBook) childrenBook;
			
			return this.getPrice() == comparingBook.getPrice() && this.getNumberOfPages() == comparingBook.getNumberOfPages()
					&& this.getISBN() == comparingBook.getISBN() && this.getIssueYear() == comparingBook.getIssueYear()
					&& this.getAuthorName() == comparingBook.getAuthorName() && this.getTitle() == comparingBook.getTitle()
					&& this.minimumAge == comparingBook.minimumAge;
		}
	}
}

