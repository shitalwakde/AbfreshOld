package com.abfresh.in.Abfresh.adapter;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Abfresh.callback.SaveAddrIdCallbackLisener;
import com.abfresh.in.Abfresh.model.CartModel;
import com.abfresh.in.Abfresh.model.DeliveryList;
import com.abfresh.in.Abfresh.utils.RestClient;
import com.abfresh.in.Controller.SessionManagement;
import com.abfresh.in.R;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.abfresh.in.Controller.SessionManagement.KEY_City_ID;
import static com.abfresh.in.Controller.SessionManagement.KEY_Pincode;
import static com.abfresh.in.Controller.SessionManagement.KEY_USERID;


public class DeliveryListAdapter extends RecyclerView.Adapter<DeliveryListAdapter.MyViewHolder> {

    ArrayList<DeliveryList> mdata;
    SaveAddrIdCallbackLisener saveAddrIdCallbackLisener;
    SessionManagement sessionManagement;

    public DeliveryListAdapter(ArrayList<DeliveryList> mdata, SaveAddrIdCallbackLisener saveAddrIdCallbackLisener) {
        this.mdata=mdata;
        this.saveAddrIdCallbackLisener=saveAddrIdCallbackLisener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_list_adapter, parent, false);
        sessionManagement = new SessionManagement(parent.getContext());
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DeliveryList deliveryList = mdata.get(position);
        holder.save_address_rb.setText(deliveryList.getDelivery_location_address());
        if(deliveryList.getDelivery_location_default().equals("Yes")){
            holder.save_address_rb.setChecked(true);
        }else {
            holder.save_address_rb.setChecked(false);
        }

    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView delet_btn_sa, edit_btn_sa;
        RadioButton save_address_rb;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            delet_btn_sa=(TextView)itemView.findViewById(R.id.delet_btn_sa);
            edit_btn_sa=(TextView)itemView.findViewById(R.id.edit_btn_sa);
            save_address_rb=(RadioButton) itemView.findViewById(R.id.save_address_rb);


            delet_btn_sa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(itemView.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dlg_exit);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tv_yes=(TextView)dialog.findViewById(R.id.tv_yes);
                    TextView tv_no=(TextView)dialog.findViewById(R.id.tv_no);
                    TextView tv_sure=(TextView)dialog.findViewById(R.id.tv_sure);

                    tv_sure.setText("Are you sure want to delete your address ?");

                    tv_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            deleteAddress(mdata.get(getAdapterPosition()).getDelivery_location_id());
                            dialog.dismiss();
                        }
                    });

                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });


            save_address_rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Dialog dialog = new Dialog(itemView.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(false);
                    dialog.setContentView(R.layout.dlg_exit);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    TextView tv_yes=(TextView)dialog.findViewById(R.id.tv_yes);
                    TextView tv_no=(TextView)dialog.findViewById(R.id.tv_no);
                    TextView tv_sure=(TextView)dialog.findViewById(R.id.tv_sure);

                    tv_sure.setText("Are you sure want to confirm this address ?");

                    tv_yes.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(save_address_rb.isChecked()){
                                mdata.get(getAdapterPosition()).setDelivery_location_default("Yes");
                                saveAddrIdCallbackLisener.saveAddrIdCallback(mdata.get(getAdapterPosition()));
                            }else {
                                mdata.get(getAdapterPosition()).setDelivery_location_default("No");
                            }
                            dialog.dismiss();
                        }
                    });

                    tv_no.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
            });


            edit_btn_sa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveAddrIdCallbackLisener.editAddrIdCallback(mdata.get(getAdapterPosition()));
                }
            });
        }


        private void deleteAddress(String adapterPosition){
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("pincode", sessionManagement.getUserDetails().get(KEY_Pincode));
            jsonObject.addProperty("city_id", sessionManagement.getUserDetails().get(KEY_City_ID));
            jsonObject.addProperty("user_id", sessionManagement.getUserDetails().get(KEY_USERID));
            jsonObject.addProperty("delivery_location_id", adapterPosition);
            jsonObject.addProperty("via", "Android");

            new RestClient().getApiService().deleteDeliveryLocation(jsonObject, new Callback<CartModel>() {
                @Override
                public void success(CartModel cartModel, Response response) {
                    if(cartModel.getSuccess().equals("1")){
                        mdata.remove(getAdapterPosition());
                        notifyItemChanged(getAdapterPosition());
                    }else {

                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    error.printStackTrace();
                }
            });
        }
    }
}
