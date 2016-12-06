package api_model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;
import javafx.scene.image.Image;

/**
 * Created by Vlad-minihp on 28/11/2016.
 */
public class Country {
    private String name;
    private String code;
    private String flagCode;

    public Country(String name, String code) {
        this.name = name;
        this.code = code;
        this.flagCode = code;
        changeFlagCodes();
        downlaodFlag(flagCode);
    }
    public Image  loadFlag(){
        Image flagImage = null;
        try{
            System.out.println("Trying to get the flag from local storage");
           flagImage = new Image(getClass().getClassLoader().getResourceAsStream(getLocalFlag()));
            System.out.println("Successfully retrieved the flag from local storage");
        }catch (Exception e){
            try{
                System.out.println("Failed to get the flag from local storage");
                System.out.println("Trying to get flag from online");
                flagImage = new Image(getOnlineFlag());
                System.out.println("Successfully retrieved the flag from online");
            }catch (Exception e2){
                System.out.println("Failed to retrieve the flag from online");
            }


       }
       return flagImage;
    }

    private void changeFlagCodes() {
        if (code == "usa") flagCode = "us";
        if (code == "deu") flagCode = "de";
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }


    private String getLocalFlag() {
        return "flags/" + flagCode + ".png";
    }

    private String getOnlineFlag() {
        return "http://flagpedia.net/data/flags/mini/" + flagCode + ".png";
    }

    private void downlaodFlag(String flagCode) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //  System.out.println("DOWNLOADING FLAG FOR "+flagCode);
                String downloadLink = "http://flagpedia.net/data/flags/mini/" + flagCode + ".png";
                try {
                    URL url = new URL(downloadLink);
                    InputStream is = url.openStream();
                    //  System.out.println("LINK IS FINE, DOWLOADED");
                    OutputStream os = new FileOutputStream("src/main/resources/flags/" + flagCode + ".png");
                    //  System.out.println("SAVED IN FODLER IS OK ");
                    byte[] b = new byte[2048];
                    int length;

                    while ((length = is.read(b)) != -1) {
                        os.write(b, 0, length);
                    }
                    is.close();
                    os.close();
                } catch (Exception e) {
                    System.out.println("Could not download flag for " + flagCode);
                }
            }
        }).start();


    }

}
