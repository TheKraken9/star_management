package utilities;

import connecting.Connecting;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class Composition {

    private String[] list = new String[5];
    private String idComposition;
    private String idComponent;
    private String idProduct;
    private String idUnit;
    private double componentQuantity;

    public Composition() {
    }

    public Composition(String idComposition, String idComponent, String idProduct, String idUnit, double componentQuantity) {
        this.idComposition = idComposition;
        this.idComponent = idComponent;
        this.idProduct = idProduct;
        this.idUnit = idUnit;
        this.componentQuantity = componentQuantity;
    }

    public void setIdComposition(String idComposition) {
        this.idComposition = idComposition;
    }

    public void setIdComponent(String idComponent) {
        this.idComponent = idComponent;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public void setIdUnit(String idUnit) {
        this.idUnit = idUnit;
    }

    public void setComponentQuantity(double componentQuantity) {
        this.componentQuantity = componentQuantity;
    }

    public String getIdComposition() {
        return idComposition;
    }

    public String getIdComponent() {
        return idComponent;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public String getIdUnit() {
        return idUnit;
    }

    public double getComponentQuantity() {
        return componentQuantity;
    }

    public Vector<String> getAllProducts(Connection co) throws Exception {
        Vector<String> allProducts = new Vector<>();
        try{
            String sql = "select * from firstlist";
            Statement statement = co.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                allProducts.add(resultSet.getString("idUnit"));
                //System.out.println(resultSet.getString("idUnit"));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return allProducts;
    }

    public double getAllUnderCompositions(String idUnit, Connection co) throws Exception {
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
                //System.out.println(resultSet.getString("idProduct") + resultSet.getDouble("price") + resultSet.getDouble("quantity"));
            }
        }catch (Exception error) {
            error.printStackTrace();
        }
        return price;
    }

    public void getAllSubCompositions(Connection co) throws Exception {
        Vector<String> allProduct = this.getAllProducts(co);
        double prixderevient = 0;
        for (int i = 0; i < allProduct.size(); i++) {
            System.out.println(allProduct.elementAt(i));
            prixderevient = this.getAllUnderCompositions(allProduct.elementAt(i), co);
            System.out.println(prixderevient);
        }
    }

    public double getPrice(String idUnit , String idProduct, Connection co) throws Exception {
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

    public Vector<Composition> getAllCompositions(Connection co) throws Exception {

        Vector<Composition> comps;
        try {
            String sql = "";
            co = Connecting.connectToOracle();
            Statement stmt = co.createStatement();
            comps = new Vector<Composition>();
            ResultSet resultSet = stmt.executeQuery("SELECT * FROM LISTTREE");
            while (resultSet.next()) {
                System.out.println(resultSet.getString("Hierarchie"));
                System.out.println(resultSet.getInt("niveau"));
                System.out.println(resultSet.getDouble("price"));
                System.out.println(resultSet.getDouble("componentquantity"));

            }
        } catch (SQLException e) {
            System.out.println("tsy mety");
            throw new RuntimeException(e);
        }
        return comps;
    }

}
