package ro.ase.cts.builder;

import ro.ase.cts.domain.Category;
import ro.ase.cts.domain.Ingredient;
import ro.ase.cts.domain.Kebab;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KebabBuilder {

    private Ingredient protein;
    private Ingredient carbohydrate;
    private Ingredient pickle;
    private Ingredient wrap;
    private Ingredient fiber;
    private Ingredient healthy;
    private final List<Ingredient> sauces = new ArrayList<>();



    public KebabBuilder withProtein(Ingredient protein) {
        validate(protein, Category.PROTEIN, "protein");
        this.protein = protein;
        return this;
    }

    public KebabBuilder withCarbohydrate(Ingredient carb) {
        validate(carb, Category.CARBOHYDRATE, "carbohydrate");
        this.carbohydrate = carb;
        return this;
    }



    public KebabBuilder withPickle(Ingredient pickle) {
        validateUnique(pickle, Category.PICKLE, "pickle", this.pickle);
        this.pickle = pickle;
        return this;
    }

    public KebabBuilder withWrap(Ingredient wrap) {
        validateUnique(wrap, Category.WRAP, "wrap", this.wrap);
        this.wrap = wrap;
        return this;
    }

    public KebabBuilder withFiber(Ingredient fiber) {
        validateUnique(fiber, Category.FIBER, "fiber", this.fiber);
        this.fiber = fiber;
        return this;
    }

    public KebabBuilder withHealthy(Ingredient healthy) {
        validateUnique(healthy, Category.HEALTHY, "healthy ingredient", this.healthy);
        this.healthy = healthy;
        return this;
    }



    public KebabBuilder addSauce(Ingredient sauce) {
        validate(sauce, Category.SAUCE, "sauce");
        if (protein == null) {
            throw new IllegalStateException("Cannot add sauce before protein");
        }
        if (sauces.size() >= 3) {
            throw new IllegalStateException("Cannot add more than 3 sauces");
        }
        sauces.add(sauce);
        return this;
    }



    public Kebab build() {
        if (protein == null) {
            throw new IllegalStateException("Kebab must have a protein");
        }
        if (carbohydrate == null) {
            throw new IllegalStateException("Kebab must have a carbohydrate");
        }

        return new Kebab(
                protein,
                carbohydrate,
                singletonOrEmpty(pickle),
                singletonOrEmpty(wrap),
                singletonOrEmpty(fiber),
                singletonOrEmpty(healthy),
                new ArrayList<>(sauces)
        );
    }


    private void validate(Ingredient ing, Category expected, String role) {
        if (ing == null || ing.getCategory() != expected) {
            throw new IllegalArgumentException("Invalid " + role + ": " + ing);
        }
    }

    private void validateUnique(Ingredient ing, Category expected,
                                String role, Ingredient existing) {
        validate(ing, expected, role);
        if (existing != null) {
            throw new IllegalStateException(
                    "Only one " + role + " allowed (" + existing.getName() + " already chosen)");
        }
    }

    private static List<Ingredient> singletonOrEmpty(Ingredient ing) {
        return ing == null ? Collections.emptyList() : Collections.singletonList(ing);
    }
}
