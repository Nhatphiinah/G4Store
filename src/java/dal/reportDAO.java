package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Report;


public class reportDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<Report> getAll() {
        List<Report> list = new ArrayList<>();
        String sql = "Select r.id_report,r.user_id,r.content_report,r.subject_report,u.user_email From report r INNER JOIN users u On u.user_id=r.user_id";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Report(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public void InsertReport(String user_id, String content_report, String subject_report, String user_email) {
        try {
            String sql = "INSERT INTO [dbo].[report]\n"
                    + "           ([user_id]\n"
                    + "           ,[content_report]\n"
                    + "           ,[subject_report]\n"
                    + "           ,[user_email])\n"
                    + "     VALUES (?,?,?,?)";
              conn = new DBContext().getConnection();
                ps = conn.prepareStatement(sql);
                ps.setString(1, user_id); 
                ps.setString(2,content_report );
                ps.setString(3, subject_report); 
                ps.setString(4, user_email); 
                ps.executeUpdate();
        } catch (Exception e) {
        }
    }
    public void deleteReport(int reportId) {
        try {
            String sql = "DELETE FROM report WHERE id_report = ?";
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setInt(1, reportId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        reportDAO dao = new reportDAO();
        System.out.println(dao.getAll().get(0).getUser_email());
    }
}