package za.ac.richfield.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class Ui {
    static int blue=Color.rgb(19,68,125);
    static int dp(Activity a,int d) { return Math.round(d*a.getResources().getDisplayMetrics().density); }
    static LinearLayout page(Activity a,String title) {
        LinearLayout root=new LinearLayout(a);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(a,16),dp(a,12),dp(a,16),dp(a,12));root.setBackgroundColor(Color.rgb(246,249,253));
        a.setContentView(root);
        TextView heading=new TextView(a);heading.setText(title);heading.setTextSize(23);heading.setTypeface(null,Typeface.BOLD);heading.setTextColor(blue);heading.setPadding(0,0,0,dp(a,10));root.addView(heading);
        LinearLayout nav=new LinearLayout(a);nav.setOrientation(LinearLayout.HORIZONTAL);
        nav.addView(navButton(a,"Pantry",PantryActivity.class),new LinearLayout.LayoutParams(0,dp(a,44),1));
        nav.addView(navButton(a,"Recipes",SuggestedActivity.class),new LinearLayout.LayoutParams(0,dp(a,44),1));
        nav.addView(navButton(a,"Settings",SettingsActivity.class),new LinearLayout.LayoutParams(0,dp(a,44),1));
        root.addView(nav);return root;
    }
    private static Button navButton(Activity a,String name,Class<?> dest) {
        Button b=new Button(a);b.setAllCaps(false);b.setText(name);b.setTextSize(12);b.setOnClickListener(v->{ if(!a.getClass().equals(dest)) { Intent i=new Intent(a,dest);i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);a.startActivity(i); } });return b;
    }
    static TextView text(Activity a,String value) { TextView t=new TextView(a);t.setText(value);t.setTextSize(16);t.setTextColor(Color.DKGRAY);t.setPadding(dp(a,6),dp(a,9),dp(a,6),dp(a,9));return t; }
    static Button button(Activity a,String label) { Button b=new Button(a);b.setText(label);b.setAllCaps(false);return b; }
}
