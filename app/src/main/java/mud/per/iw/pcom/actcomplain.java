package mud.per.iw.pcom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import static mud.per.iw.pcom.Expandsptch.sps;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static mud.per.iw.pcom.MainActivity.svcid;


public class actcomplain extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
    private static final int OPEN_DOCUMENT_CODE = 148;
    private Context mContext;
    View view;
    Button firstButton;
    Button galButton;
    Button savebtn;
    Button spbtn;
    Button prbtn;
    int TAKE_PHOTO_CODE = 0;
    public static int count = 0;
    private GridView gridView;
    private int columnWidth;
    private GridViewImageAdapter2 adapter;
    private ArrayList<String> imagePaths = new ArrayList<String>();
    private String purl="";
    private String changeres;
    private FusedLocationProviderClient fusedLocationClient;
    private String lti;
    private String lngi;
    private String sdesc1="";
    private String visitdesc1="";
    private String sage1="0";
    private String stadress1="";
    private String savg1="0";
    private String prqty1="0";
    private String sdead1="0";
    private String srmk1="0";
    private String sbread1="0";
    private String scnt1="0";
    private RecyclerView recyclerView;
    private RecyclerView recyclerView2;
    private List<Species> spdata;
    private List<Products> prdata;
    private Expandsptch spadapter2;
    private Expandpr spadapter3;
    String[] stockArr;
    ArrayAdapter<String> adaptersit;
    ArrayAdapter<String> adapter1;
    public static List<String> stationList;
    public static HashMap<Integer,String > stationsList1 =new HashMap<>();
    int stpos;
    ImageView clbtn;
    String tid="";
    AutoCompleteTextView spinner;
    Spinner spinner2;
    ArrayAdapter<String> adapter3;
    ArrayAdapter<String> adapter5;
    Spinner spinner3;
    Spinner spinner4;
    Spinner spinner5;
    String rid;
    String ctry;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_actcomplain, container, false);
        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        spdata = new ArrayList<>();
//        spinner = (AutoCompleteTextView)view.findViewById(R.id.sitelist);
//        spinner.setOnItemSelectedListener(this);
//        spinner.setOnItemClickListener(this);
//        stockArr = new String[frgmenthome.siteList.size()];
//        stockArr = frgmenthome.siteList.toArray(stockArr);

      //  adaptersit = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr);

      //  spinner.setAdapter(adaptersit);
        stationList = new ArrayList<>();
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
       tid= frgmenthome.siteList1.get(0);
        getdmsg(tid);
/////////////////////////////////////////////////////////////////////////////////
        checkloc();
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
////////////////////////////////////////////////////////////////
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {

                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object

                            lti= String.valueOf(location.getLatitude());
                            lngi= String.valueOf(location.getLongitude());
                            Log.wtf("lti", lti);
                            if(lti!=null){
                                TextView ll = (TextView) view.findViewById(R.id.textViewlltch);
                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                            }
                        }
                    }
                });
        /////////////////////////////////////////////////////////////////////////////
        spinner4 = (Spinner)view.findViewById(R.id.station);
        spinner4.setOnItemSelectedListener(this);



////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
        spinner2 = (Spinner)view.findViewById(R.id.species);
        spinner2.setOnItemSelectedListener(this);

        String[] stockArr2 = new String[frgmenthome.spList.size()];
        stockArr2 = frgmenthome.spList.toArray(stockArr2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr2);

        spinner2.setAdapter(adapter2);

        ////////////////////////////////////////////////////////////////////////////////////////
        spbtn = (Button) view.findViewById(R.id.btn_spstc);

        spbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                    int spcpos = spinner2.getSelectedItemPosition();
                    String spcpos2 = frgmenthome.spList1.get(spcpos);

                    Species a = new Species(spcpos2, "", "", spinner2.getSelectedItem().toString(), "", "", "", "");
                    spdata.add(a);
                    spadapter2 = new Expandsptch(spdata);
                    recyclerView = (RecyclerView) view.findViewById(R.id.recycler4);
                    recyclerView.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                    recyclerView.setLayoutManager(layoutManager);
//fetch data and on ExpandableRecyclerAdapter
                    recyclerView.setAdapter(spadapter2);

                    // }
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////
        savebtn = (Button) view.findViewById(R.id.btn_sntch);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {


                EditText sdesc = (EditText) view.findViewById(R.id.input_contact);
                sdesc1 = sdesc.getText().toString();
                EditText visitdesc = (EditText) view.findViewById(R.id.input_contactno);
                visitdesc1 = visitdesc.getText().toString();
                EditText visitrmk = (EditText) view.findViewById(R.id.input_rmk);
                srmk1 = visitrmk.getText().toString();



                int spcpos = spinner4.getSelectedItemPosition();
                String spcpos2 = stationsList1.get(spcpos);
                //     int stpos = spinner.getSelectedItemPosition();




                String stpos2 = tid;




                if(validate()){

                    TextView ll = (TextView) view.findViewById(R.id.textViewlltch);

                    if(!ll.getText().equals("Enable GPS!")) {
                        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        savebtn.setEnabled(false);
                        try {
                            SharedPreferences prefs = getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
                            String restoredText = prefs.getString("UserID", null);



                            if (restoredText != null) {
                                rid=  prefs.getString("RoleID", "no");
                                ctry= prefs.getString("Country", "no");
                            }
                            isertdata(tid,  sdesc1,spcpos2 ,lti, lngi, ctry, svcid, visitdesc1,srmk1);
                            // Log.wtf("cls1", qrcod);





                        } catch (Exception ex) {
                        }
                    }
                    else {

                        Thread   thread = new Thread() {
                            public void run() {
                                while (ll.getText().equals("Enable GPS!")) {
                                    try {


                                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                                        fusedLocationClient.getLastLocation()
                                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {

                                                    @Override
                                                    public void onSuccess(Location location) {
                                                        // Got last known location. In some rare situations this can be null.
                                                        if (location != null) {
                                                            // Logic to handle location object

                                                            lti= String.valueOf(location.getLatitude());
                                                            lngi= String.valueOf(location.getLongitude());
                                                            Log.wtf("lti", lti);
                                                            if(lti!=null){
                                                                TextView ll = (TextView) view.findViewById(R.id.textViewlltch);
                                                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                                                            }
                                                        }
                                                    }
                                                });

                                        Thread.sleep(2000);

                                    } catch (InterruptedException e) {
                                        //Log.e(TAG, "local Thread error", e);
                                    }
                                }
                            }
                        };
                        thread.start();

                        if(ll.getText().equals("Enable GPS!")){

                            Toast.makeText(getContext(), "Can't get GPS location please try again after opening Google Map App or Submit Again!",
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                }




            }
        });

//////////////////////////////////////////////////////////////////////////////////////////////////

        //        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view3);
//        String strcd=getArguments().getString("siteuid");
////        sitetypList=new ArrayList<>();
////        siteList=new ArrayList<>();
////        spList=new ArrayList<>();
////        prodList=   new ArrayList<>();
////        getdmsg("");
////        getsitetp();
////        // getsite();
////        getspecies();
////        getprod();
//
//        getdmsg(strcd);
//        stationsList = new ArrayList<>();
//        adapter = new StationAdapter(container.getContext(), stationsList);
//        view.findViewById(R.id.nodt).setVisibility(View.GONE);
//        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(container.getContext(), 1);
//        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new FragmentSixth.GridSpacingItemDecoration(1, dpToPx(10), true));
//        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        recyclerView.setAdapter(adapter);
////        recyclerView.setVisibility(View.GONE);

        return view;
    }

    private void prepareAlbums() {

        stationsList1.values().clear();
        stationList.clear();

        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

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

                stationList.add(Description);
                stationsList1.put(row,UId);
//                station a = new station(Code,SiteUId,StationTypeUId,Description,Lat,Long,stype,UId,stypedes);
//                stationsList.add(a);

                b++;
            }

            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            //recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }
        String[] stockArr1 = new String[stationList.size()];
        stockArr1 = stationList.toArray(stockArr1);

        adapter1 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr1);
        adapter1.notifyDataSetChanged();
        spinner4.setAdapter(adapter1);

        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);





    }
    public String getdmsg(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl8)+"?id="+val1;



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

    public String isertdata(String siteuid,String cperson,String staid,String lat,String lon,String ctryy,String uid,String cno,String rmk ){
        String sp1="";
        String sp2="";
        String sp3="";
        String sp4="";
        String sp5="";
        String sp6="";
        if(sps!=null) {
            for (int u = 0; u < sps.size(); u++) {
                try{
                    if(u==0) {
                        sp1=  sps.get(u).getspuid();
                    }
                    if(u==1) {
                        sp2=  sps.get(u).getspuid();
                    }
                    if(u==2) {
                        sp3=  sps.get(u).getspuid();
                    }
                    if(u==3) {
                        sp4=  sps.get(u).getspuid();
                    }
                    if(u==4) {
                        sp5=  sps.get(u).getspuid();
                    }
                    if(u==5) {
                        sp6=  sps.get(u).getspuid();
                    }
                }catch (Exception ex){}
            }
        }
        try{
        if(siteuid==null){
            siteuid="0";
        }
        if(staid==null){
            staid="0";
        }
    }catch (Exception ex){}
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
        String url = this.getResources().getString( R.string.weburl30)+"?ls="+lat+","+lon+"&suid="+siteuid+"&sp1='"+sp1+"'&sp2='"+sp2+"'&sp3='"+sp3+"'&sp4='"+sp4+"'&sp5='"+sp5+"'&sp6='"+sp6+"'&cp="+cperson+"&cn="+cno+"&stuid="+staid+"&rmk="+rmk+"&uid="+uid+"";

        Log.wtf("nwe1", url.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Toast.makeText(mContext, "Saved!",
                                Toast.LENGTH_LONG).show();

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                        Toast.makeText(mContext, "Error!",
                                Toast.LENGTH_LONG).show();


                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view1, int i, long l) {

//        String itemName = adaptersit.getItem(i);
//        String[] sdfgh= itemName.split("\n");
//        spinner.setSelection(0);
//        for(Album carnet : albumList) {
//            if (carnet.getsitedesp().trim().contains(sdfgh[0].trim())) {
//                tid = carnet.getuid();
//
//                break;
//            }
//        }
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    public void checkloc(){

        LocationManager lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            new AlertDialog.Builder(getContext())
                    .setMessage(R.string.gps_network_not_enabled)
                    .setPositiveButton(R.string.open_location_settings, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            //getContext().startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            Intent gps = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivityForResult(gps, 152);





                        }
                    })
                    .setNegativeButton(R.string.Cancel,null)
                    .show();

        }

    }
    public boolean validate() {
        boolean valid = true;

        String sdesc2 =sdesc1;
        String visitdesc2=visitdesc1;

        String srmk2=srmk1;

        EditText sdesc = (EditText) view.findViewById(R.id.input_contact);
        EditText visitdesc = (EditText) view.findViewById(R.id.input_contactno);
        // Spinner spinnerx = (Spinner)view.findViewById(R.id.state);
        //   int stpos = spinnerx.getSelectedItemPosition();
//        try {
//            // String stpos2 = frgmenthome.siteList1.get(stpos);
//            if(tid==""||tid==null||tid.isEmpty()){
//                Toast.makeText(getContext(), "Select Site!",
//                        Toast.LENGTH_LONG).show();
//                valid = false;
//            }
//
//        }
//        catch (Exception ex ){
//            Toast.makeText(getContext(), "Select Site!",
//                    Toast.LENGTH_LONG).show();
//            valid = false;
//        }

//

        if (spdata.size()<1) {


            Toast.makeText(getContext(), "Add Species!",
                    Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (spdata.size()>5) {


            Toast.makeText(getContext(), "Only 5 Species can be Added!",
                    Toast.LENGTH_LONG).show();
            valid = false;
        }
        if (sdesc2.isEmpty() ) {


            sdesc.setError("enter value!");
            valid = false;
        } else {
            sdesc.setError(null);
        }
        if (visitdesc2.isEmpty() ) {


            visitdesc.setError("enter value!");
            valid = false;
        } else {
            visitdesc.setError(null);
        }
        if (isNetworkAvailable(mContext)==false) {

            Toast.makeText(mContext, "Check Your Internet Connection!",
                    Toast.LENGTH_LONG).show();

            valid = false;
        } else {

        }





        return valid;
    }

    public static boolean isNetworkAvailable(Context context) {
        if(context == null)  return false;


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {


            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    }  else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)){
                        return true;
                    }
                }
            }

            else {

                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        Log.i("update_statut", "Network is available : true");
                        return true;
                    }
                } catch (Exception e) {
                    Log.i("update_statut", "" + e.getMessage());
                }
            }
        }
        Log.i("update_statut","Network is available : FALSE ");
        return false;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode ==152){

            LocationManager lm = (LocationManager)getContext().getSystemService(Context.LOCATION_SERVICE);
            boolean gps_enabled = false;
            boolean network_enabled = false;
            try {
                gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
            } catch(Exception ex) {}
            if(gps_enabled) {
                view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                final Handler handler = new Handler();
                Location location1=null;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 5s = 5000ms
                        fusedLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
                        fusedLocationClient.getLastLocation()
                                .addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {

                                    @Override
                                    public void onSuccess(Location location) {

                                        // Got last known location. In some rare situations this can be null.
                                        if (location != null) {
                                            // Logic to handle location object

                                            lti= String.valueOf(location.getLatitude());
                                            lngi= String.valueOf(location.getLongitude());
                                            Log.wtf("lti", lti);
                                            if(lti!=null){
                                                TextView ll = (TextView) view.findViewById(R.id.textViewll);
                                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                                            }
                                        }
                                    }
                                });
                    }
                }, 5000);
                view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }
    }
}
