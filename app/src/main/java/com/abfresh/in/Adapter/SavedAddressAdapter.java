package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Controller.Utility;
import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;

import java.util.ArrayList;

public class SavedAddressAdapter extends RecyclerView.Adapter<SavedAddressAdapter.ViewHolder> {
    //    int mSelectedItem;
    ArrayList<CartList> save_address_list;
    private int rbPosoition = 0;
    private RadioButton rbChecked = null;
    Context context;
    //------------------------------------Define OnItemClickListner (step 2)-------------------------------------------------------------------------

    private OnItemClickListner mListner;
    //------------------------------------Creating Interface for clicklistner(step 1)-------------------------------------------------------------------------

    public interface OnItemClickListner{
        void onEditClick(int position);
        void onDeletClick(int position);
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    public SavedAddressAdapter(Context context, ArrayList<CartList> save_address_list) {
        this.save_address_list = save_address_list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saved_address_item,parent,false);
        return new SavedAddressAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.save_address_rb.setText(save_address_list.get(position).getSaveAddress());

        if (holder.save_address_rb.isChecked()){
            rbPosoition = holder.getAdapterPosition();
//            Utility.Delivery_location_id = save_address_list.get(position).getDelivery_location_id();
//            Toast.makeText(context,  Utility.Delivery_location_id , Toast.LENGTH_SHORT).show();
        }
//        holder.save_address_rb.setChecked(position == mSelectedItem);
    }

    @Override
    public int getItemCount() {
        return save_address_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RadioButton save_address_rb;
        TextView delet_btn_sa,edit_btn_sa;
        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            save_address_rb = (RadioButton)itemView.findViewById(R.id.save_address_rb);
            delet_btn_sa = (TextView) itemView.findViewById(R.id.delet_btn_sa);
            edit_btn_sa = (TextView) itemView.findViewById(R.id.edit_btn_sa);

            delet_btn_sa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onDeletClick(position);

                        }
                    }
                }
            });

            edit_btn_sa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onEditClick(position);

                        }
                    }
                }
            });

            if (rbPosoition == 0 && save_address_rb.isChecked())
            {
                rbChecked = save_address_rb;
                rbPosoition = 0;
            }
            save_address_rb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RadioButton rb = (RadioButton) v;
                    int clickedPos = getAdapterPosition();
                    if (rb.isChecked()){
                        if(rbChecked != null)
                        {
                            rbChecked.setChecked(false);
                        }
                        rbChecked = rb;
                        rbPosoition = clickedPos;
                        Utility.Delivery_location_id = save_address_list.get(rbPosoition).getDelivery_location_id();
                        Utility.MyAddressNew = save_address_list.get(rbPosoition).getSaveAddress();
//                        Toast.makeText(context,  Utility.Delivery_location_id , Toast.LENGTH_SHORT).show();
                    }
                    else{
                        rbChecked = null;
                        rbPosoition = 0;
                    }

                }
            });

        }


    }

}
