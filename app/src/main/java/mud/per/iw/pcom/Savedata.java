package mud.per.iw.pcom;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;

import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import static mud.per.iw.pcom.AlbumsAdapter.albumList;
import static mud.per.iw.pcom.Expandpr.prds;
import static mud.per.iw.pcom.Expandsps.sps;
import static mud.per.iw.pcom.GridViewImageAdapter2.data3;
import static mud.per.iw.pcom.MainActivity.qrcod;
import static mud.per.iw.pcom.MainActivity.svcid;
import static mud.per.iw.pcom.frgmenthome.prodList;
import static mud.per.iw.pcom.frgmenthome.sitetypList;

public class Savedata extends Fragment implements AdapterView.OnItemSelectedListener, AdapterView.OnItemClickListener {
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
    private Expandsps spadapter2;
    private Expandpr spadapter3;
    String[] stockArr;
    ArrayAdapter<String> adaptersit;
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
    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      //  final String dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picFolder/";
        ////File imagePath = new File(container.getContext().getFilesDir(), "images");
       // File newdir = new File(dir);
       // imagePath.mkdirs();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        view = inflater.inflate(R.layout.actsavdata, container, false);
        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
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
                                TextView ll = (TextView) view.findViewById(R.id.textViewll);
                                ll.setText("Latitude:  "+lti+"  Longitude:  "+lngi);
                            }
                        }
                    }
                });


        RadioGroup rg = (RadioGroup) view.findViewById(R.id.radiog);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.radioButton1:
                        int spcpos = spinner2.getSelectedItemPosition();
                        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        String spcpos2 = frgmenthome.spList1.get(spcpos);
                        getprode(spcpos2);
                        break;
                    case R.id.radioButton2:
                        int spcpos1 = spinner2.getSelectedItemPosition();
                        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        String spcpos3 = frgmenthome.spList1.get(spcpos1);
                        getprodc(spcpos3);
                        break;
                    case R.id.radioButton3:
                        int spcpos5 = spinner2.getSelectedItemPosition();
                        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                        String spcpos4 = frgmenthome.spList1.get(spcpos5);
                        getprodp(spcpos4);
                        break;
                }
            }
        });
        spdata = new ArrayList<>();
        prdata = new ArrayList<>();
         spinner = (AutoCompleteTextView)view.findViewById(R.id.state);
        spinner.setOnItemSelectedListener(this);
        spinner.setOnItemClickListener(this);
         stockArr = new String[frgmenthome.siteList.size()];
        stockArr = frgmenthome.siteList.toArray(stockArr);

         adaptersit = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr);

        spinner.setAdapter(adaptersit);
     //   stockArr[frgmenthome.siteList.size()]="-SELECT SITE-";
     //   spinner.setSelection(frgmenthome.siteList.size());
    //    adapter.notifyDataSetChanged();
/////////////////////////////////////////////////////////////////////////////
        Spinner spinner4 = (Spinner)view.findViewById(R.id.unit1);
        spinner4.setOnItemSelectedListener(this);

        String[] stockArr1 = new String[frgmenthome.unitList.size()];
        stockArr1 = frgmenthome.unitList.toArray(stockArr1);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr1);

        spinner4.setAdapter(adapter1);

////////////////////////////////////////////////////////////////////////////////
         spinner2 = (Spinner)view.findViewById(R.id.species);
        spinner2.setOnItemSelectedListener(this);

        String[] stockArr2 = new String[frgmenthome.spList.size()];
        stockArr2 = frgmenthome.spList.toArray(stockArr2);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(container.getContext(),R.layout.spinner_item, stockArr2);

        spinner2.setAdapter(adapter2);

 ////////////////////////////////////////////////////////////////////////////////////////
         spinner3 = (Spinner)view.findViewById(R.id.prod);
        spinner3.setOnItemSelectedListener(this);

//        String[] stockArr3 = new String[prodList.size()];
//        stockArr3 = prodList.toArray(stockArr3);
//
//        adapter3 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr3);
//
//        spinner3.setAdapter(adapter3);
/////////////////////////////////////////////////////////////////////////////////////////
        ////////////////////////////////////////////////////////////////////////////////////////
        spinner5 = (Spinner)view.findViewById(R.id.sitetype);
        spinner5.setOnItemSelectedListener(this);

        String[] stockArr5 = new String[sitetypList.size()];
        stockArr5= sitetypList.toArray(stockArr5);

        adapter5 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr5);

        spinner5.setAdapter(adapter5);
/////////////////////////////////////////////////////////////////////////////////////////
        firstButton = (Button) view.findViewById(R.id.btn_tpic);

        firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ////////
                count++;

                String file =count+".jpg";
                try {



                    File file2 = new File(new File(Environment.getExternalStorageDirectory(), "Pictures"), file);

                    Uri outputFileUri = FileProvider.getUriForFile(container.getContext(), "mud.per.iw.pcom.fileprovider", file2);



                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

                startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                  purl=  outputFileUri.toString();
                    Log.wtf("CameraDemo", outputFileUri.toString());
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });

        galButton = (Button) view.findViewById(R.id.btn_tpic2);

        galButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                ////////
                count++;

                String file =count+".jpg";
                try {



                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    startActivityForResult(intent, OPEN_DOCUMENT_CODE);
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });
        spbtn = (Button) view.findViewById(R.id.btn_sps);

        spbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                    int spcpos = spinner2.getSelectedItemPosition();
                    String spcpos2 = frgmenthome.spList1.get(spcpos);
                    EditText sbread = (EditText) view.findViewById(R.id.sbread);
                    sbread1 = sbread.getText().toString();
                    EditText sage = (EditText) view.findViewById(R.id.sage);
                    sage1 = sage.getText().toString();
                    EditText savg = (EditText) view.findViewById(R.id.savg);
                    savg1 = savg.getText().toString();
                    EditText sdead = (EditText) view.findViewById(R.id.sdead);
                    sdead1 = sdead.getText().toString();
                    EditText scnt = (EditText) view.findViewById(R.id.cnt);
                    scnt1 = scnt.getText().toString();
//                    EditText srmk = (EditText) view.findViewById(R.id.srmk);
//                    srmk1 = srmk.getText().toString();
                    if(sbread1.equals("")){
                        sbread1="0";
                    }

                    if(sage1.equals("")){
                        sage1="0";
                    }
                    if(savg1.equals("")){
                        savg1="0";
                    }
                    if(sdead1.equals("")){
                        sdead1="0";
                    }
                    if(scnt1.equals("")){
                        scnt1="0";
                    }
                  //  if(validate2()) {
                        Species a = new Species(spcpos2, sbread1, sage1, spinner2.getSelectedItem().toString(), savg1, sdead1, scnt1, "");
                        spdata.add(a);
                        spadapter2 = new Expandsps(spdata);
                        recyclerView = (RecyclerView) view.findViewById(R.id.recycler2);
                        recyclerView.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                        recyclerView.setLayoutManager(layoutManager);
//fetch data and on ExpandableRecyclerAdapter
                        recyclerView.setAdapter(spadapter2);
                    sbread.setText("");

                    sage.setText("");

                    savg.setText("");

                    sdead.setText("");

                    scnt.setText("");
                   // }
                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
            }
        });
        prbtn = (Button) view.findViewById(R.id.btn_prd);

        prbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                try {
                    int prodpos = spinner3.getSelectedItemPosition();
                    String prodpos2 = frgmenthome.prodList1.get(prodpos);

                    int unitpos = spinner4.getSelectedItemPosition();
                    String unitpos2 = frgmenthome.unitList1.get(unitpos);
                    EditText unnit = (EditText) view.findViewById(R.id.pqty);
                    prqty1 = unnit.getText().toString();
                    if(prqty1.equals("")){
                        prqty1="0";
                    }
                    int spcpos = spinner2.getSelectedItemPosition();
                    String spcpos2 = frgmenthome.spList1.get(spcpos);
                    Products a = new Products(prodpos2,spinner3.getSelectedItem().toString(),spcpos2,unitpos2,prqty1,spcpos2);
                    prdata.add(a);
                    spadapter3 = new Expandpr( prdata);
                    recyclerView2 = (RecyclerView)view.findViewById(R.id.recycler4);
                    recyclerView2.setHasFixedSize(true);
                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext().getApplicationContext());
                    recyclerView2.setLayoutManager(layoutManager);
//fetch data and on ExpandableRecyclerAdapter
                    recyclerView2.setAdapter(spadapter3);



                }
                catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                }


                //Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
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
        gridView = (GridView) view.findViewById(R.id.grid_view2);
        savebtn = (Button) view.findViewById(R.id.btn_sn);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View v) {


                EditText sdesc = (EditText) view.findViewById(R.id.stdesc);
                 sdesc1 = sdesc.getText().toString();
                EditText visitdesc = (EditText) view.findViewById(R.id.visitdesc);
                 visitdesc1 = visitdesc.getText().toString();




//                int spcpos = spinner2.getSelectedItemPosition();
//                String spcpos2 = SecondFragment.spList1.get(spcpos);
           //     int stpos = spinner.getSelectedItemPosition();




                String stpos2 = tid;
                int subpos = spinner5.getSelectedItemPosition();
                String subpos2 =frgmenthome.sitetypList1.get(subpos);



if(validate()){

    TextView ll = (TextView) view.findViewById(R.id.textViewll);

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
            isertdata(qrcod, subpos2, stpos2, sdesc1, lti, lngi, ctry, svcid, "1", visitdesc1, svcid);
            Log.wtf("cls1", qrcod);





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
                                                TextView ll = (TextView) view.findViewById(R.id.textViewll);
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


        return view;
    }

    public boolean validate() {
        boolean valid = true;

         String sdesc2 =sdesc1;
         String visitdesc2=visitdesc1;

         String srmk2=srmk1;

        EditText sdesc = (EditText) view.findViewById(R.id.stdesc);
        EditText visitdesc = (EditText) view.findViewById(R.id.visitdesc);
       // Spinner spinnerx = (Spinner)view.findViewById(R.id.state);
     //   int stpos = spinnerx.getSelectedItemPosition();
        try {
         //   String stpos2 = frgmenthome.siteList1.get(stpos);
            if(tid==""||tid==null||tid.isEmpty()){
                Toast.makeText(getContext(), "Select Site!",
                        Toast.LENGTH_LONG).show();
                valid = false;
            }

        }
        catch (Exception ex ){
            Toast.makeText(getContext(), "Select Site!",
                    Toast.LENGTH_LONG).show();
            valid = false;
        }

//



        if (sdesc2.isEmpty() ) {


            sdesc.setError("enter value!");
            valid = false;
        } else {
            sdesc.setError(null);
        }
        if (isNetworkAvailable(mContext)==false) {

            Toast.makeText(mContext, "Check Your Internet Connection!",
                    Toast.LENGTH_LONG).show();

            valid = false;
        } else {

        }





        return valid;
    }

    public boolean validate2() {
        boolean valid = true;

        String sdesc2 =sdesc1;

        String sage2=sage1;
        String scnt2=scnt1;
        String savg2=savg1;
        String sdead2=sdead1;
        String srmk2=srmk1;
        String sbread2=sbread1;
        //  EditText sdesc = (EditText) view.findViewById(R.id.sdesc);

        EditText sage = (EditText) view.findViewById(R.id.sage);

        EditText savg = (EditText) view.findViewById(R.id.savg);

        EditText sdead = (EditText) view.findViewById(R.id.sdead);

        //EditText srmk = (EditText) view.findViewById(R.id.srmk);

        EditText sbread = (EditText) view.findViewById(R.id.sbread);

        EditText scnt = (EditText) view.findViewById(R.id.cnt);




        if (scnt2.isEmpty() ) {


            scnt.setError("enter value!");
            valid = false;
        } else {
            scnt.setError(null);
        }
        if (sage2.isEmpty() ) {


            sage.setError("enter value!");
            valid = false;
        } else {
            sage.setError(null);
        }


        if (savg2.isEmpty() ) {


            savg.setError("enter value!");
            valid = false;
        } else {
            savg.setError(null);
        }

        if (sdead2.isEmpty() ) {


            sdead.setError("enter value!");
            valid = false;
        } else {
            sdead.setError(null);
        }


        if (sbread2.isEmpty() ) {


            sbread.setError("enter value!");
            valid = false;
        } else {
            sbread.setError(null);
        }

        return valid;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
count=0;
            Log.wtf("cls1", ((Object) this).getClass().getSimpleName() + " is NOT on screen");
        }
        else
        {
            Log.wtf("cls2", ((Object) this).getClass().getSimpleName() + " is on screen");
        }
    }
    public String isertdata(String code,String suid,String subuid,String desc,String lat,String longi,String cntry,String cruser,String vistyp,String visdet,String empid ){
        String url = getContext().getResources().getString( R.string.weburl10)+"?code="+code+"&suid="+suid+"&subuid="+subuid+"&desc="+desc+"&lat="+lat+"&longi="+longi+"&cntry="+cntry+"&cruser="+cruser+"&vistyp="+vistyp+"&visdet="+visdet+"&empid="+empid+"&pcnt="+data3.size()+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(response.toString());


                        JSONArray we = null;

                        we = jsonObject.getJSONArray("Table");


                        JSONObject we1 = we.getJSONObject(0);

                        String s1 = we1.get("name").toString();
                           try{
                            insertfolder(s1);
                        }catch (Exception ex){}

                            if(sps!=null) {
                                for (int u = 0; u < sps.size(); u++) {
                                  try{
                                    isertsp(s1, sps.get(u).getspuid(), sps.get(u).getspdesc(), sps.get(u).getavgcns(), sps.get(u).getdeadpr(), sps.get(u).getage(), sps.get(u).getbreed(), sps.get(u).getcnt(), svcid,cntry);
                                }catch (Exception ex){}
                                }
                            }
                            if(prds!=null) {
                            for(int u=0;u<prds.size();u++) {
                                try{
                                isertpr(s1,prds.get(u).getpuid(),svcid,prds.get(u).getpsuid(),prds.get(u).getunit(),prds.get(u).getunitid(),cntry);
                            }catch (Exception ex){}
                            }}
                            prdata.clear();
                            spdata.clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("nwe1", error.toString());
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

    public String isertsp(String visitid ,String spid ,String inspdet ,String avgcns,String dedpr ,String spage ,String brd ,String spcnt,String empid,String cntry){
        String url = getContext().getResources().getString( R.string.weburl18)+"?visitid="+visitid+"&spid="+spid+"&inspdet="+inspdet+"&avgcns="+avgcns+"&dedpr="+dedpr+"&spage="+spage+"&brd="+brd+"&spcnt="+spcnt+"&empid="+empid+"&cntry="+cntry+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {





                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.wtf("nwe1", error.toString());
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

    public String isertpr(String visitid ,String prid,String empid,String spuid,String unit,String unitid,String cntry){
        String url = getContext().getResources().getString( R.string.weburl17)+"?visitid="+visitid+"&prid="+prid+"&empid="+empid+"&spuid="+spuid+"&Qty="+unit+"&UnitUId="+unitid+"&cntry="+cntry+"";

        Log.wtf("nwe1", url.toString());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {





                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

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


    public String insertfolder(String  vtuid){

        String url = this.getContext().getResources().getString( R.string.weburl12)+"?vtuid="+vtuid+"";
        Log.wtf("eee", url);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
                       for(int u=0;u<data3.size();u++){
                           InputStream is = null;
                           try {
                               is = getContext().getContentResolver().openInputStream( Uri.parse(data3.get(u)));
                           } catch (FileNotFoundException e) {
                               e.printStackTrace();
                           }
                           Bitmap bitmap = BitmapFactory.decodeStream(is);
                           try{
                           insertimage(bitmap, String.valueOf(u+1)+".jpg",vtuid);
                           }catch (Exception ex){}

                       }
                       try {
                           view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
                           data3.values().clear();
                           data3.clear();
                           //data3 = null;
                       }catch (Exception ex){
                           Log.wtf("dfg", ex.toString());
                       }
                        Toast.makeText(mContext, "Success!",
                                Toast.LENGTH_LONG).show();
                        Fragment  fragment1 = new frgmenthome();
                        FragmentTransaction ft1 =getActivity().getSupportFragmentManager().beginTransaction();
                        ft1.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right);
                        ft1.replace(R.id.frameLayout, fragment1);
                        ft1.commit();


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.wtf("ee2", error.toString());

                        // TODO: Handle error

                    }
                });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
// Access the RequestQueue through your singleton class.
        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        return  "";
    }

    public String insertimage(Bitmap  bitmap,String imgn,String vtuid){

        String url = this.getContext().getResources().getString( R.string.weburl13);
        Log.wtf("eee", url);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

        //sending image to server
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {

                if(s.equals("true")){
                   // Toast.makeText(getContext(), "Uploaded Successful", Toast.LENGTH_LONG).show();




                }
                else{
                   // Toast.makeText(getContext(), "Some error occurred!", Toast.LENGTH_LONG).show();
                }
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                insertimage(bitmap, imgn,vtuid);
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("image", imageString);
                parameters.put("imgn", imgn);
                parameters.put("vtuid", vtuid);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                10000,
                10,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue rQueue = Volley.newRequestQueue(getContext());
        rQueue.add(request);

        return  "";
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == TAKE_PHOTO_CODE && resultCode == RESULT_OK) {
            Log.wtf("CameraDemo", "Pic saved");
            InitilizeGridLayout(count);
            imagePaths.add(purl);
            adapter = new GridViewImageAdapter2(Savedata.this.getContext(), imagePaths,
                    columnWidth);


            gridView.setAdapter(adapter);

        }
        if (requestCode == OPEN_DOCUMENT_CODE && resultCode == RESULT_OK) {
            if (data != null) {
                // this is the image selected by the user
                Uri imageUri = data.getData();

                InitilizeGridLayout(count);
                imagePaths.add(imageUri.toString());
                adapter = new GridViewImageAdapter2(Savedata.this.getContext(), imagePaths,
                        columnWidth);


                gridView.setAdapter(adapter);
            }
        }
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
    private void InitilizeGridLayout(int cnty) {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS +8) * padding)) / AppConstant.NUM_OF_COLUMNS);

        gridView.setNumColumns(AppConstant.NUM_OF_COLUMNS);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);

        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        double newh=  cnty/3;
        newh=  Math.ceil(newh)+1;
        Log.wtf("CameraDemo233", String.valueOf(newh));
        params.height = (int) ((columnWidth*newh)+(padding*3)+50);
        params.width = (int) ((columnWidth*3)+(padding*3)+150);
        gridView.setLayoutParams(params);
    }


    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (java.lang.NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        String itemName = adaptersit.getItem(i);
        String[] sdfgh= itemName.split("\n");
        spinner.setSelection(0);
        for(Album carnet : albumList) {
            if (carnet.getsitedesp().trim().contains(sdfgh[0].trim())) {
                tid = carnet.getuid();
                break;
            }
        }
    }

    public String getprodc(String id ){
        String url = getContext().getResources().getString( R.string.weburl28)+"?id="+id+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        frgmenthome.prodList1.clear();
                        prodList.clear();
                        String  suresb = response.toString();
                        Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("ProductUId").toString();
                                String Description = we1.get("Description").toString();



                                prodList.add(Description);
                                frgmenthome.prodList1.put(row,Code);
                                b++;
                            }

                            String[] stockArr3 = new String[prodList.size()];
                            stockArr3 = prodList.toArray(stockArr3);
                            adapter3 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr3);

                            adapter3.notifyDataSetChanged();
                            spinner3.setAdapter(adapter3);
                            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


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
    public String getprode(String id ){
        String url = getContext().getResources().getString( R.string.weburl27)+"?id="+id+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        frgmenthome.prodList1.clear();
                        prodList.clear();
                        String  suresb = response.toString();
                        Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("ProductUId").toString();
                                String Description = we1.get("Description").toString();



                                prodList.add(Description);
                                frgmenthome.prodList1.put(row,Code);
                                b++;
                            }

                            String[] stockArr3 = new String[prodList.size()];
                            stockArr3 = prodList.toArray(stockArr3);
                            adapter3 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr3);
                            adapter3.notifyDataSetChanged();
                            spinner3.setAdapter(adapter3);
                            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


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
    public String getprodp(String id ){
        String url = getContext().getResources().getString( R.string.weburl26)+"?id="+id+"";



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        frgmenthome.prodList1.clear();
                        prodList.clear();
                        String  suresb = response.toString();
                        Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("ProductUId").toString();
                                String Description = we1.get("Description").toString();



                                prodList.add(Description);
                                frgmenthome.prodList1.put(row,Code);
                                b++;
                            }

                            String[] stockArr3 = new String[prodList.size()];
                            stockArr3 = prodList.toArray(stockArr3);
                            adapter3 = new ArrayAdapter<String>(mContext,R.layout.spinner_item, stockArr3);
                            adapter3.notifyDataSetChanged();
                            spinner3.setAdapter(adapter3);
                            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);


                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


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
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
}