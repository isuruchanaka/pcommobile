package mud.per.iw.pcom;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MarkerClusterRenderer extends DefaultClusterRenderer<MyItem> {

    public MarkerClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<MyItem> clusterManager) {
        super(context, map, clusterManager);
    }

    @Override
    protected void onBeforeClusterItemRendered(MyItem item, MarkerOptions markerOptions) {
        // use this to make your change to the marker option
        // for the marker before it gets render on the map
        markerOptions.icon(BitmapDescriptorFactory.
                fromResource(R.drawable.rodent));
    }
}