package com.abfresh.in.Adapter;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.Fragments.DeliverySlotBottomSheetDialog;
import com.abfresh.in.R;
import com.abfresh.in.Model.CartList;
import com.abfresh.in.Model.ParentItem;

import java.util.ArrayList;
import java.util.List;

public class SlotAdapterTomorrow extends RecyclerView.Adapter<SlotAdapterTomorrow.ViewHolder>  {
    private ArrayList<CartList> CartProductList;
    android.content.Context Context;
    public int num = 0;
    private RecyclerView.RecycledViewPool
            viewPool
            = new RecyclerView
            .RecycledViewPool();
    //   private OnItemClickListner mListner;
    private SlotAdapterTomorrow.OnItemClickListner mListner;
    private List<ParentItem> itemList;
    public static int row_index = -1;
    public interface OnItemClickListner{
        void onSlotTomClick(int position, ParentItem cartList, TextView textView,String dateName);

    }
//    public SlotAdapterTomorrow(android.content.Context context, ArrayList<CartList> cartProductList) {
//        CartProductList = cartProductList;
//        Context = context;
//    }
    public SlotAdapterTomorrow(List<ParentItem> itemList) {
        this.itemList = itemList;
    }
    public void setOnItemClickListner(SlotAdapterTomorrow.OnItemClickListner listner){
        mListner = listner;
    }
    @NonNull
    @Override
    public SlotAdapterTomorrow.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_header_adapter,parent,false);
        return new SlotAdapterTomorrow.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotAdapterTomorrow.ViewHolder holder, int position) {

        ParentItem  parentItem
                = itemList .get(position);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(
                holder.child_recyclerview
                        .getContext(),
                LinearLayoutManager.VERTICAL ,
                false);
        holder.parent_item_title.setText(parentItem.getParentItemTitle());
        Log.w("DELTAG","parentItem.getParentItemTitle()===>"+parentItem.getParentItemTitle());
        // Since this is a nested layout, so
        // to define how many child items
        // should be prefetched when the
        // child RecyclerView is nested
        // inside the parent RecyclerView,
        // we use the following method
        layoutManager
                .setInitialPrefetchItemCount(
                        parentItem.getChildItemList().size());


        // Create an instance of the child
        // item view adapter and set its
        // adapter, layout manager and RecyclerViewPool
      SlotHeaderAdapter childItemAdapter
                = new SlotHeaderAdapter(
                parentItem.getChildItemList());
        holder.child_recyclerview.setLayoutManager(layoutManager);

        holder.child_recyclerview.setAdapter(childItemAdapter);

        holder.child_recyclerview.setRecycledViewPool(viewPool);

        holder.time_ll_sha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               DeliverySlotBottomSheetDialog.clickbtn = true;
                row_index=position;
                notifyDataSetChanged();
               mListner.onSlotTomClick(position,parentItem,holder.parent_item_title,parentItem.getParentItemTitle());

            }
        });
        if(row_index==position){
            holder.parent_item_title.setTextColor(Color.parseColor("#323231"));
        }else{
            holder.parent_item_title.setTextColor(Color.parseColor("#6d6d6d"));
        }
        if(! DeliverySlotBottomSheetDialog.clickbtn){
            if(0==position){ holder.parent_item_title.setTextColor(Color.parseColor("#323231"));}
        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView parent_item_title,date_sa_tv;
        LinearLayout time_ll_sha;
        RecyclerView child_recyclerview;
        public ViewHolder(@NonNull View itemView,final SlotAdapterTomorrow.OnItemClickListner listner) {
            super(itemView);

            parent_item_title = (TextView)itemView.findViewById(R.id.parent_item_title);
            time_ll_sha = (LinearLayout) itemView.findViewById(R.id.time_ll_sha);
            child_recyclerview = (RecyclerView) itemView.findViewById(R.id.child_recyclerview);

        }
    }
}
