package utilities;

/**
 * The type Component.
 */
public class Component {
    private String nameComponent;
    private String unit;
    private String priceUnit;
    private int level;

    /**
     * Instantiates a new Component.
     */
    public Component() {}

    /**
     * Instantiates a new Component.
     *
     * @param nameComponent the name component
     * @param unit          the unit
     * @param priceUnit     the price unit
     * @param level         the level
     */
    public Component(String nameComponent, String unit, String priceUnit, int level) {
        this.nameComponent = nameComponent;
        this.unit = unit;
        this.priceUnit = priceUnit;
        this.level = level;
    }

    /**
     * Sets name component.
     *
     * @param nameComponent the name component
     */
    public void setNameComponent(String nameComponent) {
        this.nameComponent = nameComponent;
    }

    /**
     * Sets unit.
     *
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Sets price unit.
     *
     * @param priceUnit the price unit
     */
    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets name component.
     *
     * @return the name component
     */
    public String getNameComponent() {
        return nameComponent;
    }

    /**
     * Gets unit.
     *
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Gets price unit.
     *
     * @return the price unit
     */
    public String getPriceUnit() {
        return priceUnit;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }
}
