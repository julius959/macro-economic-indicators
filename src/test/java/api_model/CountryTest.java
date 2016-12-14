package api_model;

import javafx.scene.image.*;
import javafx.scene.image.Image;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created by Jihwan on 2016-12-13.
 */
public class CountryTest {
    ArrayList<String> coun = new ArrayList<String>(Arrays.asList("Argentina", "Australia", "Austria", "Brazil", "Canada", "China", "Croatia", "Cyprus", "Denmark",
            "Egypt", "Finland", "France", "Germany", "Greece", "Hungary", "India", "Indonesia", "Ireland", "Italy", "Japan", "Malaysia", "Mexico", "Monaco", "Morocco",
            "Netherlands", "Pakistan", "Poland", "Portugal", "Qatar", "Republic of Korea", "Romania", "Russian Federation", "Saudi Arabia", "Singapore",
            "South Africa", "Spain", "Sweden", "Switzerland", "Thailand", "Turkey", "Ukraine", "United Kingdom", "United States of America"));
    ArrayList<String> code = new ArrayList<String>(Arrays.asList("ar", "au", "at", "br", "ca", "cn", "hr", "cy", "dk", "eg", "fi", "fr", "deu", "gr", "hu", "in", "id", "ir", "it",
            "jp", "my", "mx", "mc", "ma", "nl", "pk", "pl", "pt", "qa", "kr", "ro", "ru", "sa", "sg", "za", "es", "se", "ch", "th", "tr", "ua", "gb", "usa"));

    @Test
    public void loadFlag() throws Exception {
        for(int i = 0; i < Model.getInstance().countries.length; i++) {
            assertNotNull(Model.getInstance().countries[i].loadFlag());
        }
    }

    @Test
    public void getName() throws Exception {
        for(int i = 0; i < Model.getInstance().countries.length; i++){
            assertEquals(Model.getInstance().countries[i].getName(), coun.get(i));
        }
    }

    @Test
    public void getCode() throws Exception {
        for(int i = 0; i < Model.getInstance().countries.length; i++){
            assertEquals(Model.getInstance().countries[i].getCode(), code.get(i));
        }
    }

}