package utilities;

public class Component {
    private String nameComponent;
    private String unit;
    private String priceUnit;
    private int level;

    public Component() {}

    public Component(String nameComponent, String unit, String priceUnit, int level) {
        this.nameComponent = nameComponent;
        this.unit = unit;
        this.priceUnit = priceUnit;
        this.level = level;
    }

    public void setNameComponent(String nameComponent) {
        this.nameComponent = nameComponent;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNameComponent() {
        return nameComponent;
    }

    public String getUnit() {
        return unit;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public int getLevel() {
        return level;
    }
}
