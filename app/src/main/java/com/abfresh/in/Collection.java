package com.abfresh.in;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import com.abfresh.in.Adapter.RecipeAdapter;
import com.abfresh.in.Model.MyData;

public class Collection extends Fragment {

    RecyclerView collection_rv;
    ArrayList<com.abfresh.in.Model.ArrayList> catProductList;
    RecipeAdapter recipeCollectioAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recipe_collection_layout,container,false);

        collection_rv = (RecyclerView)view.findViewById(R.id.collection_rv);
        getCatProduct();
        return view;
    }

    private void getCatProduct() {
        catProductList = new ArrayList<com.abfresh.in.Model.ArrayList>();
        for(int i = 0; i< MyData.baProductPic.length; i++){
            catProductList.add(new com.abfresh.in.Model.ArrayList(
                    MyData.baProductPic[i],
                    MyData.rectName[i],
                    MyData.recType[i]

            ));
        }
        recipeCollectioAdapter = new RecipeAdapter(getActivity(), catProductList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(),1);
        collection_rv.setLayoutManager(gridLayoutManager);
        collection_rv.setNestedScrollingEnabled(false);
        collection_rv.setAdapter(recipeCollectioAdapter);
//        categoryProductAdapter.setOnItemClickListner(new CategoryProductAdapter.OnItemClickListner() {
//            @Override
//            public void onCatProductClick(int position) {
//
//                Intent intent = new Intent(getContext(),Product_Discription.class);
//                intent.putExtra("proimage",MyData.fragProductPic[position]);
//                String price= MyData.fragProductPrice[position];
//                intent.putExtra("proprice",price);
//                startActivity(intent);
//                categoryProductAdapter.notifyDataSetChanged();
//            }
//        });



    }

}
