package com.clemcreativity.uzapoint.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.clemcreativity.uzapoint.R;
import com.clemcreativity.uzapoint.adapter.DividerItemDecoration;
import com.clemcreativity.uzapoint.adapter.ProductAdapter;
import com.clemcreativity.uzapoint.app.AppConfig;
import com.clemcreativity.uzapoint.app.AppController;
import com.clemcreativity.uzapoint.helper.ColoredSnackBar;
import com.clemcreativity.uzapoint.helper.SessionManager;
import com.clemcreativity.uzapoint.model.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InventoryActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private List<Product> productList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProductAdapter mAdapter;
    private static String TAG = InventoryActivity.class.getSimpleName();
    private String jsonResponse;
    private SessionManager session;
    // Progress dialog
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        //getActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        session = new SessionManager(getApplicationContext());

        mAdapter = new ProductAdapter(productList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        // set the adapter
        recyclerView.setAdapter(mAdapter);

        //Toast.makeText(this,session.getKeyApiKey(),Toast.LENGTH_SHORT).show();
        prepareProductData();
        //showpDialog();
        //makeJsonArrayRequest();
    }

    private void prepareProductData() {
        Product product = new Product("Mad Max: Fury Road", "Action & Adventure", "2015");
        productList.add(product);

        product = new Product("Inside Out", "Animation, Kids & Family", "2015");
        productList.add(product);

        product = new Product("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        productList.add(product);

        product = new Product("Shaun the Sheep", "Animation", "2015");
        productList.add(product);

        product = new Product("The Martian", "Science Fiction & Fantasy", "2015");
        productList.add(product);

        product = new Product("Mission: Impossible Rogue Nation", "Action", "2015");
        productList.add(product);

        product = new Product("Up", "Animation", "2009");
        productList.add(product);

        product = new Product("Star Trek", "Science Fiction", "2009");
        productList.add(product);

        product = new Product("The LEGO Movie", "Animation", "2014");
        productList.add(product);

        product = new Product("Iron Man", "Action & Adventure", "2008");
        productList.add(product);

        product = new Product("Aliens", "Science Fiction", "1986");
        productList.add(product);

        product = new Product("Chicken Run", "Animation", "2000");
        productList.add(product);

        product = new Product("Back to the Future", "Science Fiction", "1985");
        productList.add(product);

        product = new Product("Raiders of the Lost Ark", "Action & Adventure", "1981");
        productList.add(product);

        product = new Product("Goldfinger", "Action & Adventure", "1965");
        productList.add(product);

        product = new Product("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        productList.add(product);

        mAdapter.notifyDataSetChanged();
    }

    private void makeJsonArrayRequest() {

        JsonObjectRequest jsonReq = new JsonObjectRequest(Request.Method.GET, AppConfig.PRODUCTS_URL, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                VolleyLog.d(TAG, "Response: " + response.toString());
                System.out.print("NDIO: " + response.toString());
                if (response != null) {
                    try {
                        parseJsonFeed(response);
                        hidepDialog();
                    }catch (Exception e){
                        displayMessage(e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                String json = null;
                hidepDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                NetworkResponse response = error.networkResponse;
                if(response != null && response.data != null){
                    switch (response.statusCode){
                        case 400:
                            json = new String(response.data);
                            json = trimMessage(json,"message");
                            if(json != null) displayMessage(json);
                            break;
                        case 401:
                            json = new String(response.data);
                            json = trimMessage(json,"message");
                            if(json != null) displayMessage(json);
                            break;
                        case 404:
                            json = new String(response.data);
                            json = trimMessage(json,"message");
                            if(json != null) displayMessage(json);
                            break;
                        case 500:
                            displayMessage("Internal Server Error");
                            break;
                        default:
                            displayMessage("Debug Sever-Side Code");
                    }
                }
            }



        }){
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                //headers.put("Content-Type", "application/json");
                headers.put("Authorization", session.getKeyApiKey());
                return headers;
            }
        };

        // Adding request to volley request queue
        jsonReq.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        AppController.getInstance().addToRequestQueue(jsonReq);
    }

    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    private void parseJsonFeed(JSONObject response) {
        try {
            JSONArray feedArray = response.getJSONArray("data");

            System.out.print("HAPA: " + response.getInt("status") + " NDIO STATUS IKO");

            for (int i = 0; i < feedArray.length(); i++) {
                JSONObject dataObj = (JSONObject) feedArray.get(i);
                Product product = new Product(dataObj.getString("name"), dataObj.getString("code"), dataObj.getString("quantity"));
                productList.add(product);
            }

            // notify data changes to list adapater
            mAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public String trimMessage(String json, String key){
        String trimmedString = null;

        try{
            JSONObject obj = new JSONObject(json);
            trimmedString = obj.getString(key);
        } catch(JSONException e){
            e.printStackTrace();
            return null;
        }

        return trimmedString;
    }

    //Somewhere that has access to a context
    public void displayMessage(String toastString){
        // Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();

        //Snackbar snackbar = Snackbar.make(this.getCurrentFocus(),toastString, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        Snackbar snackbar = Snackbar
                .make(this.getCurrentFocus(), toastString, Snackbar.LENGTH_LONG)
                .setAction("RETRY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        makeJsonArrayRequest();
                    }
                });

        ColoredSnackBar.error(snackbar);

// Changing message text color
        snackbar.setActionTextColor(Color.BLACK);

// Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        snackbar.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);

        final MenuItem item = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setOnQueryTextListener(this);

        MenuItemCompat.setOnActionExpandListener(item,
                new MenuItemCompat.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        // Do something when collapsed
                        mAdapter.setFilter(productList);
                        return true; // Return true to collapse action view
                    }

                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        // Do something when expanded
                        return true; // Return true to expand action view
                    }
                });


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

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        final List<Product> filteredModelList = filter(productList, newText);
        mAdapter.setFilter(filteredModelList);
        return true;
    }

    private List<Product> filter(List<Product> models, String query) {
        query = query.toLowerCase();

        final List<Product> filteredModelList = new ArrayList<>();
        for (Product model : models) {
            final String text = model.getName().toLowerCase();
            final String code = model.getCode().toLowerCase();
            if (text.contains(query)) {
                filteredModelList.add(model);
            }
            if (code.contains(query)) {
                filteredModelList.add(model);
            }
        }
        return filteredModelList;
    }
}
