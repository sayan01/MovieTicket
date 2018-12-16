import java.io.*;

public class Database{

   private String[] movies, lang,time;

    Database() throws IOException {

        //init movies database
        File f = new File("data\\movies.txt");
        if(!f.exists()){
            System.out.println("Movie File Does Not Exist");
            System.exit(1);
        }
        else{
            System.out.println("File Exist at : "+ f.getAbsolutePath());
        }

        int k=0;
        movies = new String[count(f.getAbsolutePath())];
        RandomAccessFile raf = new RandomAccessFile(f,"r");
        while(raf.getFilePointer()!= raf.length()){
            movies[k++] = raf.readLine();
        }

        //init language database
        lang = new String[]{"English","Hindi","Bengali","Tamil","Telegu"};

        //init time database



    }

    private int count(String filename) throws IOException {
        InputStream is = new BufferedInputStream(new FileInputStream(filename));
        try {
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
            if(endsWithoutNewLine) {
                ++count;
            }
            return count;
        } finally {
            is.close();
        }
    }

    String[] getLang() {
        return lang;
    }

    String[] getTime() {
        return time;
    }

    String[] getMovies(){
        return movies;
    }
}
