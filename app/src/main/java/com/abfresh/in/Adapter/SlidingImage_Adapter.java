package com.abfresh.in.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.squareup.picasso.Picasso;
import com.abfresh.in.Offers;
import com.abfresh.in.R;
import com.abfresh.in.TabActivity;
import com.abfresh.in.Model.SliderModel;

import java.util.ArrayList;

public class SlidingImage_Adapter extends PagerAdapter {


    private ArrayList<SliderModel> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context context,ArrayList<SliderModel> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


//        imageView.setImageResource(IMAGES.get(position));
        Picasso.with(context).load(IMAGES.get(position).getBanner_image()).into(imageView);

        view.addView(imageLayout, 0);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TabActivity.class);
                // intent.putExtra("ID", String.valueOf(position));
                intent.putExtra("ID", IMAGES.get(position).getBanner_id());
                intent.putExtra("image","");
              //  Log.w("BTAG", "ImageURL "+echorizontalList.get(position).getCatImage());
                Offers.offerClick=true;
               context.startActivity(intent);

            }
        });

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
