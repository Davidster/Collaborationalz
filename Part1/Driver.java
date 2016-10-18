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

	public static void main(String[] args) {

		Book b1 = new Book(20.43, 304, 449209512, 1985, "Curse Of The Kings", "Victoria Holt");
		Book b2 = new Book(18.99, 225, 1883217210, 2005, "The Real Easy Book, Volume 2", "Chuck Sher, Michael Zisman");
		Book b3 = new Book(20.43, 304, 449209512, 1985, "Curse Of The Kings", "Victoria Holt");
		
		ChildrenBook cb1 = new ChildrenBook(13.63, 42, 978039482, 1971, "The Lorax", "Dr. Seuss", 4);
		ChildrenBook cb2 = new ChildrenBook(11.99, 64, 286759901, 2014, "The Giving Tree", "Shel Silverstein", 4);
		ChildrenBook cb3 = new ChildrenBook(11.99, 64, 286759901, 2014, "The Giving Tree", "Shel Silverstein", 4);
		
		EducationalBook eb1 = new EducationalBook(23.84, 568, 879670010, 1988, "Introduction to superstrings", "Michio Kaku", 5, "superstring theories");
		EducationalBook eb2 = new EducationalBook(29.99, 1600, 442019322, 2013, "Learning Python", "Mark Lutz", 5, "programming/computers");
		EducationalBook eb3 = new EducationalBook(23.84, 568, 879670010, 1988, "Introduction to superstrings", "Michio Kaku", 5, "superstring theories");
		
		PaperPublication pp1 = new PaperPublication(14.55, 54);
		PaperPublication pp2 = new PaperPublication(11.15, 67);
		PaperPublication pp3 = new PaperPublication(14.55, 54);
		
		Magazine m1 = new Magazine(7.95, 30, PaperQuality.Normal, IssuingFrequency.Monthly);
		Magazine m2 = new Magazine(7.77, 55, PaperQuality.High, IssuingFrequency.Weekly);
		Magazine m3 = new Magazine(7.77, 54, PaperQuality.Low, IssuingFrequency.Yearly);
		Magazine m4 = new Magazine(7.77, 54, PaperQuality.Low, IssuingFrequency.Yearly);
		
		Journal j1 = new Journal(11.99, 40, 1, "Medicine");
		Journal j2 = new Journal(17.89, 70, 5, "Tech");
		Journal j3 = new Journal(17.89, 70, 5, "Tech");
		
		//Testing toString methods
		System.out.println(b1+"\n");
		System.out.println(b2+"\n");
		System.out.println(cb1+"\n");
		System.out.println(cb2+"\n");
		System.out.println(eb1+"\n");
		System.out.println(eb2+"\n");
		System.out.println(pp1+"\n");
		System.out.println(pp2+"\n");
		System.out.println(pp3+"\n");
		System.out.println(m1+"\n");
		System.out.println(m2+"\n");
		System.out.println(m3+"\n");
		System.out.println(j1+"\n");
		System.out.println(j2+"\n");
		System.out.println(j3+"\n");
		
		//Testing equals methods
		
		//All should return false
		System.out.println(b1.equals(b2)+"\n");
		System.out.println(b1.equals(j3)+"\n");
		System.out.println(cb1.equals(cb2)+"\n");
		System.out.println(pp1.equals(pp2)+"\n");
		System.out.println(m2.equals(m3)+"\n");
		System.out.println(j1.equals(j2)+"\n");
		System.out.println(pp1.equals(m2)+"\n");
		
		//All should return true
		System.out.println(b1.equals(b3)+"\n");
		System.out.println(cb2.equals(cb3)+"\n");
		System.out.println(eb1.equals(eb3)+"\n");
		System.out.println(pp1.equals(pp3)+"\n");
		System.out.println(m3.equals(m3)+"\n");
		System.out.println(j2.equals(j3)+"\n");
		
		PaperPublication[] publications = {pp1, pp2, pp3, m1, m2, m3, m4, j1, j2, j3};
		
		double minPrice = pp1.getPrice(), maxPrice = pp1.getPrice();
		
		int minPriceIndex = 0, maxPriceIndex = 0;
		
		//trace the array for lowest price object and highest price object
		for(int i = 0; i < publications.length; i++){
			if(publications[i].getPrice() < minPrice){
				minPriceIndex = i;
				minPrice = publications[i].getPrice();
			}
			if(publications[i].getPrice() > maxPrice){
				maxPriceIndex = i;
				maxPrice = publications[i].getPrice();
			}
		}
		
		System.out.println(publications[minPriceIndex]+"\nThe index of this item is "+minPriceIndex+"\n");
		System.out.println(publications[maxPriceIndex]+"\nThe index of this item is "+maxPriceIndex+"\n");
	}

}
