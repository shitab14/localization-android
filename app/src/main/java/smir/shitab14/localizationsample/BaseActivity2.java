package smir.shitab14.localizationsample;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by Shitab Mir on 08 August, 2022.
 * HungryNaki (Technology), Daraz Bangladesh Limited, Alibaba Group
 * mushfiq.islam@hungrynaki.com | shitabmir@gmail.com
 **/

public abstract class BaseActivity2 extends AppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(App.localeManager.setLocale(base));
        Log.e(TAG, "attachBaseContext");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        showResourcesInfo();

        Toast.makeText(this, Locale.getDefault().getLanguage().toString(), Toast.LENGTH_LONG).show();
//        TextView tv = findViewById(R.id.cache);
//        tv.setText(Utility.getTitleCache());
    }

    private void showResourcesInfo() {
        Resources topLevelRes = Utility.getTopLevelResources(this);
//        updateInfo("Top level  ", findViewById(R.id.tv1), topLevelRes);

        Resources appRes = getApplication().getResources();
//        updateInfo("Application  ", findViewById(R.id.tv2), appRes);

        Resources actRes = getResources();
//        updateInfo("Activity  ", findViewById(R.id.tv3), actRes);

//        TextView tv4 = findViewById(R.id.tv4);
        String defLanguage = Locale.getDefault().getLanguage();
//        tv4.setText(String.format("Locale.getDefault() - %s", defLanguage));
//        tv4.setCompoundDrawablesWithIntrinsicBounds(null, null, getLanguageDrawable(defLanguage), null);
    }

    private void updateInfo(String title, TextView tv, Resources res) {
        Locale l = LocaleManager2.getLocale(res);
//        tv.setText(title + Utility.hexString(res) + String.format(" - %s", l.getLanguage()));
    }
}
