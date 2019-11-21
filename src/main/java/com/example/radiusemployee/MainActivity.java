package com.example.radiusemployee;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    private AdapterEmp mAdapter;

    private static String url = "https://raw.githubusercontent.com/iranjith4/radius-intern-mobile/master/users.json";

    ArrayList<DataEmp> contactList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        contactList = new ArrayList<>();


        new GetContacts().execute();
    }

    private class GetContacts extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {


            HttpHandler sh = new HttpHandler();
            String jsonStr = sh.makeServiceCall(url);
            Log.e(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    JSONArray contacts = jsonObj.getJSONArray("results");
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);


                        JSONObject na = c.getJSONObject("name");
                        String tt = na.getString("title");
                        String fname = na.getString("first");
                        String lname = na.getString("last");

                        JSONObject dobAge = c.getJSONObject("dob");
                        String a = dobAge.getString("age");

                        JSONObject im = c.getJSONObject("picture");
                        String path = im.getString("medium");

                        DataEmp dataEmp = new DataEmp();
                        String empName = tt + ":- " + fname + " " + lname;
                        dataEmp.setEmpName(empName);
                        dataEmp.setEmpAge("Age:- "+a);
                        dataEmp.setEmpImage(path);
                        contactList.add(dataEmp);


                    }
                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error  " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error  " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "errors!",Toast.LENGTH_LONG)
                                .show();
                    }
                });

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog.isShowing())
                pDialog.dismiss();
            recyclerView = (RecyclerView) findViewById(R.id.profile_detial);
            mAdapter = new AdapterEmp(MainActivity.this, contactList);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        }

    }
}
