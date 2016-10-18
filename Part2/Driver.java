// -----------------------------------------------------
// Assignment 2
// Part: 1
// Written by: Peter Granitski, ID: 27352611
// -----------------------------------------------------

import books.Book;
import books.ChildrenBook;
import books.EducationalBook;
import publications.PaperPublication;
import magazines.Magazine;
import magazines.Magazine.IssuingFrequency;
import magazines.Magazine.PaperQuality;
import journals.Journal;

public class Driver {
	
	public static PaperPublication[] copyBooks(PaperPublication[] publications){
		//Instantiating variables from each class to be able to compare classes
		PaperPublication publication = new PaperPublication();
		Magazine magazine = new Magazine();
		Journal journal = new Journal();
		Book book = new Book();
		ChildrenBook childrenBook = new ChildrenBook();
		EducationalBook educationalBook = new EducationalBook();
		
		
		PaperPublication[] publicationsCopy = new PaperPublication[publications.length];
		
		for(int i = 0; i < publications.length; i++){
			//Make sure to handle null case first to avoid nullPointerIssues while using getClass()
			if(publications[i] == null)
				publicationsCopy[i] = null;
			else if(publications[i].getClass() == publication.getClass())
				publicationsCopy[i] = new PaperPublication(publications[i]);
			else if(publications[i].getClass() == magazine.getClass())
				publicationsCopy[i] = new Magazine((Magazine)publications[i]);
			else if(publications[i].getClass() == journal.getClass())
				publicationsCopy[i] = new Journal((Journal)publications[i]);
			else if(publications[i].getClass() == book.getClass())
				publicationsCopy[i] = new Book((Book)publications[i]);
			else if(publications[i].getClass() == childrenBook.getClass())
				publicationsCopy[i] = new ChildrenBook((ChildrenBook)publications[i]);
			else if(publications[i].getClass() == educationalBook.getClass())
				publicationsCopy[i] = new EducationalBook((EducationalBook)publications[i]);
		}
		
		return publicationsCopy;
	}

	public static void main(String[] args) {

		Book b1 = new Book(20.43, 304, 449209512, 1985, "Curse Of The Kings", "Victoria Holt");
		Book b2 = new Book(18.99, 225, 1883217210, 2005, "The Real Easy Book, Volume 2", "Chuck Sher, Michael Zisman");
		
		ChildrenBook cb1 = new ChildrenBook(13.63, 42, 978039482, 1971, "The Lorax", "Dr. Seuss", 4);
		ChildrenBook cb2 = new ChildrenBook(11.99, 64, 286759901, 2014, "The Giving Tree", "Shel Silverstein", 4);
		
		EducationalBook eb1 = new EducationalBook(23.84, 568, 879670010, 1988, "Introduction to superstrings", "Michio Kaku", 5, "superstring theories");
		EducationalBook eb2 = new EducationalBook(29.99, 1600, 442019322, 2013, "Learning Python", "Mark Lutz", 5, "programming/computers");
		
		PaperPublication pp1 = new PaperPublication(14.55, 54);
		PaperPublication pp2 = new PaperPublication(11.15, 67);
		
		Magazine m1 = new Magazine(7.95, 30, PaperQuality.Normal, IssuingFrequency.Monthly);
		Magazine m2 = new Magazine(7.77, 55, PaperQuality.High, IssuingFrequency.Weekly);
		
		Journal j1 = new Journal(11.99, 40, 1, "Medicine");
		Journal j2 = new Journal(17.89, 70, 5, "Tech");
		
		PaperPublication[] publications = {b1, b2, cb1, cb2, eb1, eb2, pp1, pp2, m1, m2, j1, j2};
		
		PaperPublication[] publicationsCopy = Driver.copyBooks(publications);
		
		//Displaying content of both arrays
		for(int i = 0; i < publications.length; i++){
			System.out.println("Original array at index "+i+":\n"+publications[i]+"\n");
			System.out.println("Copied array at index "+i+":\n"+publicationsCopy[i]+"\n\n");
		}
		//Copying works, as expected
	}

}
