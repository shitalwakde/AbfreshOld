package com.abfresh.in.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.NewProductDiscription;
import com.abfresh.in.R;

import java.util.ArrayList;

public class HorizontallScrollAdapter extends RecyclerView.Adapter<HorizontallScrollAdapter.MyViewHolder> {

    private ArrayList<com.abfresh.in.Model.ArrayList> dataSet;
    Context context;
    int position=-1;
    private OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onBannerClick(int position);

    }
    public void setOnItemClickListner(OnItemClickListner listner){
        mListner = listner;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView textViewName;

        ImageView imageViewIcon;
        RelativeLayout tab_name_rl;
        RelativeLayout hory_rl;
        View line_tab_npda;
        public MyViewHolder(View itemView,final OnItemClickListner listner) {
            super(itemView);
            this.textViewName = (TextView) itemView.findViewById(R.id.textView_npd);
            this.tab_name_rl = (RelativeLayout) itemView.findViewById(R.id.tab_name_rl);
            this.line_tab_npda = (View) itemView.findViewById(R.id.line_tab_npda);

//            this.imageViewIcon = (ImageView) itemView.findViewById(R.id.imgView);
//            imageViewIcon.setVisibility(View.GONE);
            this.tab_name_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onBannerClick(position);
                        }
                    }
                }
            });
        }
    }

    public HorizontallScrollAdapter(Context context, ArrayList<com.abfresh.in.Model.ArrayList> data) {
        this.context=context;
        this.dataSet = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_pro_dis_tab, parent, false);

        //view.setOnClickListener(MainActivity.myOnClickListener);

        MyViewHolder myViewHolder = new MyViewHolder(view,mListner);
        return myViewHolder;
    }

//    @Override
//    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
//
//    }


    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int listPosition) {

        TextView textViewName = holder.textViewName;

        // TextView textViewVersion = holder.textViewVersion;
        ImageView imageView = holder.imageViewIcon;
//        Picasso.with(context).load(dataSet.get(listPosition).getHoriBannerImage()).into(imageView);
        position = listPosition;
        textViewName.setText(dataSet.get(listPosition).getName());
        Log.w("PADTAG","category====>"+dataSet.get(listPosition).getCatImage());
        Log.w("PADTAG","category ID====>"+NewProductDiscription.categoryId);
        if(NewProductDiscription.categoryId.equals(dataSet.get(listPosition).getCatImage())){
            if(position == listPosition){
                textViewName.setTextColor(context.getResources().getColor(R.color.newYellow));
                holder.line_tab_npda.setVisibility(View.VISIBLE);
            }

        }
        //textViewVersion.setText(dataSet.get(listPosition).getVersion());
//        imageView.setImageResource(dataSet.get(listPosition).getHoriBannerImage());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

//    public class ViewHolder extends RecyclerView.ViewHolder {
//        TextView image_name;
//        ImageView image_hs;
//        RelativeLayout todayimg_rl;
//
//        public ViewHolder(View itemView) {
//            super(itemView);
//
//            image_name = (TextView) itemView.findViewById(R.id.id_index_gallery_item_text);
//            image_hs = (ImageView) itemView.findViewById(R.id.id_index_gallery_item_image);
//
////
//        }
//    }
}

