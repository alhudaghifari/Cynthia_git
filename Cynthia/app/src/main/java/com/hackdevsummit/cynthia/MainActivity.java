package com.hackdevsummit.cynthia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hackdevsummit.cynthia.adapters.RecyclerHomeLeft;
import com.hackdevsummit.cynthia.databases.DbDataFashion;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private LinearLayout linearLayoutHome;
    private LinearLayout linearLayoutBrand;

    private RecyclerView mRecyclerViewHomeLeft;
    private RecyclerView mRecyclerViewHomeRight;
    private RecyclerView mRecyclerViewBrand;

    private Toolbar toolbar;

    private Realm realm;
    private RealmAsyncTask mRealmAsyncTaskQuery;
    private RealmQuery<DbDataFashion> mRealmQueryPackageData;
    private RealmResults<DbDataFashion> mRealmResultsPackageData;

    private RecyclerHomeLeft recyclerHomeLeft;

    private List<DbDataFashion> dbDataFashionList;
    private DbDataFashion dbDataFashion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.blueskyNom));

        initializeData();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, " onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, " onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, " onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, " onStop");

        turnOffAsyncRealm();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, " onDestroy");
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    toolbar.setTitle("Home");
                    return true;
                case R.id.navigation_dashboard:
                    toolbar.setTitle("Brand");
                    return true;
            }
            return false;
        }
    };

    private void initializeData() {
        linearLayoutHome = (LinearLayout) findViewById(R.id.linearlayoutHome);
        linearLayoutBrand = (LinearLayout) findViewById(R.id.linearlayoutBrand);

        mRecyclerViewHomeLeft = (RecyclerView) findViewById(R.id.rv_numbers_home_left);
        mRecyclerViewHomeRight = (RecyclerView) findViewById(R.id.rv_numbers_home_right);
        mRecyclerViewBrand = (RecyclerView) findViewById(R.id.rv_numbers_brand);
    }

    private void turnOffAsyncRealm() {
        if (mRealmAsyncTaskQuery != null && !mRealmAsyncTaskQuery.isCancelled()) {
            mRealmAsyncTaskQuery.cancel();
        }
    }
}
