package mud.per.iw.pcom;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class fragmentseven extends RecyclerView.Adapter<fragmentseven.MyViewHolder> {

    private Context mContext;
    public static List<repo> repodata;
    private int columnWidth;
    private String changeres;
    private GridViewImageAdapter adapter;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView visitd,visitp, visitbr,visitag,visitpr,visitcns,visitdr,visitrmk,textView_name;
        private ImageView ivOwner;
        public RelativeLayout buttonLayout;
        public RecyclerView refg;
        public newExpandsps vbn;

        LinearLayoutManager mLayoutManager;
        public LinearLayout expandableLayout;
        private GridView gridView;
        public List<Species> spdata;
        public newExpandsps hoursTwoRecyclerViewAdapter;

        public MyViewHolder(View view) {
            super(view);

            spdata = new ArrayList<>();
            gridView = (GridView) view.findViewById(R.id.grid_view);
            visitd = (TextView)view.findViewById(R.id.visitd);
            visitp = (TextView)view.findViewById(R.id.visitsp);
            //refg=(RecyclerView)view.findViewById(R.id.recycler5);

            textView_name = (TextView)view.findViewById(R.id.textView_name);
            ////ivOwner = (ImageView) view.findViewById(R.id.imageView_Owner);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext,    LinearLayoutManager.HORIZONTAL, false);
            refg.setLayoutManager(linearLayoutManager);
            refg.setAdapter(hoursTwoRecyclerViewAdapter);
            buttonLayout = (RelativeLayout) view.findViewById(R.id.button);
        }
    }


    public fragmentseven(Context mContext, List<repo> repodata) {
        this.mContext = mContext;
        this.repodata = repodata;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expandable_card_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        repo album = repodata.get(position);
        holder.visitd.setText("Visit Detail: " +album.getvisitd());
        holder.visitp.setText("Visit type: " +album.getVisitType());

        holder.textView_name.setText("Station Visit: " +album.getcrdt());





        //for (int row = 0; row < (repos.get(i).getspsdata().size()); row++) {



        holder.hoursTwoRecyclerViewAdapter = new newExpandsps(mContext, album.getspsdata());
        holder.hoursTwoRecyclerViewAdapter.notifyDataSetChanged();



//          adapter5 = new newExpandsps(context, repos.get(i).getspsdata());
//            recyclerView1.setAdapter(adapter5);
//       // }
        // hoursTwoRecyclerViewAdapter.notifyDataSetChanged();
        getdmsg(album.getvisituid(),holder);



    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_album, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
        popup.show();
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_add_favourite:
                    Toast.makeText(mContext, "Add to favourite", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.action_play_next:
                    Toast.makeText(mContext, "Play next", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return repodata.size();
    }



    private void InitilizeGridLayout(int cnty,GridView gridView) {
        Resources r = mContext.getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                AppConstant.GRID_PADDING, r.getDisplayMetrics());

        columnWidth = (int) ((getScreenWidth() - ((AppConstant.NUM_OF_COLUMNS +5) * padding)) / AppConstant.NUM_OF_COLUMNS);

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
        WindowManager wm = (WindowManager) mContext
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
    private void prepare(String vstuid, fragmentseven.MyViewHolder vh) {

        List<dstimg> dstdata = new ArrayList<>();
        ArrayList<String>   imagePaths = new ArrayList<String>();

        try{

            JSONObject dbovh = null;

            dbovh = new JSONObject(changeres);

            JSONArray we = null;

            we = dbovh.getJSONArray("Table");



            int b=0;

            for (int row = 0; row < (we.length()); row++) {

                JSONObject we1 = we.getJSONObject(b);

                String visitimg = we1.get("ImageName").toString();
                String visitid = we1.get("UId").toString();


                dstimg a = new dstimg(visitimg ,visitid);
                dstdata.add(a);
                Log.wtf("tyt",vstuid);
                b++;
            }
            for (int row = 0; row < (dstdata.size()); row++) {

                imagePaths.add(mContext.getResources().getString( R.string.weburl16)+"/"+vstuid+"/"+dstdata.get(row).getvisitimg()+"");

            }

            InitilizeGridLayout(imagePaths.size(),vh.gridView);
            // Gridview adapter
            adapter = new GridViewImageAdapter(mContext, imagePaths,
                    columnWidth);

            // setting grid view adapter
            vh.gridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            //  recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }


    public String getdmsg(String  val1 , MyViewHolder vivholder){
        String url = mContext.getResources().getString( R.string.weburl15)+"?id="+val1;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        changeres = response.toString();
                        prepare(val1,vivholder);
                        Log.wtf("nmn", changeres);

                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                        Log.wtf("nmn", error.toString());

                    }
                });


        singletongm.getInstance(mContext).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }
}