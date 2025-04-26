package ro.ase.kebab.factory;

import io.vavr.control.Try;
import ro.ase.kebab.exceptions.InvalidIngredientException;
import ro.ase.kebab.model.Category;
import ro.ase.kebab.model.Ingredient;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

public class IngredientFactory {

    private static final Map<String, Supplier<Ingredient>> INGREDIENTS = Map.ofEntries(
            Map.entry("chicken", () -> new Ingredient("Chicken", Category.PROTEIN, null)),
            Map.entry("lamb", () -> new Ingredient("Lamb", Category.PROTEIN, null)),
            Map.entry("falafel", () -> new Ingredient("Falafel", Category.PROTEIN, null)),
            Map.entry("potatoes", () -> new Ingredient("Potatoes", Category.CARBOHYDRATE, null)),
            Map.entry("rice", () -> new Ingredient("Rice", Category.CARBOHYDRATE, null)),
            Map.entry("cucumbers", () -> new Ingredient("Cucumbers", Category.PICKLE, null)),
            Map.entry("onions", () -> new Ingredient("Onions", Category.PICKLE, null)),
            Map.entry("tahini", () -> new Ingredient("Tahini", Category.SAUCE, null)),
            Map.entry("samurai", () -> new Ingredient("Samurai", Category.SAUCE, 8)),
            Map.entry("tzatziki", () -> new Ingredient("Tzatziki", Category.SAUCE, 12)),
            Map.entry("ketchup", () -> new Ingredient("Ketchup", Category.SAUCE, null)),
            Map.entry("salad", () -> new Ingredient("Salad", Category.WRAP, null)),
            Map.entry("pita", () -> new Ingredient("Pita", Category.WRAP, null)),
            Map.entry("cabbage", () -> new Ingredient("Cabbage", Category.FIBER, null)),
            Map.entry("tomatoes", () -> new Ingredient("Tomatoes", Category.FIBER, null)),
            Map.entry("carrots", () -> new Ingredient("Carrots", Category.FIBER, null)),
            Map.entry("spinach", () -> new Ingredient("Spinach", Category.HEALTHY, null)),
            Map.entry("radish", () -> new Ingredient("Radish", Category.HEALTHY, null))
    );

    public static Try<Ingredient> createIngredient(String name) {
        return Try.of(() -> Optional.ofNullable(INGREDIENTS.get(name.trim().toLowerCase()))
                        .orElseThrow(() -> new InvalidIngredientException("Unknown ingredient: " + name)))
                .map(Supplier::get);
    }

    public static List<Ingredient> getAllIngredients() {
        return INGREDIENTS.values().stream()
                .map(Supplier::get)
                .toList();
    }

}
