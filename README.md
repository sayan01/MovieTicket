# MovieTicket

### Project Description :
##### Language : Java SE 8
##### Dependencies and Libraries used :
* [`TMDb API`][tmdb] - for getting name of recent movies [online]
* [`Unirest`][unirest] - for Sending and Recieving Network Requests [external]
* [`org.json`][org.json] - for Parsing JSON data [external]

##### Description :
The application simulates a movie booking application. This does not actually book any tickets.
It stores the seats already booked by previous users in a **local database** and thus marks them *unavailable* for the next users. It also stores the name of the users.
It lets the user **print** the *tickets* (on paper) after booking.
It **updates** the *local movie database* from the *internet*
### System Requirements :
##### OS : Windows / Linux
##### Java : 8 [ jdk 1.8.* ] or above
### Usage Instruction :
##### How to run :
User must have jdk/jre installed on their Computer and have the `/bin` directory of jdk installation set in "path" environmental variable.
* Open terminal to the project folder's root and type `java  MovieTicket`
optionally, you can add another parameter 'local' at the end to make the program run locally. This prevents it from updating the local database from internet. eg. `java MovieTicket local`
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
The application is available here : https://github.com/sayan01/MovieTicket/archive/master.zip

The code is available in this github repo: https://github.com/sayan01/MovieTicket


[tmdb]: https://www.themoviedb.org/documentation/api "TMDb API Documentation"
[unirest]: http://unirest.io/java.html "Unirest Website"
[org.json]: https://mvnrepository.com/artifact/org.json/json "org.json repository"
[PrinterJob]: https://docs.oracle.com/javase/7/docs/api/java/awt/print/PrinterJob.html "PrinterJob Documentation"
[Calendar]: https://docs.oracle.com/javase/7/docs/api/java/util/Calendar.html "Calender Documentation"
[Swing]: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html "Swing Documentation"
[AWT]: https://docs.oracle.com/javase/7/docs/api/java/awt/package-summary.html "AWT Documentation"
