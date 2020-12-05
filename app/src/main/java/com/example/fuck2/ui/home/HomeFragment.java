package com.example.fuck2.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.fuck2.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private View root;
    private Banner banner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);

        banner = root.findViewById(R.id.banner);

        this.LoadBannerImg();
        return root;
    }

    void LoadBannerImg() {
        ArrayList<Integer> imgs = new ArrayList<>();
        imgs.add(R.drawable.ic_home_black_24dp);
        imgs.add(R.drawable.ic_home_black_24dp);
        imgs.add(R.drawable.ic_home_black_24dp);

        ArrayList<String> title = new ArrayList<>();
        title.add("奥利奥");
        title.add("趣多多");
        title.add("好多鱼");

        banner.setImages(imgs);
        banner.setImageLoader(new ImageLoadBanner());
        banner.setBannerTitles(title);
        banner.setDelayTime(5500);
        banner.isAutoPlay(true);
        banner.setIndicatorGravity(BannerConfig.CENTER);
        banner.setBannerAnimation(Transformer.Accordion);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.start();
    }

    class ImageLoadBanner extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            imageView.setImageResource(Integer.parseInt(path.toString()));
        }
    }
}