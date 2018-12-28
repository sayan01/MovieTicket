import java.util.Calendar;

public class DateTime {
    static String[] getDates(int noOfDays) {
        String[] dates = new String[noOfDays];
        Calendar c = Calendar.getInstance();
        for(int i = 0 ; i < noOfDays; i++) {
            String date = c.getTime().toString();
            date = date.substring(0, 10) + " " + date.substring(date.length() - 4);
            dates[i] = date;
            c.add(Calendar.DATE,1);
        }
        return dates;
    }

    static String[] getTimes(String date) {
        String[] times = new String[]{"07:00","13:30","16:45","23:00"};
        if(date == null) return times;      // If no date is specified, return default timings
        Calendar c = Calendar.getInstance();// else check if specified date is current date
        String current = c.getTime().toString();
        current = current.substring(0, 10) + " " + current.substring(current.length() - 4);
        if(date.equals(current)){   // If specified date is current date, then check if any showtime has already passed
            Calendar showtime = Calendar.getInstance();
            for(int i = times.length-1; i >=0;i--){
                showtime.set(Calendar.HOUR_OF_DAY,new Integer(times[i].substring(0,2)));
                showtime.set(Calendar.MINUTE,new Integer(times[i].substring(3)));
                if(showtime.before(c)){  // If the showtime has already passed, remove that and all preceding show times
                    String[] tmp = new String[times.length - i - 1];
                    System.arraycopy(times,i+1,tmp,0,tmp.length);
                    times = tmp;
                    break;
                }
            }
        }
        return times;
    }
}