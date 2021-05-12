package com.abfresh.in.Controller;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Adapter.CouponAdapter;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;


public class CustomDialogClass extends Dialog{

    public Activity myActivity;
    public Dialog d;
    SessionManagement sessionManagement;
    ProgressBar dialog_pb;
    RecyclerView ccd_rv;
    CouponAdapter couponAdapter;
    java.util.ArrayList<ArrayList> ThList;
    public CustomDialogClass(Activity a) {
        super(a);
        // TODO Auto-generated constructor stub
        this.myActivity = a;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.coupon_custom_dialog);
        sessionManagement = new SessionManagement(myActivity);
        ccd_rv = (RecyclerView)findViewById(R.id.ccd_rv);
//        dialog_pb = (ProgressBar) findViewById(R.id.dialog_pb);

//        name_account_tv.setText(Utilitys.userName + " - " + Utilitys.userAccount);
//        bank_name_tv.setText(Utilitys.BankName);
//        transfer_btn_po_d.setOnClickListener(this);
//        nw_cancel_btn_d.setOnClickListener(this);

    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.transfer_btn_po_d:
//
//                break;
//            case R.id.nw_cancel_btn_d:
//                dismiss();
//                break;
//            default:
//                break;
//        }
////        dismiss();
//    }

    private void getBaUserTransfer(String transPin) {

    }
}