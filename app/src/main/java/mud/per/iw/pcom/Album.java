package mud.per.iw.pcom;

public class Album {
    private String CustomerEmail;
    private  String sitedesp;
    private  String Address;
    private String CustomerName;
    private String CustomerContactNo;
    private String salu;
    private String surb;
    private String stat;
    private String uid;
    private String crdate;


    public Album() {
    }

    public Album(String CustomerEmail, String sitedesp,String Address,String CustomerName,String CustomerContactNo,String salu,String surb, String stat,String uid,String crdate) {
        this.CustomerEmail = CustomerEmail;
        this.sitedesp = sitedesp;
        this.Address = Address;
        this.CustomerName = CustomerName;
        this.CustomerContactNo = CustomerContactNo;
        this.salu = salu;
        this.surb = surb;
        this.stat = stat;
        this.uid = uid;
        this.crdate = crdate;

        this.uid=uid;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }
    public String getsitedesp() {
        return sitedesp;
    }
    public String getAddress() {
        return Address;
    }
    public String getCustomerName() {
        return CustomerName;
    }
    public String getCustomerContactNo() {
        return CustomerContactNo;
    }
    public String getsalu() {
        return salu;
    }
    public String getsurb() {
        return surb;
    }
    public String getstat() {
        return stat;
    }
    public String getuid() {
        return uid;
    }
    public String getcrdate() {
        return crdate;
    }

    public void setCustomerEmail(String CustomerEmail) {
        this.CustomerEmail = CustomerEmail;
    }
    public void setsitedesp(String sitedesp) {
        this.sitedesp = sitedesp;
    }
    public void setAddress(String Address) {
        this.Address = Address;
    }
    public void setCustomerName(String CustomerName) {
        this.CustomerName = CustomerName;
    }
    public void setCustomerContactNo(String CustomerContactNo) {
        this.CustomerContactNo = CustomerContactNo;
    }
    public void setsalu(String salu) {
        this.salu = salu;
    }
    public void setsurb(String surb) {
        this.surb = surb;
    }
    public void setstat(String stat) {
        this.stat = stat;
    }
    public void setuid(String uid) {
        this.uid = uid;
    }
    public void setcrdate(String crdate) {
        this.crdate = crdate;
    }




}