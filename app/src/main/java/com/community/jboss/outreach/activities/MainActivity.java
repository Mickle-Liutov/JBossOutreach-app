package com.community.jboss.outreach.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.community.jboss.outreach.ApiRepositHandler;
import com.community.jboss.outreach.R;
import com.community.jboss.outreach.RepositListRecyclerAdapter;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_list);
        swipeRefreshLayout = findViewById(R.id.swipe_refresh);
        progressBar = findViewById(R.id.progressBar);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new RepositListRecyclerAdapter(null,this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.invalidate();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                requestData();
            }
        });
        requestData();
    }

    private void requestData(){
        new ApiRepositHandler(this).execute();
    }
    public void receiveData(String[][] data){
        swipeRefreshLayout.setRefreshing(false);
        progressBar.setVisibility(View.GONE);
        if(data!=null) {
            mAdapter = new RepositListRecyclerAdapter(data,this);
            mRecyclerView.setAdapter(mAdapter);
            mRecyclerView.invalidate();
        }
        else{
            Toast.makeText(this,"No connection to the server",Toast.LENGTH_SHORT).show();
        }
    }
}
