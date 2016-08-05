package com.thanhcs.ask2know.adapter;

import android.app.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;
import java.util.Random;

import com.thanhcs.ask2know.MainActivity;
import com.thanhcs.ask2know.R;
import com.thanhcs.ask2know.utils.Utils;

public class CardsAdapter extends ArrayAdapter<String> {

    public static String TAG = CardsAdapter.class.getSimpleName().toUpperCase();
    Activity activity;
    CardView cv;
    public CardsAdapter(Activity context) {
        super(context, R.layout.card_content);
        this.activity = context;
    }

    @Override
    public View getView(int position, final View contentView, ViewGroup parent){
        TextView v = (TextView)(contentView.findViewById(R.id.content));
        cv = (CardView) (contentView.findViewById(R.id.card));
        TextView tvRight = (TextView) (contentView.findViewById(R.id.tvRight));
        TextView tvLeft = (TextView) (contentView.findViewById(R.id.tvLeft));
        int i  = new Random().nextInt(MainActivity.arrayList.length-1);
        cv.setCardBackgroundColor(Color.parseColor(MainActivity.arrayList[i]));
        v.setText(getItem(position));
        int right = 100+ new Random().nextInt(900);
        int left = 1+ new Random().nextInt(50);
        tvLeft.setText(left+" UP");
        tvRight.setText(right+" DOWN");

        Typeface font = Typeface.createFromAsset(activity.getAssets(), "fonts/robotoregular.ttf");
        v.setTypeface(font);
        tvRight.setTypeface(font);
        tvLeft.setTypeface(font);
        contentView.findViewById(R.id.imgShare).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareScreen();
            }
        });
        return contentView;
    }

    private void shareScreen() {
        checkAndCreateFolder();
        createFileAndShare();
    }

    private void createFileAndShare() {
        try {
            Date now = new Date();
            android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/"+ Utils.FILE_DIRECTOR +"/" + now + ".jpg";
            // create bitmap screen capture
            View v1 = MainActivity.frRoot;
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            //  openScreenshot(imageFile);
            shareImage(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or OOM
            e.printStackTrace();
        }
    }

    private void checkAndCreateFolder() {
        try {
            File folder = new File(Environment.getExternalStorageDirectory() + "/" + Utils.FILE_DIRECTOR);
            boolean success = true;
            if (!folder.exists()) {
                Log.wtf(TAG, "Directory Does Not Exist, Create It");
                success = folder.mkdir();
            }
            if (success) {
                Log.wtf(TAG, "Directory Created");
            } else {
                Log.wtf(TAG, "Failed - Error");
            }
        } catch (Exception e) {
            Log.wtf("CRAETE FOLDER", e.toString() + "");
        }
    }

    protected void shareImage(File imageFile) {
        Intent share = new Intent(Intent.ACTION_SEND);
        // If you want to share a png image only, you can do:
        // setType("image/png"); OR for jpeg: setType("image/jpeg");
        share.setType("image/*");
        // Make sure you put example png image named myImage.png in your
        // directory
        Uri uri = Uri.fromFile(imageFile);
        share.putExtra(Intent.EXTRA_STREAM, uri);
        //	share.putExtra(Intent.EXTRA_TEXT, "I dare you to beat me on this game.\nDownoad game Faster Thinking.\nhttps://play.google.com/store/apps/details?id=com.com.thanhcs.fasterthinking");
        activity.startActivity(Intent.createChooser(share, "Share image to..."));
    }




}

