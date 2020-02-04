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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Expandpr extends RecyclerView.Adapter<Expandpr.ViewHolder>{
    public static List<Products> prds;
    private SparseBooleanArray expandState = new SparseBooleanArray();
    private Context context;


    private int columnWidth;
    private String changeres;

    public Expandpr(List<Products> prds) {
        this.prds = prds;
        //set initial expanded state to false
        for (int i = 0; i < prds.size(); i++) {
            expandState.append(i, false);
        }
    }

    @Override
    public Expandpr.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        this.context = viewGroup.getContext();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.expandpr, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final Expandpr.ViewHolder viewHolder, final  int i) {

        //viewHolder.setIsRecyclable(false);


        Log.wtf("biu",prds.get(i).getpuid());
        viewHolder.visitd.setText("Product: " +prds.get(i).getpdesc());
        viewHolder.visitbr.setText("Product: " +prds.get(i).getpdesc());









        //check if view is expanded
        final boolean isExpanded = expandState.get(i);
        viewHolder.expandableLayout.setVisibility(isExpanded?View.VISIBLE:View.GONE);

        viewHolder.buttonLayout.setRotation(expandState.get(i) ? 180f : 0f);
        viewHolder.buttonLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                onClickButton(viewHolder.expandableLayout, viewHolder.buttonLayout,  i);
            }
        });

        viewHolder.rmvly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                prds.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, prds.size());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return prds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView visitd,visitsp, visitbr,visitag,visitpr,visitcns,visitdr,visitrmk,textView_name;
        private ImageView ivOwner;
        public RelativeLayout buttonLayout,rmvly;
        public LinearLayout expandableLayout;

        public ViewHolder(View view) {
            super(view);


            visitd = (TextView)view.findViewById(R.id.spdesc);
            visitbr  = (TextView)view.findViewById(R.id.prduct);


            ////ivOwner = (ImageView) view.findViewById(R.id.imageView_Owner);

            buttonLayout = (RelativeLayout) view.findViewById(R.id.exbtn);
            rmvly = (RelativeLayout) view.findViewById(R.id.rmvbtn);
            expandableLayout = (LinearLayout) view.findViewById(R.id.expandableLayout2);
        }
    }

    private void onClickButton(final LinearLayout expandableLayout, final RelativeLayout buttonLayout, final  int i) {


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

}
