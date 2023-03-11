package top.linesoft.open2share;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class ReceiveOpenActivity extends AppCompatActivity {

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("a","Activity 收到数据，执行Finish");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.addCategory("android.intent.category.DEFAULT");
        Uri uri = getIntent().getData();
//        Log.d("分享","Data："+ getIntent().getData().toString()+ uri.getScheme());
//        Log.d("分享","Type："+ getIntent().getType());
        if (uri.getScheme().equals("file")) {
           boolean b= PreferenceManager.getDefaultSharedPreferences(this).getBoolean("use_file_uri",false);
           if (b){
               //API24以上系统分享支持file:///开头
               StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
               StrictMode.setVmPolicy(builder.build());
               builder.detectFileUriExposure();
           }else{
               Toast.makeText(getApplicationContext(), R.string.no_use_file_uri_msg, Toast.LENGTH_LONG).show();
               finishAffinity();
           }
        }
        sendIntent.putExtra(Intent.EXTRA_STREAM, uri);
        sendIntent.setType(getIntent().getType());
        sendIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(Intent.createChooser(sendIntent, getString(R.string.share_title)), 1);
        //finish();
    }
}
