package mud.per.iw.pcom;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
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


public class Fragviewcomp extends Fragment {

    View view;
    Button secondButton;
    private RecyclerView recyclerView;
    String rid;
    String ctry;
    private ComplainAdapter adapter;
    public static List<station> albumListnew1;


    private String changeres;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_fragviewcomp, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view4);
        view.findViewById(R.id.nodt).setVisibility(View.GONE);
        albumListnew1 = new ArrayList<>();

        SharedPreferences prefs = getContext().getSharedPreferences("userinfo", MODE_PRIVATE);
        String restoredText = prefs.getString("UserID", null);



        if (restoredText != null) {
            rid=  prefs.getString("RoleID", "no");
            ctry=  prefs.getString("Country", "no");
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

              //  getdmsg(strtext);




            adapter = new ComplainAdapter(container.getContext(), albumListnew1);



        }

        else {

            getdmsg(ctry);

//            if(rid.equals("1003")){
//                getdmsg2(restoredText);
//                adapter = new AlbumsAdapter(container.getContext(), albumListnew);
//            }
//            else {
            adapter = new ComplainAdapter(container.getContext(), albumListnew1);
            adapter.notifyDataSetChanged();
//            }


        }



        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(container.getContext(), 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        // recyclerView.setVisibility(View.GONE);
        view.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);

        return view;
    }






    private void prepareAlbums() {



        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;
if(we.length()==0){
    view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

}
else {
    view.findViewById(R.id.nodt).setVisibility(View.GONE);
}
            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String Code = we1.get("FirstName").toString()+" "+we1.get("LastName").toString();
                String SiteUId = we1.get("Complain_date").toString();
                String StationTypeUId = we1.get("Locof_sighting").toString();
                String[] locarr = StationTypeUId.split(",");
                String Description ="";
                if(!(we1.get("sp1").toString().equals(""))&&!(we1.get("sp1").toString().equals("null"))){
                    Description=we1.get("sp1").toString();
                }
                if(!(we1.get("sp2").toString().equals(""))&&!(we1.get("sp2").toString().equals("null"))){
                    Description=Description+","+we1.get("sp2").toString();
                }
                if(!(we1.get("sp3").toString().equals(""))&&!(we1.get("sp3").toString().equals("null"))){
                    Description=Description+","+we1.get("sp3").toString();
                }
                if(!(we1.get("sp4").toString().equals(""))&&!(we1.get("sp4").toString().equals("null"))){
                    Description=Description+","+we1.get("sp4").toString();
                }
                if(!(we1.get("sp5").toString().equals(""))&&!(we1.get("sp5").toString().equals("null"))){
                    Description=Description+","+we1.get("sp5").toString();
                }
                if(!(we1.get("sp6").toString().equals(""))&&!(we1.get("sp6").toString().equals("null"))){
                    Description=Description+","+we1.get("sp6").toString();
                }

                String contact_person = we1.get("contact_person").toString();
                String contact_no = we1.get("contact_no").toString();
                String stdes = we1.get("stdes").toString();

                String Lat = locarr[0];
                String Long = locarr[1];
                String stype = we1.get("sitedesp").toString();
                String UId = we1.get("Uid").toString();
                String stypedes = we1.get("remarks").toString();


                station a = new station(Code,SiteUId,StationTypeUId,Description,contact_no,contact_person,stype,stdes,stypedes);
                albumListnew1.add(a);

                b++;
            }
            if(albumListnew1.size()==0){
                view.findViewById(R.id.nodt).setVisibility(View.VISIBLE);

            }
            else {
                view.findViewById(R.id.nodt).setVisibility(View.GONE);
            }
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
//            recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }






        adapter.notifyDataSetChanged();
    }
    public String getdmsg(String  val1 ){
        String url = getContext().getResources().getString( R.string.weburl31)+"?id="+val1;

        Log.wtf("ert", url.toString());

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


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }




}