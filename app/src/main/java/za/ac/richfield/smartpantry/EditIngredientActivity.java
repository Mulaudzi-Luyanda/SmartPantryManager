package za.ac.richfield.smartpantry;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.text.InputType;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Locale;

public class EditIngredientActivity extends Activity {
    EditText name,qty,expiry;Spinner unit;DatabaseHelper db;long id;
    final String[] units={"pcs","g","kg","ml","l","slices"};
    @Override public void onCreate(Bundle b) {super.onCreate(b);db=new DatabaseHelper(this);id=getIntent().getLongExtra("id",-1);
        LinearLayout root=Ui.page(this,id>0?"Edit Ingredient":"Add Ingredient");
        root.addView(Ui.text(this,"Ingredient name"));name=new EditText(this);name.setSingleLine(true);name.setHint("e.g. tomatoes");root.addView(name);
        root.addView(Ui.text(this,"Quantity (must be greater than zero)"));qty=new EditText(this);qty.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);qty.setHint("e.g. 2");root.addView(qty);
        root.addView(Ui.text(this,"Unit"));unit=new Spinner(this);unit.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,units));root.addView(unit);
        root.addView(Ui.text(this,"Expiry date (optional, YYYY-MM-DD)"));expiry=new EditText(this);expiry.setSingleLine(true);expiry.setHint("YYYY-MM-DD");root.addView(expiry);
        Button date=Ui.button(this,"Choose expiry date");root.addView(date);
        date.setOnClickListener(v->{Calendar c=Calendar.getInstance();new DatePickerDialog(this,(view,y,m,d)->expiry.setText(String.format(Locale.US,"%04d-%02d-%02d",y,m+1,d)),c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH)).show();});
        Button save=Ui.button(this,"Save ingredient");root.addView(save);save.setOnClickListener(v->save());
        if(id>0) {Ingredient item=db.getIngredient(id);if(item!=null) {name.setText(item.name);qty.setText(PantryActivity.format(item.quantity));expiry.setText(item.expiry);for(int i=0;i<units.length;i++) if(units[i].equals(item.unit)) unit.setSelection(i);} }
    }
    void save() {
        String n=name.getText().toString().trim(),q=qty.getText().toString().trim(),e=expiry.getText().toString().trim();
        if(n.isEmpty()) {name.setError("Name is required");name.requestFocus();return;}
        double amount;try {amount=Double.parseDouble(q);}catch(Exception ex){qty.setError("Enter a valid number");qty.requestFocus();return;}
        if(!Double.isFinite(amount)||amount<=0) {qty.setError("Quantity must be greater than zero");qty.requestFocus();return;}
        if(!e.isEmpty()) {try {if(!LocalDate.parse(e).toString().equals(e)) throw new IllegalArgumentException();}catch(Exception ex){expiry.setError("Use a valid date: YYYY-MM-DD");expiry.requestFocus();return;}}
        db.saveIngredient(id,n,amount,unit.getSelectedItem().toString(),e);finish();
    }
}
