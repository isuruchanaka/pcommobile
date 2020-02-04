package mud.per.iw.pcom;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;

import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableRecyclerAdapter extends RecyclerView.Adapter<ExpandableRecyclerAdapter.ViewHolder>{
    public static List<repo> repos;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;
    private GridViewImageAdapter adapter;
    public List<Species> spsdata;
    private int columnWidth;
    private String changeres;



    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    public ExpandableRecyclerAdapter(List<repo> repos) {
        this.repos = repos;
        //set initial expanded state to false
        for (int i = 0; i < repos.size(); i++) {
            expandState.append(i, true);
        }
    }

    @Override
    public ExpandableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expandable_card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ExpandableRecyclerAdapter.ViewHolder holder, int position) {
        //holder.setIsRecyclable(false);
        final repo item = repos.get(position);

        holder.visitd.setText("Visit Detail: " + item.getvisitd());
        holder.visitp.setText("Visit type: " + item.getVisitType());

        holder.textView_name.setText("Station Visit: " + item.getcrdt());
        Log.wtf("dfg","dfg");
        getdmsg(item.getvisituid(), holder);
        try {
            if (!item.getvisituid().equals(getsp(item.getvisituid()))) {
              //  holder.expandableListView.getLayoutParams().height=800*holder.expandableListView.getChildCount();
                holder.ly2.getLayoutParams().height = 80*holder.expandableListView.getChildCount();

//                holder.ly3.getLayoutParams().height = 80*holder.expandableListView1.getChildCount();
                holder.expandableListDetail = ExpandableListDataPump.getData(item.getvisituid(),item.getspsdata(),item.getprdata());
                holder.expandableListTitle = new ArrayList<String>(holder.expandableListDetail.keySet());
                holder.expandableListAdapter = new CustomExpandableListAdapter(context, holder.expandableListTitle, holder.expandableListDetail);
                holder.expandableListView.setAdapter(holder.expandableListAdapter);
           //////////////////
//                holder.expandableListDetail1 = ExpandableListDataPump2.getData(item.getvisituid(),item.getprdata());
//                holder.expandableListTitle1 = new ArrayList<String>(holder.expandableListDetail1.keySet());
//                holder.expandableListAdapter1 = new CustomExpandableListAdapter(context, holder.expandableListTitle1, holder.expandableListDetail1);
//                holder.expandableListView1.setAdapter(holder.expandableListAdapter1);


            }
        }catch (Exception ex){

            holder.expandableListDetail = ExpandableListDataPump.getData(item.getvisituid(),item.getspsdata(),item.getprdata());
            holder.expandableListTitle = new ArrayList<String>(holder.expandableListDetail.keySet());
            holder.expandableListAdapter = new CustomExpandableListAdapter(context, holder.expandableListTitle, holder.expandableListDetail);
            holder.expandableListView.setAdapter(holder.expandableListAdapter);

            //////////////////
//            holder.expandableListDetail1 = ExpandableListDataPump2.getData(item.getvisituid(),item.getprdata());
//            holder.expandableListTitle1 = new ArrayList<String>(holder.expandableListDetail1.keySet());
//            holder.expandableListAdapter1 = new CustomExpandableListAdapter(context, holder.expandableListTitle1, holder.expandableListDetail1);
//            holder.expandableListView1.setAdapter(holder.expandableListAdapter1);

        }
        holder.expandableListView.setSelection(position);
//        holder.expandableListView1.setSelection(position);
        holder.expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int height = 0;
                int chkl = 0;
                int rt=holder.expandableListView.getChildCount();
                chkl=  item.getprdata().size()+item.getspsdata().size();
                for (int i = 0; i < holder.expandableListView.getChildCount(); i++) {
                    height += holder.expandableListView.getChildAt(i).getMeasuredHeight();
                    height += holder.expandableListView.getDividerHeight();
                }
               // holder.expandableListView.setSelectedGroup(groupPosition);
                //Toast.makeText(context,String.valueOf( holder.expandableListView.getChildCount()) , Toast.LENGTH_SHORT).show();
                holder.expandableListView.getLayoutParams().height = (height+(600*chkl));
                holder.ly2.getLayoutParams().height =(height+(600*chkl));


            }
        });

        // Listview Group collapsed listener
        holder.expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {

                int nb_children =  holder.expandableListView.getChildCount();
                holder.expandableListView.getLayoutParams().height -= 50*nb_children;
                //holder.expandableListView.getLayoutParams().height =109;
                holder.ly2.getLayoutParams().height =80*holder.expandableListView.getChildCount();
            }
        });
        ///////////////////////////////
//        holder.expandableListView1.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                int height = 0;
//                int rt=holder.expandableListView1.getChildCount();
//                for (int i = 0; i < holder.expandableListView1.getChildCount(); i++) {
//                    height += holder.expandableListView1.getChildAt(i).getMeasuredHeight();
//                    height += holder.expandableListView1.getDividerHeight();
//                }
//                holder.expandableListView1.setSelectedGroup(groupPosition);
//                holder.expandableListView1.getLayoutParams().height = (height+717);
//                holder.ly3.getLayoutParams().height =(height+717);
//            }
//        });
//
//        // Listview Group collapsed listener
//        holder.expandableListView1.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//
//                int nb_children =  holder.expandableListView1.getChildCount();
//                holder.expandableListView1.getLayoutParams().height -= 50*nb_children;
//                holder.ly3.getLayoutParams().height =117*holder.expandableListView1.getChildCount();
//            }
//        });



//        holder.expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
//
//            @Override
//            public void onGroupExpand(int groupPosition) {
//                Toast.makeText(context,
//                        holder.expandableListTitle.get(groupPosition) + " List Expanded.",
//                        Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        holder.expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
//
//            @Override
//            public void onGroupCollapse(int groupPosition) {
//                Toast.makeText(context,
//                        holder.expandableListTitle.get(groupPosition) + " List Collapsed.",
//                        Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//        holder.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        context,
//                        holder.expandableListTitle.get(groupPosition)
//                                + " -> "
//                                + holder.expandableListDetail.get(
//                                holder.expandableListTitle.get(groupPosition)).get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
//                return false;
//            }
//        });







        final boolean isExpanded = expandState.get(position);
        holder.expandableLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        holder.buttonLayout.setRotation(expandState.get(position) ? 180f : 0f);
        holder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                onClickButton(holder.expandableLayout, holder.buttonLayout,  position,holder);
            }
        });
    }

    Species getsp(String codeIsIn) {
        for(Species carnet : spsdata) {
            if(carnet.getrmk().equals(codeIsIn)) {
                return carnet;
            }
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView visitd,visitp, visitbr,visitag,visitpr,visitcns,visitdr,visitrmk,textView_name;
        private ImageView ivOwner;
        public RelativeLayout buttonLayout;
        public RecyclerView refg;
        public newExpandsps vbn;
        public LinearLayout ly2;
        public LinearLayout ly3;
        LinearLayoutManager mLayoutManager;
        public LinearLayout expandableLayout;
        ExpandableListView expListView;
        private GridView gridView;
        public List<Species> spdata;
        ExpandableListView expandableListView;
//        ExpandableListView expandableListView1;
        ExpandableListAdapter expandableListAdapter;
        ExpandableListAdapter expandableListAdapter1;
        List<String> expandableListTitle;
        HashMap<String, List<String>> expandableListDetail;
        List<String> expandableListTitle1;
        HashMap<String, List<String>> expandableListDetail1;

        public ViewHolder(View view) {
            super(view);
           // spdata = new ArrayList<>();
            gridView = (GridView) view.findViewById(R.id.grid_view);
            visitd = (TextView)view.findViewById(R.id.visitd);
            visitp = (TextView)view.findViewById(R.id.visitsp);
            //refg=(RecyclerView)view.findViewById(R.id.recycler5);
ly2=(LinearLayout)view.findViewById(R.id.ly1);
           // ly3=(LinearLayout)view.findViewById(R.id.ly2);
            textView_name = (TextView)view.findViewById(R.id.textView_name);
            ////ivOwner = (ImageView) view.findViewById(R.id.imageView_Owner);

            buttonLayout = (RelativeLayout) view.findViewById(R.id.button);
            expandableLayout = (LinearLayout) view.findViewById(R.id.expandableLayout);
            expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
//            expandableListView1 = (ExpandableListView) view.findViewById(R.id.expandableListView1);



        }
    }

    private void onClickButton(final LinearLayout expandableLayout, final RelativeLayout buttonLayout, final  int i,ViewHolder vb) {


        //Simply set View to Gone if not expanded
        //Not necessary but I put simple rotation on button layout
        if (expandableLayout.getVisibility() == View.VISIBLE){

            createRotateAnimator(buttonLayout, 180f, 0f).start();
            expandableLayout.setVisibility(View.GONE);
            expandState.put(i, false);
        }else{

            createRotateAnimator(buttonLayout, 0f, 180f).start();
            expandableLayout.setVisibility(View.VISIBLE);
            expandableLayout.requestFocus();
            expandState.put(i, true);
        }
    }

    //Code to rotate button
    private ObjectAnimator createRotateAnimator(final View target, final float from, final float to) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(target, "rotation", from, to);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        return animator;
    }

    private void InitilizeGridLayout(int cnty,GridView gridView) {
        Resources r = context.getResources();
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
    private void Initilizeexp(RecyclerView rs) {


        ViewGroup.LayoutParams params = rs.getLayoutParams();

        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        rs.setLayoutParams(params);
    }
    public int getScreenWidth() {
        int columnWidth;
        WindowManager wm = (WindowManager) context
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
    private void prepare(String vstuid,ViewHolder vh) {

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

                imagePaths.add(context.getResources().getString( R.string.weburl16)+"/"+vstuid+"/"+dstdata.get(row).getvisitimg()+"");

            }

            InitilizeGridLayout(imagePaths.size(),vh.gridView);
            // Gridview adapter
            adapter = new GridViewImageAdapter(context, imagePaths,
                    columnWidth);

            // setting grid view adapter
            vh.gridView.setAdapter(adapter);
          //  adapter.notifyDataSetChanged();

//            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
          //  recyclerView.setVisibility(View.VISIBLE);

        } catch (NullPointerException ex){
            Log.wtf("CameraDemo", ex.toString());

        } catch (Exception e){
            Log.wtf("CameraDemo", e.toString());
        }







    }


    public String getdmsg(String  val1 ,ViewHolder vivholder){
        String url = context.getResources().getString( R.string.weburl15)+"?id="+val1;

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


        singletongm.getInstance(context).addToRequestQueue(jsonObjectRequest);
        return changeres;
    }



//    @Override
//    public int getItemViewType(int position) {
//        // Just as an example, return 0 or 2 depending on position
//        // Note that unlike in ListView adapters, types don't have to be   contiguous
////        if(dataList.get(position).isVideo()){
////            return position;
////
////        }else{
////            return -1;//indicates general type, if you have more types other than video, you can use -1,-2,-3 and so on.
////        }
//    }

}
