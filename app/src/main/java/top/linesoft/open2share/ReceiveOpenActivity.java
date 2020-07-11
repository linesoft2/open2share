package top.linesoft.open2share;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ReceiveOpenActivity extends AppCompatActivity {

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Log.d("a","Activity 收到数据，执行Finish");
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

        setContentView(R.layout.activity_receive_open);
        //Toast.makeText(this,"已经将打开文件转换为分享文件",Toast.LENGTH_LONG).show();

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sendIntent.addCategory("android.intent.category.DEFAULT");
//        Log.d("分享","Data："+ getIntent().getData().toString());
//        Log.d("分享","Type："+ getIntent().getType());
        sendIntent.putExtra(Intent.EXTRA_STREAM, getIntent().getData());
        sendIntent.setType(getIntent().getType());
        startActivityForResult(Intent.createChooser(sendIntent,"分享文件"),1);
        //finish();

    }
}
