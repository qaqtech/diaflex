package ft.com.website;

import java.io.Serializable;

public class UserRegistrationInfo implements Serializable {
    @SuppressWarnings("compatibility:5291870285842260771")
    private static final long serialVersionUID = 1L;

    private String userId;
    private String password;
    private String name;
    private String firstName;
    private String lastName;
    private String companyName;
    private String designation;
    
    private String address;
    private String building;
    private String street;
    private Integer cityId;
    private String country;
    private String city;
    private String knowus;
    private String telephoneNo1;
    private String telephoneNo2;
    private String fax;
    private String mobile;
    private String email;
    private String biz;
    private String membership;
    private String reg_dte;
    private String skypeId;
    private String zip;
    private String birthdate;
    private String state;
    private String council_mem;
    private String verify="";

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDesignation() {
        return designation;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBuilding() {
        return building;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreet() {
        return street;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }

    public void setTelephoneNo1(String telephoneNo1) {
        this.telephoneNo1 = telephoneNo1;
    }

    public String getTelephoneNo1() {
        return telephoneNo1;
    }

    public void setTelephoneNo2(String telephoneNo2) {
        this.telephoneNo2 = telephoneNo2;
    }

    public String getTelephoneNo2() {
        return telephoneNo2;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getFax() {
        return fax;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setBiz(String biz) {
        this.biz = biz;
    }

    public String getBiz() {
        return biz;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getMembership() {
        return membership;
    }

    public void setSkypeId(String skypeId) {
        this.skypeId = skypeId;
    }

    public String getSkypeId() {
        return skypeId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setKnowus(String knowus) {
        this.knowus = knowus;
    }

    public String getKnowus() {
        return knowus;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setReg_dte(String reg_dte) {
        this.reg_dte = reg_dte;
    }

    public String getReg_dte() {
        return reg_dte;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getZip() {
        return zip;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public void setCouncil_mem(String council_mem) {
        this.council_mem = council_mem;
    }

    public String getCouncil_mem() {
        return council_mem;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getVerify() {
        return verify;
    }
}
