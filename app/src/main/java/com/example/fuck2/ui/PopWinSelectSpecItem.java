package com.example.fuck2.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.fuck2.R;

import java.util.List;


public class PopWinSelectSpecItem extends LinearLayout {
    private String title;
    private List<String> values;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        TextView titleTextView = findViewById(R.id.title);
        titleTextView.setText(title);
    }

    public List<String> getValues() {
        return values;
    }

    public void onChange(String title) {

    }

    public void setValues(List<String> values) {
        this.values = values;
        RadioGroup radioGroup = findViewById(R.id.spec_set);
        radioGroup.removeAllViews();
        for (int i = 0; i < values.size(); i++) {
            final RadioButton radioButton = (RadioButton) LayoutInflater.from(getContext()).inflate(R.layout.spec_btn, null);
            radioButton.setText(values.get(i));
            radioButton.getLayoutParams();
            radioButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    onChange(radioButton.getText().toString());
                }
            });
            radioGroup.addView(radioButton);

        }
    }


    public void LoadLayout(Context context) {
        LayoutInflater.from(context).inflate(R.layout.popwin_select_spec_item, this, true);
    }

    public PopWinSelectSpecItem(Context context) {
        super(context);
        LoadLayout(context);
    }

    public PopWinSelectSpecItem(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LoadLayout(context);
    }

    public PopWinSelectSpecItem(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LoadLayout(context);
    }

    public PopWinSelectSpecItem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        LoadLayout(context);
    }
}
