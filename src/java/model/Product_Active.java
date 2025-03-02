package model;

/**
 *
 * @author An
 */
public class Product_Active {
    private String product_id;
    private String acitve;

    public Product_Active() {
    }

    public Product_Active(String product_id, String acitve) {
        this.product_id = product_id;
        this.acitve = acitve;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getAcitve() {
        return acitve;
    }

    public void setAcitve(String acitve) {
        this.acitve = acitve;
    }

}