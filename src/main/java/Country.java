
/**
 * Created by Vlad-minihp on 28/11/2016.
 */
public class Country {
    private String name;
    private String code;
    private String flagCode;
    public Country(String name,String code){
        this.name = name;
        this.code = code;
        this.flagCode = code;
    }
    public String getName(){
        return name;
    }
    public String getCode(){
        return code;
    }
    public String getFlag(){
        if(code=="usa") flagCode = "us";
        if(code=="deu") flagCode ="de";
        return "http://flagpedia.net/data/flags/mini/"+flagCode+".png";
    }
    public String getValue(String indicator, String year){
        //QUERY THE DATABASE AND RETRIEVE THE VALUE
        return "";
    }

}
