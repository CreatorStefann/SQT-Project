package ro.ase.cts.domain;

import java.util.Collections;
import java.util.List;

public class Kebab {
    private final Ingredient protein;
    private final Ingredient carbohydrate;
    private final List<Ingredient> pickles;
    private final List<Ingredient> wraps;
    private final List<Ingredient> fibers;
    private final List<Ingredient> healthies;
    private final List<Ingredient> sauces;

    @Override
    public String toString() {
        return "Kebab{" +
                "protein=" + protein +
                ", carbohydrate=" + carbohydrate +
                ", pickles=" + pickles +
                ", wraps=" + wraps +
                ", fibers=" + fibers +
                ", healthies=" + healthies +
                ", sauces=" + sauces +
                '}';
    }

    public Ingredient getProtein() {
        return protein;
    }

    public Ingredient getCarbohydrate() {
        return carbohydrate;
    }

    public List<Ingredient> getPickles() {
        return pickles;
    }

    public List<Ingredient> getWraps() {
        return wraps;
    }

    public List<Ingredient> getFibers() {
        return fibers;
    }

    public List<Ingredient> getHealthies() {
        return healthies;
    }

    public List<Ingredient> getSauces() {
        return sauces;
    }

    public Kebab(Ingredient protein,
                 Ingredient carbohydrate,
                 List<Ingredient> pickles,
                 List<Ingredient> wraps,
                 List<Ingredient> fibers,
                 List<Ingredient> healthies,
                 List<Ingredient> sauces)
    {
        this.protein = protein;
        this.carbohydrate = carbohydrate;
        this.pickles = Collections.unmodifiableList(pickles);
        this.wraps = Collections.unmodifiableList(wraps);
        this.fibers = Collections.unmodifiableList(fibers);
        this.healthies = Collections.unmodifiableList(healthies);
        this.sauces = Collections.unmodifiableList(sauces);
    }
}
