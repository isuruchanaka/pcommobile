package mud.per.iw.pcom;




        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;

        import com.google.android.gms.maps.CameraUpdateFactory;
        import com.google.android.gms.maps.GoogleMap;
        import com.google.android.gms.maps.MapView;
        import com.google.android.gms.maps.MapsInitializer;
        import com.google.android.gms.maps.OnMapReadyCallback;
        import com.google.android.gms.maps.model.BitmapDescriptorFactory;
        import com.google.android.gms.maps.model.CameraPosition;
        import com.google.android.gms.maps.model.LatLng;
        import com.google.android.gms.maps.model.Marker;
        import com.google.android.gms.maps.model.MarkerOptions;

        import androidx.fragment.app.Fragment;

public class fourthfragment extends Fragment implements
        GoogleMap.OnMarkerClickListener
{

    View view;
    Button firstButton;
    MapView mMapView;
    private GoogleMap googleMap;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment


        view = inflater.inflate(R.layout.fourthfragment, container, false);

        String strlat=getArguments().getString("lat");
        String strlon=getArguments().getString("longi");
        String strdesc=getArguments().getString("desc");
        String straddr=getArguments().getString("addr");
        Log.wtf("icw",strlon);
// get the reference of Button
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;
                mMap.setOnMarkerClickListener(fourthfragment.this::onMarkerClick);

                // For showing a move to my location button
                // googleMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                // For dropping a marker at a point on the Map

                if(!strlat.equals("null")&&!strlon.equals("null")) {
                    LatLng sydney = new LatLng(Double.parseDouble(strlat), Double.parseDouble(strlon));

                    googleMap.addMarker(new MarkerOptions().position(sydney).title(strdesc).snippet(straddr).icon(BitmapDescriptorFactory.fromResource(R.drawable.rodent)));
                    CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(8).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                // For zooming automatically to the location of the marker

                view.findViewById(R.id.progressBar).setVisibility(View.GONE);

            }




        });





        return view;
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {

        // Retrieve the data from the marker.
        Integer clickCount = (Integer) marker.getTag();
        Log.wtf("icw","Map clicked");
        // Check if a click count was set, then display the click count.
        if (clickCount != null) {
            clickCount = clickCount + 1;
            marker.setTag(clickCount);

        }

        // Return false to indicate that we have not consumed the event and that we wish
        // for the default behavior to occur (which is for the camera to move such that the
        // marker is centered and for the marker's info window to open, if it has one).
        return false;
    }


}
