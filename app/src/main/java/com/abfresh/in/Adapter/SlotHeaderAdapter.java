package com.abfresh.in.Adapter;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.abfresh.in.Controller.Utility;
import com.abfresh.in.Fragments.DeliverySlotBottomSheetDialog;
import com.abfresh.in.R;
import com.abfresh.in.Model.ChildItem;

import java.util.List;

public class SlotHeaderAdapter
        extends RecyclerView
        .Adapter<SlotHeaderAdapter.ChildViewHolder> {

    private List<ChildItem> ChildItemList;
    private SlotHeaderAdapter.OnItemClickListner mListner;
    Context context;
    // Constuctor 
    SlotHeaderAdapter(List<ChildItem> childItemList)
    {
        this.ChildItemList = childItemList;
    }
    public interface OnItemClickListner{
        void onSlotTomClick(int position, TextView textView);

    }
    @NonNull
    @Override
    public ChildViewHolder onCreateViewHolder(
            @NonNull ViewGroup viewGroup,
            int i)
    {

        // Here we inflate the corresponding 
        // layout of the child item 
        View view = LayoutInflater
                .from(viewGroup.getContext())
                .inflate(
                        R.layout.slot_adapter,
                        viewGroup, false);
        return new ChildViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ChildViewHolder childViewHolder,
            int position)
    {

        // Create an instance of the ChildItem 
        // class for the given position 
        ChildItem childItem
                = ChildItemList.get(position);

        // For the created instance, set title. 
        // No need to set the image for 
        // the ImageViews because we have 
        // provided the source for the images 
        // in the layout file itself 
        childViewHolder
                .ChildItemTitle
                .setText(childItem.getChildItemTitle());

        childViewHolder.time_ll_adap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeliverySlotBottomSheetDialog.mListener.onButtonClicked(ChildItemList.get(position).getChildItemTitle());
                Utility.deliverySlot = ChildItemList.get(position).getChildItemTitle();
                Utility.deliveryDay = ChildItemList.get(position).getDelivery_slots_name();
                DeliverySlotBottomSheetDialog.activity.dismiss();
//                Toast.makeText(v.getContext(), ChildItemList.get(position).getChildItemTitle()+""+ChildItemList.get(position).getDelivery_slots_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount()
    {

        // This method returns the number 
        // of items we have added 
        // in the ChildItemList 
        // i.e. the number of instances 
        // of the ChildItemList 
        // that have been created 
        return ChildItemList.size();
    }

    // This class is to initialize 
    // the Views present 
    // in the child RecyclerView 
    class ChildViewHolder
            extends RecyclerView.ViewHolder {

        TextView ChildItemTitle;
        LinearLayout time_ll_adap;
        ChildViewHolder(View itemView,SlotHeaderAdapter.OnItemClickListner listner)
        {
            super(itemView);
            ChildItemTitle
                    = itemView.findViewById(
                    R.id.slot1);
            time_ll_adap = (LinearLayout) itemView.findViewById(R.id.time_ll_adap);

//       time_ll_adap.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if(listner != null){
//                        int position = getAdapterPosition();
//                        if(position != RecyclerView.NO_POSITION){
//                            listner.onSlotTomClick(position,ChildItemTitle);
//                        }
//                    }
//                }
//            });
        }
    }
} 
