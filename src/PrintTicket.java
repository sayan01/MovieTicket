import java.awt.image.ImageObserver;
import java.awt.print.*;
import java.awt.*;
import java.io.IOException;

public class PrintTicket
        implements Printable {

    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) {
            return NO_SUCH_PAGE;
        }
        Graphics2D g2d = (Graphics2D)g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        String[] labels = {"Movie: ","Language: ","Date: ","Time: ","Name: ","Seats: "};
        int y = 300;

        g.setFont(new Font("Century Gothic",Font.PLAIN,55));
        g.drawString("MovieTicket",50,100);

        g.drawImage(MovieTicket.icon.getImage(), 400, 10, 150, 150,null);

        for(int i = 0; i<6;i++) {
            String label = labels[i];
            String data = Database.receipt[i];
            g.setFont(new Font("Arial",Font.BOLD,20));
            g.drawString(label, 20, y);
            g.setFont(new Font("Consolas",Font.PLAIN,16));
            y = printExcess(g,data,y);
            y+=30;
        }

        g.setFont(new Font("Malgun Gothic Semilight",Font.PLAIN,10));
        g.drawString("© VMS Computer Project – XI-A – 18 • 21 • 25",400,775);

        return PAGE_EXISTS;
    }

    private int printExcess(Graphics g, String excess, int y) {
        int exl = 40;
        if(excess.length()<exl) {
            g.drawString(excess,200,y);
            return y;
        }

        g.drawString(excess.substring(0,exl),200,y);
        return printExcess(g,excess.substring(exl),y+25);

    }

    public static void print(){

        PrinterJob job = PrinterJob.getPrinterJob();
        PrintTicket pt = new PrintTicket();
        job.setPrintable(pt);
        boolean doPrint = job.printDialog();
        if (doPrint) {
            try {
                job.print();
            } catch (PrinterException e) {
            }
        }
    }
}