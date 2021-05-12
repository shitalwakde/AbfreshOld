package com.abfresh.in;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.Button;

import com.airbnb.lottie.LottieAnimationView;
import com.abfresh.in.Fragments.Container_Fragment;
import com.muddzdev.styleabletoast.StyleableToast;

import jrizani.jrspinner.JRSpinner;

public class SelectCity {
    public void showDialog(Activity activity, String msg){
        final Dialog dialog = new Dialog(activity);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("My Location");
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout);


//        TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
//        text.setText(msg
        LottieAnimationView logo_dialog = (LottieAnimationView)dialog.findViewById(R.id.logo_dialog);
        JRSpinner  jrSpinner_dialog_city = (JRSpinner)dialog.findViewById(R.id.JRSpinner_city_dialog);
        Button dialogButton = (Button) dialog.findViewById(R.id.submit);
        jrSpinner_dialog_city.setItems(activity.getResources().getStringArray(R.array.city_list));

        jrSpinner_dialog_city.setTitle("My Location");
        jrSpinner_dialog_city.setOnItemClickListener(new JRSpinner.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
//                StyleableToast.makeText(activity,String.valueOf(position), Toast.LENGTH_SHORT).show();
               Container_Fragment.cityPosition = position;
//                cityBoolean = true;
                Container_Fragment.cityBoolean = true;
            }
        });
        logo_dialog.playAnimation();
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if(Container_Fragment.cityBoolean){
                    logo_dialog.pauseAnimation();
                    dialog.dismiss();
                }else{
//                    StyleableToast.makeText(activity, "Please Enter any one Location", Toast.LENGTH_SHORT).show();
                    StyleableToast.makeText(activity,"Please Select City",R.style.mySizeToast).show();
                }



//                dialog.dismiss();
            }
        });

        dialog.show();

    }
    }
