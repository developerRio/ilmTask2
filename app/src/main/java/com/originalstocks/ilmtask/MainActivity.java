package com.originalstocks.ilmtask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.originalstocks.ilmtask.Activities.SecondPageActivity;
import com.originalstocks.ilmtask.Adapters.UserRecyclerAdapter;
import com.originalstocks.ilmtask.Utils.MyUtils;
import com.originalstocks.ilmtask.Utils.MyVolleySingleton;
import com.originalstocks.ilmtask.model.UsersModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    public static final String API_URL = "https://api.github.com/users?Since=135";
    private static final String TAG = "MainActivity";
    int pastVisiblesItems, visibleItemCount, totalItemCount;
    private boolean loading = true;
    private UserRecyclerAdapter recyclerAdapter;
    private RecyclerView usersRecyclerView;
    private List<UsersModel> usersList;
    private ProgressBar mProgressBar;
    private LinearLayoutManager linearLayoutManager;
    private RecyclerView.OnScrollListener recyclerViewOnScrollListener;
    private boolean isLoading = false;
    private MaterialButton nextPageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nextPageButton = findViewById(R.id.next_Page_Button);

        usersRecyclerView = findViewById(R.id.user_details_recycler_view);
        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);
        recyclerAdapter = new UserRecyclerAdapter(MainActivity.this, usersList);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false);


        nextPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SecondPageActivity.class));
            }
        });

        if (MyUtils.isNetworkAvailable()) {
            gettingUserData();
        } else {
            Toast.makeText(MainActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
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
                        Log.v("...", "Last Item Wow !");
                        if (MyUtils.isNetworkAvailable()) {
                            gettingUserData();
                        } else {
                            Toast.makeText(MainActivity.this, "Please check your Internet Connection", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        };
    }


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
                            UsersModel usersModel = new UsersModel(i);
                            JSONObject rootObject = rootArray.getJSONObject(i);

                            usersModel.setUserImageLink(rootObject.getString("avatar_url"));
                            usersModel.setLogin(rootObject.getString("login"));
                            usersModel.setUserType(rootObject.getString("type"));
                            usersModel.setUserURL(rootObject.getString("url"));

                            usersList.add(usersModel);
                        }
                        // setting up recycler data
                        usersRecyclerView.setLayoutManager(linearLayoutManager);
                        usersRecyclerView.setHasFixedSize(true);


                        recyclerAdapter = new UserRecyclerAdapter(MainActivity.this, usersList);
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
