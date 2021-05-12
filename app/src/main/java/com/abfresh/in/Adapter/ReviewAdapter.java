package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder> {

    private java.util.ArrayList<ArrayList> dataSet;
    Context context;
    int position=-1;
    private ReviewAdapter.OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onBannerClick(int position);

    }
    public void setOnItemClickListner(ReviewAdapter.OnItemClickListner listner){
        mListner = listner;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView review_dis,review_name;

        public MyViewHolder(View itemView,final ReviewAdapter.OnItemClickListner listner) {
            super(itemView);
            this.review_dis = (TextView) itemView.findViewById(R.id.review_dis);
            this.review_name = (TextView) itemView.findViewById(R.id.review_name);



        }
    }

    public ReviewAdapter(Context context, java.util.ArrayList<ArrayList> data) {
        this.context=context;
        this.dataSet = data;
    }

    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_adapter_layout, parent, false);


        ReviewAdapter.MyViewHolder myViewHolder = new ReviewAdapter.MyViewHolder(view,mListner);
        return myViewHolder;
    }




    @Override
    public void onBindViewHolder(final ReviewAdapter.MyViewHolder holder, final int listPosition) {

        TextView review_dis = holder.review_dis;
        TextView review_name = holder.review_name;

        position = listPosition;
        review_name.setText("-"+dataSet.get(listPosition).getQuantity());
        review_dis.setText(dataSet.get(listPosition).getWeight());


    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

}

