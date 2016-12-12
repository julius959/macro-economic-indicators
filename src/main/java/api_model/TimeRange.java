package api_model;

import java.util.Calendar;

/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 08/12/2016.
 */
public class TimeRange {
    /**
     * Integer that represents the current year configured on the machine
     */
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    /**
     * String that represents the end year used in the queries. The default value is the current year.
     */
    private String endYear = Integer.toString(currentYear);
    /**
     * String that represents the start year used in the query. By default is the current year - 11 (considering that there is no data available for the current year, so the application will display information for the last 10 finished years.
     */
    private String startYear = Integer.toString(currentYear - 11);
    /**
     * Constructor of the class
     */
    public TimeRange(){}

    /**
     * Setter for the start year for a specific query.
     * @param startYear The string representing the starting year for the data that has to be displayed.
     */
    public void setStartYear(String startYear){
        this.startYear = startYear;
    }

    /**
     * Setter for the end year for a specific query.
     * @param endYear The string representing the ending year for the data that has to be displayed.
     */
    public void setEndYear(String endYear){
        this.endYear = endYear;
    }

    /**
     * Getter fot the start year
     * @return Returns the starting year for a certain query.
     */
    public String getStartYear(){
        return startYear;
    }

    /**
     * Getter for the end year
     * @return Returns the ending year for a certain query.
     */
    public String getEndYear(){
        return endYear;
    }

}
