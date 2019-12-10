package com.gm.soft.sqlite;

import android.Manifest;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.os.ConditionVariable;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private HttpUtil httpUtil;
    private DBUtil dbUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();
        dbUtil = DBUtil.getInstance();
        findViewById(R.id.getData).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
            }
        });
    }

    private void initData() {
        httpUtil = HttpUtil.getInstance();
        dbUtil.createTable();
        httpUtil.asynch("/app/init", httpUtil.TYPE_POST, null, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        String data = response.body().string();
                        JSONObject jsonObject = new JSONObject(data);
                        String list = jsonObject.getString("data");
                        JSONObject j = new JSONObject(list);
                        JSONArray jsonArray = j.getJSONArray("trainTypeList");
                        dbUtil.db.beginTransaction();
                        String tableName = "traintype";
                        dbUtil.deleteAll(tableName);
                        for (int i = 0, num = jsonArray.length(); i < num; i++) {
                            TrainTypeModel trainTypeModel = new TrainTypeModel(jsonArray.getJSONObject(i));
                            ContentValues contentValues = new ContentValues();
                            contentValues.put("traintypeid", trainTypeModel.getTrainTypeId());
                            contentValues.put("traintypename", trainTypeModel.getTrainTypeName());
                            Log.d("myapp", trainTypeModel.getTrainTypeName());
                            Log.d("myapp", i + "");
                            dbUtil.db.insert(tableName, null, contentValues);
                        }
                        dbUtil.db.setTransactionSuccessful();
                        dbUtil.db.endTransaction();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void getData() {
        Cursor cursor = dbUtil.select("select * from traintype", null);
        if (cursor != null) {
            int i = 0;
            while (cursor.moveToNext()) {
                Log.d("myapp", cursor.getString(0) + cursor.getString(1));
                Log.d("myapp", (i++) + "");
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void requestAllPower() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                != PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 107);
        }
    }
}
