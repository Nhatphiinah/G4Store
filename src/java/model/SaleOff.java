/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author ZZ
 */


import java.util.Date;

public class SaleOff {
    private String sale_id;
    private String product_id;
    private float discount_percentage;
    private Date start_date;
    private Date end_date;

    public SaleOff() {}

    public SaleOff(String sale_id, String product_id, float discount_percentage, Date start_date, Date end_date) {
        this.sale_id = sale_id;
        this.product_id = product_id;
        this.discount_percentage = discount_percentage;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getSale_id() {
        return sale_id;
    }

    public void setSale_id(String sale_id) {
        this.sale_id = sale_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public float getDiscount_percentage() {
        return discount_percentage;
    }

    public void setDiscount_percentage(float discount_percentage) {
        this.discount_percentage = discount_percentage;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "SaleOff{" + "sale_id=" + sale_id + ", product_id=" + product_id + ", discount_percentage=" + discount_percentage + ", start_date=" + start_date + ", end_date=" + end_date + '}';
    }
}
