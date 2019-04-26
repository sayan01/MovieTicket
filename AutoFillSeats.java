import java.io.*;
public class AutoFillSeats {
    /*
    *   This class is not a part of the application.
    *   This class is a bot that auto-books 5 seats (pseudo-randomly) of every show.
    *   It books seats of each movies' each languages' each dates' each times' 5 seats.
    *   This is made so that most shows are not totally vacant
    */
    public static void main(String[] args) throws IOException{
        File src = new File("data/movies.txt");		// Read the movies file to get all the movies now showing
        if(!src.exists()){
            System.out.print("File does not exist!");	// Bot cannot operate without the movie database
            System.exit(1);
        }
        File dst = new File("data/seats.txt");			// write the entries to seats.txt file
        if(!dst.exists())								// if file doesn't exist, then create it
            if(dst.createNewFile()) throw new IOException("File Couldn't be created");
        RandomAccessFile raf_src = new RandomAccessFile(src,"rw");
        RandomAccessFile raf_dst = new RandomAccessFile(dst,"rw");
        raf_dst.seek(raf_dst.length());       // append to the end of seats.txt file
        String[] lang = new String[]{"English","Hindi"};
        String[] dates = DateTime.getDates(5);	// seats for future 5 days are booked
        String[] times = new String[]{"07:00","10:15","13:30","16:45","20:00","23:00"};
        int lines = Database.countLines(src.getAbsolutePath());		// getting how many movies are there in database
        for(int i = 0;i<lines;i++){
            String movie = raf_src.readLine();
            for (String aLang : lang) {
                for (String date : dates) {
                    for (String time : times) {
                        raf_dst.writeBytes(movie + "\t" + aLang + "\t" + date + "\t" + time + "\t<AutoFillBot>");
                        // writing each entries to seats.txt file
                        for (int m = 0; m < 5; m++) {
                            char row = (char) ((int) (Math.random() * 6) + 65);		// getting a random row
                            int col = ((int) (Math.random() * 10)) + 1;				// getting a random column
                            raf_dst.writeBytes("\t" + row + col);					// writing seats
                        }
                        raf_dst.writeBytes("\n");
                    }
                }
            }
        }
    }
}
