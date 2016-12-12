package api_model;

import java.util.ArrayList;
import java.util.Arrays;
/**
 * @author Vlad Niculescu
 * Created by Vlad Niculescu on 08/12/2016.
 */

public class Indicator {
    /**
     * Name of the indicator category that will be shown in the UI
     */
    private String name;
    /**
     * ArrayList of Indicator codes for all the indicators contained in the current category. Those are used for queries.
     */
    private ArrayList<String> subIndicatorsCodes;
    /**
     * ArrayList of Indicator names for all the indicators contained in the current category. Those are displayed in the main UI pane.
     */
    private ArrayList<String> subIndicatorsLabels;
    /**
     * ArrayList of Indicator units for all the indicators contained in the current category. Those are displayed in the charts panes.
     */
    private ArrayList<String> subIndicatorUnits;

    /**
     * Constructor that assigns the indicator category name and creates new ArrayLists
     * @param name Indicator category name
     */
    public Indicator(String name) {
        this.name = name;
    }

    /**
     * Getter for the indicator category name
     * @return Returns the name of the indicator category
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the indicator codes contained in the current indicator category
     * @return Returns an ArrayList of Strings containing the indicator codes
     */
    public ArrayList<String> getSubIndicatorsCodes() {
        return subIndicatorsCodes;
    }

    /**
     * Setter for the indicator codes contained in the current indicator category
     * @param codes Array of Strings representing the indicator codes that has to be stored in the current indicator category.
     */
    public void setSubIndicatorsCodes(String[] codes) {
        this.subIndicatorsCodes = new ArrayList<String>(Arrays.asList(codes));
    }

    /**
     * Setter for the indicator units contained in the current indicator category
     * @param units Array of Strings representing the indicator units that has to be stored in the current indicator category
     */
    public void setSubIndicatorUnits(String[] units){
        this.subIndicatorUnits = new ArrayList<>(Arrays.asList(units));
    }

    /**
     * Getter that retrieves the Indicator unit given the indicator code.
     * @param code Indicator code as a String
     * @return Returns a String representing the unit of measurement that corresponds to the given indicator code.
     */
    public String getSubIndicatorUnitFromCode(String code){
        return subIndicatorUnits.get(subIndicatorsCodes.indexOf(code));
    }

    /**
     * Getter for all the indicator codes contained in the current category
     * @return Returns an ArrayList of indicator names that are further displayed in the UI.
     */
    public ArrayList<String> getSubIndicatorsLabels() {
        return subIndicatorsLabels;
    }

    /**
     * Setter for indicator names.
     * @param labels Array of labels
     */
    public void setSubIndicatorsLabels(String[] labels) {
        this.subIndicatorsLabels = new ArrayList<>(Arrays.asList(labels));
    }

    /**
     * Getter for the indicator name given the indicator code
     * @param code Indicator code as a String
     * @return Returns  the indicator name as a String
     */
    public String getLabelFromCode(String code) {
        return subIndicatorsLabels.get(subIndicatorsCodes.indexOf(code));
    }

    /**
     * Getter for the indicator code given the indicator name
     * @param label name of the indicator as a String
     * @return Returns the indicator code
     */
    public String getCodeFromLabel(String label) {
        return subIndicatorsCodes.get(subIndicatorsLabels.indexOf(label));
    }


}
