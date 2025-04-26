package ro.ase.kebab.model;

import java.util.List;
import java.util.Objects;

public class Kebab {

    private final Ingredient protein;
    private final Ingredient carbohydrate;
    private final List<Ingredient> pickles;
    private final List<Ingredient> wraps;
    private final List<Ingredient> fibers;
    private final List<Ingredient> healthies;
    private final List<Ingredient> sauces;

    public Kebab(
            Ingredient protein,
            Ingredient carbohydrate,
            List<Ingredient> pickles,
            List<Ingredient> wraps,
            List<Ingredient> fibers,
            List<Ingredient> healthies,
            List<Ingredient> sauces
    ) {
        this.protein = Objects.requireNonNull(protein, "Protein cannot be null");
        this.carbohydrate = Objects.requireNonNull(carbohydrate, "Carbohydrate cannot be null");
        this.pickles = List.copyOf(pickles);
        this.wraps = List.copyOf(wraps);
        this.fibers = List.copyOf(fibers);
        this.healthies = List.copyOf(healthies);
        this.sauces = List.copyOf(sauces);
    }

    public Ingredient getProtein() {
        return this.protein;
    }

    public Ingredient getCarbohydrate() {
        return this.carbohydrate;
    }

    public List<Ingredient> getPickles() {
        return this.pickles;
    }

    public List<Ingredient> getWraps() {
        return this.wraps;
    }

    public List<Ingredient> getFibers() {
        return this.fibers;
    }

    public List<Ingredient> getHealthies() {
        return this.healthies;
    }

    public List<Ingredient> getSauces() {
        return this.sauces;
    }

    @Override
    public String toString() {
        return "Kebab{" +
                "protein=" + this.protein +
                ", carbohydrate=" + this.carbohydrate +
                ", pickles=" + this.pickles +
                ", wraps=" + this.wraps +
                ", fibers=" + this.fibers +
                ", healthies=" + this.healthies +
                ", sauces=" + this.sauces +
                '}';
    }
}
