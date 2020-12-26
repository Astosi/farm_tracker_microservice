package farmtracker.springbootmongodb;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Converter {

    SimpleDateFormat formatter;

    public Converter(){

        this.formatter = new SimpleDateFormat("yyyy-MM-dd");
    }

    public Calendar stringToCal(String dateToConvert){

         Calendar cal = Calendar.getInstance();

        try {
            Date date = formatter.parse(dateToConvert);
            cal.setTime(date);
        }
        catch (ParseException ex){
            System.out.println(ex.toString());
        }

        return cal;
    }


    public String addDays(Calendar startDate, int numberOfdays){
        System.out.println("method -> add days "+"\n -> number of days -->" + numberOfdays + "\n" + "before add ->" + startDate.get(Calendar.DAY_OF_MONTH));
        startDate.add(Calendar.DATE, numberOfdays);   //Add days
        System.out.println("after add -> " + startDate.get(Calendar.DAY_OF_MONTH));
        return calToString(startDate);        //Return String
    }

    public String addDays(String startDate, int numberOfdays){

        System.out.println("method -> add days "+"\n -> number of days -->" + numberOfdays + "\n" + "before add ->" + startDate);

        Calendar cal = stringToCal(startDate);
        cal.add(Calendar.DATE, numberOfdays);   //Add days

        System.out.println("after add -> " + cal.get(Calendar.DAY_OF_MONTH));

        return calToString(cal);        //Return String
    }


    public String subDays(Calendar startDate, int numberOfdays){

        System.out.println("method -> sub days "+"\n -> number of days -->" + numberOfdays + "\n" + "before sub ->" + startDate.get(Calendar.DAY_OF_MONTH));

        startDate.add(Calendar.DATE, -numberOfdays);   //Sub days

        System.out.println("after sub -> " + startDate.get(Calendar.DAY_OF_MONTH));
        return calToString(startDate);        //Return as String
    }

    public String calToString(Calendar dateToConvert){

        System.out.println("method -> calendar to string " + dateToConvert.get(Calendar.DAY_OF_MONTH));
        return formatter.format(dateToConvert.getTime());
    }

    public List<String> calToString(List<Calendar> dateToConvert){

        List<String> convertedList = new ArrayList<>() ;

        dateToConvert.forEach(cal -> convertedList.add(calToString(cal)));

        return convertedList;
    }

    public List<Calendar> stringToCal(List<String> dateToConvert){

        List<Calendar> convertedList = new ArrayList<>() ;
        dateToConvert.forEach(cal -> convertedList.add(stringToCal(cal)));

        return convertedList;
    }

    public String getDateToday() {
        Calendar cal = Calendar.getInstance();
        return formatter.format(cal.getTime());
    }
}
