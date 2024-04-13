package top.linesoft.open2share;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.SwitchPreferenceCompat;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.kiylx.m3preference.ui.BaseSettingsFragment;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SettingsFragment settingsFragment = new SettingsFragment();
        settingsFragment.setIconHide(false);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, settingsFragment)
                .commit();
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayHomeAsUpEnabled(true);

    }

    public static class SettingsFragment extends BaseSettingsFragment implements Preference.OnPreferenceClickListener, Preference.OnPreferenceChangeListener {
        Preference guidePreference;
        SwitchPreferenceCompat hidePreference;
        SwitchPreferenceCompat fileUriPreference;
        Preference aboutPreference;

        @Override
        public boolean onPreferenceClick(Preference preference) {
            if (preference == guidePreference) {
                Intent intent = new Intent(getActivity(), GuideActivity.class);
                startActivity(intent);
                return true;
            } else if (preference == aboutPreference) {
                new MaterialAlertDialogBuilder(requireContext())
                        .setTitle(R.string.about_dialogue_title)
                        .setMessage(R.string.about_dialogue_msg)
                        .setPositiveButton(R.string.ok, null)
                        .create()
                        .show();
            }
            return false;
        }

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            guidePreference = findPreference("guide");
            if (guidePreference != null) {
                guidePreference.setOnPreferenceClickListener(this);
            }
            hidePreference = findPreference("hide_icon");
            if (hidePreference != null) {
                hidePreference.setOnPreferenceChangeListener(this);
            }
            fileUriPreference = findPreference("use_file_uri");
            if (fileUriPreference != null) {
                fileUriPreference.setOnPreferenceChangeListener(this);
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
//            Log.d("onPreferenceChange", "onPreferenceChange: 被触发");
            if (preference == hidePreference) {
//                Toast.makeText(getContext(),"当前值为："+newValue,Toast.LENGTH_LONG).show();
                PackageManager pm = requireContext().getPackageManager();
                ComponentName hideComponentName = new ComponentName(requireContext(), "top.linesoft.open2share.hide_icon");
                ComponentName unhideComponentName = new ComponentName(getContext(), "top.linesoft.open2share.unhide_icon");
                if ((Boolean) newValue) {

                    MaterialAlertDialogBuilder mDialogBuilder = new MaterialAlertDialogBuilder(requireActivity());
                    mDialogBuilder.setTitle(R.string.warn)
                            .setMessage(R.string.hide_tips)
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                pm.setComponentEnabledSetting(hideComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                                pm.setComponentEnabledSetting(unhideComponentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                                SettingsFragment.this.hidePreference.setChecked(true);
                            }).setNegativeButton(R.string.no, null).create().show();
                    return false;


                } else {
                    pm.setComponentEnabledSetting(hideComponentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
                    pm.setComponentEnabledSetting(unhideComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
                    return true;
                }

            } else if (preference == fileUriPreference) {
                if ((Boolean) newValue) {
                    MaterialAlertDialogBuilder mDialogBuilder = new MaterialAlertDialogBuilder(requireActivity());
                    mDialogBuilder.setTitle(R.string.warn)
                            .setMessage(R.string.open_file_uri_msg)
                            .setPositiveButton(R.string.yes, (dialog, which) -> {
                                SettingsFragment.this.fileUriPreference.setChecked(true);
                            }).setNegativeButton(R.string.no, (dialog, which) -> {
                                SettingsFragment.this.fileUriPreference.setChecked(false);
                            }).create().show();
                    return false;
                } else {
                    return true;
                }
            }
            return false;
        }
    }
}