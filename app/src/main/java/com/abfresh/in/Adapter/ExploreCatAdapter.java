package com.abfresh.in.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.abfresh.in.Custom.MyBounceInterpolator;
import com.abfresh.in.R;

import java.util.ArrayList;

public class ExploreCatAdapter extends RecyclerView.Adapter<ExploreCatAdapter.ViewHolder> {
    Animation scaleUp;

    private Context mContext;
    private int lastPosition = -1;
    int row_index = -1;
    private ArrayList<com.abfresh.in.Model.ArrayList> productList;
    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onBannerClick(int position, ImageView productImage);

    }
    public ExploreCatAdapter(Context context, ArrayList<com.abfresh.in.Model.ArrayList> productList) {
        mContext = context;
        this.productList = productList;
    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }

    @NonNull
    @Override
    public ExploreCatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       // scaleUp = AnimationUtils.loadAnimation(mContext, R.anim.up_from_bottom);
        View view = LayoutInflater.from(mContext).inflate(R.layout.explorecat_layout,parent,false);
        return new ExploreCatAdapter.ViewHolder(view,mListner);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        holder.productImage.setImageResource(productList.get(position).getSizeArray());
//        Picasso.with(mContext).load(productList.get(position).getColorArray()).fit().centerInside().into(holder.productImage);
        Log.w("NBTAG", "cat image adapter "+productList.get(position).getColorArray());
//        Picasso.with(mContext).load(productList.get(position).getCatImage()).into(holder.productImage);
        holder.cat_item_name.setText(productList.get(position).getQuantity());

        Picasso.with(mContext).load(productList.get(position).getWeight()).into(holder.productImage);

        final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
        MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
        myAnim.setInterpolator(interpolator);
        holder.cat_ll.startAnimation(myAnim);
//        holder.cat_ll.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                final Animation myAnim = AnimationUtils.loadAnimation(mContext, R.anim.bounce);
//                MyBounceInterpolator interpolator = new MyBounceInterpolator(0.1, 20);
//                myAnim.setInterpolator(interpolator);
//                holder.cat_ll.startAnimation(myAnim);
//                return false;
//            }
//        });

//        Animation animation = AnimationUtils.loadAnimation(mContext,
//                (position > lastPosition-1) ? R.anim.up_from_bottom
//                        : R.anim.down_from_top);
//        holder.cat_ll.startAnimation(animation);
//        lastPosition = position;


      //  setAnimation(holder.cat_ll, position);

    }

    @Override
    public int getItemCount() {

        return productList.size();
//        return productList == null ? 0 : productList.size() * 2;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView productImage;
//        TextView productName,productPrice;
        LinearLayout cat_ll;
        TextView cat_item_name;

        public ViewHolder(@NonNull View itemView,final OnItemClickListner listner) {
            super(itemView);
            productImage = (ImageView)itemView.findViewById(R.id.cat_item_image);
//            productName = (TextView)itemView.findViewById(R.id.cat_item_name);
            cat_ll = (LinearLayout) itemView.findViewById(R.id.cat_ll);
            cat_item_name = (TextView) itemView.findViewById(R.id.cat_item_name);

            productImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onBannerClick(position,productImage);
                        }
                    }
                }
            });
        }
    }

    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            //TranslateAnimation anim = new TranslateAnimation(0,-1000,0,-1000);
            ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            //anim.setDuration(new Random().nextInt(501));//to make duration random number between [0,501)
            anim.setDuration(550);//to make duration random number between [0,501)
            viewToAnimate.startAnimation(anim);
            lastPosition = position;

        }
    }
}
