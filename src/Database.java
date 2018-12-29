import com.mashape.unirest.http.exceptions.UnirestException;

import java.io.*;
import java.util.ArrayList;

public class Database{

   private String[] movies, lang,seats;
   private File movies_f, seats_f;
   static String[] receipt;
   static boolean update = true;

    Database() throws IOException {

        // if there is internet connection, then update the local movie database from the internet
        try {
            assert(update);
            updateMovies();
        }
        catch (UnirestException ue){
            // If no internet, then don't update
            System.out.println("No Internet Connection,\nCouldn't Update Database,\nUsing Local Database");
        }
        catch (AssertionError ae){
            System.out.println("Executing locally");
        }
        receipt = new String[7];
        //init movies database
        loadMovies();
        //init seats database
        loadSeats();
        //init language database
        lang = new String[]{"English","Hindi"};
    }

    private void updateMovies() throws UnirestException , IOException {
        System.out.println("Updating Local Database");
        movies = GetMoviesOnline.getMovies();   // may throw Unirest exception
        movies_f = new File("data\\movies.txt");
        if(movies_f.exists()){
            if(!movies_f.delete()) throw new IOException("Local Database could not be deleted");
        }
        if(!movies_f.createNewFile()) throw new IOException("Local Database could not be created");
        RandomAccessFile raf = new RandomAccessFile(movies_f,"rw");
        for(String movie:movies){
            raf.writeBytes(movie+"\n");
        }
    }

    private void loadMovies() throws IOException{
        movies_f = new File("data\\movies.txt");
        if(!movies_f.exists()){
            System.err.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("Movie File Exist at : "+ movies_f.getAbsolutePath());
        }
        int k=0;
        movies = new String[countLines(movies_f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(movies_f,"r");
        while(raf.getFilePointer()!= raf.length()){
            movies[k++] = raf.readLine();
        }
        System.out.println("Movie Database loaded!");
    }

    private void loadSeats()  throws IOException {

        seats_f = new File("data\\seats.txt");
        if(!seats_f.exists()){
            System.err.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("Seats File Exist at : "+ seats_f.getAbsolutePath());
        }
        int k=0;
        seats = new String[countLines(seats_f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(seats_f,"r");
        while(raf.getFilePointer()!= raf.length()){
            seats[k++] = raf.readLine();
        }
        System.out.println("Seats Database Loaded!");
    }

    static int countLines(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) {
            byte[] c = new byte[1024];
            int count = 0;
            int readChars = 0;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n')
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n');
            }
            if (endsWithoutNewLine) {
                ++count;
            }
            return count;
        }
    }

    String[] getLang() {
        return lang;
    }

    String[] getMovies(){
        return movies;
    }

    String[] getSeats() {
        return seats;
    }

    void setSeats(String movie, String lang, String date, String time, String name , ArrayList<String> seats)
                                                                    throws IOException  {

        receipt[0] = movie;
        receipt[1] = lang;
        receipt[2] = date;
        receipt[3] = time;
        receipt[4] = name;
        receipt[5] = "";
        receipt[6] = "0";

        if(name.equals("")){
            name = "<Anonymous>";
        }

        if(!seats_f.exists()){
            System.err.println("Seat File Does Not Exist");
            System.exit(1);
        }
        RandomAccessFile raf = new RandomAccessFile(seats_f,"rw");
        raf.seek(raf.length());
        raf.writeBytes(movie+"\t"+lang+"\t"+date+"\t"+time+"\t"+name);
        for(String seat:seats){
            receipt[5] += seat+" ";
            receipt[6] = new Integer(receipt[6]) + price(seat) + "";
            raf.writeBytes("\t"+seat);
        }
        receipt[5] = receipt[5].trim();
        raf.writeBytes("\n");
        raf.close();
    }

    static int price(String seat){

        int row = seat.charAt(0) - 65 ;
        int d = row/2;
        d+=1;
        return d*120;
    }

}