import java.io.*;
public class AutoFillSeats {
    /*
    *   This class is not a part of the application.
    *   This class is a bot that auto-books 5 seats (pseudo-randomly) of every show.
    *   It books seats of each movies' each languages' each dates' each times' 5 seats.
    *   This is made so that most shows are not totally vacant
    */
    public static void main(String[] args) throws IOException{
        File src = new File("data\\movies.txt");
        if(!src.exists()){
            System.out.print("File does not exist!");
            System.exit(1);
        }
        File dst = new File("data\\seats.txt");
        if(!dst.exists())
            if(dst.createNewFile()) throw new IOException("File Couldn't be created");
        RandomAccessFile raf_src = new RandomAccessFile(src,"rw");
        RandomAccessFile raf_dst = new RandomAccessFile(dst,"rw");
        raf_dst.seek(raf_dst.length());       //append to the end of seats.txt file
        String[] lang = new String[]{"English","Hindi"};
        String[] dates = DateTime.getDates(5);
        String[] times = new String[]{"07:00","10:15","13:30","16:45","20:00","23:00"};
        int lines = Database.countLines(src.getAbsolutePath());
        for(int i = 0;i<lines;i++){
            String movie = raf_src.readLine();
            for (String aLang : lang) {
                for (String date : dates) {
                    for (String time : times) {
                        raf_dst.writeBytes(movie + "\t" + aLang + "\t" + date + "\t" + time + "\t<AutoFillBot>");
                        for (int m = 0; m < 5; m++) {
                            char row = (char) ((int) (Math.random() * 6) + 65);
                            int col = ((int) (Math.random() * 10)) + 1;
                            raf_dst.writeBytes("\t" + row + col);
                        }
                        raf_dst.writeBytes("\n");
                    }
                }
            }
        }
    }
}
