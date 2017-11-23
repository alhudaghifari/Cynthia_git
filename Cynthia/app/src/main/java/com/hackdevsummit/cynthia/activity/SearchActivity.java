package com.hackdevsummit.cynthia.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.hackdevsummit.cynthia.MainActivity;
import com.hackdevsummit.cynthia.R;
import com.hackdevsummit.cynthia.adapters.RecyclerResultSearch;
import com.hackdevsummit.cynthia.databases.DbDataFashion;
import com.hackdevsummit.cynthia.helper.AppController;
import com.hackdevsummit.cynthia.helper.VolleyMultipartRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmAsyncTask;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SearchActivity extends AppCompatActivity {

    private static String TAG = SearchActivity.class.getSimpleName();

    private ImageView imageViewSearchFor;
    private Uri imageUri;

    private Realm realm;
    private RealmAsyncTask mRealmAsyncTaskQuery;
    private RealmQuery<DbDataFashion> mRealmQueryPackageData;
    private RealmResults<DbDataFashion> mRealmResultsPackageData;

    private RecyclerResultSearch recyclerResultSearch;
    private RecyclerView recyclerView;

    private List<DbDataFashion> dbDataFashionList;
    private DbDataFashion dbDataFashion;

    private static final String UPLOAD_URL = "https://devsum.herokuapp.com/postrequest";

    private String namaFileFoto = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        imageViewSearchFor = (ImageView) findViewById(R.id.iv_gambar_search);

        Intent intent = getIntent();
        String dataUri = intent.getStringExtra("galeryku");
        imageUri = Uri.parse(dataUri);
        imageViewSearchFor.setImageURI(imageUri);

        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = getApplicationContext().getContentResolver().query(imageUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            Log.d(TAG,"cursor : " + cursor.getString(column_index));
            Log.d(TAG,"cursor : " + cursor.getString(column_index));


            File file = new File("" + cursor.getString(column_index));
            Log.d(TAG,"nama file : " + dataUri);
            Log.d(TAG,"nama file : " + file.getName());
            Log.d(TAG,"nama file : " + file.getName());
            Log.d(TAG,"nama file : " + file.getName());
            namaFileFoto = file.getName();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        initializeData();
        getDataFromDatabaseHome();

        try {
            //getting bitmap object from uri
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

            uploadBitmapStringRequest(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initializeData() {
        Realm.init(SearchActivity.this);
        realm = Realm.getDefaultInstance();

        dbDataFashionList = new ArrayList<>();
        recyclerView = (RecyclerView) findViewById(R.id.rv_numbers_search_result);

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
                        } else {
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

    public void initializationOfViewer() {
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerResultSearch = new RecyclerResultSearch(SearchActivity.this, dbDataFashionList);

        recyclerView.setAdapter(recyclerResultSearch);
    }

    /*
    * The method is taking Bitmap as an argument
    * then it will return the byte[] array for the given bitmap
    * and we will send this array to the server
    * here we are using PNG Compression with 80% quality
    * you can give quality between 0 to 100
    * 0 means worse quality
    * 100 means best quality
    * */
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    private void uploadBitmapStringRequest(final Bitmap bitmap) {
        try {
//            final String encodedImage = Base64.encodeToString(getFileDataFromDrawable(bitmap), Base64.DEFAULT);
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("img", namaFileFoto);
            final String requestBody = jsonBody.toString();

            Log.d(TAG,"requestBody : " + requestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.d(TAG,"response : " + response);
                    try {
//                        JSONObject jObj = new JSONObject(response);
                        JSONArray jsonArray = new JSONArray(response);
                        Log.d(TAG,"json : " + jsonArray.getString(0).length());
                        byte[] decodedString = Base64.decode(jsonArray.get(0) + "", Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                        Uri aa = getImageUri(getApplicationContext(), decodedByte);

                        imageViewSearchFor.setImageURI(aa);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG,"responseError : " + error.toString());
                }
            }) {

                @Override
                protected Map<String, String> getParams() {

                    // Posting parameters to login url
                    Map<String, String> params = new HashMap<String, String>();
//                    String mypass = md5(password);
//                    String mypass1 = md5(mypass);

                    params.put("img", namaFileFoto);

                    return params;
                }

            };
            AppController.getInstance().addToRequestQueue(stringRequest, "send_img");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void uploadBitmap(final Bitmap bitmap) {
        Log.d(TAG,"start upload bitmap");
        //getting the tag from the edittext
        final String tags = "testing";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d(TAG,"onResponse Volley");
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Log.d(TAG,"obj : " + obj.getString("test"));
                            Toast.makeText(getApplicationContext(), obj.getString("test"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG,"onErrorResponse Volley");
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
            * If you want to add more parameters with the image
            * you can do it here
            * here we have only one parameter with the image
            * which is tags
            * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Log.d(TAG,"getParams Volley");
                Map<String, String> params = new HashMap<>();
                params.put("tags", tags);
                return params;
            }

            /*
            * Here we are passing image by renaming it with a unique name
            * */
            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Log.d(TAG,"getByteData Volley");
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
