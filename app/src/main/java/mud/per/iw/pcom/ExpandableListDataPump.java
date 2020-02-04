package mud.per.iw.pcom;



import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(String getvisituid, List<Species> getspsdata, List<Products> getprdata) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> cricket=null;
String vtsdes = "";

    for (int i = 0; i < getspsdata.size(); i++) {
        try {
        cricket = new ArrayList<String>();
        vtsdes = "Species:" + getspsdata.get(i).getspdesc();
        cricket.add("Species:" + getspsdata.get(i).getspdesc());
        cricket.add("Visible:" + getspsdata.get(i).getage());
        cricket.add("Breed:" + getspsdata.get(i).getbreed());
        cricket.add("Average Consumed:" + getspsdata.get(i).getavgcns());
        cricket.add("Dead Presence:" + getspsdata.get(i).getdeadpr());
        cricket.add("Captured:" + getspsdata.get(i).getcnt());
        if(getprdata!=null){
            for (int i1 = 0; i1 < getprdata.size(); i1++) {
                if(getprdata.get(i1).getspid().equals(getspsdata.get(i).getspuid())) {
                    cricket.add("Product:" + getprdata.get(i1).getpdesc());

                    cricket.add("Quantity:" + getprdata.get(i1).getunit() + " " + getprdata.get(i1).getunitid());

                } }}

        expandableListDetail.put(vtsdes, cricket);
        }catch (Exception ex){
            Log.wtf("jkl", ex.toString());
        }
        }



//        Iterator myVeryOwnIterator = expandableListDetail.keySet().iterator();
//        while(myVeryOwnIterator.hasNext()) {
//            String key=(String)myVeryOwnIterator.next();
//          //  String value=(String)expandableListDetail.get(key);
//
//            Log.wtf("jkl", "Key: "+key);
//        }







        return expandableListDetail;
    }
}
