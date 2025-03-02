package model;
/**
 *
 * @author Nam
 */
public class Report {

    private int id_report;
    private int user_id;
    private String content_report;
    private String subject_report;
    private String user_email;

    public Report() {
    }

    public Report(int id_report, int user_id, String content_report, String subject_report, String user_email) {
        this.id_report = id_report;
        this.user_id = user_id;
        this.content_report = content_report;
        this.subject_report = subject_report;
        this.user_email = user_email;
    }

    public int getId_report() {
        return id_report;
    }

    public void setId_report(int id_report) {
        this.id_report = id_report;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getContent_report() {
        return content_report;
    }

    public void setContent_report(String content_report) {
        this.content_report = content_report;
    }

    public String getSubject_report() {
        return subject_report;
    }

    public void setSubject_report(String subject_report) {
        this.subject_report = subject_report;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

}
