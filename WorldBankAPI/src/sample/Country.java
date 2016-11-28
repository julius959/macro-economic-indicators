package sample;

/**
 * Created by Vlad-minihp on 28/11/2016.
 */
public class Country {
    private String name;
    private String code;
    public Country(String name,String code){
        this.name = name;
        this.code = code;
    }
    public String getName(){
        return name;
    }
    public String getCode(){
        return code;
    }

}
