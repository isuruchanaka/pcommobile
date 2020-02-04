package mud.per.iw.pcom;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;


import static mud.per.iw.pcom.frgmenthome.pairdata;
import static mud.per.iw.pcom.frgmenthome.prodList;


public class fragspecies extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    //    String u1;
//    String v1;
//    String z1;
//     String t1;
//     String s1;
    View view;
    private String changeres;
    public static HashMap<String,String > eprodList1 =new HashMap<>();
    public static HashMap<String,String > cprodList1 =new HashMap<>();
    public static HashMap<String,String > pprodList1 =new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_species, container, false);
        getdmsg("",view);
        return view;
    }
    public String getdmsg(String  val1,View vv ){
        String url = getContext().getResources().getString( R.string.weburl5);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();


                        setgmviews(250, 200,5,vv);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }
    private void setgmviews(int layoutHeight, int layoutWidth, int cnt,View vv) {

        try{

            JSONObject jsonObject = null;

            jsonObject = new JSONObject(changeres);

            JSONArray we = null;

            we = jsonObject.getJSONArray("Table");
            Log.wtf("fff", Integer.toString(we.length()));


            int b=0;

            int height = 260;

            ArrayList<String> data = new ArrayList<>();
            data.clear();

            TableLayout table = new TableLayout(getContext());

            table.removeAllViews();
            TableRow[] tableRow = new TableRow[(we.length())];

            Random random = new Random();
            int num = (we.length());


            for (int row = 0; row < num; row++) {
                JSONObject we1 = we.getJSONObject(b);

                String   s1 = we1.get("Description").toString();
                String   t1 = we1.get("UId").toString();
                String  u1 = we1.get("Habitat").toString();
                String  v1 = we1.get("Treatment").toString();
                String z1 = we1.get("Remarks").toString();



                tableRow[b] = new TableRow(getContext());
                tableRow[b].setGravity(Gravity.CENTER);
                getprodc(t1);

                LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.splayout,null);

                String imn=   we1.get("ImagePath").toString();


                final ImageView mImageView;
                String url =  getContext().getResources().getString( R.string.weburl22)+"/"+t1+"/"+imn;
                mImageView = (ImageView) v.findViewById(R.id.imageView3);



                ImageRequest request = new ImageRequest(url,
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                mImageView.setImageBitmap(bitmap);
                            }
                        }, 0, 0, null,null, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        error.printStackTrace();

                    }
                }
                );

                singletongm.getInstance(getContext()).addToRequestQueue(request);
                mImageView.getLayoutParams().width =getResources().getDimensionPixelSize(R.dimen.dimen_ent_in_dp);
                mImageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dimen_ent_in_dp);
                mImageView.setId(row);
                mImageView.setClickable(true);
                mImageView.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {

                        LayoutInflater inflater= LayoutInflater.from(getContext());
                        View view=inflater.inflate(R.layout.alertx, null);



//alertDialog.setMessage("Here is a really long message.");

//                        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
//                        alertDialog.setMessage(s1);
//                        LinearLayout layout = new LinearLayout(getContext());
//                        layout.setOrientation(LinearLayout.VERTICAL);




                        // Add a TextView here for the "Title" label, as noted in the comments
//                        final TextView titleBox1 = new TextView(getContext());
//                        titleBox1.setText(s1);
//                        //titleBox1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        layout.addView(titleBox1); // Notice this is an add method
// Add a TextView here for the "Title" label, as noted in the comments
                        final   TextView textview=(TextView)view.findViewById(R.id.textmsg);
                        String hbitat="";
                        if(u1.equals("null")){
                            //  titleBox.setText("  Habitat: ");
                            hbitat="  Habitat: ";
                        }
                        else{
                            //  titleBox.setText("  Habitat: "+u1);
                            hbitat="  Habitat: "+u1;
                        }

                        //titleBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        //  layout.addView(titleBox); // Notice this is an add method
                        String  trtmnt="";
// Add another TextView here for the "Description" label

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            pairdata.sort(Comparator.comparing(Pair::getCode));
                        }
                        for (int i = 0; i < pairdata.size(); i++) {

                            Pair album = pairdata.get(i);
                            if(album !=null) {
                                if (album.getSiteUId().equals(t1)){
                                    trtmnt="   "+trtmnt+"    \n    "+album.getCode();

                                }
                            }

                        }

                        trtmnt="  Treatment: "+trtmnt;




                        //descriptionBox.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        layout.addView(descriptionBox); // Another add method
//                        alertDialog.setView(layout);
// Add another TextView here for the "Description" label
                        String rmkd="";
                        if(z1.equals("null")){
                            rmkd="  Remarks: ";
                        }
                        else{
                            rmkd="  Remarks: "+z1;
                        }
                        textview.setText(hbitat+"\n"+trtmnt+"\n"+rmkd);
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setTitle(s1);
                        //descriptionBox1.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                        layout.addView(descriptionBox1); // Another add method
//                        alertDialog.setView(layout);
                       // alertDialog.setIcon(R.drawable.ic_menu_manage);
//                        alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Change password", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialogInterface, int which) {
//
//
//                            }
//                        });

                        alertDialog.setView(view);
                        //alertDialog.setButton("OK", null);
                        AlertDialog alert = alertDialog.create();
                        alert.show();

                    }
                });
                TextView textView = (TextView) v.findViewById(R.id.textView2);
                textView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dimen_ent_in_dp);
                textView.setClickable(true);
                textView.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v) {
//                        Intent i = new Intent(gmcadert.this, gmfglist.class);
//                        i.putExtra("EXTRA_SESSION_ID",t1);
//                        startActivity(i);
                    }
                });


                if(s1!="null"){ textView.setText(s1);}
                textView.setId(row);

                if ( (b & 1) == 0 ){
                    tableRow[b].addView(v);

                }
                else {
                    tableRow[b-1].addView(v);
                }
                Log.wtf("ttt",Integer.toString((we.length())));
                if ( row==(we.length()-1)&&((we.length()) & 1) != 0){
                    LayoutInflater vi1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v11 = vi1.inflate(R.layout.splayout,null);
                    tableRow[b].addView(v11);


                }
                if(we.length()==1){
                    LayoutInflater vi1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View v11 = vi1.inflate(R.layout.splayout,null);
                    tableRow[row].addView(v11);
                    Log.wtf("rrr",Integer.toString(row));
                }
                else {

                }

                table.setStretchAllColumns(true);
                table.addView(tableRow[b]);



                b++;
            }
            ConstraintLayout container = (ConstraintLayout)vv.findViewById(R.id.tblatuot);

            container.addView(table);
        } catch (NullPointerException ex){
            Log.wtf("rrr",ex.toString());
        } catch (Exception e){
            Log.wtf("tt",e.toString());
        }

        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
    }

    public String getprodc(String id ){
        String url = getContext().getResources().getString( R.string.weburl32)+"?id="+id+"";

        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

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

                                String Code = we1.get("ct").toString()+":"+we1.get("pt").toString();





                                cprodList1.put(id,Code);
                                b++;
                            }


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


}
