package com.rainfool.zhihudailyrrdcopy.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.rainfool.zhihudailyrrdcopy.R;

/**
 * Created by rainfool on 2016/3/17.
 */
public class TextSliderView extends BaseSliderView {
    public TextSliderView(Context context) {
        super(context);
    }

    @Override
    public View getView() {
        View v = LayoutInflater.from(getContext()).inflate(R.layout.slider_item, null);
        ImageView target = (ImageView) v.findViewById(R.id.iv_slider);
        TextView title = (TextView) v.findViewById(R.id.tv_title);
        title.setText(getDescription());
        bindEventAndShow(v, target);
        return v;
    }
}
