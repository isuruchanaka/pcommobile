package mud.per.iw.pcom;



import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListDataPump2 {
    public static HashMap<String, List<String>> getData(String getvisituid, List<Products> getspsdata) {
        HashMap<String, List<String>> expandableListDetail = new HashMap<String, List<String>>();
        List<String> cricket=null;
        String vtsdes = "";
        if(getspsdata!=null) {
            for (int i = 0; i < getspsdata.size(); i++) {
                cricket = new ArrayList<String>();
                cricket.add("Species:" + getspsdata.get(i).getpsuid());

                cricket.add("Quantity:" + getspsdata.get(i).getunit()+" "+ getspsdata.get(i).getunitid());
                vtsdes = getspsdata.get(i).getpdesc();

                Log.wtf("ljkll", vtsdes);
                expandableListDetail.put(vtsdes, cricket);
            }
        }






        return expandableListDetail;
    }
}
