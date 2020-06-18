import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

/*
 *
 * This is the main class of the program, it contains many methods dedicated to showing a particular scene.
 * It also contains a public static void main(String[] args) method which is invoked on running the application.
 * This class is for the UI of the Application as well as for running it.
 * It uses all other classes to co-ordinate the program.
 * This class is the heart and soul of the program.
 *
 */


public class MovieTicket implements ActionListener {
	static JFrame f;                                        // Frame which contains the UI
	private JButton next,back,print;                        // Buttons for navigation
	private JComboBox<String> movies,lang,date,time;        // Combo Boxes for choosing movie, lang, date and time
	private String name;                                    // String storing user's name
	private Database db;                                    // Database which contains all of data
	private JLabel t1,t2,t3;                                // Text Fields for various uses
	static ImageIcon icon;                                  // Image of application's icon
	private static ImageIcon icon_;                         // Image of application's icon (smaller)
	private static ImageIcon[] seatIcons;                   // Images of icon of seats (not clicked, clicked)
	private int width,height,movie_sel,lang_sel,date_sel,time_sel,scene;    // Variable for storing numeric values
	private JButton[][] seats = new JButton[10][6];         // Buttons for choosing seats
	private ArrayList<String> seats_booked;                 // Array containing all of previous seat bookings


	/*
	 *   Constructor :
	 *       Parameters: none
	 *       Description: It runs when a MovieTicket object is initialized, it performs initial tasks and inputs user's
	 *       name. It also starts scene 1.
	 */

	private MovieTicket() throws IOException {
		width = 450; height = 250;          // setting the dimensions of the frame of the application
		db = new Database();                // creating new database object
		icon = new ImageIcon("assets/icon.png");                   // loading assets
		icon_ = new ImageIcon("assets/icon_small.png");            // loading assets
		seatIcons = new ImageIcon[2];
		seats_booked = new ArrayList<>();
		for(int i = 0;i < 2; i++){
			seatIcons[i] = new ImageIcon("assets/seat"+i+".png");  // loading assets
		}
		input();                                                            // Inputting the user's name
		f = new JFrame("MovieTicket");                                  // Creating the frame
		scene=1;                                                            // Setting current scene = 1
		sc1();                                                              // Starting scene 1.
	}

	/*
	 *   Method: main()
	 *   Parameters: String[] args - for passing additional arguments
	 *   Returns: void
	 *   Description: This method runs the program.
	 */

	public static void main(String[] args) throws IOException {
		if(args.length>0){                          // If an argument is passed
			for (String arg : args) {
				if (arg.equalsIgnoreCase("local")) {    //If any argument is 'local' then make the program
					Database.update = false;                      // Run locally [Do not update the database from net]
				}
			}
		}
		new MovieTicket();                                          // Start program
	}

	/*
	 *   Method: input()
	 *   Parameters: none
	 *   Returns: void
	 *   Description: This method inputs the user's name and stores it in global variable
	 */

	private void input(){

		//Input Prompt for inputting username
		name = JOptionPane.showInputDialog(null,   // parent frame
				"Enter name: ",                         // message to be shown
				"Username Input - MovieTicket",           // title of the dialog box
				JOptionPane.PLAIN_MESSAGE);                  // type of message

		/*
		   If the user clicked cancel, the method will return null,
		   If the user pressed enter without entering a name, the method will return "" [empty string]
		   */

		// If user presses cancel, exit program
		if(name == null){
			System.err.println("No Name Entered");
			System.exit(1);
		}

		// Remove any illegal character from username
		for(int i = 0;i<name.length();i++){
			if(!Character.isLetter(name.charAt(i))){
				name = name.substring(0,i)+name.substring(i+1);
				i--;
			}
		}

		//If name is too long, truncate it
		if(name.length()>20){
			name = name.substring(0,20)+"...";
		}
	}

	/*
	 *   Method: sc1()
	 *   Parameters: none
	 *   Returns: void
	 *   Description:   This method paints scene 1 on frame.
	 *                  Scene 1 : A scene which shows a welcome message,
	 *                            and lets the user choose movie and language using combo boxes.
	 *                            This scene has a next button to go to the next scene. [scene 2]
	 */


	private void sc1()  {

		// Configure Frame
		f.setSize(width, height);                                   // Sets the size of frame
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Sets the program to terminate when frame is closed
		f.setIconImage(icon.getImage());                          // Sets the frame's icon

		//Configure next button
		next = new JButton();
		next.setText("Next>");                                // Set the text on next button
		next.setBounds(width - 95 , 180, 75, 25);// Set the boundaries of next button (x,y,width,height)
		next.addActionListener(this);                    // Make next button call the action listener if it is pressed

		//Configure movies combo box
		String[] mov = db.getMovies();                        // Get the available movies from the database
		movies = new JComboBox<>(mov);                       // Put all those movies in the combo box
		movies.setBounds(100, 75, 250, 25); // Set the boundaries of combo box
		movies.setSelectedIndex(-1);                       // Set no object as selected

		//Configure language combo box
		lang = new JComboBox<>(db.getLang());               // Create combo box and put all the languages in it
		lang.setBounds(100, 125, 250, 25); // Set the boundaries of combo box
		lang.setSelectedIndex(-1);                        // Set no object as selected

		//Configure text field
		t1 = new JLabel();
		t1.setText("Movie: ");                              // Text Field to display "Movie:" before the movie combo box
		t1.setBounds(0,75,100,25);         // Set the boundaries of text field
		t1.setHorizontalAlignment(SwingConstants.CENTER); // Align the text to center

		//Configure text field
		t2 = new JLabel();
		t2.setText("Language: ");                     // Text Field to display "Language:" before the language combo box
		t2.setBounds(0,125,100,25);  // Set the boundaries of text field
		t2.setHorizontalAlignment(SwingConstants.CENTER);// Align the text to center

		//Configure text field
		t3 = new JLabel();
		t3.setText("Welcome "+name);                        // Text Field to welcome the user
		t3.setHorizontalAlignment(SwingConstants.CENTER);  // Align the text to center
		t3.setBounds(0,10,width,20);           // Set the boundaries of text field

		// Add all elements to the frame and make the frame visible
		f.add(movies);  f.add(next);    f.add(lang);    f.add(t1);  f.add(t2);  f.add(t3);
		f.setLayout(null);
		f.setVisible(true);
	}

	/*
	 *   Method: sc2()
	 *   Parameters: none
	 *   Returns: void
	 *   Description:   This method paints scene 2 on frame.
	 *                  Scene 2 : A scene which lets the user choose date and time using combo boxes.
	 *                            This scene has a next button to go to the next scene. [scene 3]
	 *                            This scene has a back button to go to the previous scene. [scene 1]
	 *                            This scene also displays the movie and language selected in the previous scene.
	 */

	private void sc2(){
		//Configuring text field
		String movie = movies.getItemAt(movie_sel);                        // get which movie was selected
		if(movie.length()>50)   movie = movie.substring(0,50)+"...";      // truncate name if too big
		t1.setText("Movie:  "+movie);                                    // Show the movie selected
		t1.setBounds(30,10,width,20);                        // Set boundaries of text field
		t1.setHorizontalAlignment(SwingConstants.LEFT);                // Make text align to left

		//Configuring text field
		t2.setText("Language:  "+lang.getSelectedItem());                // Show the language selected
		t2.setBounds(10,40,width,20);                        // Set boundaries of text field
		t2.setHorizontalAlignment(SwingConstants.LEFT);                // Make text align to left

		//Configuring back button
		back = new JButton();
		back.setText("<Back");                               // Setting text back
		back.setBounds(10 , 180, 75, 25);   // Setting boundaries of button
		back.addActionListener(this);                    // Make the button call the action listener if it is pressed

		//Configuring next button
		next.setBounds(width - 95 , 180, 75, 25);   // Setting boundaries of button

		//Configuring date combo box
		date = new JComboBox<>(DateTime.getDates(5));       // Setting 5 days from current date in date choice
		date.setBounds(100,75,250,25);             // Setting boundaries of combo box
		date.setSelectedIndex(-1);                                // Setting default selected item to none
		date.addActionListener(this);                          // Adding an action listener, so when date is changed,
		// the action listener is called

		//Configuring time combo box
		time = new JComboBox<>(DateTime.getTimes((String)date.getSelectedItem())); // Setting the available show timings
		// in time combo box
		time.setBounds(100, 125, 250, 25);                       // Setting boundaries of combo box
		time.setSelectedIndex(-1);                                              // Setting default selected item to none

		//Configuring text field
		t3 = new JLabel();
		t3.setText("Date: ");                                   // Showing "Date:" before date combo box
		t3.setHorizontalAlignment(SwingConstants.CENTER);      // Align text to center
		t3.setBounds(0,75,100,25);            // Setting boundaries of text field

		//Configuring text field
		JLabel t4 = new JLabel();
		t4.setText("Time: ");                                   // Showing "Date:" before date combo box
		t4.setHorizontalAlignment(SwingConstants.CENTER);      // Align text to center
		t4.setBounds(0,125,100,25);           // Setting boundaries of text field

		// Adding elements to frame
		f.add(t1); f.add(date); f.add(time); f.add(t2); f.add(t3); f.add(t4); f.add(next); f.add(back);
	}

	/*
	 *   Method: sc3()
	 *   Parameters: none
	 *   Returns: void
	 *   Description:   This method paints scene 3 on frame.
	 *                  Scene 3 : A scene which lets the user choose seats.
	 *                            This scene has a next button to go to the next scene. [scene 4]
	 *                            This scene has a back button to go to the previous scene. [scene 2]
	 *                            This scene displays where the screen is.
	 *                            This scene also displays price of individual seats,
	 *                            when the mouse is hovered on top of them.
	 */

	private void sc3() {
		t1.setText("---------------- Screen here ----------------");        // Shows which way the screen is
		t1.setHorizontalAlignment(SwingConstants.CENTER);                  // Align text to center
		t1.setBounds(width/2 - 150,10,300,25);            // Setting boundaries of text field

		//Configuring next and back button
		next.setBounds(width - 95,10,75,25);        // Setting boundaries
		back.setBounds(10,10,75,25);               // of text field

		String[] seats_oc = db.getSeats();              // Array of all the seats which are already booked

		// Make seats and add them to frame
		int xs = (width-30)/seats.length;       //x-scale for drawing seats
		int ys = (height-80)/seats[0].length;   //y-scale for drawing seats
		for(int i = 0; i < seats.length;i++){
			for(int j = 0; j < seats[i].length;j++){
				String seatCode = ""+(char)(j+65)+(i+1); // get seat row
				JButton seat;                           // make seat button
				seat = new JButton();
				seat.setBounds(10 + (xs*i),40+(ys*j),xs-5,ys-5); // Setting boundaries of seat
				seat.setIcon(seatIcons[0]);                                     // Setting all seats as unselected
				seat.setText(seatCode);                                        // Setting seat number as button text
				seat.setMargin(new Insets(0,0,0,0));       // Making buttons margin-less
				seat.setFont(new Font("Consolas", Font.PLAIN, 10)); // Setting seat font
				seat.setHorizontalTextPosition(JButton.CENTER);              // Align text to center
				seat.setVerticalTextPosition(JButton.CENTER);               // Align text to center
				seat.addActionListener(this);                            // If seat is clicked, call action listener
				seat.setToolTipText("Rs. "+Database.price(seatCode));     // Show the price of seat when hovered on it
				f.add(seat);                                             // Add seat to frame
				seats[i][j] = seat;                                     // Store memory address of seat in array
			}
		}
		// For every entry in seats database, check if any entry is of the show the current user is booking
		for (String st : seats_oc) {
			/* Checking if any seats of this show is booked,
			 * If so, then marking them as booked and thus
			 * Unavailable for booking by current user.
			 */
			String[] tokens = st.split("\t");                   // Each entry has >= 6 tokens separated by tabs
			if (tokens.length < 6) continue;                        // All valid entries have at least 6 tokens
			if (tokens[0].equals(movies.getItemAt(movie_sel)))     // If entry's movie and current user's movie are same
				if (tokens[1].equals(lang.getItemAt(lang_sel)))   // If entry's lang and current user's lang are same
					if (tokens[2].equals(date.getItemAt(date_sel)))//If entry's date and current user's date are same
						if (tokens[3].equals(time.getItemAt(time_sel))) { // If timing are same
							// token[4] is name of user who booked the tickets
							for (int j = 5; j < tokens.length; j++) {   // all tokens from index 5 are seat numbers
								String seatID = tokens[j];
								int y = seatID.charAt(0) - 65;                  // get row of seat
								int x = Integer.parseInt(seatID.substring(1)) - 1;  // get column of seat
								seats[x][y].setEnabled(false);                // If seat was already booked earlier,
								seats[x][y].removeActionListener(this);    // then make it unavailable to current user
							}
						}

		}
		// Adding elements to frame
		f.add(t1); f.add(next); f.add(back);
		// Creating a new Array to store the seats which the current user shall choose
		seats_booked = new ArrayList<>();
	}

	/*
	 *   Method: sc4()
	 *   Parameters: none
	 *   Returns: void
	 *   Description:   This method paints scene 4 on frame.
	 *                  Scene 4 : A scene which tells the user that their seats have been booked.
	 *                            This scene has two buttons:
	 *                             * Print - to print the tickets of the booked seats
	 *                             * Close - to close the application
	 */

	private void sc4() throws IOException{

		//Configuring Text Field
		t1.setText("Tickets Booked Successfully!");
		t1.setHorizontalAlignment(SwingConstants.CENTER);           // Align text to center
		t1.setBounds(width/2 - 125,10,250,20);     // Set boundaries of text field

		//Configuring Text Field
		t2.setText("Your tickets have been booked successfully.");
		t2.setHorizontalAlignment(SwingConstants.CENTER);           // Align text to center
		t2.setBounds(width/2 - 200,50,400,20);     // Set boundaries of text field

		//Configuring Text Field
		t3.setText("You can now exit the window or print your tickets.");
		t3.setHorizontalAlignment(SwingConstants.CENTER);           // Align text to center
		t3.setBounds(width/2 - 200,90,400,20);     // Set boundaries of text field

		//Configuring next button
		next.setText("Close");                 // set text "close" instead of "next" as it is the last scene
		next.setBounds(width - 95 , 180, 75, 25);   // Set Boundaries of text field

		//Configuring print button
		print = new JButton("Print");                   // making a print button to print ticket
		print.setBounds(10 , 180, 75, 25); // setting boundaries of text field
		print.addActionListener(this);                  // call action listener if button is pressed

		//Adding elements to frame
		f.add(t1);  f.add(t2);  f.add(t3);  f.add(next);    f.add(print);

		String movie = movies.getItemAt(movie_sel);
		String lan = lang.getItemAt(lang_sel);
		String dat = date.getItemAt(date_sel);
		String tim = time.getItemAt(time_sel);
		db.setSeats(movie, lan, dat, tim, name, seats_booked);  // add the current booking to database
	}

	/*
	 *   Method: actionPerformed()
	 *   Parameters: ActionEvent ae
	 *   Returns: void
	 *   Description:   This method is invoked whenever any button is pressed or any date is selected.
	 *                  This method determines what to do when something is clicked based on what was clicked.
	 */

	public void actionPerformed(ActionEvent ae) {

		/*
		   If a date is selected, change the timing options accordingly.
		   Eg. if current date is selected, then only show timings which are still in the future.
		   */
		if (ae.getSource() == date){
			f.remove(time);
			time = new JComboBox<>(DateTime.getTimes((String) date.getSelectedItem()));
			time.setBounds(100, 125, 250, 25);
			time.setSelectedIndex(-1);
			f.add(time);
			f.revalidate();
			f.repaint();
		}

		// If print button is clicked, print the ticket

		if(ae.getSource() == print){
			PrintTicket.print();
		}

		/*
		 *
		 * If NEXT Button is clicked :
		 *       * Check which scene is currently being shown
		 *       * Take necessary actions
		 *       * Go to the next scene
		 *
		 */
		if (ae.getSource() == next) {  // if button is pressed

			// if currently were selecting movie and language [scene 1]
			if(scene==1){
				//Remember choices
				movie_sel = movies.getSelectedIndex();
				lang_sel = lang.getSelectedIndex();


				// Checking if any field is empty, i.e. user has not selected something, then displaying error dialog
				if(movie_sel < 0 && lang_sel < 0){                                          // If both not selected
					JOptionPane.showMessageDialog(f, "Please Choose Movie and Language",
							"Required Fields are empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(movie_sel < 0){                                                    // If movie not selected
					JOptionPane.showMessageDialog(f, "Please Choose Movie",
							"Required Field is empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				else if(lang_sel < 0){                                                  // If language not selected
					JOptionPane.showMessageDialog(f, "Please Choose Language",
							"Required Field is empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				System.out.println("Movie Selected: " + movies.getSelectedItem());
				System.out.println("Language Selected: " + lang.getSelectedItem());
				f.getContentPane().removeAll();                                         //Removing scene 1 elements
				sc2();                                                                  //Moving to next scene [scene 2]
			}


			// If currently choosing date and time [scene 2]
			else if (scene==2){
				//Remembering choices
				date_sel = date.getSelectedIndex();
				time_sel = time.getSelectedIndex();


				// Checking if any field is empty, i.e. user has not selected something, then displaying error dialog
				if(date_sel < 0 && time_sel < 0) {                                  //If both fields are empty
					JOptionPane.showMessageDialog(f, "Please Choose Date and Time",
							"Required Fields are empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(date_sel < 0) {                                                     //If date field is empty
					JOptionPane.showMessageDialog(f, "Please Choose Date",
							"Required Field is empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				if(time_sel < 0) {                                                      //If time field is empty
					JOptionPane.showMessageDialog(f, "Please Choose Time",
							"Required Field is empty", JOptionPane.ERROR_MESSAGE);
					return;
				}
				System.out.println("Date Selected: " + date.getSelectedItem());
				System.out.println("Time Selected: " + time.getSelectedItem());
				f.getContentPane().removeAll();                             //Removing option elements of scene 2
				sc3();                                                      //Moving to next scene [scene 3]
			}


			// If currently choosing seats [scene 3]
			else if (scene==3){

				// If no seat is chosen
				if(seats_booked.isEmpty()){
					JOptionPane.showMessageDialog(f, "Please choose at least one seat.",
							"No Seats Chosen", JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Else, Show Confirmation Dialog
				{
					StringBuilder seats = new StringBuilder();  // variable to store all the booked seats
					int price = 0;                              // variable to store total price of booking
					for (String seat : seats_booked) {           // for every seat booked
						seats.append(seat).append(" ");     // add the seat to the seats variable
						price += Database.price(seat);      // find total price of booking
					}
					String msg = "Are you sure you want to finalize the booking?" +
						"\nName: " + name +
						"\nMovie: " + movies.getItemAt(movie_sel) +
						"\nLanguage: " + lang.getItemAt(lang_sel) +
						"\nDate: " + date.getItemAt(date_sel) +
						"\nTime: " + time.getItemAt(time_sel) +
						"\nSeats: " + seats +
						"\nPrice: " + price;                        // message to be shown

					// show message
					int confirm = JOptionPane.showConfirmDialog(f,      // parent frame
							msg,                                        // message
							"Confirm Booking?",                     //title
							JOptionPane.YES_NO_OPTION,                  // options to be given to user
							JOptionPane.QUESTION_MESSAGE);              // type of message

					if (confirm == 1) return;                   // If user pressed NO, then do nothing

				}
				// If confirmed, then move to scene 4
				System.out.println("Seats Selected: " + seats);


				f.getContentPane().removeAll();                 //Removing scene 3 elements
				try {
					sc4();                                      //moving to next scene [scene 4]
				}catch (IOException ignored){ }                 // scene 4 tries to print ticket, which may throw IOE
			}

			// If currently in scene 4 [user pressed close button]
			if(scene == 4){
				// Exit
				System.exit(0);
			}

			// If next button is pressed and user went to a new scene, then revalidate the elements of the new scene
			f.revalidate();
			f.repaint();
			scene++;            // update scene counter
		}


		/*
		 *
		 * If BACK button is clicked :
		 *            * Check which scene is currently being shown
		 *            * Go to the previous scene
		 *
		 */
		if(ae.getSource() == back){
			f.getContentPane().removeAll(); //Removing all elements of current scene
			if(scene == 2) sc1();
			if(scene == 3) sc2();
			f.revalidate(); //repainting frame
			f.repaint();
			scene--;        // updating scene counter
		}

		/*
		 *
		 * If any of the seats are clicked:
		 *           * If seat was previously selected:
		 *                   * Deselect it
		 *                   * Remove it from array of selected seats
		 *          * Else :
		 *                  * Select it
		 *                  * Add it to array of selected seats
		 *
		 */

		if(seats[0][0]==null) return; // if seats are not yet initialized, don't do the following.
		for(int i = 0 ; i < seats.length;i++){
			for(int j = 0;j<seats[i].length;j++){
				JButton seat = seats[i][j];
				if(ae.getSource() == seat){
					if(seat.getIcon()==seatIcons[0]) {  // if seat was not selected
						seat.setIcon(seatIcons[1]);     // select seat
						seats_booked.add(""+(char)(j+65)+(i+1));    // add the seat to the array of selected seats
					}
					else if(seat.getIcon()==seatIcons[1]) { //if seat was selected
						seat.setIcon(seatIcons[0]);         //deselect seat
						seats_booked.remove(""+(char)(j+65)+(i+1));//remove the seat from the array of selected seats
					}
				}
			}
		}

	}
}
