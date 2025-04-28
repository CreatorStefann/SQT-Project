package ro.ase.kebab.builder;

import io.vavr.control.Try;
import ro.ase.kebab.exceptions.MissingCarbohydrateException;
import ro.ase.kebab.exceptions.MissingProteinException;
import ro.ase.kebab.exceptions.TooManySaucesException;
import ro.ase.kebab.model.Category;
import ro.ase.kebab.model.Ingredient;
import ro.ase.kebab.model.Kebab;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class KebabBuilder {

    private Ingredient protein;
    private Ingredient carbohydrate;
    private final List<Ingredient> pickles = new ArrayList<>();
    private final List<Ingredient> wraps = new ArrayList<>();
    private final List<Ingredient> fibers = new ArrayList<>();
    private final List<Ingredient> healthies = new ArrayList<>();
    private final List<Ingredient> sauces = new ArrayList<>();

    public KebabBuilder withProtein(Ingredient protein) {
        this.protein = validateCategory(protein, Category.PROTEIN);
        return this;
    }

    public KebabBuilder withCarbohydrate(Ingredient carbohydrate) {
        this.carbohydrate = validateCategory(carbohydrate, Category.CARBOHYDRATE);
        return this;
    }

    public KebabBuilder addPickle(Ingredient pickle) {
        this.pickles.add(validateCategory(pickle, Category.PICKLE));
        return this;
    }

    public KebabBuilder addWrap(Ingredient wrap) {
        this.wraps.add(validateCategory(wrap, Category.WRAP));
        return this;
    }

    public KebabBuilder addFiber(Ingredient fiber) {
        this.fibers.add(validateCategory(fiber, Category.FIBER));
        return this;
    }

    public KebabBuilder addHealthy(Ingredient healthy) {
        this.healthies.add(validateCategory(healthy, Category.HEALTHY));
        return this;
    }

    public KebabBuilder addSauce(Ingredient sauce) {
        if (this.protein == null) {
            throw new IllegalStateException("Cannot add sauce before choosing protein");
        }
        if (this.sauces.size() >= 3) {
            throw new TooManySaucesException();
        }
        this.sauces.add(validateCategory(sauce, Category.SAUCE));
        return this;
    }

    public Try<Kebab> build() {
        return Try.of(() -> {
            if (this.protein == null) {
                throw new MissingProteinException();
            }
            if (this.carbohydrate == null) {
                throw new MissingCarbohydrateException();
            }
            return new Kebab(
                    this.protein,
                    this.carbohydrate,
                    this.pickles,
                    this.wraps,
                    this.fibers,
                    this.healthies,
                    this.sauces
            );
        });
    }

    private Ingredient validateCategory(Ingredient ingredient, Category expected) {
        Objects.requireNonNull(ingredient, "Ingredient cannot be null");
        if (ingredient.category() != expected) {
            throw new IllegalArgumentException("Expected " + expected + " but got " + ingredient.category());
        }
        return ingredient;
    }
}
