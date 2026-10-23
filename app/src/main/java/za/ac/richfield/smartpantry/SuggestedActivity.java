package za.ac.richfield.smartpantry;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class SuggestedActivity extends Activity {
    DatabaseHelper db;List<Recipe> matches=new ArrayList<>();ListView list;TextView status;
    @Override public void onCreate(Bundle b) {super.onCreate(b);db=new DatabaseHelper(this);
        LinearLayout root=Ui.page(this,"Suggested Recipes");
        root.addView(Ui.text(this,"Only recipes with every ingredient and enough quantity appear here."));
        status=Ui.text(this,"");root.addView(status);
        list=new ListView(this);root.addView(list,new LinearLayout.LayoutParams(-1,0,1));
        list.setOnItemClickListener((p,v,pos,id)->{Intent i=new Intent(this,RecipeDetailActivity.class);i.putExtra("recipe_id",matches.get(pos).id);startActivity(i);});
    }
    @Override protected void onResume(){super.onResume();matches=RecipeMatcher.suggestions(db.getRecipes(),db.getPantry());
        status.setText(matches.isEmpty()?"No recipes match your pantry yet - add more ingredients.":matches.size()+" recipe(s) you can make now:");
        list.setAdapter(new ArrayAdapter<Recipe>(this,android.R.layout.simple_list_item_2,android.R.id.text1,matches){
            @Override public View getView(int pos,View convert,ViewGroup parent) {View row=super.getView(pos,convert,parent);Recipe r=getItem(pos);
                ((TextView)row.findViewById(android.R.id.text1)).setText(r.name);
                ((TextView)row.findViewById(android.R.id.text2)).setText(r.ingredients.size()+" required ingredient(s)");return row;}
        });
    }
}
