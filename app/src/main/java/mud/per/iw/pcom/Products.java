package mud.per.iw.pcom;



public class Products {
    private String puid;
    private  String pdesc;
    private  String psuid;
    private  String unitid;
    private  String unit;
    private String spid;
    public Products() {
    }

    public Products(String puid, String pdesc,String psuid,String unitid,String unit,String spid) {
        this.puid = puid;
        this.pdesc = pdesc;
        this.psuid=psuid;
        this.unitid=unitid;
        this.unit=unit;
        this.spid=spid;
    }

    public String getpuid() {
        return puid;
    }
    public String getpdesc() {
        return pdesc;
    }
    public String getunit() {
        return unit;
    }
    public String getunitid() {
        return unitid;
    }
    public String getpsuid() {
        return psuid;
    }
    public String getspid() {
        return spid;
    }
    public void setpuid(String puid) {
        this.puid = puid;
    }
    public void setpdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void setpsuid(String psuid) {
        this.psuid = psuid;
    }
}