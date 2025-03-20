package dal;

import model.Comment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class commentRatingDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    //NhuanPPCE180878-Add Comment
    public void addComment(String productId, String userId, String comment, int rating, String user_name) {
        String sql = "INSERT INTO product_comment (product_id, user_id, comment, rating, created_at, user_name) "
                + "SELECT ?, ?, ?, ?, GETDATE(), ? "
                + "WHERE EXISTS ("
                + "    SELECT 1 FROM bill b "
                + "    JOIN bill_detail bi ON b.bill_id = bi.bill_id "
                + "    WHERE b.user_id = ? AND bi.product_id = ? AND b.status=3"
                + ")";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, userId);
            ps.setString(3, comment);
            ps.setInt(4, rating);
            ps.setString(5, user_name);
            ps.setString(6, userId); // Tham số để kiểm tra user_id trong bảng bill
            ps.setString(7, productId); // Tham số để kiểm tra product_id trong bảng bill_detail
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public List<Comment> getCommentsByProductId(String productId) {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT DISTINCT c.* FROM product_comment c JOIN users u ON c.user_id = u.user_id\n"
                + "Inner Join bill b On b.user_id=c.user_id\n"
                + "Inner Join bill_detail bi On bi.bill_id = b.bill_id And bi.product_id= c.product_id\n"
                + "WHERE c.product_id = ? ORDER BY c.created_at DESC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            while (rs.next()) {
                Comment comment = new Comment();
                comment.setId(rs.getInt("id"));
                comment.setProductId(rs.getString("product_id"));
                comment.setUserId(rs.getInt("user_id"));
                comment.setComment(rs.getString("comment"));
                comment.setCreatedAt(rs.getTimestamp("created_at"));
                comment.setRating(rs.getInt(5));
                comment.setUser_name(rs.getString("user_name"));
                comments.add(comment);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return comments;
    }

    public List<Comment> getComment() {
        List<Comment> comments = new ArrayList<>();
        String sql = "SELECT * from product_comment";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                comments.add(new Comment(
                        rs.getInt("id"),
                        rs.getString("product_id"),
                        rs.getInt("user_id"),
                        rs.getString("comment"),
                        rs.getTimestamp("created_at"),
                        rs.getInt("rating"),
                        rs.getString("user_name")
                ));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return comments;
       
    }
    //NhuanPPCE180878-  detele comment
    public void deleteComment(int commentId) {
        String sql = "DELETE FROM product_comment WHERE id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, commentId);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public double getAverageRatingForProduct(String productId) {
        String sql = "SELECT ROUND(CAST(SUM(rating) AS FLOAT) / CAST(COUNT(rating) AS FLOAT), 1) AS avg_rating\n"
                + "FROM product_comment\n"
                + "WHERE product_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avg_rating");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return 0;
    }

    public boolean hasUserCommented(String productId, String userId) {
        String sql = "SELECT COUNT(*) FROM product_comment WHERE product_id = ? AND user_id = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, productId);
            ps.setString(2, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            Logger.getLogger(commentRatingDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return false;
    }

// Helper method to close connection, PreparedStatement, and ResultSet
    private void closeConnection() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Comment> getCommentsByRating(int rating) {
    List<Comment> comments = new ArrayList<>();
    String sql = "SELECT * FROM product_comment WHERE rating = ?";
    try {
        conn = new DBContext().getConnection();
        ps = conn.prepareStatement(sql);
        ps.setInt(1, rating);
        rs = ps.executeQuery();
        while (rs.next()) {
            comments.add(new Comment(
                    rs.getInt("id"),
                    rs.getString("product_id"),
                    rs.getInt("user_id"),
                    rs.getString("comment"),
                    rs.getTimestamp("created_at"),
                    rs.getInt("rating"),
                    rs.getString("user_name")
            ));
        }
    } catch (Exception e) {
        System.out.println(e);
    }
    return comments;
}

    
    

}
