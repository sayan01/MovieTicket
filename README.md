# MovieTicket
 **MovieTicket** Project was made by : 
 
* ***Sayan Ghosh*** (18)
* ***Snehashis Ray*** (21)
* ***Sroyon Bhattacharya*** (25)

of XI-A (2018-19) – VMS, for Computer Project.

### Project Description :	
##### Language : Java SE 8
##### Dependencies and Libraries used : 
* [`Swing`][Swing] and [`AWT`][AWT] - for GUI [internal]
* [`TMDb API`][tmdb] - for getting name of recent movies [online]
* [`Unirest`][unirest] - for Sending and Recieving Network Requests [external]
* [`org.json`][org.json] - for Parsing JSON data [external]
* [`PrinterJob`][PrinterJob] (`java.awt`) - for Printing [internal]
* [`Calendar`][Calendar] (`java.util`) - for Date and Time management [internal]
* Other `util` and `io` classes [internal]

##### Description :	
The application lets the user **book** *seats* for *movie shows* for a *single hall*. [not real tickets]
It stores the seats already booked by previous users in a **local database** and thus marks them *unavailable* for the next users. It also stores the name of the users.
It lets the user **print** the *tickets* (on paper) after booking.
It **updates** the *local movie database* from the *internet*
### System Requirements :
##### OS : Windows
##### Java : 8 [ jdk 1.8.* ]
### Usage Instruction :
##### How to run :  
User must have Java installed on their Windows Computer and have the `\bin` directory of jdk installation set in "path" environmental variable.
* Simply run the `MovieTicket.jar` file to run the program.
In case it isn't automatically associated to be opened with the `Java(TM) Platform SE binary`, open its properties and change its default 'open with' to the `javaw.exe` present in the `/bin` directory where jdk is installed.
OR
* Open the command prompt to the project folder's root and type `java -jar MovieTicket.jar` 
optionally, you can add another parameter 'local' at the end to make the program run locally. This prevents it from updating the local database from internet. eg. `java -jar MovieTicket.jar local`
##### After launching : 
After program is launched, User is expected to : 
* Enter their **name** (optional)
* Choose which **movie** they wants to see
* Choose the **language**
* Choose the **date** of the show
* Choose the **timing** of the show
* Choose the **seats** they wants to book
Seats already booked are grayed out, user can select as many seats he wants (minimum 1)
* Confirm the booking
* User can also **print** the ticket (optional)
## Source :
The code is available in this github repo: https://github.com/sayan01/MovieTicket
It contains the following : 
* ##### `src/`  - source code for the program (`.java` files)
* ##### `assets/`  - assets needed for the program (`.png` files)
* ##### `data/`  - database needed for the program (`.txt` files)
* ##### `out/`  - executable classes (`.class` files)
* ##### `lib/`  - external libraries (`.class` files)
* ##### `MovieTicket.jar`  - main executable jar file

[tmdb]: https://www.themoviedb.org/documentation/api "TMDb API Documentation"
[unirest]: http://unirest.io/java.html "Unirest Website"
[org.json]: https://mvnrepository.com/artifact/org.json/json "org.json repository"
[PrinterJob]: https://docs.oracle.com/javase/7/docs/api/java/awt/print/PrinterJob.html "PrinterJob Documentation"
[Calendar]: https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html "Calender Documentation"
[Swing]: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html "Swing Documentation"
[AWT]: https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html "AWT Documentation"