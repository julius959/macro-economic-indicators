package api_model;

import java.io.*;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 28/11/2016.
 */
public class Country {
    /**
     * Name of the country
     */
    private String name;
    /**
     * ISO code of the country used for building the api query
     */
    private String code;
    /**
     * FLag code similiar to the country code with slightly little differences because of the API used.
     */
    private String flagCode;

    /**
     * Constructor that assigns the name and the code for the current country and calls several methods.
     * @param name Name of the country as a String
     * @param code Country ISO code as a String
     */
    public Country(String name, String code) {
        this.name = name;
        this.code = code;
        this.flagCode = code;
        changeFlagCodes();
        downlaodFlag(flagCode);
    }
    /**
     * Method that retrieves the image of the flag either from local storage or from online api
     * @return Return the Image of the flag.
     */
    public Image  loadFlag(){
        Image flagImage = null;
        try{
            flagImage = new Image(getClass().getClassLoader().getResourceAsStream(getLocalFlag()));
        }catch (Exception e){
            try{
                flagImage = new Image(getOnlineFlag());
            }catch (Exception e2){
            }
        }
       return flagImage;
    }
    /**
     * Method that changes the flag codes that are by default the same as the country codes. This is done according to the api that retrieves the flags.
     */
    private void changeFlagCodes() {
        if (code == "usa") flagCode = "us";
        if (code == "deu") flagCode = "de";
    }

    /**
     * Getter fot the name of the country
     * @return Returns the name of the country
     */
    public String getName() {
        return name;
    }

    /**
     * Getter fot the country code
     * @return Returns the code of the country
     */
    public String getCode() {
        return code;
    }

    /**
     * Gets the flag image from local storage
     * @return Returns the String containing the path in the resources folder of the required flag. The path is generated using the flag code.
     */
    private String getLocalFlag() {
        return "flags/" + flagCode + ".png";
    }

    /**
     * Gets the flag image from the online api
     * @return Returns the String containing the URL to the online image of the required flag. The link is generated using the flag code.
     */
    private String getOnlineFlag() {
        return "http://flagpedia.net/data/flags/mini/" + flagCode + ".png";
    }

    /**
     * Method that downloads the flag image from the online api from http://flagpedia.net/ into the local resources folder. The process is done in a new thread.
     * @param flagCode
     */
    private void downlaodFlag(String flagCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String downloadLink = "http://flagpedia.net/data/flags/mini/" + flagCode + ".png";
                try {
                    URL url = new URL(downloadLink);
                    InputStream is = url.openStream();
                    OutputStream os = new FileOutputStream("src/main/resources/flags/" + flagCode + ".png");
                    byte[] b = new byte[2048];
                    int length;
                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    os.close();
                } catch (Exception e) {
                }
            }
        }).start();
    }
}
