package za.ac.richfield.smartpantry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class RecipeMatcher {
    public static String normalise(String name) {
        String n=name.toLowerCase(Locale.ROOT).trim().replaceAll("\\s+"," ");
        if(n.equals("tomatoes")) return "tomato";
        if(n.equals("potatoes")) return "potato";
        if(n.equals("eggs")) return "egg";
        if(n.equals("loaves")) return "loaf";
        if(n.endsWith("ies") && n.length()>3) return n.substring(0,n.length()-3)+"y";
        if(n.endsWith("s") && !n.endsWith("ss") && !n.endsWith("us") && n.length()>3) return n.substring(0,n.length()-1);
        return n;
    }
    // Quantities are expressed in a common base unit for each compatible group.
    private static String group(String unit) {
        if(unit.equals("g")||unit.equals("kg")) return "mass";
        if(unit.equals("ml")||unit.equals("l")) return "volume";
        return unit;
    }
    private static double base(double qty,String unit) {
        if(unit.equals("kg")||unit.equals("l")) return qty*1000;
        return qty;
    }
    private static String key(String name,String unit) { return normalise(name)+"|"+group(unit); }
    public static boolean canMake(Recipe recipe,List<Ingredient> pantry) {
        Map<String,Double> available=new HashMap<>();
        for(Ingredient p:pantry) {
            String k=key(p.name,p.unit);
            available.put(k,available.containsKey(k)?available.get(k)+base(p.quantity,p.unit):base(p.quantity,p.unit));
        }
        // Also handles a recipe that repeats the same ingredient in several rows.
        Map<String,Double> required=new HashMap<>();
        for(Ingredient i:recipe.ingredients) {
            String k=key(i.name,i.unit);
            required.put(k,required.containsKey(k)?required.get(k)+base(i.quantity,i.unit):base(i.quantity,i.unit));
        }
        for(String k:required.keySet()) if(available.getOrDefault(k,0.0)+0.000001<required.get(k)) return false;
        return true;
    }
    public static List<Recipe> suggestions(List<Recipe> all,List<Ingredient> pantry) {
        List<Recipe> result=new ArrayList<>();
        for(Recipe r:all) if(canMake(r,pantry)) result.add(r);
        return result;
    }
}
