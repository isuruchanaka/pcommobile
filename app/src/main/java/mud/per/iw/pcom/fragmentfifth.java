package mud.per.iw.pcom;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static mud.per.iw.pcom.AlbumsAdapter.albumList;

import static mud.per.iw.pcom.StationAdapter.statoinList;

public class fragmentfifth extends Fragment {

    private RecyclerView recyclerView;

    private List<repo> repodata;
    private ExpandableRecyclerAdapter adapter2;
    public List<Species> spsdata;
    public List<Products> prdata;
    View view;
    View view1;
    MapView mMapView;
    private GoogleMap googleMap;
    Button firstButton;

    private String changeres;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

// get the reference of Button
//        firstButton = (Button) view.findViewById(R.id.firstButton);
//// perform setOnClickListener on first Button
//        firstButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//// display a message by using a Toast
//                Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
//            }
//        });



        view = inflater.inflate(R.layout.fifthfragment, container, false);


       // utils = new Utils(this);

        // Initilizing Grid View


        // loading all image paths from SD card
       // imagePaths = utils.getFilePaths();



        String strcd=getArguments().getString("stationuid");
        station al=  getalbum(strcd);
        if(al!=null) {


            Album a2 = getalbum2(al.getSiteUId());
            Log.wtf("bnb", strcd);

            getdmsg(al.getUId());

            TextView sdesc = (TextView) view.findViewById(R.id.descp);
            sdesc.setText(al.getDescription());
            TextView stp = (TextView) view.findViewById(R.id.stp);
            stp.setText("Site Type: "+al.getstypedes());
            TextView suburb = (TextView) view.findViewById(R.id.suburbs);
            suburb.setText(a2.getsurb());
            TextView state = (TextView) view.findViewById(R.id.states);
            state.setText(a2.getstat());
            TextView stadress = (TextView) view.findViewById(R.id.saddress);
            stadress.setText(a2.getAddress());
            repodata = new ArrayList<>();


            String strlat = al.getLat();
            String strlon = al.getLong();
            String strdesc = al.getDescription();
            String straddr = a2.getAddress();

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
                    mMap.setOnMarkerClickListener(fragmentfifth.this::onMarkerClick);

                    // For showing a move to my location button
                    // googleMap.setMyLocationEnabled(true);
                    mMap.getUiSettings().setMapToolbarEnabled(true);
                    mMap.getUiSettings().setZoomControlsEnabled(true);
                    mMap.getUiSettings().setMapToolbarEnabled(true);
                    // For dropping a marker at a point on the Map

                    if (!strlat.equals("null") && !strlon.equals("null")) {
                        LatLng sydney = new LatLng(Double.parseDouble(strlat), Double.parseDouble(strlon));

                        googleMap.addMarker(new MarkerOptions().position(sydney).title(strdesc).snippet(straddr).icon(BitmapDescriptorFactory.fromResource(R.drawable.rodent)));
                        CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(13).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                    }
                    // For zooming automatically to the location of the marker


                }


            });


            adapter2 = new ExpandableRecyclerAdapter(repodata);
            recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
            recyclerView.setHasFixedSize(true);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);

//fetch data and on ExpandableRecyclerAdapter
            recyclerView.setAdapter(adapter2);
            DefaultItemAnimator animator = new DefaultItemAnimator() {
                @Override
                public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                    return true;
                }
            };
            recyclerView.setItemAnimator(animator);
            recyclerView.getLayoutManager().smoothScrollToPosition(recyclerView, null, 0);
            //recyclerView.setVisibility(View.GONE);
            //view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        }
else{
            Toast.makeText(getActivity(), "No data!", Toast.LENGTH_LONG).show();
            Fragment myFragment = new SecondFragment();

            AppCompatActivity activity = (AppCompatActivity) view.getContext();

            activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myFragment).addToBackStack(null).commit();
        }
        return view;
    }


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


    station getalbum(String codeIsIn) {
        for(station carnet : statoinList) {
            if(carnet.getUId().equals(codeIsIn)) {
                return carnet;
            }
        }
        return null;
    }
    Album getalbum2(String codeIsIn) {
        for(Album carnet : albumList) {
            if(carnet.getuid().equals(codeIsIn)) {
                return carnet;
            }
        }
        return null;
    }

    repo getalbum3(String codeIsIn) {
        for(repo carnet : repodata) {
            if(carnet.getvisituid().equals(codeIsIn)) {
                return carnet;
            }
        }
        return null;
    }
    private void prepare() {



        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;
          //  recyclerView.getLayoutParams().height=200*we.length();
            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);
                String visituid = we1.get("UId").toString();
                String visitd = we1.get("vsitds").toString();
                String VisitType = we1.get("VisitType").toString();
if(VisitType.equals("1")){
    VisitType="Initial Visit";
}
else{
    VisitType="Visit";
}
                String crdt="";
                if(we1.get("EffectiveDate").toString()!="null"){
                    crdt =we1.get("EffectiveDate").toString().split("T")[0];
                }


                Log.wtf("lll",visitd);

                getdmsg1(visitd,visituid,VisitType,crdt);
                getdmsg2(visituid);
                b++;
            }

//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }



    public String getdmsg(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl14)+"?id="+val1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepare();
                        Log.wtf("nmn", url);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("nmn", error.toString());

                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }
    private void prepare1(String val,String visitd, String visituid1, String visitType, String crdt1) {



        try{
            spsdata = new ArrayList<>();
            JSONObject dbovh = null;

            dbovh = new JSONObject(val);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);
                String visituid = we1.get("UId").toString();

                String visitsp = we1.get("Description").toString();
                String visitbr = we1.get("Breed").toString();
                String visitag = we1.get("SpeciesAge").toString();
                String visicnt = we1.get("Count").toString();

                String visitcns = we1.get("AverageConsumed").toString();
                String visitdr = we1.get("DeadPresence").toString();
                 String spuid = we1.get("UId1").toString();
                String crdt="";
                if(we1.get("EffectiveDate").toString()!="null"){
                    crdt =we1.get("EffectiveDate").toString().split("T")[0];
                }

                Species a = new Species(spuid,visitbr,visitag,visitsp,visitcns,visitdr,visicnt,visituid1 );
                spsdata.add(a);
                Log.wtf("uiu",  visituid+"**"+visitsp);

                b++;
            }
            repo a1 = new repo( visitd,visituid1,visitType,crdt1,spsdata,prdata);
            repodata.add(a1);
            adapter2.notifyDataSetChanged();

            DefaultItemAnimator animator = new DefaultItemAnimator() {
                @Override
                public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                    return true;
                }
            };
            recyclerView.setItemAnimator(animator);
            recyclerView.getRecycledViewPool().setMaxRecycledViews(0, 0);
            recyclerView.setVisibility(View.VISIBLE);
            // InitilizeGridLayout(imagePaths.size(),vh.gridView);
            // Gridview adapter
            // Initilizeexp(vv.refg);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,    LinearLayoutManager.HORIZONTAL, false);
//            vv.refg.setLayoutManager(linearLayoutManager);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
//            vv.refg.setLayoutManager(layoutManager);
//            adapter5 = new newExpandsps(context,spsdata);
//
//            // setting grid view adapter
//            vv.refg.setAdapter(adapter5);
//            DefaultItemAnimator animator = new DefaultItemAnimator() {
//                @Override
//                public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
//                    return true;
//                }
//            };
//            vv.refg.setItemAnimator(animator);
//            vv.refg.getRecycledViewPool().setMaxRecycledViews(0, 0);
            // adapter5.notifyItemRangeInserted(0,spsdata.size());
            // Log.wtf("ghj", spsdata.get(1).getspdesc()+"*"+spsdata.get(0).getspdesc()+"*"+visituid1+"*"+visitd+"*"+String.valueOf(spsdata.size()));

            // adapter2.notifyDataSetChanged();


        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }
    public String getdmsg1( String visitd, String visituid1, String visitType, String crdt){
        String url = getContext().getResources().getString( R.string.weburl20)+"?id="+visituid1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        prepare1(response.toString(),visitd,visituid1,visitType,crdt);

                        Log.wtf("uyu",url);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("nmn", error.toString());

                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }
    private void prepare2(String val, String visituid1) {



        try{
            prdata = new ArrayList<>();
            JSONObject dbovh = null;

            dbovh = new JSONObject(val);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);
                String ProductUId = we1.get("ProductUId").toString();

                String Description = we1.get("Description").toString();

                String spuid = we1.get("spuid").toString();
                String spid = we1.get("spid").toString();
                String Qty = we1.get("Qty").toString();
                String UnitUId = we1.get("UnitUId").toString();

                Products a = new Products(ProductUId,Description, spuid,UnitUId,Qty,spid);
                prdata.add(a);
                Log.wtf("uiu",  ProductUId+"**"+Description);

                b++;
            }
            repo a1 = getalbum3(visituid1);
            a1.setprdata(prdata);


            adapter2.notifyDataSetChanged();


            // InitilizeGridLayout(imagePaths.size(),vh.gridView);
            // Gridview adapter
            // Initilizeexp(vv.refg);
//            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,    LinearLayoutManager.HORIZONTAL, false);
//            vv.refg.setLayoutManager(linearLayoutManager);
//            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context.getApplicationContext());
//            vv.refg.setLayoutManager(layoutManager);
//            adapter5 = new newExpandsps(context,spsdata);
//
//            // setting grid view adapter
//            vv.refg.setAdapter(adapter5);
//            DefaultItemAnimator animator = new DefaultItemAnimator() {
//                @Override
//                public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
//                    return true;
//                }
//            };
//            vv.refg.setItemAnimator(animator);
//            vv.refg.getRecycledViewPool().setMaxRecycledViews(0, 0);
            // adapter5.notifyItemRangeInserted(0,spsdata.size());
            // Log.wtf("ghj", spsdata.get(1).getspdesc()+"*"+spsdata.get(0).getspdesc()+"*"+visituid1+"*"+visitd+"*"+String.valueOf(spsdata.size()));

            // adapter2.notifyDataSetChanged();


        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }

    public String getdmsg2( String visituid1){
        String url = getContext().getResources().getString( R.string.weburl19)+"?id="+visituid1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {


                        prepare2(response.toString(),visituid1);

                        Log.wtf("uyu",url);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("nmn", error.toString());

                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }
}