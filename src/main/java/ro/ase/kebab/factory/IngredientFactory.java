package ro.ase.kebab.factory;

import io.vavr.control.Try;
import ro.ase.kebab.model.Category;
import ro.ase.kebab.model.Ingredient;
import ro.ase.kebab.service.SauceService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class IngredientFactory {

    private static final Map<String, Supplier<Ingredient>> INGREDIENTS = new HashMap<>();
    private static SauceService sauceService;

    public static void initialize(SauceService service) {
        sauceService = service;
        loadStaticIngredients();
    }

    private static void loadStaticIngredients() {
        INGREDIENTS.put("chicken", () -> new Ingredient("Chicken", Category.PROTEIN, null));
        INGREDIENTS.put("lamb", () -> new Ingredient("Lamb", Category.PROTEIN, null));
        INGREDIENTS.put("falafel", () -> new Ingredient("Falafel", Category.PROTEIN, null));

        INGREDIENTS.put("potatoes", () -> new Ingredient("Potatoes", Category.CARBOHYDRATE, null));
        INGREDIENTS.put("rice", () -> new Ingredient("Rice", Category.CARBOHYDRATE, null));

        INGREDIENTS.put("cucumbers", () -> new Ingredient("Cucumbers", Category.PICKLE, null));
        INGREDIENTS.put("onions", () -> new Ingredient("Onions", Category.PICKLE, null));

        INGREDIENTS.put("salad", () -> new Ingredient("Salad", Category.WRAP, null));
        INGREDIENTS.put("pita", () -> new Ingredient("Pita", Category.WRAP, null));

        INGREDIENTS.put("cabbage", () -> new Ingredient("Cabbage", Category.FIBER, null));
        INGREDIENTS.put("tomatoes", () -> new Ingredient("Tomatoes", Category.FIBER, null));
        INGREDIENTS.put("carrots", () -> new Ingredient("Carrots", Category.FIBER, null));

        INGREDIENTS.put("spinach", () -> new Ingredient("Spinach", Category.HEALTHY, null));
        INGREDIENTS.put("radish", () -> new Ingredient("Radish", Category.HEALTHY, null));
    }

    public static Try<Ingredient> createIngredient(String name) {
        var key = name.trim().toLowerCase();

        if (INGREDIENTS.containsKey(key)) {
            return Try.of(() -> INGREDIENTS.get(key).get());
        }

        return Try.of(() -> sauceService.getSauces().stream()
                .filter(s -> s.name().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown ingredient: " + name))
        );
    }

    public static List<Ingredient> getAllIngredients() {
        List<Ingredient> staticIngredients = INGREDIENTS.values().stream()
                .map(Supplier::get)
                .toList();
        List<Ingredient> sauces = sauceService.getSauces();

        return new ArrayList<>() {{
            addAll(staticIngredients);
            addAll(sauces);
        }};
    }
}
