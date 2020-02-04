package mud.per.iw.pcom;

public class Pair {
    private String Code;
    private  String SiteUId;
    private  String stypedes;
    public Pair() {
    }
    public Pair(String Code, String SiteUId,String stypedes){
        this.Code = Code;
        this.SiteUId = SiteUId;
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

    public void setCode(String Code) {
        this.Code = Code;
    }
    public void setstypedes(String stypedes) {
        this.stypedes = stypedes;
    }
    public void setSiteUId(String SiteUId) {
        this.SiteUId = SiteUId;
    }
}
