/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;


public class Size {
    String product_id;
    String size;
    int quantity;

    public Size() {
    }

    public Size(String product_id, String size) {
        this.product_id = product_id;
        this.size = size;
    }
    
    public Size(String product_id, String size,int quantity) {
        this.product_id = product_id;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
    
}
