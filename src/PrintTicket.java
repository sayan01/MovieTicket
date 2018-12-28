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
        int i = 100;
            for(String para:MovieTicket.receipt) {
                g.drawString(para, 100, i);
                i+=100;
            }

        return PAGE_EXISTS;
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