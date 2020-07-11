package top.linesoft.open2share;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
        Preference guidePreference;
        SwitchPreference hidePreference;
        Preference aboutPreference;

        /**
         * Called when a preference has been clicked.
         *
         * @param preference The preference that was clicked
         * @return {@code true} if the click was handled
         */
        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == guidePreference){
                Intent intent = new Intent(getActivity(), GuideActivity.class);
                startActivity(intent);
                return true;
            }else if(preference == aboutPreference){
                new AlertDialog.Builder(getContext())
                        .setTitle("关于本软件")
                        .setMessage("作者：双霖")
                        .setPositiveButton("确定",null)
                        .setNeutralButton("网站", (dialog, which) -> {
                            MainActivity.openUrl(getContext(),"https://www.linesoft.top");
                        })
                        .create()
                        .show();
            }
            return false;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            guidePreference = findPreference("guide");
            if(guidePreference != null){
                guidePreference.setOnPreferenceClickListener(this);
            }
            hidePreference = findPreference("hide_icon");
            if(hidePreference != null){
                hidePreference.setOnPreferenceChangeListener(this);
            }
            aboutPreference = findPreference("about");
            aboutPreference.setOnPreferenceClickListener(this);



        }

        /**
         * Called when a preference has been changed by the user. This is called before the state
         * of the preference is about to be updated and before the state is persisted.
         *
         * @param preference The changed preference
         * @param newValue   The new value of the preference
         * @return {@code true} to update the state of the preference with the new value
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            Log.d("onPreferenceChange", "onPreferenceChange: 被触发");
            if(preference == hidePreference){
//                Toast.makeText(getContext(),"当前值为："+newValue,Toast.LENGTH_LONG).show();
                PackageManager pm = getContext().getPackageManager();
                ComponentName hideComponentName = new ComponentName(getContext(), "top.linesoft.open2share.hide_icon");
                ComponentName unhideComponentName = new ComponentName(getContext(), "top.linesoft.open2share.unhide_icon");
                if((Boolean) newValue == true){

                    AlertDialog.Builder mDialogBuilder = new AlertDialog.Builder(getContext());
                    mDialogBuilder.setTitle("警告：")
                            .setMessage("在部分系统下，隐藏桌面图标会导致本Activity被关闭，您将很难再次进入到本Activity，是否继续？")
                            .setPositiveButton("是", (dialog, which) -> {
                                pm.setComponentEnabledSetting(hideComponentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                                pm.setComponentEnabledSetting(unhideComponentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                                SettingsFragment.this.hidePreference.setChecked(true);
                    }).setNegativeButton("否",null).create().show();
                    return false;


                }else{
                    pm.setComponentEnabledSetting(hideComponentName,PackageManager.COMPONENT_ENABLED_STATE_DISABLED,PackageManager.DONT_KILL_APP);
                    pm.setComponentEnabledSetting(unhideComponentName,PackageManager.COMPONENT_ENABLED_STATE_ENABLED,PackageManager.DONT_KILL_APP);
                    return true;
                }

            }


            return false;
        }
    }
}