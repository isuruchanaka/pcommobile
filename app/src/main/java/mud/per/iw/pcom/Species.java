package mud.per.iw.pcom;



public class Species {
    private String spuid;
    private  String breed;
    private  String age;
    private String spdesc;
    private String avgcns;
    private String deadpr;
    private String cnt;
    private String rmk;
    public Species() {
    }

    public Species(String spuid, String breed,String age,String spdesc, String avgcns, String deadpr, String cnt,String rmk) {
        this.spuid = spuid;
        this.breed = breed;
        this.age = age;
        this.spdesc = spdesc;
        this.avgcns = avgcns;
        this.deadpr = deadpr;
        this.cnt = cnt;
        this.rmk = rmk;
    }

    public String getspuid() {
        return spuid;
    }
    public String getbreed() {
        return breed;
    }
    public String getspdesc() {
        return spdesc;
    }
    public String getavgcns() {
        return avgcns;
    }
    public String getage() {
        return age;
    }
    public String getdeadpr() {
        return deadpr;
    }
    public String getcnt() {
        return cnt;
    }
    public String getrmk() {
        return rmk;
    }


    public void setspuid(String spuid) {
        this.spuid = spuid;
    }
    public void setbreed(String breed) {
        this.breed = breed;
    }
    public void setspdesc(String spdesc) {
        this.spdesc = spdesc;
    }
    public void setage(String age) {
        this.age = age;
    }
    public void setavgcns(String avgcns) {
        this.avgcns = avgcns;
    }
    public void setdeadpr(String deadpr) {
        this.deadpr = deadpr;
    }
    public void setcnt(String cnt) {
        this.cnt = cnt;
    }
    public void setrmk(String rmk) {
        this.rmk = rmk;
    }

}