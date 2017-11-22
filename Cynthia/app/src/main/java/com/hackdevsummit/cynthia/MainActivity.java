package com.hackdevsummit.cynthia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackdevsummit.cynthia.adapters.RecyclerHomeLeft;
import com.hackdevsummit.cynthia.adapters.RecyclerHomeRight;
import com.hackdevsummit.cynthia.databases.DbDataFashion;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

//    private Toolbar toolbar;

    private Realm realm;
    private RealmAsyncTask mRealmAsyncTaskQuery;
    private RealmQuery<DbDataFashion> mRealmQueryPackageData;
    private RealmResults<DbDataFashion> mRealmResultsPackageData;

    private RecyclerHomeLeft recyclerHomeLeft;
    private RecyclerHomeRight recyclerHomeRight;

    private List<DbDataFashion> dbDataFashionList;
    private DbDataFashion dbDataFashion;

    private TextView textViewNoDataHome;
    private TextView textViewNoDataBrand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.blueskyNom));

        initializeData();
        getDataFromDatabaseHome();
//        addDummyData();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void addDummyData() {
//        Realm.init(MainActivity.this);
        // Get a Realm instance for this thread
//        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 12");
            dbDataFashion.setLinkGambar("https://static.cdn-cdpl.com/270x135/c420ea5d6888e29a87437c2d005edd57/Foto_1_-_Andalin_Winner_Tech_in_Asia_Arena_Jakarta_2017.png");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 13");
            dbDataFashion.setLinkGambar("https://static.cdn-cdpl.com/270x135/998b78e349061b4971c0a2b0e8d6be41/popcorn-movie-party-entertainment.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 14");
            dbDataFashion.setLinkGambar("https://static.cdn-cdpl.com/270x135/2e2e60f587dfd485821293c09e51a70b/Alasan_Mengapa_Kamu_Harus_Menggunakan_Framework.png");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 15");
            dbDataFashion.setLinkGambar("https://static.cdn-cdpl.com/270x135/998b78e349061b4971c0a2b0e8d6be41/construct-2.png");
            realm.commitTransaction();

            showToast("data dummy saved");
        }catch (Exception e) {
            e.printStackTrace();
        }
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
//                    toolbar.setTitle("Home");
                    linearLayoutHome.setVisibility(View.VISIBLE);
                    return true;
                case R.id.navigation_dashboard:
//                    toolbar.setTitle("Brand");
                    linearLayoutHome.setVisibility(View.GONE);
                    return true;
            }
            return false;
        }
    };

    private void initializeData() {
        Realm.init(MainActivity.this);
        realm = Realm.getDefaultInstance();

        dbDataFashionList = new ArrayList<>();

        textViewNoDataHome = (TextView) findViewById(R.id.tv_nodata);
        textViewNoDataBrand = (TextView) findViewById(R.id.tv_nodata_brand);

        linearLayoutHome = (LinearLayout) findViewById(R.id.linearlayoutHome);
        linearLayoutBrand = (LinearLayout) findViewById(R.id.linearlayoutBrand);

        mRecyclerViewHomeLeft = (RecyclerView) findViewById(R.id.rv_numbers_home_left);
        mRecyclerViewHomeRight = (RecyclerView) findViewById(R.id.rv_numbers_home_right);
//        mRecyclerViewBrand = (RecyclerView) findViewById(R.id.rv_numbers_brand);
    }

    public void initializationOfViewer() {
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        final LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(MainActivity.this);
        mRecyclerViewHomeLeft.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewHomeLeft.setHasFixedSize(true);
        mRecyclerViewHomeRight.setLayoutManager(mLinearLayoutManager2);
        mRecyclerViewHomeRight.setHasFixedSize(true);

        recyclerHomeLeft = new RecyclerHomeLeft(MainActivity.this, dbDataFashionList);
        recyclerHomeRight = new RecyclerHomeRight(MainActivity.this, dbDataFashionList);

        mRecyclerViewHomeLeft.setAdapter(recyclerHomeLeft);
        mRecyclerViewHomeRight.setAdapter(recyclerHomeRight);
    }

    private void initializeListener() {

    }

    public void getDataFromDatabaseHome() {
        mRealmAsyncTaskQuery = realm.executeTransactionAsync(
                new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        mRealmQueryPackageData = realm.where(DbDataFashion.class);
                        mRealmResultsPackageData = mRealmQueryPackageData.findAll();

                        if (mRealmResultsPackageData.isEmpty()) {
                            dbDataFashionList = new ArrayList<>();
                            textViewNoDataHome.setVisibility(View.VISIBLE);
                        } else {
                            textViewNoDataHome.setVisibility(View.GONE);
                            dbDataFashionList = realm.copyFromRealm(mRealmResultsPackageData);
                        }
                    }
                },
                new Realm.Transaction.OnSuccess() {
                    @Override
                    public void onSuccess() {
                        Log.d(TAG,"get database success");
                        initializationOfViewer();
                    }
                },
                new Realm.Transaction.OnError() {
                    @Override
                    public void onError(Throwable error) {
                        error.printStackTrace();
                    }
                });
    }

    private void turnOffAsyncRealm() {
        if (mRealmAsyncTaskQuery != null && !mRealmAsyncTaskQuery.isCancelled()) {
            mRealmAsyncTaskQuery.cancel();
        }
    }

    /**
     * method ini untuk menampilkan toast agar pemanggilan lebih sederhana
     * @param s pesan
     */
    private void showToast(String s) {
        Toast.makeText(MainActivity.this,s,Toast.LENGTH_LONG).show();
    }
}
