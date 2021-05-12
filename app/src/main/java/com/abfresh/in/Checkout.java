package com.abfresh.in;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

//import com.example.darubottle.R;
import com.abfresh.in.Adapter.ChooseDeliveryAdapter;
import com.abfresh.in.Model.CartList;
import com.sanojpunchihewa.glowbutton.GlowButton;

import java.util.ArrayList;

public class Checkout extends AppCompatActivity {
    RecyclerView checkout_rv;
    ChooseDeliveryAdapter checkoutAdapter;
    ArrayList<CartList> checkoutLists;
    Toolbar checkoutToolbar;
    GlowButton checkout_placeorder_btn;
    RelativeLayout select_pay_rl;
    int click=1;
    RadioGroup pay_option_rg;
    RadioButton checkout_cod_rb,checkout_paytm_rb;
    RadioButton paymentOption_rb;
    int radioId=0;
    ImageView cout_home_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout_layout);

        checkout_cod_rb = (RadioButton)findViewById(R.id.checkout_cod_rb);
        checkout_paytm_rb =(RadioButton)findViewById(R.id.checkout_paytm_rb);
        checkoutToolbar = (Toolbar)findViewById(R.id.checkout_toolbar);
        setSupportActionBar(checkoutToolbar);
        getSupportActionBar().setTitle("");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);
        select_pay_rl = (RelativeLayout)findViewById(R.id.select_pay_rl);
        pay_option_rg = (RadioGroup)findViewById(R.id.pay_option_rg);
        checkout_rv = (RecyclerView)findViewById(R.id.checkout_rv);
        checkout_placeorder_btn = (GlowButton)findViewById(R.id.checkout_proceed_btn);
        cout_home_btn = (ImageView) findViewById(R.id.cout_home_btn);

        checkout_placeorder_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if (radioId==0){
//                    Toast.makeText(Checkout.this, "Please select payment Option", Toast.LENGTH_SHORT).show();
//                }else{
//                    finish();
//                    Toast.makeText(Checkout.this, "Thanks for Buying", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(Checkout.this,Container_Main_Class.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
//                }
                Intent intent = new Intent(Checkout.this,Proceed_To_Pay.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

            }
        });

//        select_pay_rl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(click==1){
//                    pay_option_rg.setVisibility(View.VISIBLE);
//                    click=2;
//                }else{
//                    pay_option_rg.setVisibility(View.GONE);
//                    click=1;
//                }
//
//            }
//        });
//        getCheckoutist();
        cout_home_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

//    public void chekout_cheked(View view){
//        radioId = pay_option_rg.getCheckedRadioButtonId();
//        paymentOption_rb = findViewById(radioId);
//    }


//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id =item.getItemId();
//        if(id== android.R.id.home){
//            finish();
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

//    private void getCheckoutist() {
//        checkoutLists = new ArrayList<CartList>();
//        for (int i = 0; i< MyData.cartBrandName.length; i++){
//            checkoutLists.add(new CartList(
//
//
//                    MyData.cartImage[i],
//                    MyData.cartBrandName[i],
//                    MyData.cartProductWeight[i],
//                    MyData.cartNewPricee[i],
//                    MyData.cartProducQuant[i]
//
//            ));
//        }
//        checkoutAdapter = new ChooseDeliveryAdapter(checkoutLists);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        checkout_rv.setLayoutManager(linearLayoutManager);
//        checkout_rv.setAdapter(checkoutAdapter);
//        checkout_rv.setNestedScrollingEnabled(false);
//    }
}
