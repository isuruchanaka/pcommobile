package mud.per.iw.pcom;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

public class ComplainAdapter extends RecyclerView.Adapter<ComplainAdapter.MyViewHolder> {

    private Context mContext;
    public static List<station> statoinList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count,crdate,count2,count3,count4,count5;
        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.count1);
            count2 = (TextView) view.findViewById(R.id.count2);
            count3 = (TextView) view.findViewById(R.id.count3);
            count4 = (TextView) view.findViewById(R.id.count4);
            count5 = (TextView) view.findViewById(R.id.count5);
            crdate = (TextView) view.findViewById(R.id.count);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }


    public ComplainAdapter(Context mContext, List<station> statoinList) {
        this.mContext = mContext;
        this.statoinList = statoinList;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.compcard, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        station album = statoinList.get(position);
        holder.title.setText(album.getstype());
        String glat=album.getStationTypeUId();
        String[] locarr = glat.split(",");
        String Lat = locarr[0];
        String Long = locarr[1];
        if(Lat.equals("null")){

            Lat="";
        }

        //String glon=album.getLong();
        if(Long.equals("null")){

            Long="";
        }
        holder.count.setText("Lattitude:"+Lat+"        Longitude:" +Long);
        //holder.crdate.setText("Remarks:"+album.getstypedes() );
        holder.count2.setText("Station: "+album.getUId() );
        holder.count3.setText("Customer: "+album.getCode() );
        holder.count4.setText("Contact person:"+album.getLat()+"  Contact No:"+album.getLong());
        holder.count5.setText("Species: "+album.getDescription());
//holder.thumbnail.setImageResource(album.getThumbnail());
        // loading album cover using Glide library
        //Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                alertDialog.setMessage("Remarks");
                LinearLayout layout = new LinearLayout(mContext);
                layout.setOrientation(LinearLayout.VERTICAL);



                final TextView titleBox = new TextView(mContext);
                titleBox.setText(album.getstypedes());

                layout.addView(titleBox); // Notice this is an add method


                alertDialog.setView(layout);
                alertDialog.setIcon(R.drawable.ic_menu_manage);


                alertDialog.show();

            }
        });
        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // showPopupMenu(holder.overflow);

                String glat=album.getStationTypeUId();
                String[] locarr = glat.split(",");
                String Lat = locarr[0];
                String Long = locarr[1];

                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new fourthfragment();
                Bundle bundle=new Bundle();
                bundle.putString("lat",Lat);
                bundle.putString("longi",Long);
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