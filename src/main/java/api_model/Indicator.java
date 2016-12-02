package api_model;

import java.util.ArrayList;
import java.util.Arrays;

public class Indicator {
    private String name;
    private ArrayList<String> subIndicatorsCodes;
    private ArrayList<String> subIndicatorsLabels;

    public Indicator(String name) {
        this.name = name;
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
}
