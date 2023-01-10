package utilities;

/**
 * The type Product.
 */
public class Product {
    private String nameProduct;
    private String price;

    /**
     * Instantiates a new Product.
     *
     * @param nameProduct the name product
     * @param price       the price
     */
    public Product(String nameProduct, String price) {
        this.nameProduct = nameProduct;
        this.price = price;
    }

    /**
     * Sets name product.
     *
     * @param nameProduct the name product
     */
    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     * Gets name product.
     *
     * @return the name product
     */
    public String getNameProduct() {
        return nameProduct;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public String getPrice() {
        return price;
    }
}
