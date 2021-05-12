package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;
import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class AdvSlidingAdapter extends
        SliderViewAdapter<AdvSlidingAdapter.SliderAdapterVH> {
    private java.util.ArrayList<ArrayList> productList;

    private Context context;
//    private List<SliderItem> mSliderItems = new ArrayList<>();

    public AdvSlidingAdapter(Context context, java.util.ArrayList<ArrayList> sliderItems) {
        this.context = context;
        this.productList = sliderItems;
    }

    public void renewItems(java.util.ArrayList<ArrayList> sliderItems) {

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
    public AdvSlidingAdapter.SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.adv_sliding_image, null);
        return new AdvSlidingAdapter.SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(AdvSlidingAdapter.SliderAdapterVH viewHolder, final int position) {

        com.abfresh.in.Model.ArrayList sliderItem = productList.get(position);

        Picasso.with(context).load(productList.get(position).getCatImage()).into(viewHolder.productImage1);

//        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, TabActivity.class);
//                intent.putExtra("ID", productList.get(position).getName());
//                Log.w("DTAG",productList.get(position).getName());
//                intent.putExtra("image","");
//                Offers.offerClick=true;
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return productList.size();
    }

    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        View itemView;
        ImageView productImage1;
        //        TextView productName,productPrice;
        FrameLayout cat_ll1;


        public SliderAdapterVH(View itemView) {
            super(itemView);
            productImage1 = (ImageView)itemView.findViewById(R.id.image_adv);
            cat_ll1 = (FrameLayout) itemView.findViewById(R.id.frame2);
            this.itemView = itemView;
        }
    }

}
