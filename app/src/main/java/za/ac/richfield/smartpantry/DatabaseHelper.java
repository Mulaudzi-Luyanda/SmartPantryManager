package za.ac.richfield.smartpantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME="smart_pantry.db";
    private static final int DB_VERSION=1;
    public DatabaseHelper(Context context) { super(context,DB_NAME,null,DB_VERSION); }

    @Override public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE pantry (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, expiry TEXT)");
        db.execSQL("CREATE TABLE recipes (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, steps TEXT NOT NULL)");
        db.execSQL("CREATE TABLE recipe_ingredients (id INTEGER PRIMARY KEY AUTOINCREMENT, recipe_id INTEGER NOT NULL, name TEXT NOT NULL, quantity REAL NOT NULL, unit TEXT NOT NULL, FOREIGN KEY(recipe_id) REFERENCES recipes(id))");
        seed(db);
    }
    @Override public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion) { }

    public long saveIngredient(long id,String name,double quantity,String unit,String expiry) {
        ContentValues v=new ContentValues();
        v.put("name",name.trim());v.put("quantity",quantity);v.put("unit",unit);v.put("expiry",expiry.trim());
        SQLiteDatabase db=getWritableDatabase();
        if(id>0) { db.update("pantry",v,"id=?",new String[]{String.valueOf(id)}); return id; }
        return db.insert("pantry",null,v);
    }
    public void deleteIngredient(long id) { getWritableDatabase().delete("pantry","id=?",new String[]{String.valueOf(id)}); }
    public List<Ingredient> getPantry() {
        ArrayList<Ingredient> result=new ArrayList<>();
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry ORDER BY name COLLATE NOCASE",null)) {
            while(c.moveToNext()) result.add(new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.isNull(4)?"":c.getString(4)));
        }
        return result;
    }
    public Ingredient getIngredient(long id) {
        try(Cursor c=getReadableDatabase().rawQuery("SELECT id,name,quantity,unit,expiry FROM pantry WHERE id=?",new String[]{String.valueOf(id)})) {
            if(c.moveToFirst()) return new Ingredient(c.getLong(0),c.getString(1),c.getDouble(2),c.getString(3),c.isNull(4)?"":c.getString(4));
        }
        return null;
    }
    public List<Recipe> getRecipes() {
        ArrayList<Recipe> list=new ArrayList<>();
        SQLiteDatabase db=getReadableDatabase();
        try(Cursor c=db.rawQuery("SELECT id,name,steps FROM recipes ORDER BY name",null)) {
            while(c.moveToNext()) list.add(new Recipe(c.getLong(0),c.getString(1),c.getString(2)));
        }
        for(Recipe r:list) {
            try(Cursor c=db.rawQuery("SELECT name,quantity,unit FROM recipe_ingredients WHERE recipe_id=?",new String[]{String.valueOf(r.id)})) {
                while(c.moveToNext()) r.ingredients.add(new Ingredient(0,c.getString(0),c.getDouble(1),c.getString(2),""));
            }
        }
        return list;
    }
    public Recipe getRecipe(long id) { for(Recipe r:getRecipes()) if(r.id==id) return r; return null; }

    private void add(SQLiteDatabase db,String name,String steps,String... ingredients) {
        ContentValues v=new ContentValues();v.put("name",name);v.put("steps",steps);
        long recipeId=db.insert("recipes",null,v);
        for(String item:ingredients) {
            String[] a=item.split("\\|");
            ContentValues i=new ContentValues();i.put("recipe_id",recipeId);i.put("name",a[0]);i.put("quantity",Double.parseDouble(a[1]));i.put("unit",a[2]);
            db.insert("recipe_ingredients",null,i);
        }
    }
    private void seed(SQLiteDatabase db) {
        add(db,"Scrambled Eggs","1. Beat the eggs.\n2. Heat oil in a pan.\n3. Cook and stir until set.","egg|2|pcs","oil|10|ml");
        add(db,"Boiled Eggs","1. Place eggs in water.\n2. Boil for 9 minutes.\n3. Cool and peel.","egg|2|pcs","water|500|ml");
        add(db,"Tomato Salad","1. Wash and chop tomatoes.\n2. Add chopped onion.\n3. Mix with oil.","tomato|2|pcs","onion|1|pcs","oil|10|ml");
        add(db,"Cheese Sandwich","1. Place cheese between slices of bread.\n2. Serve.","bread|2|slices","cheese|40|g");
        add(db,"Egg Sandwich","1. Boil and chop eggs.\n2. Put on bread with butter.","egg|2|pcs","bread|2|slices","butter|10|g");
        add(db,"Butter Toast","1. Toast bread.\n2. Spread butter.","bread|2|slices","butter|10|g");
        add(db,"Peanut Butter Sandwich","1. Spread peanut butter on bread.\n2. Close sandwich.","bread|2|slices","peanut butter|30|g");
        add(db,"Banana Oats","1. Simmer oats in milk.\n2. Slice banana on top.","oats|60|g","milk|200|ml","banana|1|pcs");
        add(db,"Porridge","1. Heat milk.\n2. Stir in oats until soft.","oats|60|g","milk|200|ml");
        add(db,"Plain Rice","1. Rinse rice.\n2. Simmer rice in water until tender.","rice|100|g","water|250|ml");
        add(db,"Tomato Rice","1. Cook rice in water.\n2. Stir through chopped tomatoes.","rice|100|g","water|250|ml","tomato|2|pcs");
        add(db,"Garlic Pasta","1. Boil pasta in water.\n2. Drain and toss with garlic and oil.","pasta|100|g","water|500|ml","garlic|2|pcs","oil|15|ml");
        add(db,"Cheesy Pasta","1. Boil pasta in water.\n2. Drain and mix in cheese.","pasta|100|g","water|500|ml","cheese|50|g");
        add(db,"Mashed Potatoes","1. Boil peeled potatoes in water.\n2. Drain and mash with butter.","potato|3|pcs","water|500|ml","butter|20|g");
        add(db,"Fried Potatoes","1. Slice potatoes.\n2. Fry in oil until tender.","potato|3|pcs","oil|30|ml");
        add(db,"Carrot Salad","1. Grate carrots.\n2. Toss with oil.","carrot|2|pcs","oil|10|ml");
        add(db,"Cucumber Tomato Salad","1. Chop cucumber and tomatoes.\n2. Toss with oil.","cucumber|1|pcs","tomato|2|pcs","oil|10|ml");
        add(db,"Milk Banana Smoothie","1. Blend banana with milk.\n2. Serve cold.","banana|2|pcs","milk|250|ml");
        add(db,"Fried Egg on Toast","1. Fry egg in oil.\n2. Toast bread and top with egg.","egg|1|pcs","oil|10|ml","bread|1|slices");
        add(db,"Cheese Omelette","1. Beat eggs.\n2. Cook in oil and sprinkle cheese.\n3. Fold.","egg|2|pcs","oil|10|ml","cheese|30|g");
    }
}
