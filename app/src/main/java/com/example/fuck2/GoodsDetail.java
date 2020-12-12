package com.example.fuck2;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.fuck2.config.Config;
import com.example.fuck2.utils.ApiThread;
import com.example.fuck2.utils.Utils;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;

public class GoodsDetail extends AppCompatActivity {
    private Banner banner;
    private int subGoodsId = 3;
    private TextView titleTextView, descTextView;

    static private class MHandler extends Handler {
        private final WeakReference<GoodsDetail> weakReference;


        public MHandler(GoodsDetail goodsDetail) {
            weakReference = new WeakReference<GoodsDetail>(goodsDetail);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            int code = msg.what;
            if (code == 0) {
                String body = msg.obj.toString();
                String title = JSONObject.parseObject(body).getJSONObject("data").getString("title");
                String desc = JSONObject.parseObject(body).getJSONObject("data").getString("desc");
                String img = JSONObject.parseObject(body).getJSONObject("data").getString("banner");
                String detailImg = JSONObject.parseObject(body).getJSONObject("data").getString("detail_img");
                weakReference.get().titleTextView.setText(title);
                weakReference.get().descTextView.setText(desc);
                weakReference.get().LoadBannerImg(Utils.ParseJSONString(img));
                weakReference.get().LoadDetailImg(Utils.ParseJSONString(detailImg));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);
        banner = findViewById(R.id.banner);

        //LoadBannerImg();
        titleTextView = findViewById(R.id.title);
        descTextView = findViewById(R.id.desc);

        RelativeLayout textView = findViewById(R.id.select_spec);

        final View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectSpecPopWin selectSpecPopWin = new SelectSpecPopWin(GoodsDetail.this, onClickListener, subGoodsId);
                selectSpecPopWin.showAtLocation(findViewById(R.id.main_view), Gravity.CENTER, 0, 0);
            }
        });

        //
        HashMap<String, String> param = new HashMap<>();
        param.put("sub_goods_id", String.valueOf(subGoodsId));
        MHandler mHandler = new MHandler(this);
        new ApiThread(0, mHandler, "get", Config.getServerAddress() + "/v1/goods", Utils.MapToHttpParam(param)).start();
    }

    private void LoadDetailImg(List<String> imgPathList) {
        LinearLayout imgSet = findViewById(R.id.img_set);
        for (int i = 0; i < imgPathList.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            ViewGroup.LayoutParams imgParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            Glide.with(getApplicationContext()).load(imgPathList.get(i)).into(imageView);
            imageView.setLayoutParams(imgParam);
            imgSet.addView(imageView);
        }
    }

    private void LoadBannerImg(List<String> imgPathList) {
        banner.setImages(imgPathList);
        banner.setImageLoader(new ImageLoadBanner());
        banner.setDelayTime(5500);
        banner.isAutoPlay(true);

        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.Accordion);
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.start();
    }

    static class ImageLoadBanner extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path.toString()).into(imageView);
        }
    }
}
