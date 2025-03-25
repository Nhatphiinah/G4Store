package dal;

import model.SaleOff;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleOffDAO extends DBContext {

    public List<SaleOff> getAllSaleOffs() {
        List<SaleOff> saleOffs = new ArrayList<>();
        String sql = "SELECT * FROM product_saleOFF";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                SaleOff saleOff = new SaleOff(
                        rs.getString("sale_id"),
                        rs.getString("product_id"),
                        rs.getFloat("discount_percentage"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date")
                );
                saleOffs.add(saleOff);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return saleOffs;
    }

    public void updateSaleOff(SaleOff saleOff) {
        String sql = "UPDATE product_saleOFF SET discount_percentage = ?, start_date = ?, end_date = ? WHERE sale_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setFloat(1, saleOff.getDiscount_percentage());
            ps.setDate(2, new java.sql.Date(saleOff.getStart_date().getTime()));
            ps.setDate(3, new java.sql.Date(saleOff.getEnd_date().getTime()));
            ps.setString(4, saleOff.getSale_id());

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertSaleOff(SaleOff saleOff) {
        String sql = "INSERT INTO product_saleOFF (sale_id, product_id, discount_percentage, start_date, end_date) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, saleOff.getSale_id());
            ps.setString(2, saleOff.getProduct_id());
            ps.setFloat(3, saleOff.getDiscount_percentage());
            ps.setDate(4, new java.sql.Date(saleOff.getStart_date().getTime()));
            ps.setDate(5, new java.sql.Date(saleOff.getEnd_date().getTime()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteSaleOff(String saleOffId) {
        String sql = "delete from product_saleOFF WHERE sale_id = ?";
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, saleOffId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
