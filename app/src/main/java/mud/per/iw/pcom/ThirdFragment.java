package mud.per.iw.pcom;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import static mud.per.iw.pcom.AlbumsAdapter.albumList;
import static mud.per.iw.pcom.frgmenthome.CodeList;

public class ThirdFragment extends Fragment implements
        GoogleMap.OnMarkerClickListener, ClusterManager.OnClusterItemInfoWindowClickListener<MyItem>, AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
             private Context mContext;
    View view;
    ImageView clbtn;
    MapView mMapView;
    private List<station> stationsListv;
    private GoogleMap googleMap;
             private String changeres;
    int stpos=0;
    private String rid;
    AutoCompleteTextView spinner;
    ArrayAdapter<String> adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment


        view = inflater.inflate(R.layout.third_fragment, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Contact Us", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                showMapTypeSelectorDialog();

            }
        });

        Switch onOffSwitch = (Switch)  view.findViewById(R.id.switch1);
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Boolean switchState = onOffSwitch.isChecked();
                if(switchState){
                    setUpClusterer();
                }else{
                    googleMap.clear();
                    mClusterManager.clearItems();
                   // spinner.setSelection(frgmenthome.siteList.size(),true);
//                    String stpos2 = frgmenthome.siteList1.get(0);
//
//
//                    getdmsg(stpos2);
//                    view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                    onOffSwitch.setChecked(false);
                }

            }
        });

        clbtn = (ImageView) view.findViewById(R.id.calc_clear_txt_Prise);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
          //  clbtn.setBackground(makeSelector(Color.parseColor("#ff7200")));
        }

        clbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                spinner.getText().clear();



            }
        });

                  spinner = (AutoCompleteTextView) view.findViewById(R.id.state);


                 String[] stockArr = new String[frgmenthome.siteList.size()];
                 stockArr = frgmenthome.siteList.toArray(stockArr);


//                 String[] a2 = new String[stockArr.length + 1];
//                 a2[0] = "All";
//                 System.arraycopy(stockArr, 0, a2, 1, stockArr.length);
       // stockArr[frgmenthome.siteList.size()]="-SELECT SITE-";
                 adapter = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr);

                 spinner.setAdapter(adapter);
                // spinner.setSelected(false);  // must
               ////  spinner.setSelection(0,true);  //must

       // spinner.setSelection(frgmenthome.siteList.size(),true);
                 spinner.setOnItemSelectedListener(this);
        spinner.setOnItemClickListener(this);
                 spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                         googleMap.clear();
                         mClusterManager.clearItems();


                      //   String selected = spinner.getSelectedItem().toString();
                     //     stpos = spinner.getSelectedItemPosition();
                         String stpos2 = frgmenthome.siteList1.get(stpos);
                         Log.wtf("erbnm", stpos2);
                       // if(stpos!=0) {
                            getdmsg(stpos2);
                            view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
                            Log.wtf("jkm", stpos2);

                      //  }
//                        else {
//                            Log.wtf("jkn", stpos2);
//                            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//                            setUpClusterer();
//
//                        }
                         onOffSwitch.setChecked(false);
                     }
                     @Override
                     public void onNothingSelected(AdapterView<?> arg0) {
                     }
                 });



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
                mMap.setOnMarkerClickListener(ThirdFragment.this::onMarkerClick);

                // For showing a move to my location button
               // googleMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                // For dropping a marker at a point on the Map
//                LatLng sydney = new LatLng(-34, 151);
//                googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description").icon(BitmapDescriptorFactory.fromResource(R.drawable.mouse)));
//
//                // For zooming automatically to the location of the marker
//                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(3).build();
//                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               // String stpos2 = frgmenthome.siteList1.get(stpos);
                //getdmsg(stpos2);
                  setUpClusterer();

                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {

                        LinearLayout info = new LinearLayout(mContext);
                        info.setOrientation(LinearLayout.VERTICAL);

                        TextView title = new TextView(mContext);
                        title.setTextColor(Color.BLACK);
                        title.setGravity(Gravity.CENTER);
                        title.setTypeface(null, Typeface.BOLD);
                        title.setText(marker.getTitle());

                        TextView snippet = new TextView(mContext);
                        snippet.setTextColor(Color.GRAY);
                        snippet.setText(marker.getSnippet());

                        info.addView(title);
                        info.addView(snippet);

                        return info;
                    }
                });
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

             // Declare a variable for the cluster manager.
             private ClusterManager<MyItem> mClusterManager;

             private void setUpClusterer() {
                 // Position the map.
                 if(googleMap!=null) {
                     googleMap.clear();
                     if(mClusterManager!=null) {
                         mClusterManager.clearItems();
                     }
                  //   googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-26.0596013, 139.1578578), 3));

                     // Initialize the manager with the context and the map.
                     // (Activity extends context, so we can pass 'this' in the constructor.)
                     mClusterManager = new ClusterManager<MyItem>(mContext, googleMap);
                     mClusterManager.setRenderer(new MarkerClusterRenderer(mContext, googleMap, mClusterManager));
                     // Point the map's listeners at the listeners implemented by the cluster
                     // manager.
                     googleMap.setOnCameraIdleListener(mClusterManager);
                     googleMap.setOnMarkerClickListener(mClusterManager);
                     googleMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

                     googleMap.setOnInfoWindowClickListener(mClusterManager); //added
                     mClusterManager.setOnClusterItemInfoWindowClickListener(this);


                     // Add cluster items (markers) to the cluster manager.
                     // getdmsg("");
                     addItems();
                 }
             }

             private void addItems() {

                 // Set some lat/lng coordinates to start with.
                 double lat = 0;
                 double lng = 0;

                 // Add ten cluster items in close proximity, for purposes of this example.
                 for (int i = 0; i < CodeList.size(); i++) {

                    Code album =CodeList.get(i);
                    // Code album=  getalbum(albumList.get(i).getuid());
                    // Log.wtf("icj",album.getUId());
                     if(album !=null) {
                         if (!album.getLat().equals("null") && !album.getLong().equals("null") && !album.getLat().equals("") && !album.getLong().equals("null")) {
                             lat = Double.parseDouble(album.getLat());
                             lng = Double.parseDouble(album.getLong());
                             if(i==0) {
                                 CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(3).build();
                                 googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                             }
                             MyItem offsetItem = new MyItem(lat, lng, album.getstypedes(), "Site :" + album.getDescription().toString(), album.getUId());
                             mClusterManager.addItem(offsetItem);
                         }

                     }

                 }
                 view.findViewById(R.id.progressBar).setVisibility(View.GONE);
             }
    Code getalbum(String codeIsIn) {
        for(Code carnet : CodeList) {
            if(carnet.getSiteUId().equals(codeIsIn)) {
                return carnet;
            }
        }
        return null;
    }
    private void setUpClusterer1() {
        // Position the map.
      //  googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-26.0596013, 139.1578578), 3));

        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = new ClusterManager<MyItem>(mContext,googleMap);
        mClusterManager.setRenderer(new MarkerClusterRenderer(mContext, googleMap, mClusterManager));
        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        googleMap.setOnCameraIdleListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);
        googleMap.setInfoWindowAdapter(mClusterManager.getMarkerManager());

        googleMap.setOnInfoWindowClickListener(mClusterManager); //added
        mClusterManager.setOnClusterItemInfoWindowClickListener(this);

        googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                LinearLayout info = new LinearLayout(mContext);
                info.setOrientation(LinearLayout.VERTICAL);

                TextView title = new TextView(mContext);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());

                TextView snippet = new TextView(mContext);
                snippet.setTextColor(Color.GRAY);
                snippet.setText(marker.getSnippet());

                info.addView(title);
                info.addView(snippet);

                return info;
            }
        });

        // Add cluster items (markers) to the cluster manager.
        // getdmsg("");
        addItems1();
    }

    private void addItems1() {

        // Set some lat/lng coordinates to start with.
        double lat = 0;
        double lng = 0;

        // Add ten cluster items in close proximity, for purposes of this example.
        for (int i = 0; i < stationsListv.size(); i++) {
try {
    station album = stationsListv.get(i);

    Log.wtf("icjo", frgmenthome.siteList.get( stpos).toString());
    if (!album.getLat().equals("null") && !album.getLong().equals("null") && !album.getLat().equals("") && !album.getLong().equals("null")) {
        lat = Double.parseDouble(album.getLat());
        lng = Double.parseDouble(album.getLong());
        if(i==0) {
            CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(lat, lng)).zoom(3).build();
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
        MyItem offsetItem = new MyItem(lat, lng, album.getDescription().toUpperCase(), "Site :"+frgmenthome.siteList.get(stpos).toString(), album.getUId());
        mClusterManager.addItem(offsetItem);
        mClusterManager.cluster();
    }

}
catch (Exception ex){

    Log.wtf("icj", ex.toString());
}

        }
    }


             @Override
             public void onClusterItemInfoWindowClick(MyItem myItem) {

                 //Cluster item InfoWindow clicked, set title as action
                 Log.wtf("icw1",myItem.getTitle());
                 AppCompatActivity activity = (AppCompatActivity) view.getContext();
                 Fragment myFragment = new fragmentfifth();
                 Bundle bundle=new Bundle();
                 bundle.putString("stationuid",myItem.getqr());
                 // bundle.putString("longi",album.getlongi());

                 myFragment.setArguments(bundle);
                 activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myFragment).addToBackStack(null).commit();

                 //You may want to do different things for each InfoWindow:
                 if (myItem.getTitle().equals("some title")){

                     //do something specific to this InfoWindow....

                 }

             }
             private static final CharSequence[] MAP_TYPE_ITEMS =
                     {"Road Map", "Satellite", "Terrain", "Hybrid"};

             private void showMapTypeSelectorDialog() {
                 // Prepare the dialog by setting up a Builder.
                 final String fDialogTitle = "Select Map Type";
                 AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                 builder.setTitle(fDialogTitle);

                 // Find the current map type to pre-check the item representing the current state.
                 int checkItem = googleMap.getMapType() - 1;

                 // Add an OnClickListener to the dialog, so that the selection will be handled.
                 builder.setSingleChoiceItems(
                         MAP_TYPE_ITEMS,
                         checkItem,
                         new DialogInterface.OnClickListener() {

                             public void onClick(DialogInterface dialog, int item) {
                                 // Locally create a finalised object.

                                 // Perform an action depending on which item was selected.
                                 switch (item) {
                                     case 1:
                                         googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                                         break;
                                     case 2:
                                         googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                                         break;
                                     case 3:
                                         googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                                         break;
                                     default:
                                         googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                 }
                                 dialog.dismiss();
                             }
                         }
                 );

                 // Build the dialog and show it.
                 AlertDialog fMapTypeDialog = builder.create();
                 fMapTypeDialog.setCanceledOnTouchOutside(true);
                 fMapTypeDialog.show();
             }
             @Override
             public void onAttach(Context context) {
                 super.onAttach(context);
                 mContext = context;
             }


    private void prepareAlbums() {



        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;
            stationsListv = new ArrayList<>();
            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String Code = we1.get("Code").toString();
                String SiteUId = we1.get("SiteUId").toString();
                String StationTypeUId = we1.get("StationTypeUId").toString();
                String Description = we1.get("Description").toString();
                String Lat = we1.get("Lat").toString();
                String Long = we1.get("Long").toString();
                String stype = we1.get("stype").toString();
                String UId = we1.get("UId").toString();
                String stypedes = we1.get("stypedes").toString();


                station a = new station(Code,SiteUId,StationTypeUId,Description,Lat,Long,stype,UId,stypedes);
                stationsListv.add(a);

                b++;
            }

            view.findViewById(R.id.progressBar).setVisibility(View.GONE);
            setUpClusterer1();


        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }

    public String getdmsg(String  val1 ){
        String url = mContext.getResources().getString( R.string.weburl8)+"?id="+val1;
        view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepareAlbums();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("CameraDemo", error.toString());



                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       // Spinner spinner = (Spinner)view.findViewById(R.id.state);

        Log.wtf("CameraDemo", "");


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Log.wtf("CameraDemo", "");
        String tid="";
        String itemName = adapter.getItem(i);
       String[] sdfgh= itemName.split("\n");
        spinner.setSelection(0);
        for(Album carnet : albumList) {
            if (carnet.getsitedesp().trim().contains(sdfgh[0].trim())) {
                tid = carnet.getuid();
                break;
            }
        }

        googleMap.clear();
        mClusterManager.clearItems();
        //String stpos2 = frgmenthome.siteList1.get(i);
        getdmsg(tid);
        //view.findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
    }
}
