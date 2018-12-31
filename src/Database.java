import com.mashape.unirest.http.exceptions.UnirestException; // UnirestException may be thrown while trying to update
                                                                    // Movie Database

import java.io.*;
import java.util.ArrayList;

/*
*   This class stores all of the data needed for the application to run, namely:
 *          * the movies now showing (present in the local movie database)
 *          * the languages available (English and Hindi only)
 *          * the seats which are already booked ( present in the local seats database)
 *          * the receipt for the tickets booked by the current user (initialized after booking is finalized)
 *  It has methods for loading the data from the local database into memory
 *  and method for updating the local database from online database
*/

class Database{

   private String[] movies, lang,seats;     // The data from the database
   private File movies_f, seats_f;          // The local files of the database
   static String[] receipt;                 // Receipt data
   static boolean update = true;            // Whether to update the local database or not

    /*
    *   Constructor :
    *       Parameters: none
    *       Description: It runs when a Database object is initialized, it performs initial loading tasks.
    */

    Database() throws IOException {
                                                                // if there is internet connection,
                                                               // then update the local movie database from the internet
        try {
            if(!update) throw new AssertionError();     // If it is mentioned to run locally, then don't update database
            updateMovies();                             // May throw Unirest Exception if unable to connect to internet
        }
        catch (UnirestException ue){                    // If no internet, then don't update
            System.out.println("No Internet Connection,\nCouldn't Update Database,\nUsing Local Database");
        }
        catch (AssertionError ae){
            System.out.println("Executing locally");
        }
        receipt = new String[7];    // initializing the receipt array
        loadMovies();               // loading the movies local database into memory
        loadSeats();                // loading the seats local database into memory
        lang = new String[]{"English","Hindi"}; // initializing language database
    }

    /*
    *   Method: updateMovies()
    *   Parameters: none
    *   Returns: void
    *   Description: This method tries to update the local movies database by calling the getMovies() method from
    *               class GetMoviesOnline
    */

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
            int readChars;
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