package com.example.housinghubv6.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;

import com.example.housinghubv6.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

public class GridImageAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private LayoutInflater mInflater;
    private int layoutResource;
    private String mAppend;
    private ArrayList<String> imgURLs;

    public GridImageAdapter(Context context, int layoutResource, String append, ArrayList<String> imgURLs) {
        super(context, layoutResource, imgURLs);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
        this.layoutResource = layoutResource;
        mAppend = append;
        this.imgURLs = imgURLs;
    }

    private static class ViewHolder{
        Square_ImageView image;
        ProgressBar mProgressBar;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        /**
         * Viewholder buildpattern
         */
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(layoutResource,parent,false);
            viewHolder= new ViewHolder();
            viewHolder.mProgressBar = (ProgressBar) convertView.findViewById(R.id.gridimage_progressbar);
            viewHolder.image = (Square_ImageView) convertView.findViewById(R.id.gridImageView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        String imgURL = getItem(position);
        ImageLoader imageLoader = ImageLoader.getInstance();

        imageLoader.displayImage(mAppend + imgURLs, viewHolder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                if( viewHolder.mProgressBar!= null){
                    viewHolder.mProgressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                if(viewHolder.mProgressBar != null){
                    viewHolder.mProgressBar.setVisibility(View.GONE);
                }
            }
        });
        return convertView;
    }
}