package model;
/**
 *
 * @author Doanh
 */
public class User {

    int user_id;
    String user_name;
    String user_email;
    String user_pass;
    String isAdmin;
    String dateOfBirth;
    String address;
    String phoneNumber;
    boolean banned;
    String adminReason;
    String isStoreStaff;

    public User() {
    }

    public User(int user_id, String user_name, String user_email, String user_pass, String isAdmin, String dateOfBirth, String address, String phoneNumber, boolean banned, String adminReason,String isStoreStaff) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_pass = user_pass;
        this.isAdmin = isAdmin;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.banned = banned;
        this.adminReason = adminReason;
        this.isStoreStaff = isStoreStaff;
    }

    public User(String user_name) {
        this.user_name = user_name;
    }

    public boolean isBanned() {
        return banned;
    }

    public String getAdminReason() {
        return adminReason;
    }

    public void setAdminReason(String adminReason) {
        this.adminReason = adminReason;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIsStoreStaff() {
        return isStoreStaff;
    }

    public void setIsStoreStaff(String isStoreStaff) {
        this.isStoreStaff = isStoreStaff;
    }
}
