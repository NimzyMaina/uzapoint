package com.clemcreativity.uzapoint.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.clemcreativity.uzapoint.R;
import com.clemcreativity.uzapoint.helper.ColoredSnackBar;
import com.clemcreativity.uzapoint.helper.SessionManager;
import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.iconics.context.IconicsLayoutInflater;

public class MainActivity extends AppCompatActivity {

    private SessionManager session;
    private ImageButton ile;
    private TextView sales,inventory,sale,customer,others,performance;
    private IconicsDrawable cart,buliding,list,users,plus,chart;
    private LinearLayout ex,topLeft,topRight,middleLeft,middleRight,bottomLeft,bottomRight;
    Class<?> c = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(getLayoutInflater(), new IconicsLayoutInflater(getDelegate()));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sale = (TextView) findViewById(R.id.sale);
        inventory = (TextView) findViewById(R.id.inventory);
        sales = (TextView) findViewById(R.id.sales);
        customer = (TextView) findViewById(R.id.customers);
        others = (TextView) findViewById(R.id.others);
        performance = (TextView) findViewById(R.id.performance);
        topLeft = (LinearLayout) findViewById(R.id.topLeft);
        topRight = (LinearLayout) findViewById(R.id.topRight);
        middleLeft = (LinearLayout) findViewById(R.id.middleLeft);
        middleRight = (LinearLayout)findViewById(R.id.middleRight);
        bottomLeft = (LinearLayout) findViewById(R.id.bottomLeft);
        bottomRight = (LinearLayout) findViewById(R.id.bottomRight);


        //add sale icon
        cart = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_shopping_cart).color(Color.WHITE).sizeDp(44);
        sale.setCompoundDrawablesWithIntrinsicBounds(null,cart,null,null);
        //inventory icon
        buliding = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_android).color(Color.WHITE).sizeDp(44);
        inventory.setCompoundDrawablesWithIntrinsicBounds(null,buliding,null,null);
        //sales icon
        list = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_list).color(Color.WHITE).sizeDp(44);
        sales.setCompoundDrawablesWithIntrinsicBounds(null,list,null,null);
        // customers
        users = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_users).color(Color.WHITE).sizeDp(44);
        customer.setCompoundDrawablesWithIntrinsicBounds(null,users,null,null);
        //others icon
        plus = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_plus).color(Color.WHITE).sizeDp(44);
        others.setCompoundDrawablesWithIntrinsicBounds(null,plus,null,null);
        //performance
        chart = new IconicsDrawable(this).icon(FontAwesome.Icon.faw_line_chart).color(Color.WHITE).sizeDp(44);
        performance.setCompoundDrawablesWithIntrinsicBounds(null,chart,null,null);


        // session manager
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

        topRight.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,InventoryActivity.class);
                startActivity(intent);
            }
        });

        topLeft.setOnClickListener(new View.OnClickListener()  {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddSaleActivity.class);
                startActivity(intent);
            }
        });


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

        if(id == R.id.logout){
            logoutUser();
        }

        return super.onOptionsItemSelected(item);
    }

    private void logoutUser() {
        session.setLogin(false,null,null,null);
        String s = MainActivity.this.getPackageName()+".LoginActivity";
        try{
            if(s != null){
                c = Class.forName(s);
            }
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        // Launching the login activity
        //String path = PathForClass.class.getResource()
        Intent intent = new Intent(MainActivity.this,c);
        startActivity(intent);
        finish();
    }

//    @Override
//    public void onClick(View v) {
//
//        if (v.getId() == R.id.topLeft){
//            Intent intent = new Intent(this,AddSaleActivity.class);
//            startActivity(intent);
//        }
//        if(v.getId() == R.id.topRight){
//            Intent intent =  new Intent(this,InventoryActivity.class);
//            startActivity(intent);
//        }
//
//    }
}
