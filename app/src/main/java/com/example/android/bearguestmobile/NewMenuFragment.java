package com.example.android.bearguestmobile;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class NewMenuFragment extends Fragment {

    private View newMenuFragmentView;
    public String MAIN_TAB_FRAGMENT = "main_tab_fragment";
    private FragmentTransaction transaction;
    private Toolbar topToolbar;

    public static NewMenuFragment newInstance() {
        return new NewMenuFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        newMenuFragmentView = inflater.inflate(R.layout.new_menu_fragment, container, false);

        //TextView parkTitle = newMenuFragmentView.findViewById(R.id.newmenutitle);


        //get the spinner from the xml.
        Spinner parkdropdown = newMenuFragmentView.findViewById(R.id.parkspinner);
        //create a list of items for the spinner.
        CharSequence[] park_items = new CharSequence[]{"Magic Kingdom", "Epcot", "Hollywood Studios"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, park_items);
        //set the spinners adapter to the previously created one.
        parkdropdown.setAdapter(adapter);

        //get the spinner from the xml.
        Spinner landdropdown = newMenuFragmentView.findViewById(R.id.landspinner);
        //create a list of items for the spinner.
        CharSequence[] land_items = new CharSequence[]{"Mexico", "Japan", "Germany"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter adapter2 = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, land_items);
        //set the spinners adapter to the previously created one.
        landdropdown.setAdapter(adapter2);

        Button uploadImg = newMenuFragmentView.findViewById(R.id.button);

        uploadImg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);

                //Intent browserIntent =
                        //new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.be-ar-guest.herokuapp.com"));
                //startActivity(browserIntent);

            }

        });

        return newMenuFragmentView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    Log.v("activity", "Uri = " + selectedImage);
                    Bitmap bitmap = null;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    int index = AnnotationFragment.augmentedImageDatabase.addImage("restaurant id/name", bitmap);
                    // imageview.setImageURI(selectedImage);
                    AnnotationFragment arAnnotationFragment = new AnnotationFragment();
                    // getActivity().getSupportFragmentManager().beginTransaction()
                            // .replace(newMenuFragmentView.getId(), arAnnotationFragment, "findThisFragment")
                            // .addToBackStack(null)
                            // .commit();
                }
                break;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


}
