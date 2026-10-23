package za.ac.richfield.smartpantry;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class RecipeDetailActivity extends Activity {
    @Override public void onCreate(Bundle b){super.onCreate(b);
        LinearLayout root=Ui.page(this,"Recipe Details");
        Recipe recipe=new DatabaseHelper(this).getRecipe(getIntent().getLongExtra("recipe_id",-1));
        if(recipe==null){root.addView(Ui.text(this,"Recipe not found."));return;}
        ScrollView scroll=new ScrollView(this);root.addView(scroll);LinearLayout content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);scroll.addView(content);
        TextView name=Ui.text(this,recipe.name);name.setTextSize(23);content.addView(name);
        content.addView(Ui.text(this,"INGREDIENTS"));
        for(Ingredient i:recipe.ingredients)content.addView(Ui.text(this,"• "+i.name+": "+PantryActivity.format(i.quantity)+" "+i.unit));
        content.addView(Ui.text(this,"PREPARATION"));content.addView(Ui.text(this,recipe.steps));
    }
}
