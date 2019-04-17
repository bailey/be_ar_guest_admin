package com.example.android.bearguestmobile;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class DashboardFragment extends Fragment {

    private View dashboardFragmentView;
    private FirebaseAuth mAuth;
    private Toolbar topToolbar;
    private Context context;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        dashboardFragmentView = inflater.inflate(R.layout.dashboard_fragment, container, false);
        context = dashboardFragmentView.getContext();

        mAuth = FirebaseAuth.getInstance();

        // Set screen title
        ViewModelProviders.of((MainActivity)getActivity()).get(ToolbarViewModel.class).setToolbarTitle("Be AR Guest - Admin");

        // Set user's name in Welcome text
        String uid = mAuth.getCurrentUser().getUid();
        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getUserByUid(uid).observe(this, new Observer<Profile>() {
            @Override
            public void onChanged(@Nullable Profile profile) {
                ((TextView) dashboardFragmentView.findViewById(R.id.dashboard_welcome_user))
                        .setText("Welcome, " + profile.getfName() + "!");
                // if they are not an admin, log them out
                if(profile!=null && !profile.getRole().equals("admin")) {
                    ((MainActivity) getActivity()).finish();
                    startActivity(new Intent(context, FirebaseUIActivity.class));
                    mAuth.signOut();

                    Toast.makeText(context, "Sorry, this is for admin users only", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return dashboardFragmentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
}
