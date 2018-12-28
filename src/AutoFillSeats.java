import java.io.*;
public class AutoFillSeats {
    public static void main(String[] args) throws IOException{
        File f = new File("data\\movies.txt");
        if(!f.exists()){
            System.out.print("File no no exists!");
            System.exit(1);
        }
        File f2 = new File("data\\seats.txt");
        if(!f2.exists())
            f2.createNewFile();
        RandomAccessFile raf = new RandomAccessFile(f,"rw");
        RandomAccessFile raf2 = new RandomAccessFile(f2,"rw");
        raf2.seek(raf2.length());
        String[] lang = new String[]{"English","Hindi"};
        String[] dates = DateTime.getDates(5);
        String[] times = new String[]{"07:00","10:15","13:30","16:45","20:00","23:00"};
        int lines = countLines(f.getAbsolutePath());
        for(int i = 0;i<lines;i++){
            String movie = raf.readLine();

            for(int j = 0; j < lang.length;j++){
                for(int k = 0; k<dates.length;k++){
                    for(int l = 0; l< times.length;l++){

                        raf2.writeBytes(movie+"\t"+lang[j]+"\t"+dates[k]+"\t"+times[l]+"\t<AutoFillBot>");
                        for(int m = 0; m<5;m++){
                            char row = (char) ( (int)(Math.random()*6) + 65 );
                            int col = (  (int)(Math.random()*10)  ) + 1;
                            raf2.writeBytes("\t"+row+col);
                        }
                        raf2.writeBytes("\n");
                    }
                }
            }

        }
    }
    private static int countLines(String filename) throws IOException {
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

}
