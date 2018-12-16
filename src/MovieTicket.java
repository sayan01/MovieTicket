
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;

public class MovieTicket implements ActionListener {
    JFrame f;
    JButton b;
    JComboBox<String> movies;
    JComboBox<String> lang;
    JComboBox<String> date;
    JComboBox<String> time;
    String name;
    Database db;
    JLabel t1,t2,t3;
    ImageIcon icon;
    int width,height;
    int movie_sel;
    int lang_sel;
    int scene;

    MovieTicket() throws IOException {

        scene=0;
        width = 450; height = 350;
        db = new Database();
        icon = new ImageIcon("assets\\icon.png");
        input();
        welcome();

    }

    public static void main(String[] args) throws IOException {
        new MovieTicket();
    }

    void input()   /* inputs username */   {

        name = JOptionPane.showInputDialog(f,
                "Enter name: ",
                "Username Input - MovieTicket",
                JOptionPane.PLAIN_MESSAGE);

        if(name == null){
            System.err.println("No Name Entered");
            System.exit(1);
        }

        for(int i = 0;i<name.length();i++){
            if(!Character.isLetter(name.charAt(i))){
                name = name.substring(0,i)+name.substring(i+1);
                i--;
            }
        }

        if(name.length()>20){
            name = name.substring(0,17)+"...";
        }
        scene = 1;

    }

    void welcome()  {

        f = new JFrame("MovieTicket");
        f.setSize(width, height);
        f.setDefaultCloseOperation(3);
        f.setIconImage(icon.getImage());

        b = new JButton("OK");
        b.setBounds(450 / 2 - 100 / 2, 250, 100, 50);
        b.addActionListener(this);

        String[] mov = db.getMovies();
        movies = new JComboBox<String>(mov);
        movies.setBounds(150, 100, 250, 25);
        movies.setSelectedIndex(-1);
        movies.addActionListener(this);

        lang = new JComboBox<String>(db.getLang());
        lang.setBounds(150, 150, 250, 25);
        lang.setSelectedIndex(-1);
        lang.addActionListener(this);

        t1 = new JLabel("Choose Movie: ");
        t1.setBounds(0,100,150,25);

        t2 = new JLabel("Choose Language: ");
        t2.setBounds(0,150,150,25);

        t3 = new JLabel("Welcome "+name);
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(0,10,width,20);


        f.add(movies);
        f.add(b);
        f.add(lang);
        f.add(t1); f.add(t2); f.add(t3);
        f.setLayout(null);
        f.setVisible(true);
    }

    void timing(){
        t1.setText("Choose date and timing: ");
        t1.setHorizontalAlignment(SwingConstants.CENTER);
        t1.setBounds(0,10,width,20);

        date = new JComboBox<>(DateTime.getDates(20));
        date.setBounds(100,50,250,25);
        date.setSelectedIndex(-1);
        date.addActionListener(this);

        time = new JComboBox<>(DateTime.getTimes((String)date.getSelectedItem()));
        time.setBounds(100, 150, 250, 25);
        time.setSelectedIndex(-1);
        time.addActionListener(this);

        f.add(t1); f.add(date); f.add(time); f.add(b);

    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == movies) // if movie is changed
            System.out.println("Movie Selected: " + movies.getSelectedItem());

        if (ae.getSource() == lang) // if language is changed
            System.out.println("Language Selected: " + lang.getSelectedItem());

        if (ae.getSource() == time) // if language is changed
            System.out.println("Time Selected: " + time.getSelectedItem());

        if (ae.getSource() == date) /* if date is changed */ {
            System.out.println("Date Selected: " + date.getSelectedItem());
            f.remove(time);
            time = new JComboBox<>(DateTime.getTimes((String) Objects.requireNonNull(date.getSelectedItem())));
            time.setBounds(100, 150, 250, 25);
            time.addActionListener(this);
            f.add(time);
            f.revalidate();
            f.repaint();
        }


        if (ae.getSource() == b) {  // if button is pressed

            if(scene==1){
                //Remembering choices
                movie_sel = movies.getSelectedIndex();
                lang_sel = lang.getSelectedIndex();

                if(movie_sel < 0 || lang_sel < 0){
                    JOptionPane.showMessageDialog(f, "Please Choose Movie and Language",
                            "Required Fields are empty", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                //Removing option to choose movies
                f.getContentPane().removeAll();

                //Moving to next scene
                scene = 2;
                timing();

                //repainting frame
                f.revalidate();
                f.repaint();

            }
            else if (scene==2){
                // implement moving to scene 3
                System.out.println("Moving to scene 3");
            }

        }
    }

}
