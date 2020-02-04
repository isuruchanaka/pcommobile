package mud.per.iw.pcom;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class GridViewImageAdapter extends BaseAdapter {

    private Context _activity;
    private ArrayList<String> _filePaths = new ArrayList<String>();
    private ArrayList<Bitmap> imgmb = new ArrayList<Bitmap>();
    private ArrayList<Integer> cnt = new ArrayList<Integer>();
    private int imageWidth;
    //private int _postion;




    public GridViewImageAdapter(Context activity, ArrayList<String> filePaths,
                                int imageWidth) {
        this._activity = activity;
        this._filePaths = filePaths;
        this.imageWidth = imageWidth;
    }

    @Override
    public int getCount() {
        return this._filePaths.size();
    }

    @Override
    public Object getItem(int position) {
        return this._filePaths.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
         HashMap<Integer,String > data2 =new HashMap<>();
        if (convertView == null) {

            imageView = new ImageView(_activity);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,imageWidth));

        } else {

            imageView = (ImageView) convertView;
        }

        //imageView.setImageResource(R.drawable.loading);

        String url = _filePaths.get(position);
        Log.wtf("tt", String.valueOf(imageWidth));

        Glide
                .with(_activity)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(imageView);
        data2.put(position, url);
//    ImageRequest request = new ImageRequest(url,
//            new Response.Listener<Bitmap>() {
//                @Override
//                public void onResponse(Bitmap bitmap) {
//
//                    Bitmap bitmap2 =  Bitmap.createScaledBitmap(bitmap,100, 100, true);
//                    imageView.setImageBitmap(bitmap2);
//                    //imgmb.add(bitmap);
//                    data2.put(position, bitmap);
//                    Log.wtf("tt", "vvvv");
//                }
//            }, 0, 0, null, null, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            Log.wtf("tt", error.toString());
//            error.printStackTrace();
//
//        }
//    }
//    );
//    AppCompatActivity activity = (AppCompatActivity) _activity;
   // singletongm.getInstance(activity).addToRequestQueue(request);





    imageView.setOnClickListener(new OnImageClickListener(position,data2));
        //this._postion = position;
        return imageView;
    }

    class OnImageClickListener implements OnClickListener {

       int _postion;
        HashMap<Integer, String> dt1;
        // constructor
        public OnImageClickListener(int position, HashMap<Integer, String> dt) {
            this._postion = position;
            this.dt1 = dt;

        }

        @Override
        public void onClick(View v) {
            // on selecting grid view image
            // launch full screen activity
            Log.wtf("ttr", String.valueOf(_postion));

            showImage(dt1.get(_postion));

        }

    }

    /*
     * Resizing image size
     */
    public void showImage(String nm) {
        Dialog builder = new Dialog(_activity);
        builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
        builder.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        builder.setContentView(R.layout.custom_dialog);
        builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //nothing;

            }
        });
        ImageView dialogButton = (ImageView) builder.findViewById(R.id.imageView_close);

        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                builder.dismiss();
            }
        });

        ImageView imageView = (ImageView) builder.findViewById(R.id.imageView3);
       // ImageView imageView = new ImageView(_activity);

        Glide
                .with(_activity)
                .load(nm)

                .apply(new RequestOptions()
                        .placeholder(R.drawable.loading)
                        .override(1000, 1000) // set exact size
                        .fitCenter() // keep memory usage low by fitting into (w x h) [optional]
                )
                .into(imageView);



        //imageView.setImageBitmap(nm);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);

        //imageView.setLayoutParams(new ViewGroup.LayoutParams(1000,1000));
//        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//                1000,
//                1000));
        builder.show();
    }

}