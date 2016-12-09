package api_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Indicator {
    private String name;
    private String unit;
    private ArrayList<String> subIndicatorsCodes;
    private ArrayList<String> subIndicatorsLabels;
    private ArrayList<String> subIndicatorUnits;

    public Indicator(String name) {
        this.name = name;
        this.unit = unit;
        this.subIndicatorsCodes = new ArrayList<>();
        this.subIndicatorsLabels = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getSubIndicatorsCodes() {
        return subIndicatorsCodes;
    }

    public void setSubIndicatorsCodes(String[] codes) {
        this.subIndicatorsCodes = new ArrayList<String>(Arrays.asList(codes));
    }
    public void setSubIndicatorUnits(String[] units){
        this.subIndicatorUnits = new ArrayList<>(Arrays.asList(units));
    }
    public ArrayList<String> getSubIndicatorUnits(){
        return subIndicatorUnits;
    }
    public String getSubIndicatorCodeFromCode(String code){
        return subIndicatorUnits.get(subIndicatorsCodes.indexOf(code));
    }
    public ArrayList<String> getSubIndicatorsLabels() {
        return subIndicatorsLabels;
    }

    public void setSubIndicatorsLabels(String[] labels) {
        this.subIndicatorsLabels = new ArrayList<>(Arrays.asList(labels));
    }

    public String getLabelFromCode(String code) {
        return subIndicatorsLabels.get(subIndicatorsCodes.indexOf(code));
    }

    public String getCodeFromLabel(String label) {
        return subIndicatorsCodes.get(subIndicatorsLabels.indexOf(label));
    }

    public String getUnit(){
        return unit;
    }

}
