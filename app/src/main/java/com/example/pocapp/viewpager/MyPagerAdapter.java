package com.example.pocapp.viewpager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.pocapp.R;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {

    private Context context;
    private List<ScreenSlide> slides;

    public MyPagerAdapter(Context context, List<ScreenSlide> slides) {
        this.context = context;
        this.slides = slides;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = inflater.inflate(R.layout.screen_layout, null);

            TextView title = view.findViewById(R.id.textViewTitle);
            TextView description = view.findViewById(R.id.textViewDescription);

            title.setText(slides.get(position).getTitle());
            description.setText(slides.get(position).getDescription());

            container.addView(view);

            return view;

    }

    @Override
    public int getCount() {
        return slides.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
