
    
    package com.abfresh.in.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Fragments.DeliverySlotBottomSheetDialog;
import com.abfresh.in.R;
import com.abfresh.in.Model.ChildItem;

import java.util.List;

    public class SlotAdapterTest extends RecyclerView.Adapter<com.abfresh.in.Adapter.SlotAdapterTest.ViewHolder>  {
        private List<ChildItem> CartProductList;
        android.content.Context Context;
        public int num = 0;
        //   private OnItemClickListner mListner;
        private com.abfresh.in.Adapter.SlotAdapterTest.OnItemClickListner mListner;

        public interface OnItemClickListner{
            void onSlotClick(int position, TextView textView);

        }
        public SlotAdapterTest(android.content.Context context, List<ChildItem> cartProductList) {
            CartProductList = cartProductList;
            Context = context;
        }
        public void setOnItemClickListner(com.abfresh.in.Adapter.SlotAdapterTest.OnItemClickListner listner){
            mListner = listner;
        }
        @NonNull
        @Override
        public com.abfresh.in.Adapter.SlotAdapterTest.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_date_test,parent,false);
            return new com.abfresh.in.Adapter.SlotAdapterTest.ViewHolder(view,mListner);
        }

        @SuppressLint("UseCompatLoadingForDrawables")
        @Override
        public void onBindViewHolder(@NonNull com.abfresh.in.Adapter.SlotAdapterTest.ViewHolder holder, int position) {

            holder.slot1.setText(CartProductList.get(position).getChildItemTitle());
            if(CartProductList.get(position).getIs_disable().equals("No")){
                holder.slot1.setEnabled(true);
                holder.slot1.setTextColor(Context.getResources().getColor(R.color.white));
                holder.icon_coupon_clock.setColorFilter(ContextCompat.getColor(Context, R.color.white), android.graphics.PorterDuff.Mode.SRC_IN);

            }else{
                holder.slot1.setEnabled(false);
                holder.slot1.setTextColor(Context.getResources().getColor(R.color.new_gray_color2));
                holder.icon_coupon_clock.setColorFilter(ContextCompat.getColor(Context, R.color.new_gray_color2), android.graphics.PorterDuff.Mode.SRC_IN);

            }
            holder.slot1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeliverySlotBottomSheetDialog.mListener.onButtonClicked(CartProductList.get(position).getChildItemTitle());
                    Utility.deliverySlot = CartProductList.get(position).getChildItemTitle();
                    Utility.deliveryDay = CartProductList.get(position).getDelivery_slots_name();
                    DeliverySlotBottomSheetDialog.activity.dismiss();
                }
            });

        }

        @Override
        public int getItemCount() {
            return CartProductList.size();
        }

        public static class ViewHolder extends RecyclerView.ViewHolder{

            TextView slot1;
            LinearLayout time_ll_adap;
            ImageView icon_coupon_clock;
            public ViewHolder(@NonNull View itemView,final com.abfresh.in.Adapter.SlotAdapterTest.OnItemClickListner listner) {
                super(itemView);

                slot1 = (TextView)itemView.findViewById(R.id.slot1);
                time_ll_adap = (LinearLayout) itemView.findViewById(R.id.time_ll_sdt);
                icon_coupon_clock = (ImageView) itemView.findViewById(R.id.icon_coupon_clock);
//                time_ll_adap.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if(listner != null){
//                            int position = getAdapterPosition();
//                            if(position != RecyclerView.NO_POSITION){
//                                listner.onSlotClick(position,slot1);
//                            }
//                        }
//                    }
//                });
            }
        }
    }


