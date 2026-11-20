package app.chronicity.timer;

import android.os.Bundle;
import com.getcapacitor.BridgeActivity;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        registerPlugin(ChronicityPlugin.class);
        super.onCreate(savedInstanceState);
    }
}
