package za.ac.richfield.smartpantry;

import java.util.ArrayList;
import java.util.List;

class Ingredient {
    long id;
    String name, unit, expiry;
    double quantity;
    Ingredient(long id, String name, double quantity, String unit, String expiry) {
        this.id=id; this.name=name; this.quantity=quantity; this.unit=unit; this.expiry=expiry;
    }
}
class Recipe {
    long id;
    String name, steps;
    List<Ingredient> ingredients = new ArrayList<>();
    Recipe(long id, String name, String steps) { this.id=id; this.name=name; this.steps=steps; }
}
