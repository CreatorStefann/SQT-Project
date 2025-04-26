package ro.ase.cts.factory;

import ro.ase.cts.domain.Category;
import ro.ase.cts.domain.Ingredient;


public class IngredientFactory {

    public static Ingredient createIngredient(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Ingredient name cannot be null");
        }

        String input = name.trim().toLowerCase();
        switch (input) {
            case "chicken":
                return new Ingredient("Chicken", Category.PROTEIN, null);
            case "lamb":
                return new Ingredient("Lamb", Category.PROTEIN, null);
            case "falafel":
                return new Ingredient("Falafel", Category.PROTEIN, null);

            case "potatoes":
                return new Ingredient("Potatoes", Category.CARBOHYDRATE, null);
            case "rice":
                return new Ingredient("Rice", Category.CARBOHYDRATE, null);

            case "cucumbers":
                return new Ingredient("Cucumbers", Category.PICKLE, null);
            case "onions":
                return new Ingredient("Onions", Category.PICKLE, null);

            case "tahini":
                return new Ingredient("Tahini", Category.SAUCE, null);
            case "samurai":
                return new Ingredient("Samurai", Category.SAUCE, 8);
            case "tzatziki":
                return new Ingredient("Tzatziki", Category.SAUCE, 12);
            case "ketchup":
                return new Ingredient("Ketchup", Category.SAUCE, null);

            case "salad":
                return new Ingredient("Salad", Category.WRAP, null);
            case "pita":
                return new Ingredient("Pita", Category.WRAP, null);

            case "cabbage":
                return new Ingredient("Cabbage", Category.FIBER, null);
            case "tomatoes":
            case "tomato":
                return new Ingredient("Tomatoes", Category.FIBER, null);
            case "carrots":
            case "carrot":
                return new Ingredient("Carrots", Category.FIBER, null);

            case "spinach":
                return new Ingredient("Spinach", Category.HEALTHY, null);
            case "radish":
                return new Ingredient("Radish", Category.HEALTHY, null);

            default:
                throw new IllegalArgumentException("Unknown ingredient: " + name);
        }
    }
}
