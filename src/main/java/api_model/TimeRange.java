package api_model;

import java.util.Calendar;

/**
 * Created by vladniculescu on 08/12/2016.
 */
public class TimeRange {
    private int currentYear = Calendar.getInstance().get(Calendar.YEAR);

    private String endYear = Integer.toString(currentYear);
    private String startYear = Integer.toString(currentYear - 11);

    public TimeRange(){

    }
    public void setStartYear(String startYear){
        this.startYear = startYear;
    }
    public void setEndYear(String endYear){
        this.endYear = endYear;
    }
    public String getStartYear(){
        return startYear;
    }
    public String getEndYear(){
        return endYear;
    }

}
