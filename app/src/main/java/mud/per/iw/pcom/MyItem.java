package mud.per.iw.pcom;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

public class MyItem implements ClusterItem {
    private final LatLng mPosition;
    private final String mTitle;
    private final String mSnippet;
    private final String mqrc;


    public MyItem(double lat, double lng, String title, String snippet,String qrc) {
        mPosition = new LatLng(lat, lng);
        mTitle = title;
        mSnippet = snippet;
        mqrc = qrc;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }


    public String getqr() {
        return mqrc;
    }


    @Override
    public String getSnippet() {
        return mSnippet;
    }
}