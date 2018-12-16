
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Objects;

public class MovieTicket implements ActionListener {
    JFrame f;
    JButton next,back;
    JComboBox<String> movies;
    JComboBox<String> lang;
    JComboBox<String> date;
    JComboBox<String> time;
    String name;
    Database db;
    JLabel t1,t2,t3,t4,t5;
    ImageIcon icon;
    int width,height;
    int movie_sel;
    int lang_sel;
    int date_sel;
    int time_sel;
    int scene;

    MovieTicket() throws IOException {
        scene=0;
        width = 450; height = 250;
        db = new Database();
        icon = new ImageIcon("assets\\icon.png");
        input();
        f = new JFrame("MovieTicket");
        sc1();
    }

    public static void main(String[] args) throws IOException {
        new MovieTicket();
    }

    void input()   /* inputs username */   {
        name = JOptionPane.showInputDialog(f,       //Input Prompt for inputting username
                "Enter name: ",
                "Username Input - MovieTicket",
                JOptionPane.PLAIN_MESSAGE);
        if(name == null){           // If user presses cancel, exit program
            System.err.println("No Name Entered");
            System.exit(1);
        }
        for(int i = 0;i<name.length();i++){     // Remove any illegal character from username
            if(!Character.isLetter(name.charAt(i))){
                name = name.substring(0,i)+name.substring(i+1);
                i--;
            }
        }
        if(name.length()>20){               //If name is too long, truncate it
            name = name.substring(0,20)+"...";
        }
        scene = 1;
    }



    void sc1()  {

        f.setSize(width, height);
        f.setDefaultCloseOperation(3);
        f.setIconImage(icon.getImage());

        next = new JButton();
        next.setText("Next>");
        next.setBounds(width - 95 , 180, 75, 25);
        next.addActionListener(this);

        String[] mov = db.getMovies();
        movies = new JComboBox<>(mov);
        movies.setBounds(100, 75, 250, 25);
        movies.setSelectedIndex(-1);
        movies.addActionListener(this);


        lang = new JComboBox<>(db.getLang());
        lang.setBounds(100, 125, 250, 25);
        lang.setSelectedIndex(-1);
        lang.addActionListener(this);

        t1 = new JLabel();
        t1.setText("Movie: ");
        t1.setBounds(0,75,100,25);
        t1.setHorizontalAlignment(SwingConstants.CENTER);

        t2 = new JLabel();
        t2.setText("Language: ");
        t2.setBounds(0,125,100,25);
        t2.setHorizontalAlignment(SwingConstants.CENTER);

        t3 = new JLabel();
        t3.setText("Welcome "+name);
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(0,10,width,20);

        f.add(movies);
        f.add(next);
        f.add(lang);
        f.add(t1); f.add(t2); f.add(t3);
        f.setLayout(null);
        f.setVisible(true);
    }

    void sc2(){
        String movie = (String) movies.getSelectedItem();
        if(movie.length()>50)   movie = movie.substring(0,50)+"...";
        t1.setText("Movie:  "+movie);
        t1.setBounds(30,10,width,20);
        t1.setHorizontalAlignment(SwingConstants.LEFT);

        t2.setText("Language:  "+lang.getSelectedItem());
        t2.setBounds(10,40,width,20);
        t2.setHorizontalAlignment(SwingConstants.LEFT);

        back = new JButton();
        back.setText("<Back");
        back.setBounds(10 , 180, 75, 25);
        back.addActionListener(this);

        date = new JComboBox<>(DateTime.getDates(20));
        date.setBounds(100,75,250,25);
        date.setSelectedIndex(-1);
        date.addActionListener(this);

        time = new JComboBox<>(DateTime.getTimes((String)date.getSelectedItem()));
        time.setBounds(100, 125, 250, 25);
        time.setSelectedIndex(-1);
        time.addActionListener(this);

        t3 = new JLabel();
        t3.setText("Date: ");
        t3.setHorizontalAlignment(SwingConstants.CENTER);
        t3.setBounds(0,75,100,25);

        t4 = new JLabel();
        t4.setText("Time: ");
        t4.setHorizontalAlignment(SwingConstants.CENTER);
        t4.setBounds(0,125,100,25);

        f.add(t1); f.add(date); f.add(time); f.add(t2); f.add(t3); f.add(t4); f.add(next); f.add(back);
    }

    private void seat() {

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
            time.setSelectedIndex(-1);
            time.addActionListener(this);
            f.add(time);
            f.revalidate();
            f.repaint();
        }

        if (ae.getSource() == next) {  // if button is pressed
            if(scene==1){ // if currently were selecting movie and language
                movie_sel = movies.getSelectedIndex(); //Remembering choices
                lang_sel = lang.getSelectedIndex();
                if(movie_sel < 0 || lang_sel < 0){      //If fields area empty
                    JOptionPane.showMessageDialog(f, "Please Choose Movie and Language",
                            "Required Fields are empty", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                f.getContentPane().removeAll(); //Removing option to choose movies
                sc2();   //Moving to next scene
                f.revalidate(); //redoing frame
                f.repaint();
            }
            else if (scene==2){ // If currently choosing date and time
                date_sel = date.getSelectedIndex(); //Remembering choices
                time_sel = time.getSelectedIndex();
                if(date_sel < 0 || time_sel < 0) {       //If fields are empty
                    JOptionPane.showMessageDialog(f, "Please Choose Date and Time",
                            "Required Fields are empty", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                f.getContentPane().removeAll(); //Removing option to choose date and time
                seat();     //moving to next scene
                f.revalidate(); //repainting frame
                f.repaint();
            }
            scene++;
        }
        if(ae.getSource() == back){
            if(scene==2){
                f.getContentPane().removeAll(); //Removing option to choose date and time
                sc1();     //moving to previous scene
                f.revalidate(); //repainting frame
                f.repaint();
                scene--;
            }
        }
    }
}
