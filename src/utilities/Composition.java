package utilities;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

/**
 * The type Composition.
 */
public class Composition {
    private String idComposition;
    private String idComponent;
    private String idProduct;
    private String idUnit;
    private double componentQuantity;

    /**
     * Instantiates a new Composition.
     */
    public Composition() {
    }

    /**
     * Instantiates a new Composition.
     *
     * @param idComposition     the id composition
     * @param idComponent       the id component
     * @param idProduct         the id product
     * @param idUnit            the id unit
     * @param componentQuantity the component quantity
     */
    public Composition(String idComposition, String idComponent, String idProduct, String idUnit, double componentQuantity) {
        this.idComposition = idComposition;
        this.idComponent = idComponent;
        this.idProduct = idProduct;
        this.idUnit = idUnit;
        this.componentQuantity = componentQuantity;
    }

    /**
     * Sets id composition.
     *
     * @param idComposition the id composition
     */
    public void setIdComposition(String idComposition) {
        this.idComposition = idComposition;
    }

    /**
     * Sets id component.
     *
     * @param idComponent the id component
     */
    public void setIdComponent(String idComponent) {
        this.idComponent = idComponent;
    }

    /**
     * Sets id product.
     *
     * @param idProduct the id product
     */
    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    /**
     * Sets id unit.
     *
     * @param idUnit the id unit
     */
    public void setIdUnit(String idUnit) {
        this.idUnit = idUnit;
    }

    /**
     * Sets component quantity.
     *
     * @param componentQuantity the component quantity
     */
    public void setComponentQuantity(double componentQuantity) {
        this.componentQuantity = componentQuantity;
    }

    /**
     * Gets id composition.
     *
     * @return the id composition
     */
    public String getIdComposition() {
        return idComposition;
    }

    /**
     * Gets id component.
     *
     * @return the id component
     */
    public String getIdComponent() {
        return idComponent;
    }

    /**
     * Gets id product.
     *
     * @return the id product
     */
    public String getIdProduct() {
        return idProduct;
    }

    /**
     * Gets id unit.
     *
     * @return the id unit
     */
    public String getIdUnit() {
        return idUnit;
    }

    /**
     * Gets component quantity.
     *
     * @return the component quantity
     */
    public double getComponentQuantity() {
        return componentQuantity;
    }

    /**
     * Gets all products. Includes these components
     *
     * @param co the connection of database
     * @return the all products
     * @throws SQLException the sql exception
     */
    public Vector<String> getAllProducts(Connection co) throws SQLException {
        Vector<String> allProducts = new Vector<>();
        try{
            String sql = "select * from firstlist";
            Statement statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                allProducts.add(resultSet.getString("idUnit"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    /**
     * Gets all under compositions. A specific product and his components. *
     *
     * @param idUnit the id unit
     * @param co     the connection of database
     * @return the all under compositions
     * @throws SQLException the sql exception
     */
    public double getAllUnderCompositions(String idUnit, Connection co) throws SQLException {
        double price = 0;
        try{
            String sql = "select * from listtree where idUnit ='"+ idUnit +"' and niveau = 2";
            Statement statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                if(resultSet.getDouble("price") == 0) {
                    price += this.getPrice(idUnit ,resultSet.getString("idProduct"), co);
                } else {
                    price += resultSet.getDouble("price")*resultSet.getDouble("quantity");
                }
            }
        }catch (Exception error) {
            error.printStackTrace();
        }
        return price;
    }

    /**
     * Gets all sub compositions.
     *
     * @param co the connection of database
     * @throws SQLException the sql exception
     */
    public void getAllSubCompositions(Connection co) throws SQLException {
        Vector<String> allProduct = this.getAllProducts(co);
        double prixderevient = 0;
        for (int i = 0; i < allProduct.size(); i++) {
            System.out.println(allProduct.elementAt(i));
            prixderevient = this.getAllUnderCompositions(allProduct.elementAt(i), co);
            System.out.println(prixderevient);
        }
    }

    /**
     * Gets price of the subcomponent of one product using 'start with' by oracle
     *
     * @param idUnit    the id unit
     * @param idProduct the id product
     * @param co        the connection of database
     * @return the price
     * @throws SQLException the sql exception
     */
    public double getPrice(String idUnit , String idProduct, Connection co) throws SQLException {
        double price = 0;
        try {
            String sql = "SELECT C2.nameComponent as Hierarchie, " +
                    "LEVEL as niveau, " +
                    "C2.unitPrice as price, " +
                    "C1.idUnit as nounit, " +
                    "C1.idProduct as noproduct, " +
                    "C2.nameComponent , " +
                    "C1.idComposition, " +
                    "C1.idComponent, " +
                    "C1.componentQuantity " +
                    "as quantity " +
                    "FROM composition C1 JOIN component C2 on C1.idProduct = C2.idComponent " +
                    "where C1.idUnit = '"+idUnit+"' and LEVEL = 2 " +
                    "START WITH C1.idProduct = '"+ idProduct +"' " +
                    "CONNECT BY PRIOR C1.idProduct = C1.idComponent " +
                    "group by C1.idUnit, C1.idProduct, C2.nameComponent, LEVEL, C2.unitPrice , " +
                    "C1.idComponent, C1.idComposition, C1.componentQuantity, LPAD(' --> ', 4 * (Level)) || C2.nameComponent " +
                    "order by idUnit, idProduct ";
            Statement statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                price += (resultSet.getDouble("price")*resultSet.getDouble("quantity"));
                if(resultSet.getDouble("price") == 0) {
                    price += getPrice(idUnit, resultSet.getString("idProduct"), co);
                }
            }
        }catch (Exception error) {
            error.printStackTrace();
        }
        return price;
    }

}
