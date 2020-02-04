package mud.per.iw.pcom;



public class station {
    private String Code;
    private  String SiteUId;
    private  String StationTypeUId;
    private String Description;
    private String Lat;
    private String Long;
    private String stype;
    private String UId;
    private String stypedes;


    public station() {
    }

    public station(String Code, String SiteUId,String StationTypeUId,String Description,String Lat,String Long,String stype, String UId,String stypedes) {
        this.Code = Code;
        this.SiteUId = SiteUId;
        this.StationTypeUId = StationTypeUId;
        this.Description = Description;
        this.Lat = Lat;
        this.Long = Long;
        this.stype = stype;
        this.UId = UId;
        this.stypedes = stypedes;

    }

    public String getCode() {
        return Code;
    }
    public String getstypedes() {
        return stypedes;
    }
    public String getSiteUId() {
        return SiteUId;
    }
    public String getStationTypeUId() {
        return StationTypeUId;
    }
    public String getDescription() {
        return Description;
    }
    public String getLat() {
        return Lat;
    }
    public String getLong() {
        return Long;
    }
    public String getstype() {
        return stype;
    }
    public String getUId() {
        return UId;
    }


    public void setCode(String Code) {
        this.Code = Code;
    }
    public void setstypedes(String stypedes) {
        this.stypedes = stypedes;
    }
    public void setSiteUId(String SiteUId) {
        this.SiteUId = SiteUId;
    }
    public void setStationTypeUId(String StationTypeUId) {
        this.StationTypeUId = StationTypeUId;
    }
    public void setDescription(String Description) {
        this.Description = Description;
    }
    public void setLat(String Lat) {
        this.Lat = Lat;
    }
    public void setLong(String Long) {
        this.Long = Long;
    }
    public void setstype(String stype) {
        this.stype = stype;
    }
    public void setUId(String UId) {
        this.UId = UId;
    }





}