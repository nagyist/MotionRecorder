package put.iwm.android.motionrecorder.registration;

import java.sql.Date;

/**
 * Created by Szymon on 2015-04-08.
 */
public class RegisterRequest {

    private String username;
    private String password;
    private String passwordRepeat;
    private boolean isMale;
    private Date dateOfBirth;
    private int weight;
    private int height;

    public RegisterRequest(String username, String password, String passwordRepeat, boolean isMale, Date dateOfBirth, int weight, int height) {
        this.username = username;
        this.password = password;
        this.passwordRepeat = passwordRepeat;
        this.isMale = isMale;
        this.dateOfBirth = dateOfBirth;
        this.weight = weight;
        this.height = height;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordRepeat() {
        return passwordRepeat;
    }

    public void setPasswordRepeat(String passwordRepeat) {
        this.passwordRepeat = passwordRepeat;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean isMale) {
        this.isMale = isMale;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

}
