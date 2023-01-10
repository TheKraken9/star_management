package utilities;

public class Product {
    private String nameProduct;
    private String price;

    public Product(String nameProduct, String price) {
        this.nameProduct = nameProduct;
        this.price = price;
    }

    public void setNameProduct(String nameProduct) {
        this.nameProduct = nameProduct;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNameProduct() {
        return nameProduct;
    }

    public String getPrice() {
        return price;
    }
}
