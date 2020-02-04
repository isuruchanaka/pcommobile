package mud.per.iw.pcom;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.MyViewHolder> {

    private Context mContext;
    public static List<station> statoinList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,crdate,count2,count3;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count1);
            count2 = (TextView) view.findViewById(R.id.count2);
            count3 = (TextView) view.findViewById(R.id.count3);
            crdate = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public StationAdapter(Context mContext, List<station> statoinList) {
        this.mContext = mContext;
        this.statoinList = statoinList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stationcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        station album = statoinList.get(position);
        holder.title.setText(album.getDescription());
        String glat=album.getLat();
        if(glat.equals("null")){

            glat="";
        }

        String glon=album.getLong();
        if(glon.equals("null")){

            glon="";
        }
        holder.count.setText("Lattitude:"+glat+"        Longitude:" +glon);
        holder.crdate.setText("QR Code:"+album.getCode() );
//        holder.count2.setText(album.getCustomerContactNo());
//        holder.count3.setText(album.getAddress());
//holder.thumbnail.setImageResource(album.getThumbnail());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new fragmentfifth();
                Bundle bundle=new Bundle();
                bundle.putString("stationuid",album.getUId());
                // bundle.putString("longi",album.getlongi());

                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, myFragment).addToBackStack(null).commit();
            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showPopupMenu(holder.overflow);



                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new fourthfragment();
                Bundle bundle=new Bundle();
                bundle.putString("lat",album.getLat());
                bundle.putString("longi",album.getLong());
                bundle.putString("desc",album.getDescription());
                bundle.putString("addr",album.getstypedes());
                myFragment.setArguments(bundle);
                activity.getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(R.animator.slide_in_left, R.animator.slide_out_right).replace(R.id.frameLayout, myFragment).addToBackStack(null).commit();


//set Fragmentclass Arguments

            }
        });


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showPopupMenu(holder.overflow);
            }
        });
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
        return statoinList.size();
    }
}