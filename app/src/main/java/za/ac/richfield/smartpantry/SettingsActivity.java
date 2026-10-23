package za.ac.richfield.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;

public class SettingsActivity extends Activity {
    @Override public void onCreate(Bundle b){super.onCreate(b);
        LinearLayout root=Ui.page(this,"Settings");
        root.addView(Ui.text(this,"Pantry display preferences"));
        CheckBox reminder=new CheckBox(this);reminder.setText("Show expiring-soon labels in pantry");
        reminder.setChecked(getSharedPreferences("settings",MODE_PRIVATE).getBoolean("expiry",true));root.addView(reminder);
        reminder.setOnCheckedChangeListener((button,checked)->getSharedPreferences("settings",MODE_PRIVATE).edit().putBoolean("expiry",checked).apply());
        root.addView(Ui.text(this,"Ingredients expire soon when their date is within 3 days, including already expired items. This is an on-screen label, not a push notification."));
        root.addView(Ui.text(this,"All pantry items and recipes are stored locally in SQLite. No internet or location services are used."));
    }
}
