package com.example.housinghubv6.YourAccount;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.housinghubv6.R;
import com.example.housinghubv6.Utils.UniversalImageLoader;


public class EditAccountFragment extends Fragment {
    private static final String TAG ="EditProfileFragment";
    private ImageView mprofilePhoto;
    private ImageView bBackArrow;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile,container,false);
        mprofilePhoto = (ImageView) view.findViewById(R.id.profile_picture);

        bBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        bBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Going back to home page");
                getActivity().finish();
            }
        });

        setProfileImage();
        return view;
    }

    private void setProfileImage(){
        Log.d(TAG, "setProfileImage: setting profile image.");
        String imgURL = "www.androidcentral.com/sites/androidcentral.com/files/styles/xlarge/public/article_images/2016/08/ac-lloyd.jpg?itok=bb72IeLf";
        UniversalImageLoader.setImage(imgURL, mprofilePhoto, null, "https://");
    }

}
