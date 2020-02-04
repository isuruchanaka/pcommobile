package mud.per.iw.pcom;

import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class FirstFragment extends Fragment {


    View view;
    View view1;
    Button firstButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
// Inflate the layout for this fragment

// get the reference of Button
//        firstButton = (Button) view.findViewById(R.id.firstButton);
//// perform setOnClickListener on first Button
//        firstButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//// display a message by using a Toast
//                Toast.makeText(getActivity(), "First Fragment", Toast.LENGTH_LONG).show();
//            }
//        });



        view = inflater.inflate(R.layout.fragment_first, container, false);
        view1 = inflater.inflate(R.layout.cardview_item1, container, false);
        //View view1 = inflate(getContext(), R.layout.cardview_item1, null);



//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                500);
//        params.setMargins(10,20,10,20);
//        LinearLayout l = (LinearLayout)view.findViewById(R.id.ffcard);
//
//        for (int i =0;i<10;i++) {
//            MaterialCardView valueTV = new MaterialCardView(new ContextThemeWrapper(container.getContext(), R.style.Widget_MaterialComponents_CardView), null, 1);
//            valueTV.setLayoutParams(params);
//            valueTV.setCardBackgroundColor(getResources().getColor(R.color.colorAccent1) );
//            valueTV.setCardElevation(50);
//            //valueTV.setStrokeColor(getResources().getColor(R.color.colorAccent2));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                valueTV.setElevation(50);
//
//            }
//
//
//            //valueTV.setStrokeWidth(2);
//            valueTV.setClickable(true);
//           valueTV.setBackgroundResource(R.drawable.layout_bg);
//
//            l.addView(valueTV);
//        }

        LinearLayout l = (LinearLayout)view.findViewById(R.id.ffcard);
        ConstraintLayout l1 = (ConstraintLayout)view1.findViewById(R.id.cnslay);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                500);
        params.setMargins(10,20,10,20);
        for (int i =0;i<10;i++) {
        CardView infobase =(CardView) view1.findViewById(R.id.cdvw);
            infobase.setLayoutParams(params);
        infobase.setBackgroundResource(R.drawable.cdbk);
        infobase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), activity.class);
//                Log.e("Cardview","CLICK");
//                startActivity(intent);
            }
        });
//        ImageView icon1 = view.findViewById(R.id.);
//        icon1.setImageResource(R.drawable.ic_bank);



            l.addView(infobase);
        }

        return view;
    }
}