package api_model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.*;
import java.net.URL;

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
    //    downlaodFlag(flagCode);
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


    public Image getFlag() {
        Image image = null;
        try {
            File localFile = new File("/resources/" + flagCode + ".png");
            image = ImageIO.read(localFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("FILE IMAGE FOR "+flagCode+ "IS "+image);
        return image;
    }

    private void downlaodFlag(String flagCode) {
        System.out.println("DOWNLOADING FLAG FOR "+flagCode);
        String downloadLink = "http://flagpedia.net/data/flags/mini/" + flagCode + ".png";
        try {
            URL url = new URL(downloadLink);
            InputStream is = url.openStream();
            System.out.println("LINK IS FINE, DOWLOADED");
            OutputStream os = new FileOutputStream("../resources/" + flagCode + ".png");
            System.out.println("SAVED IN FODLER IS OK ");

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

}
