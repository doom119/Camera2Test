package com.doom119.camera2test;

import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EActivity(R.layout.activity_parameters)
public class ParametersActivity extends AppCompatActivity {

    @ViewById(R.id.recycler_view)
    RecyclerView mRecyclerView;

    List<CameraCharacteristics.Key<?>> mKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
            String[] ids = cameraManager.getCameraIdList();
            CameraCharacteristics cameraCharacteristics = cameraManager.getCameraCharacteristics(ids[0]);
            mKeys = cameraCharacteristics.getKeys();
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    @AfterViews
    public void initViews() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new CharactericsAdapter());
    }

    public class CharactericsAdapter extends RecyclerView.Adapter<CharactericsAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(ParametersActivity.this)
                    .inflate(android.R.layout.simple_list_item_1, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mKeys.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return mKeys.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView)itemView.findViewById(android.R.id.text1);
            }
        }
    }
}
