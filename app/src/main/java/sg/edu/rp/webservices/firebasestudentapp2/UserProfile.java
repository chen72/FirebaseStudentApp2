package sg.edu.rp.webservices.firebasestudentapp2;

import java.io.Serializable;

public class UserProfile implements Serializable {
    private String id;
    private String name;
    private String contactNo;
    private String hobbies;

    public UserProfile(String id, String name, String contactNo, String hobbies) {
        this.id = id;
        this.name = name;
        this.contactNo = contactNo;
        this.hobbies = hobbies;
    }
    public UserProfile(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }
}
