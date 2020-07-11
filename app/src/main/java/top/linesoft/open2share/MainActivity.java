package top.linesoft.open2share;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button1;
    Button button2;
    Button button3;

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        if (v == button1) {
            WebView webView1 = new WebView(this);
            webView1.loadUrl("http://pandown.linesoft.top/open2share/guide.html");
            AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(this);
            mDialogBuilder.setTitle("使用说明").setView(webView1).setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                @Override
                public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                }
            }).show();//显示此对话框


        } else if (v == button2) {

        } else if (v == button3) {

            new AlertDialog.Builder(MainActivity.this).setTitle(button3.getText())//设置对话框标题
                    .setMessage("作者：双霖")//设置显示的内容
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                        @Override
                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件

                        }
                    }).show();//显示此对话框

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);
        finish();

    }

    protected static void openUrl(Context context, String uriString) {
        android.net.Uri uri = android.net.Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        intent.addFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
