/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
/**
 *
 * @author 
 */
import java.util.ArrayList;
import java.util.List;


public class Cart {

    private int id;
    private String userId;
    private User user;
    List<Item> items;
    
    
    public Cart() {
        items = new ArrayList<>();
    }

    public Cart(List<Item> items) {
        this.items = items;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    
    
    // số lượng 1 sản phẩm trong giỏ - khách sẽ mua
    public int getQuantityById(String id) {
        return getItemById(id).getQuantity();
    }

    private Item getItemById(String id) {
        for (Item i : items) {
            if (i.getProduct().getProduct_id().equals(id)) {
                return i;
            }
        }
        return null;
    }
    private Item CheckItem(String id, String size, String color) {
        for (Item i : items) {
            if (i.getProduct().getProduct_id().equals(id) && i.size.equals(size) && i.color.endsWith(color)) {
                return i;
            }
        }
        return null;
    }

    // add 1 sản phẩm vào giỏ, nếu có rồi thì tăng số lượng
    public void addItem(Item t) {
        if (getItemById(t.getProduct().getProduct_id()) != null && CheckItem(t.getProduct().getProduct_id(),t.size, t.color) != null) {
            Item m = getItemById(t.getProduct().getProduct_id());
            m.setQuantity(m.getQuantity() + t.getQuantity());
        } else {
            items.add(t);
        }
    }
    public void updateQuantity(String productId, int newQuantity) {
    for (Item item : items) {
        if (item.getProduct().getProduct_id().equals(productId)) {
            item.setQuantity(newQuantity);
            return;
        }
    }
}
    //loại sản phẩm khỏi giỏ

    public void removeItem(String id) {
        if (getItemById(id) != null) {
            items.remove(getItemById(id));
        }
    }
    //tổng tiền của cả giỏ hàng – sẽ add vào bảng Order

public double getTotalMoney() {
    double total = 0;
    for (Item i : items) {
        double price = i.getProduct().getProduct_price();
        if (i.getProduct().getDiscount() > 0) {
            price = price * (1 - i.getProduct().getDiscount() / 100.0);
        }
        total += i.getQuantity() * price;
    }
    return total;
}

}
