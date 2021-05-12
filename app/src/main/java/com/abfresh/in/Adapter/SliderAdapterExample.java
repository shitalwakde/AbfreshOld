package com.abfresh.in.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.abfresh.in.Controller.AppController;
import com.abfresh.in.Offers;
import com.abfresh.in.R;
import com.abfresh.in.TabActivity;

import java.util.ArrayList;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderAdapterExample.SliderAdapterVH> {
    private ArrayList<com.abfresh.in.Model.ArrayList> productList;

    private Context context;
//    private List<SliderItem> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context,ArrayList<com.abfresh.in.Model.ArrayList> sliderItems) {
        this.context = context;
        this.productList = sliderItems;
    }

    public void renewItems(ArrayList<com.abfresh.in.Model.ArrayList> sliderItems) {

        this.productList = sliderItems;
        notifyDataSetChanged();
    }

    public void deleteItem(int position) {
        this.productList.remove(position);
        notifyDataSetChanged();
    }

    public void addItem(com.abfresh.in.Model.ArrayList sliderItem) {
        this.productList.add(sliderItem);
        notifyDataSetChanged();
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slidingimages_layout, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        com.abfresh.in.Model.ArrayList sliderItem = productList.get(position);

        Picasso.with(context).load(productList.get(position).getCatImage()).into(viewHolder.productImage);

        if (AppController.clickBanner.equals("1")){
            viewHolder.itemView.setEnabled(true);
            viewHolder.itemView.setClickable(true);
        }

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppController.clickBanner="1";
                viewHolder.itemView.setEnabled(false);
                viewHolder.itemView.setClickable(false);
                //((Container_Main_Class) context).finish();
                Intent intent = new Intent(context, TabActivity.class);
                intent.putExtra("ID", productList.get(position).getName());
                intent.putExtra("image","");
                Offers.offerClick=true;
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return productList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView productImage;
        //        TextView productName,productPrice;
        FrameLayout cat_ll;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            productImage = (ImageView)itemView.findViewById(R.id.image);
            cat_ll = (FrameLayout) itemView.findViewById(R.id.frame);
            this.itemView = itemView;
        }
    }

}
