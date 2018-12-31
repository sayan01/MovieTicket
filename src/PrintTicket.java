import java.awt.print.*;
import java.awt.*;
/*
*	This class is used for printing the ticket after booking has been finalized (in scene 4)
*	The print() method uses the PrinterJob class and this class to print the receipt
*/
public class PrintTicket
        implements Printable {	// the class needs to implement Printable interface in order to be passed as a Printable
        						// to the PrinterJob class's setPrintable() method

    /*
	*	Method: print()
	*	Parameters: Graphics g - The Graphics object to draw on
	*			    PageFormat pf - The PageFormat object containing information about the page to be printed
	*			    int page - page number
	*	Returns:  int - returns if PAGE EXISTS or not
	*	Desc:	This method is a method needed to be overloaded on implementing the Printable interface,
	*			It is called when the PrinterJob.print() method is called
	*			Here details of what to print in the receipt is decided by drawing on the passed Graphics object
    */
    public int print(Graphics g, PageFormat pf, int page) {
        if (page > 0) {		// if page doesn't exist, return
            return NO_SUCH_PAGE;
        }
        if(MovieTicket.f == null)   return NO_SUCH_PAGE;	// if MovieTicket class is not initialized, return
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());	// translate to the part of page which is drawable
        String[] labels = {"Movie: ","Language: ","Date: ","Time: ","Name: ","Seats: ","Price: "};	// the data fields
                                                                                                    // in the receipt
        int y = 300;				// The y-axis coordinate to start printing the receipt from
                                    // (0px - 300px is used for Heading and logo)

        g.setFont(new Font("Century Gothic",Font.PLAIN,55));
        g.drawString("MovieTicket",50,100);					// Draw big heading at the top of page

        g.drawImage(MovieTicket.icon.getImage(), 400, 10, 150, 150,null);   // Draw the program
                                                                                                        // logo at top
                                                                                            // (MovieTicket class needs
        																	// to be instantiated to access icon field)
        for(int i = 0; i<labels.length;i++) {
            String label = labels[i];				// for each field label
            String data = Database.receipt[i];		// for each field data
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(label, 20, y);				// draw field label
            g.setFont(new Font("Consolas",Font.PLAIN,16));
            y = printExcess(g,data,y,40);			// draw field data , if data is too big, continue drawing in
                                                        // next lines (wordWrap)
            y+=30;									// after all of data is drawn, increase y for drawing next field
        }

        g.setFont(new Font("Malgun Gothic Semilight",Font.PLAIN,10));
        g.drawString("© VMS Computer Project – XI-A – 18 • 21 • 25",400,775);	// draw footer

        return PAGE_EXISTS;
    }
	/*
	*	Method: printExcess()
	*	Parameters: Graphics g - The Graphics object to draw on
	*			    String excess - The String data to be printed
	*			    int y - y coordinate to print data in
	*			int exl - the number of characters to print in one line
	*	Returns : int - returns the final y coordinate, after printing whole data
	*	Desc:	This method is used to print field data on the Graphics object with word flow using recursion
	*			It prints data up to a certain length, then prints the excess data in the next line (y+=25)
	*			Finally, after all of data is printed, it returns the y coordinate of the last line of the data
    */
    private int printExcess(Graphics g, String excess, int y,int exl) {
        if(excess.length()<exl) {
            g.drawString(excess,200,y);	// If whole data can fit in one line, print it and return current y
            return y;
        }
        // Else, print $exl$ characters then print remaining data in next line (y+=25) recursively
        g.drawString(excess.substring(0,exl),200,y);	// print first $exl$ characters
        return printExcess(g,excess.substring(exl),y+25,exl);	// pass remaining data to be drawn recursively

    }
	/*
	*	Method: print()
	*	Parameters: none
	*	Returns : void
	*	Desc:	This method is called by the main class (MovieTicket.java) when it wants the program to print a receipt
	*			The method uses the PrinterJob class to print the receipt
    */
    static void print(){
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintTicket pt = new PrintTicket();		// creating object of this class
        job.setPrintable(pt);					// setting this class as the printable for the PrinterJob
        if (job.printDialog()) {				// show the print Dialog, and if the user doesn't cancel the dialog,
                                                // then print the receipt
            try {
                job.print();		// Print the receipt (calls the print(Graphics,PageFormat,int) method internally)
            }
            catch (PrinterException pe) {
                System.out.print("Could not Print\n"+pe.toString());	// If unable to print, log it
            }
        }
    }
}