package top.linesoft.open2share;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ShareCompat;

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

        //解决极少数旧App未使用URI分享会导致报错的问题
        //目前来看这类应用极少，先注释掉了
//        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
//        StrictMode.setVmPolicy(builder.build());
//        builder.detectFileUriExposure();

        //setContentView(R.layout.activity_receive_open);
        //Toast.makeText(this,"已经将打开文件转换为分享文件",Toast.LENGTH_LONG).show();

        new ShareCompat.IntentBuilder(this)
                .addStream(getIntent().getData())
                .setType(getIntent().getType())
                .startChooser();

    }
}
