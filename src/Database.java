import java.io.*;

public class Database{

   private String[] movies, lang,seats;

    Database() throws IOException {
        //init movies database
        loadMovies();
        //init seats database
        loadSeats();
        //init language database
        lang = new String[]{"English","Hindi","Bengali","Tamil","Telegu"};
    }

    private void loadMovies() throws IOException{
        File f = new File("data\\movies.txt");
        if(!f.exists()){
            System.err.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("File Exist at : "+ f.getAbsolutePath());
        }
        int k=0;
        movies = new String[countLines(f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(f,"r");
        while(raf.getFilePointer()!= raf.length()){
            movies[k++] = raf.readLine();
        }
    }

    private void loadSeats()  throws IOException {

        File f = new File("data\\seats.txt");
        if(!f.exists()){
            System.err.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("File Exist at : "+ f.getAbsolutePath());
        }
        int k=0;
        seats = new String[countLines(f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(f,"r");
        while(raf.getFilePointer()!= raf.length()){
            seats[k++] = raf.readLine();
        }
    }

    private int countLines(String filename) throws IOException {
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

}