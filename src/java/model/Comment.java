package model;
import java.sql.Timestamp;
/**
 *
 * @author Kien
 */
public class Comment {
    public int id;
    public String productId;
    public int userId;
    public String comment;
    public Timestamp createdAt;

    public int rating;
    public String user_name;
    public Comment() {
    }

    public Comment(int id, String productId, int userId, String comment, Timestamp createdAt, int rating, String user_name) {
        this.id = id;
        this.productId = productId;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
        this.rating = rating;
        this.user_name = user_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    

}