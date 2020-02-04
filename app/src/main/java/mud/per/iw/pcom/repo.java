package mud.per.iw.pcom;


import java.util.List;

public class repo {
    private String visitd,visituid, VisitType,crdt;
    private List<Species> spsdata;
    private int thumbnail;
    List<Products> prdata;
    public repo() {
    }

    public repo(String visitd, String visituid,String VisitType,String crdt,List<Species> spsdata,List<Products> prdata) {
        this.visitd = visitd;
        this.VisitType = VisitType;
        this.visituid = visituid;
        this.crdt = crdt;
        this.spsdata = spsdata;
        this.prdata = prdata;
    }

    public String getvisitd() {
        return visitd;
    }
    public String getVisitType() {
        return VisitType;
    }
    public String getvisituid() {
        return visituid;
    }
    public String getcrdt() {
        return crdt;
    }
    public List<Species> getspsdata() {
        return spsdata;
    }
    public List<Products> getprdata() {
        return prdata;
    }

    public void setprdata(List<Products> prdata) {
        this.prdata = prdata;
    }
    public void setuid(String visituid) {
        this.visituid = visituid;
    }
    public void setcrdt(String crdt) {
        this.crdt = crdt;
    }

    public void setVisitType(String VisitType) {
        this.VisitType = VisitType;
    }
    public void setvisitd(String visitd) {
        this.visitd = visitd;
    }

//    public String getNumOfSongs() {
//        return numOfSongs;
//    }
//
//    public void setNumOfSongs(String numOfSongs) {
//        this.numOfSongs = numOfSongs;
//    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }
}