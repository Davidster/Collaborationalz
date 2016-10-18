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
		return "This children book is titled "+this.title+" and was written by "+this.authorName+". "
				+"\nIt is suitable for age "+this.minimumAge+ " and up."
				+ "\nThis book has "+super.getNumberOfPages()+" pages and costs "+super.getPrice()
				+"$.\nThe ISBN "+this.ISBN+" and the issue year is "+this.issueYear+".";
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
					&& this.ISBN == comparingBook.ISBN && this.issueYear == comparingBook.issueYear
					&& this.authorName == comparingBook.authorName && this.title == comparingBook.title
					&& this.minimumAge == comparingBook.minimumAge;
		}
	}
}

