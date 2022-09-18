package com.abfresh.in.Abfresh.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.abfresh.in.Abfresh.activities.DashboardActivity;
import com.abfresh.in.Abfresh.callback.CategoryCallbackLisener;
import com.abfresh.in.Abfresh.model.NotificationModel;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.R;
import com.google.gson.JsonObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TCApplyFragment extends Fragment {


    TextView tv_title, tv_description;
    ImageView iv_back_arrow;
    CategoryCallbackLisener categoryCallbackLisener;
    NestedScrollView nestedscrollview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tcapply, container, false);
        init(view);

        categoryCallbackLisener.visibleBackArrowClickLisner();
        return view;
    }


    private void init(View view){
        tv_title=(TextView)view.findViewById(R.id.tv_title);
        tv_description=(TextView)view.findViewById(R.id.tv_description);
        nestedscrollview=(NestedScrollView)view.findViewById(R.id.nestedscrollview);


        if(getArguments().getString("type").equalsIgnoreCase("TCApply")){
            tv_title.setText("T & C Apply");
            getTermCondition();
        }else if(getArguments().getString("type").equalsIgnoreCase("SecureShopping")){
            tv_title.setText("Secure Shopping");
            getSecureShopping();
        }else if(getArguments().getString("type").equalsIgnoreCase("PrivacyPolicy")){
            tv_title.setText("Privacy Policy");
            getPrivacy();
        }else if(getArguments().getString("type").equalsIgnoreCase("ReturnCancellationPolicy")){
            tv_title.setText("Return & Cancellation Policy");
            getReturnCancellationPoloicy();
        }else if(getArguments().getString("type").equalsIgnoreCase("AboutAbFresh")){
            tv_title.setText("About AbFresh");
            getAbfreshAbout();
        }

    }



    private void getTermCondition(){
        JsonObject jsonObject = new JsonObject();

        new RestClient().getApiService().termsCondition(jsonObject, new Callback<NotificationModel>() {
            @Override
            public void success(NotificationModel notificationModel, Response response) {
                if(notificationModel.getSuccess().equals("1")){
                    nestedscrollview.setVisibility(View.VISIBLE);
                    tv_description.setText(notificationModel.getDesc());
                }else {
                    nestedscrollview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), notificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nestedscrollview.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

    }

    private void getPrivacy(){
        JsonObject jsonObject = new JsonObject();

        new RestClient().getApiService().privacy(jsonObject, new Callback<NotificationModel>() {
            @Override
            public void success(NotificationModel notificationModel, Response response) {
                if(notificationModel.getSuccess().equals("1")){
                    nestedscrollview.setVisibility(View.VISIBLE);
                    tv_description.setText(notificationModel.getDesc());
                }else {
                    nestedscrollview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), notificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nestedscrollview.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

    }


    private void getSecureShopping(){
        JsonObject jsonObject = new JsonObject();

        new RestClient().getApiService().secureShopping(jsonObject, new Callback<NotificationModel>() {
            @Override
            public void success(NotificationModel notificationModel, Response response) {
                if(notificationModel.getSuccess().equals("1")){
                    nestedscrollview.setVisibility(View.VISIBLE);
                    tv_description.setText(notificationModel.getDesc());
                }else {
                    nestedscrollview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), notificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nestedscrollview.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

    }


    private void getReturnCancellationPoloicy(){
        JsonObject jsonObject = new JsonObject();

        new RestClient().getApiService().returnCancellation(jsonObject, new Callback<NotificationModel>() {
            @Override
            public void success(NotificationModel notificationModel, Response response) {
                if(notificationModel.getSuccess().equals("1")){
                    nestedscrollview.setVisibility(View.VISIBLE);
                    tv_description.setText(notificationModel.getDesc());
                }else {
                    nestedscrollview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), notificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nestedscrollview.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

    }


    private void getAbfreshAbout(){
        JsonObject jsonObject = new JsonObject();

        new RestClient().getApiService().aboutUs(jsonObject, new Callback<NotificationModel>() {
            @Override
            public void success(NotificationModel notificationModel, Response response) {
                if(notificationModel.getSuccess().equals("1")){
                    nestedscrollview.setVisibility(View.VISIBLE);
                    tv_description.setText(notificationModel.getDesc());
                }else {
                    nestedscrollview.setVisibility(View.GONE);
                    Toast.makeText(getContext(), notificationModel.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                nestedscrollview.setVisibility(View.GONE);
                error.printStackTrace();
            }
        });

    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof DashboardActivity) {
            categoryCallbackLisener = (CategoryCallbackLisener) context;
        }
    }

}
