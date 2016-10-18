// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

package books;

import publications.PaperPublication;

public class Book extends PaperPublication {
	/*
	 * COMMENT ON THE DECISION ABOUT USING RESTRICTED ACCES (I.E. WHAT ARE THE TRADE OFFS)
	 * By changing these attributes from protected to private, they cannot be called by subclasses 
	 * by their name. They must now be called using getters and setters provided by this parent class.
	 */
	private long ISBN;
	private int issueYear;
	private String title, authorName;
	
	public Book(){
		this.ISBN = 0;
		this.issueYear = 0;
		this.title = "";
		this.authorName = "";
	}
	
	public Book(double price, int numberOfPages, long ISBN, int issueYear, String title, String authorName){
		super.setPrice(price);
		super.setNumberOfPages(numberOfPages);
		
		this.ISBN = ISBN;
		this.issueYear = issueYear;
		this.title = title;
		this.authorName = authorName;
	}
	
	public Book(Book book){
		this(book.getPrice(), book.getNumberOfPages(), book.ISBN, book.issueYear, book.title, book.authorName);
	}
	
	public long getISBN(){
		return this.ISBN;
	}
	
	public void setISBN(long ISBN){
		this.ISBN = ISBN;
	}
	
	public int getIssueYear(){
		return this.issueYear;
	}
	
	public void setIssueYear(int issueYear){
		this.issueYear = issueYear;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getAuthorName(){
		return this.authorName;
	}
	
	public void setAuthorName(String authorName){
		this.authorName = authorName;
	}
	
	public String toString(){
		return "This book is titled "+this.title+" and was written by "+this.authorName+". "
				+ "\nThis book has "+super.getNumberOfPages()+" pages and costs "+super.getPrice()
				+"$.\nThe ISBN "+this.ISBN+" and the issue year is "+this.issueYear+".";
	}
	
	public boolean equals(Object book){
		//The first two conditions make sure that we don't get any nullPointerExceptions during runtime and
		//so we can safely check in the third condition if these two objects being compared even belong to the same class.
		if(book == null || this == null || this.getClass() != book.getClass())
			return false;
		else{
			//Necessary downcasting because to the compiler the Book being compared is just an object
			Book comparingBook = (Book) book;
			
			return this.getPrice() == comparingBook.getPrice() && this.getNumberOfPages() == comparingBook.getNumberOfPages()
					&& this.ISBN == comparingBook.ISBN && this.issueYear == comparingBook.issueYear
					&& this.authorName == comparingBook.authorName && this.title == comparingBook.title;
		}
	}
}