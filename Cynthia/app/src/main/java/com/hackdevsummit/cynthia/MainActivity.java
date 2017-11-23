package com.hackdevsummit.cynthia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hackdevsummit.cynthia.activity.SearchActivity;
import com.hackdevsummit.cynthia.adapters.RecyclerBrand;
import com.hackdevsummit.cynthia.adapters.RecyclerHomeLeft;
import com.hackdevsummit.cynthia.adapters.RecyclerHomeRight;
import com.hackdevsummit.cynthia.databases.DbDataFashion;
import com.hackdevsummit.cynthia.helper.SessionManager;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static String TAG = MainActivity.class.getSimpleName();

    private static final int PICK_IMAGE = 100;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

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
    private RecyclerBrand recyclerBrand;

    private List<DbDataFashion> dbDataFashionList;
    private DbDataFashion dbDataFashion;

    private TextView textViewNoDataHome;
    private TextView textViewNoDataBrand;

    private ImageView imageViewSearchPhoto;
    private ImageView imageViewCamera;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.blueskyNom));

        initializeData();
        initializeListener();
        getDataFromDatabaseHome();
//        addDummyData();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menumain, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            sessionManager.setLogin(false);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDummyData() {
//        Realm.init(MainActivity.this);
        // Get a Realm instance for this thread
//        Realm realm = Realm.getDefaultInstance();

        try {
            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 12");
            dbDataFashion.setLinkGambar("https://cdn.brilio.net/news/2017/03/23/123353/605785-fashion-item-cowok-bikin-cewek-ilfil-.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 13");
            dbDataFashion.setLinkGambar("https://s1.bukalapak.com/img/6652566611/m-1000-1000/JAKET_BOMBER_ADIDAS_PRIA_TERBARU_JAKET_FASHION_COWOK_MODEL_K.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian pria 14");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/7542304491/m-1000-1000/Sweater___Hoodie___Jaket_Murah_RipNDip.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 15");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/2916902661/m-1000-1000/2017_09_24T00_19_36_07_00.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 23");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/7591248981/m-1000-1000/Mutif_161___Hijab_Fashion___Baju_Muslim.jpg");
            realm.commitTransaction();

            // oke

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 23");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/7591248981/m-1000-1000/Mutif_161___Hijab_Fashion___Baju_Muslim.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 51");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/7766678371/m-1000-1000/161_Strawbery_Pink___Rio_Red_scaled.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 23");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/2212116391/m-1000-1000/0_0501f5bc_3370_46cc_a000_889be40cb16e_540_675.jpg");
            realm.commitTransaction();


            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 511");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/7290116391/m-1000-1000/226196536_f12004fd_6f4f_4eef_a60a_65a8c0bdb4d9_540_540.jpg");
            realm.commitTransaction();

            realm.beginTransaction();
            dbDataFashion = realm.createObject(DbDataFashion.class, UUID.randomUUID().toString());
            dbDataFashion.setJudul("pakaian wanita 123");
            dbDataFashion.setLinkGambar("https://s2.bukalapak.com/img/2242113381/m-1000-1000/PERSEGI_4_BHP_09_BAHAN__MAXMARA_WARNA_BIRU_mt__BUNGA_UK_120X.jpg");
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
                    linearLayoutBrand.setVisibility(View.GONE);
                    return true;
                case R.id.navigation_dashboard:
//                    toolbar.setTitle("Brand");
                    linearLayoutHome.setVisibility(View.GONE);
                    linearLayoutBrand.setVisibility(View.VISIBLE);
                    return true;
            }
            return false;
        }
    };

    private void initializeData() {
        Realm.init(MainActivity.this);
        realm = Realm.getDefaultInstance();

        sessionManager = new SessionManager(getApplicationContext());

        dbDataFashionList = new ArrayList<>();

        textViewNoDataHome = (TextView) findViewById(R.id.tv_nodata);
        textViewNoDataBrand = (TextView) findViewById(R.id.tv_nodata_brand);

        linearLayoutHome = (LinearLayout) findViewById(R.id.linearlayoutHome);
        linearLayoutBrand = (LinearLayout) findViewById(R.id.linearlayoutBrand);

        mRecyclerViewHomeLeft = (RecyclerView) findViewById(R.id.rv_numbers_home_left);
        mRecyclerViewHomeRight = (RecyclerView) findViewById(R.id.rv_numbers_home_right);
        mRecyclerViewBrand = (RecyclerView) findViewById(R.id.rv_numbers_brand);

        imageViewCamera = (ImageView) findViewById(R.id.iv_camera);
        imageViewSearchPhoto = (ImageView) findViewById(R.id.iv_search);
    }

    public void initializationOfViewer() {
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(MainActivity.this);
        final LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(MainActivity.this);
        final LinearLayoutManager mLinearLayoutManager3 = new LinearLayoutManager(MainActivity.this);
        mRecyclerViewHomeLeft.setLayoutManager(mLinearLayoutManager);
        mRecyclerViewHomeLeft.setHasFixedSize(true);
        mRecyclerViewHomeRight.setLayoutManager(mLinearLayoutManager2);
        mRecyclerViewHomeRight.setHasFixedSize(true);
        mRecyclerViewBrand.setLayoutManager(mLinearLayoutManager3);
        mRecyclerViewBrand.setHasFixedSize(true);

        recyclerHomeLeft = new RecyclerHomeLeft(MainActivity.this, dbDataFashionList);
        recyclerHomeRight = new RecyclerHomeRight(MainActivity.this, dbDataFashionList);
        recyclerBrand = new RecyclerBrand(MainActivity.this, dbDataFashionList);

        mRecyclerViewHomeLeft.setAdapter(recyclerHomeLeft);
        mRecyclerViewHomeRight.setAdapter(recyclerHomeRight);
        mRecyclerViewBrand.setAdapter(recyclerBrand);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK ) {
            String imageUri = data.getData().toString();
//            iv_wajah.setImageURI(imageUri);
            Intent intent = new Intent(MainActivity.this, SearchActivity.class);
            intent.putExtra("galeryku",imageUri);
            startActivity(intent);

        } else if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            bmWajah = imageBitmap;
//            iv_wajah.setImageBitmap(imageBitmap);
        }

    }

    private void initializeListener() {
        imageViewSearchPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, PICK_IMAGE);
            }
        });
        imageViewCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
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
