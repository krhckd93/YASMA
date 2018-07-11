package assess.talview.com.yalview_yasma.user.room;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import assess.talview.com.yalview_yasma.user.room.submodels.Address;
import assess.talview.com.yalview_yasma.user.room.submodels.Company;
import assess.talview.com.yalview_yasma.user.room.submodels.Geo;

@Entity( tableName = "res_user")
public class UserModel {
    @PrimaryKey
    @NonNull
    private int id;

    private String name;
    private String username;
    private String email;
    private String phone;
    private String website;

    @Embedded(prefix = "company_")
    private Company company;
//
    @Embedded(prefix = "addr_")
    private Address address;


    public UserModel() { }

    @Ignore
    public UserModel(@NonNull int id, String name, String username, String email, String phone, String website, Company company, Address address) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
        this.company = company;
        this.address = address;
    }

    @NonNull
    public int getId() { return id; }

    public void setId(@NonNull int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getUsername() { return username; }

    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }

    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public String getWebsite() { return website; }

    public void setWebsite(String website) { this.website = website; }

    public Company getCompany() { return company; }

    public void setCompany(Company company) { this.company = company; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }


    /*@Override
    public String toString() {
        return "\n\n" + this.getId() + " " + this.getUsername() + " " + this.getName() + " " + this.getPhone() + " " + this.getEmail() + " " + this.getWebsite();
    }*/

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", website='" + website + '\'' +
                ", company=" + company +
                ", address=" + address +
                '}';
    }
}
