package app.chronicity.timer;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(ChronicityPlugin.class);
        super.onCreate(savedInstanceState);
        try {
            getBridge().getWebView().addJavascriptInterface(new Hooks(), "ChronicityNative");
        } catch (Exception ignored) {
        }
    }

    private void openSafely(Intent i) {
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            startActivity(i);
        } catch (Exception e) {
            try {
                Intent d = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                d.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(d);
            } catch (Exception ignored) {
            }
        }
    }

    /** Direct JS bridge: window.ChronicityNative.* (no Capacitor plugin machinery needed). */
    public class Hooks {
        @JavascriptInterface
        public boolean ready() {
            return true;
        }

        @JavascriptInterface
        public void startService(final String text) {
            runOnUiThread(() -> {
                try {
                    if (Build.VERSION.SDK_INT >= 33
                        && ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] { Manifest.permission.POST_NOTIFICATIONS }, 77);
                    }
                    Intent i = new Intent(MainActivity.this, TimerService.class);
                    i.putExtra("text", text);
                    ContextCompat.startForegroundService(MainActivity.this, i);
                } catch (Exception ignored) {
                }
            });
        }

        @JavascriptInterface
        public void stopService() {
            runOnUiThread(() -> MainActivity.this.stopService(new Intent(MainActivity.this, TimerService.class)));
        }

        @JavascriptInterface
        public boolean batteryIgnoring() {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            return pm != null && pm.isIgnoringBatteryOptimizations(getPackageName());
        }

        @JavascriptInterface
        public void requestUnrestricted() {
            runOnUiThread(() -> openSafely(new Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS, Uri.parse("package:" + getPackageName()))));
        }

        @JavascriptInterface
        public void openBatterySettings() {
            runOnUiThread(() -> openSafely(new Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)));
        }

        @JavascriptInterface
        public void openAppSettings() {
            runOnUiThread(() -> openSafely(new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()))));
        }
    }
}
