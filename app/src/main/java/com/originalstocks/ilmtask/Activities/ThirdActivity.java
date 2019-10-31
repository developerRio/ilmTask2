package com.originalstocks.ilmtask.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.originalstocks.ilmtask.Adapters.DataRecyclerAdapter;
import com.originalstocks.ilmtask.Adapters.ImageRecyclerAdapter;
import com.originalstocks.ilmtask.R;
import com.originalstocks.ilmtask.Utils.MyUtils;
import com.originalstocks.ilmtask.Utils.MyVolleySingleton;
import com.originalstocks.ilmtask.model.DataModel;
import com.originalstocks.ilmtask.model.ImageModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {
    public static final String API_URL = "https://jsonplaceholder.typicode.com/photos";
    private static final String TAG = "ThirdActivity";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private ImageRecyclerAdapter recyclerAdapter;
    private RecyclerView usersRecyclerView;
    private List<ImageModel> usersList;
    private ProgressBar mProgressBar;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);


        usersRecyclerView = findViewById(R.id.data_recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        recyclerAdapter = new ImageRecyclerAdapter(this, usersList);
        linearLayoutManager = new LinearLayoutManager(ThirdActivity.this, RecyclerView.VERTICAL, false);

        if (MyUtils.isNetworkAvailable()) {
            gettingUserData();
        } else {
            Toast.makeText(ThirdActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
        }

        recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount = linearLayoutManager.getChildCount();
                totalItemCount = linearLayoutManager.getItemCount();
                pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                if (loading) {
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                        loading = false;
                        Log.v("...", "Last !");
                        if (MyUtils.isNetworkAvailable()) {
                            gettingUserData();
                        } else {
                            Toast.makeText(ThirdActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };

    }// onCreate closes


    private void gettingUserData() {
        usersList = new ArrayList<>();
        StringRequest userDataRequest = new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i(TAG, "onResponse_fetched_data = " + response);

                try {
                    Log.i(TAG, "onResponse_fetched_data = " + response);
                    if (response != null) {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        JSONArray rootArray = new JSONArray(response);
                        for (int i = 0; i < rootArray.length(); i++) {
                            ImageModel usersModel = new ImageModel(i);
                            JSONObject rootObject = rootArray.getJSONObject(i);

                            usersModel.setImgLink(rootObject.getString("url"));

                            usersList.add(usersModel);
                        }
                        // setting up recycler data
                        usersRecyclerView.setLayoutManager(linearLayoutManager);
                        usersRecyclerView.setHasFixedSize(true);

                        recyclerAdapter = new ImageRecyclerAdapter(ThirdActivity.this, usersList);
                        usersRecyclerView.setAdapter(recyclerAdapter);// setAdapter for first time only


                    } else {
                        mProgressBar.setVisibility(View.INVISIBLE);
                        Log.e(TAG, "onResponse: response is null");
                    }

                } catch (JSONException e) {
                    Log.e(TAG, "onResponse_exception = " + e.getLocalizedMessage());
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mProgressBar.setVisibility(View.INVISIBLE);
                Log.e(TAG, "onErrorResponse: " + error);
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Accept", "application/json");
                return params;
            }
        };

        userDataRequest.setRetryPolicy(
                new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        // Access the RequestQueue through your singleton class.
        MyVolleySingleton.getInstance(this).addToRequestQueue(userDataRequest);
    }


}
