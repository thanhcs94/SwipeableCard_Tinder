package com.thanhcs.ask2know;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.thanhcs.ask2know.adapter.CardsAdapter;
import com.thanhcs.ask2know.utils.Utils;
import com.wenchao.cardstack.CardStack;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class MainActivity extends AppCompatActivity {
    private CardStack mCardStack;
    private CardsAdapter mCardAdapter;
    public static String [] arrayList;
    public static FrameLayout frRoot;
    public  SweetAlertDialog dialog;
    android.support.design.widget.FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        arrayList = getResources().getStringArray(R.array.colors);
        mCardStack = (CardStack)findViewById(R.id.container);
        frRoot = (FrameLayout) findViewById(R.id.frRoot);
        mCardStack.setContentResource(R.layout.card_content);
        mCardAdapter = new CardsAdapter(this);
        for(int i = 0 ; i < 100 ;i++){
            mCardAdapter.add("Example ("+i+")");
        }
        mCardStack.setAdapter(mCardAdapter);
        if(mCardStack.getAdapter() != null) {
            Log.i("MainActivity", "Card Stack size: " + mCardStack.getAdapter().getCount());
        }
        showDialogHelp();

    }

    private void showDialogHelp() {
       dialog =  new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("View Sample app on Google Play")
                .setContentText("GO GO GO GO GO GO GO GO GO GO GO GO GO GO ")
                .setCancelText("No")
                .setConfirmText("Yes")
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        openGGPlay();
                        dialog.dismissWithAnimation();
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        openGGPlay();
                        dialog.dismissWithAnimation();
                    }
                });
        dialog.show();
    }

    private void openGGPlay() {
        String url = Utils.APP_URL;
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.about) {
            share();
            return true;
        }
        if (id == R.id.add) {
            share();
            return true;
        }
        if (id == R.id.share) {
            share();
            return true;
        }
        if (id == R.id.chplay) {
            share();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void share(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "#ask2know");
        i.putExtra(Intent.EXTRA_TEXT, Utils.APP_URL);
        startActivity(Intent.createChooser(i, "ask2know"));
    }
}
