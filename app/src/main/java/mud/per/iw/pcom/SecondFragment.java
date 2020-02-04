package mud.per.iw.pcom;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static mud.per.iw.pcom.AlbumsAdapter.albumList;
import static mud.per.iw.pcom.frgmenthome.CodeList;
import static mud.per.iw.pcom.frgmenthome.siteList;
import static mud.per.iw.pcom.frgmenthome.siteList1;


public class SecondFragment extends Fragment {

    View view;
    Button secondButton;
    private RecyclerView recyclerView;
    String rid;
    String ctry;
    private AlbumsAdapter adapter;
    public static List<Album> albumListnew;


    private String changeres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_second, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        view.findViewById(R.id.nodt).setVisibility(View.GONE);
        albumListnew = new ArrayList<>();

        SharedPreferences prefs = getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);



        if (restoredText != null) {
            rid=  prefs.getString("RoleID", "no");
            ctry= prefs.getString("Country", "no");
        }
       //getsite();

        //getstation();
        Bundle args = getArguments();
        if (args != null) {

            String strtext=args.getString("message");
//            if(rid.equals("1003")&&strtext.equals("")){
//
//                getdmsg2(restoredText);
//
//            }
            if(rid.equals("1003")&&!strtext.equals("")){

                getdmsg3(restoredText,strtext);

            }
            else{

                getdmsg(strtext,ctry);


            }

            adapter = new AlbumsAdapter(container.getContext(), albumListnew);



        }

        else {

            //getdmsg("");

//            if(rid.equals("1003")){
//                getdmsg2(restoredText);
//                adapter = new AlbumsAdapter(container.getContext(), albumListnew);
//            }
//            else {
            if(albumList.size()==0){
                view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

            }
            else {
                view.findViewById(R.id.nodt).setVisibility(View.GONE);
            }


                adapter = new AlbumsAdapter(container.getContext(), albumList);
                adapter.notifyDataSetChanged();
//            }


        }



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(container.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
       // recyclerView.setVisibility(View.GONE);
        view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);

        return view;
    }





    public String getstation( ){
        String url = getContext().getResources().getString( R.string.weburl1);



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String  suresb = response.toString();
                        // Log.wtf("CameraDemo1", suresb);
                        try{

                            JSONObject dbovh = null;

                            dbovh = new JSONObject(suresb);

                            JSONArray we = null;

                            we = dbovh.getJSONArray("Table");



                            int b=0;

                            for (int row = 0; row < (we.length()); row++) {

                                JSONObject we1 = we.getJSONObject(b);

                                String Code = we1.get("Code").toString();
                                String SiteUId = we1.get("SiteUId").toString();
                                //String StationTypeUId = we1.get("StationTypeUId").toString();
                                String Description = we1.get("sitedesp").toString();
                                String Lat = we1.get("Lat").toString();
                                String Long = we1.get("Long").toString();
                                String stype = we1.get("sttype").toString();
                                String UId = we1.get("UId").toString();
                                String stypedes = we1.get("sitedesp").toString();


                                Code a = new Code(Code,SiteUId,Description,Lat,Long,stype,UId,stypedes);



                                CodeList.add(a);
                                b++;
                            }




                        } catch (NullPointerException ex){
                            Log.wtf("CameraDemo", ex.toString());
                        } catch (Exception e){
                            Log.wtf("CameraDemo", e.toString());
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {




                    }
                });


        singletongm.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);


        return changeres;
    }
    private void prepareAlbums() {



        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");

            if(we.length()==0){
                view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

            }
            else {
                view.findViewById(R.id.nodt).setVisibility(View.GONE);
            }

            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String CustomerEmail = we1.get("CustomerEmail").toString();
                String sitedesp = we1.get("sitedesp").toString();
                String Address = we1.get("Address").toString();
                String CustomerName = we1.get("CustomerName").toString();
                String CustomerContactNo = we1.get("CustomerContactNo").toString();
                String salu = we1.get("salu").toString();
                String surb = we1.get("surb").toString();
                String stat = we1.get("stat").toString();
                String uid = we1.get("UId").toString();
                String crdt="";
                if(we1.get("CreatedDate").toString()!="null"){
                    crdt =we1.get("CreatedDate").toString().split("T")[0];
                }

                siteList.add(sitedesp+"    \n"+salu+CustomerName+"    \n"+CustomerContactNo+"    \n"+CustomerEmail);
                siteList1.put(row,uid);
                Album a = new Album(CustomerEmail,sitedesp,Address,CustomerName,CustomerContactNo,salu,surb,stat,uid,crdt);
                albumListnew.add(a);

                b++;
            }
            adapter.notifyDataSetChanged();
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }
    public String getdmsg(String  val1,String  val2 ){
        String url = getContext().getResources().getString( R.string.weburl4)+"?id="+val1+"&id1="+val2;



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
    public String getdmsg2(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl23)+"?id="+val1;



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
    public String getdmsg3(String  val1,String  val2 ){
        String url = getContext().getResources().getString( R.string.weburl24)+"?id="+val1+"&cid="+val2;



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
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


    public void chngwhab(){
        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String gmname = we1.get("Name").toString();
                String gmdecsrip = we1.get("Description").toString();
                String gmaddrr = we1.get("Address").toString();
                String gmcontc = we1.get("ContactNos").toString();
                String gmurl = we1.get("WebUrl").toString();







                b++;
            }




        } catch (NullPointerException ex){

        } catch (Exception e){

        }

    }

}