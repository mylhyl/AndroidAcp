package com.mylhyl.acp.sample;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;

import java.util.List;

public class BlankActivity extends AppCompatActivity {

    public static void gotoAct(Activity act) {
        act.startActivity(new Intent(act, BlankActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .add(android.R.id.content, BlankFragment.newInstance()).commitAllowingStateLoss();
    }

    public static class BlankFragment extends Fragment implements View.OnClickListener {

        public BlankFragment() {
        }


        public static BlankFragment newInstance() {
            BlankFragment fragment = new BlankFragment();
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            TextView textView = new TextView(getActivity());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                    .LayoutParams.MATCH_PARENT));
            textView.setGravity(Gravity.CENTER);
            textView.setText(R.string.hello_blank_fragment);
            textView.append("\n\nFragment中申请权限");
            textView.setOnClickListener(this);
            return textView;
        }

        @Override
        public void onClick(View v) {
            Acp.getInstance(this.getContext()).request(new AcpOptions.Builder()
                            .setPermissions(Manifest.permission.READ_PHONE_STATE).build(),
                    new AcpListener() {
                        @Override
                        public void onGranted() {
                            getIMEI();
                        }

                        @Override
                        public void onDenied(List<String> permissions) {
                            Toast.makeText(BlankFragment.this.getActivity(), permissions.toString() + "权限拒绝", Toast
                                    .LENGTH_SHORT).show();
                        }

                    });
        }

        @SuppressLint("MissingPermission")
        private void getIMEI() {
            FragmentActivity activity = this.getActivity();
            TelephonyManager tm = (TelephonyManager) this.getActivity().getSystemService(Activity.TELEPHONY_SERVICE);
            if (tm != null)
                Toast.makeText(activity, tm.getDeviceId(), Toast.LENGTH_SHORT).show();
        }
    }
}
