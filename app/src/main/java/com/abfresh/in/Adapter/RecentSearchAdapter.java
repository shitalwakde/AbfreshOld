package com.abfresh.in.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abfresh.in.R;
import com.abfresh.in.Model.ArrayList;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.ViewHolder> implements Filterable {
    private java.util.ArrayList<ArrayList> CartProductList;
    private java.util.ArrayList<ArrayList> filteredData;
    //    private java.util.ArrayList<com.tbd.abFresh.model.ArrayList> CartProductList;
    android.content.Context Context;
    public int num = 0;
    private RecentSearchAdapter.OnItemClickListner mListner;
    public interface OnItemClickListner{
        void onAddClick(int position);
    }
    //   private OnItemClickListner mListner;

//    public RecentSearchAdapter(Context context, java.util.ArrayList<ArrayList>  cartProductList) {
//        CartProductList = cartProductList;
//        Context = context;
//    }

    public RecentSearchAdapter(Context applicationContext, java.util.ArrayList<ArrayList> offerList) {
        CartProductList = offerList;
        Context = applicationContext;
        filteredData = new java.util.ArrayList<>(CartProductList);
    }
    public void setOnItemClickListner(RecentSearchAdapter.OnItemClickListner listner){
        mListner = listner;
    }
    @NonNull
    @Override
    public RecentSearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_layout,parent,false);
        return new RecentSearchAdapter.ViewHolder(view,mListner);
    }



    @Override
    public void onBindViewHolder(@NonNull RecentSearchAdapter.ViewHolder holder, int position) {
        String adp_cityName = CartProductList.get(position).getRecipeTypeName();
        String adp_cityState = CartProductList.get(position).getRecipeImage();
        String adp_cityPincode = CartProductList.get(position).getRecipeDuration();
        String adp_cityAddress = CartProductList.get(position).getRecipeServes();

        holder.textView_search.setText(adp_cityAddress + " " +adp_cityName+","+adp_cityState+","+adp_cityPincode);


    }

    @Override
    public int getItemCount() {
        return CartProductList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_search;

        public ViewHolder(@NonNull View itemView,final RecentSearchAdapter.OnItemClickListner listner) {
            super(itemView);

            textView_search = (TextView)itemView.findViewById(R.id.textView_search);

            textView_search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listner != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listner.onAddClick(position);
                        }
                    }
                }
            });

        }
    }


    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            java.util.ArrayList<ArrayList> filteredList = new java.util.ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(filteredData);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (ArrayList item : filteredData) {
                    if (item.getRecipeServes().toLowerCase().contains(filterPattern) || item.getRecipeTypeName().toLowerCase().contains(filterPattern) || item.getRecipeDuration().toLowerCase().contains(filterPattern)|| item.getRecipeImage().toLowerCase().contains(filterPattern)) {
                        filteredList.add(item);
                    }
//                    else if(item.getRecipeDuration().toLowerCase().contains(filterPattern)){
//                        filteredData.add(item);
//                    }else if (item.getRecipeImage().toLowerCase().contains(filterPattern)){
//                        filteredData.add(item);
//                    }else if (item.getRecipeTypeName().toLowerCase().contains(filterPattern)){
//                        filteredData.add(item);
//                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            CartProductList.clear();
//            CartProductList=new java.util.ArrayList<>();
            //CartProductList.addAll((Collection<? extends ArrayList>) results.values);
            CartProductList.addAll((java.util.ArrayList<ArrayList>) results.values);
            notifyDataSetChanged();
        }
        };
}


