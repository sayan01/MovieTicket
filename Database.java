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
   private static String[] receipt;         // Receipt data
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
        catch (AssertionError ae){                      // If explicitly mentioned to run locally, then run locally
            System.out.println("Executing locally");
        }
        receipt = new String[7];    // initializing the receipt array with 7 fields
                                    // (movie, language, date, time, Username, seats, price)
        loadMovies();               // loading the movies local database into memory
        loadSeats();                // loading the seats local database into memory
        lang = new String[]{"English","Hindi"}; // initializing language database
    }

    /*
    *   Method: updateMovies()
    *   Parameters: none
    *   Returns: void
    *   Description: This method tries to update the local movies database by calling the getMovies() method from
    *               class GetMoviesOnline. It creates a new movies.txt file and stores the fetched results in the file
    */

    private void updateMovies() throws UnirestException , IOException {
        System.out.println("Updating Local Database");
        movies = GetMoviesOnline.getMovies();   // try to fetch latest movies from internet and store it in a variable
                                                // may throw Unirest exception if unable to connect to API
        movies_f = new File("data/movies.txt");
        if(movies_f.exists()){    // If movie file already exists, delete it, as updated version of file will be created
            if(!movies_f.delete()) throw new IOException("Local Database could not be deleted");// If unable to delete
        }
                        // Create a new file to store data
        if(!movies_f.createNewFile()) throw new IOException("Local Database could not be created");//If unable to create
        RandomAccessFile raf = new RandomAccessFile(movies_f,"rw");
        for(String movie:movies){
            raf.writeBytes(movie+"\n");         // Write each movie in database in a new line
        }
    }

    /*
     *   Method: loadMovies()
     *   Parameters: none
     *   Returns: void
     *   Description: This method tries to load the local movies database and store its value in memory.
     */

    private void loadMovies() throws IOException{
        movies_f = new File("data/movies.txt");
        if(!movies_f.exists()){                             // If database doesn't exist, terminate
            System.err.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("Movie File Exist at : "+ movies_f.getAbsolutePath());
        }
        int k=0;
        movies = new String[countLines(movies_f.getAbsolutePath())];    // create local variable to store data of file
                                                                        // (size of local array = no. of lines in data)
        RandomAccessFile raf = new RandomAccessFile(movies_f,"r");
        while(raf.getFilePointer()!= raf.length()){
            movies[k++] = raf.readLine();                               // store each line present in database in array
        }
        System.out.println("Movie Database loaded!");
    }

    /*
     *   Method: loadSeats()
     *   Parameters: none
     *   Returns: void
     *   Description: This method tries to load the local seats database and store its value in memory.
     */

    private void loadSeats()  throws IOException {

        seats_f = new File("data/seats.txt");
        if(!seats_f.exists()){                  // If database file doesn't exist, create it.
            System.err.println("Seats File Does Not Exist, Creating.");
            if(seats_f.createNewFile()) System.out.println("Seats File Created at : "+ seats_f.getAbsolutePath());
            else{
                System.err.print("Could not create seats file.");   // If cannot make file, exit.
            }
        }
        else{
            System.out.println("Seats File Exist at : "+ seats_f.getAbsolutePath());
        }
        int k=0;
        seats = new String[countLines(seats_f.getAbsolutePath())];  // create local variable to store data of file
                                                                    // (size of local array = no. of lines in data)
        RandomAccessFile raf = new RandomAccessFile(seats_f,"r");
        while(raf.getFilePointer()!= raf.length()){
            seats[k++] = raf.readLine();                            // store each line present in database in array
        }
        System.out.println("Seats Database Loaded!");
    }

    /*
     *   Method: countLines()
     *   Parameters: String filename - the file name of the document
     *   Returns: int - the number of lines in the document
     *   Description: This counts the number of lines in a given document (by counting the number of '\n' characters
      *               present in it.
     */

    static int countLines(String filename) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(filename))) { // tries to access given file
            byte[] c = new byte[1024];  // local buffer of 1KB
            int count = 0;
            int readChars;
            boolean endsWithoutNewLine = false;
            while ((readChars = is.read(c)) != -1) {    // while EOF is not encountered
                for (int i = 0; i < readChars; ++i) {   // read the characters stored in buffer
                    if (c[i] == '\n')                   // if '/n' is present, increase counter
                        ++count;
                }
                endsWithoutNewLine = (c[readChars - 1] != '\n'); // Stores weather the last character of current buffer
                                                                // was a newline or not.
            }
            if (endsWithoutNewLine) {               // if the last character of the file wasn't a newline, then increase
                                                    // counter by 1
                ++count;
            }
            return count;
        }
    }

    /*
     *   Method: getLang()
     *   Parameters: none
     *   Returns: String[]
     *   Description: Getter of lang variable
     */

    String[] getLang() {
        return lang;
    }

    /*
     *   Method: getMovies()
     *   Parameters: none
     *   Returns: String[]
     *   Description: Getter of movies variable
     */

    String[] getMovies(){
        return movies;
    }

    /*
     *   Method: getSeats()
     *   Parameters: none
     *   Returns: String[]
     *   Description: Getter of seats variable
     */

    String[] getSeats() {
        return seats;
    }

    /*
     *   Method: getReceipt()
     *   Parameters: none
     *   Returns: String[]
     *   Description: Getter of receipt variable
     */

    public static String[] getReceipt() {
        return receipt;
    }
    /*
     *   Method: setSeats()
     *   Parameters: none
     *   Returns: void
     *   Description: This methods stores the current user's booking details in the database and in the receipt variable
     */

    void setSeats(String movie, String lang, String date, String time, String name , ArrayList<String> seats)
                                                                  throws IOException  {

        /*  Store the data in receipt variable    */
        receipt[0] = movie;
        receipt[1] = lang;
        receipt[2] = date;
        receipt[3] = time;
        receipt[4] = name;
        receipt[5] = "";
        receipt[6] = "0";   // set price initially to 0

        if(name.equals("")){        // If no name is entered, store it as anonymous.
            name = "<Anonymous>";
        }

        if(!seats_f.exists()){  // If seats database doesn't exist, terminate
            System.err.println("Seat File Does Not Exist");
            System.exit(1);
        }
        RandomAccessFile raf = new RandomAccessFile(seats_f,"rw");
        raf.seek(raf.length());     // Append to the end of the file
        raf.writeBytes(movie+"\t"+lang+"\t"+date+"\t"+time+"\t"+name);  // Deliminate tokens using tabs in database
        for(String seat:seats){
            receipt[5] += seat+" ";     // Store seats in receipt
            receipt[6] = Integer.parseInt(receipt[6]) + price(seat) + "";    // Calculate price of seats and store in receipt
            raf.writeBytes("\t"+seat);  // Store seats in database
        }
        receipt[5] = receipt[5].trim();
        raf.writeBytes("\n");
        raf.close();
    }

    /*
     *   Method: price()
     *   Parameters: String seat - the seat code whose price is to be evaluated
     *   Returns: int - the price of booking the seat
     *   Description: Calculates the price of booking a particular seat and returns it
     */

    static int price(String seat){

        int row = seat.charAt(0) - 65 ; // get the row number of the seat
        int d = row/2;                  // divide the rows in three categories, front, middle, and back, having
        d+=1;                           // different prices.
        return d*120;
    }

}
