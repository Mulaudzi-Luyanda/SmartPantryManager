package za.ac.richfield.smartpantry;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PantryActivity extends Activity {
    DatabaseHelper db; List<Ingredient> items=new ArrayList<>();ListView list;TextView empty;
    @Override public void onCreate(Bundle b) { super.onCreate(b);db=new DatabaseHelper(this);
        LinearLayout root=Ui.page(this,"My Pantry");
        Button add=Ui.button(this,"+ Add Ingredient");root.addView(add);add.setOnClickListener(v->startActivity(new Intent(this,EditIngredientActivity.class)));
        root.addView(Ui.text(this,"Tap an item to edit. Long-press it to delete."));
        empty=Ui.text(this,"");root.addView(empty);
        list=new ListView(this);root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,EditIngredientActivity.class);i.putExtra("id",items.get(pos).id);startActivity(i);});
        list.setOnItemLongClickListener((p,v,pos,id)->{
            Ingredient item=items.get(pos);
            new AlertDialog.Builder(this).setTitle("Delete ingredient?").setMessage("Remove "+item.name+" from your pantry?")
              .setNegativeButton("Cancel",null).setPositiveButton("Delete",(d,w)->{db.deleteIngredient(item.id);refresh();}).show();return true;
        });
    }
    @Override protected void onResume() {super.onResume();refresh();}
    void refresh() {
        items=db.getPantry();empty.setText(items.isEmpty()?"Your pantry is empty. Add an ingredient to get started.":items.size()+" ingredient(s) saved locally.");
        SharedPreferences prefs=getSharedPreferences("settings",MODE_PRIVATE);
        boolean showExpiry=prefs.getBoolean("expiry",true);
        ArrayAdapter<Ingredient> adapter=new ArrayAdapter<Ingredient>(this,android.R.layout.simple_list_item_2,android.R.id.text1,items) {
            @Override public View getView(int position,View convert,ViewGroup parent) {
                View row=super.getView(position,convert,parent);Ingredient i=getItem(position);
                TextView t1=row.findViewById(android.R.id.text1),t2=row.findViewById(android.R.id.text2);
                t1.setText(i.name);t1.setTextSize(18);
                String info=String.format(Locale.US,"%s %s",format(i.quantity),i.unit);
                if(i.expiry!=null&&!i.expiry.isEmpty()) {
                    info+=" | Expires: "+i.expiry;
                    if(showExpiry) try { long days=ChronoUnit.DAYS.between(LocalDate.now(),LocalDate.parse(i.expiry));if(days<=3) info+=" | EXPIRING SOON"; } catch(Exception ignored) {}
                }
                t2.setText(info);t2.setTextSize(13);return row;
            }
        };
        list.setAdapter(adapter);
    }
    static String format(double n) {return n==Math.floor(n)?String.format(Locale.US,"%.0f",n):String.format(Locale.US,"%.2f",n);}
}
