package ro.ase.cts.domain;

import java.util.ArrayList;
import java.util.List;

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
        if (protein == null) {
            throw new IllegalArgumentException("Kebab must have a protein");
        }
        if (carbohydrate == null) {
            throw new IllegalArgumentException("Kebab must have a carbohydrate");
        }
        this.protein = protein;
        this.carbohydrate = carbohydrate;


        this.pickles   = new ArrayList<>(pickles);
        this.wraps     = new ArrayList<>(wraps);
        this.fibers    = new ArrayList<>(fibers);
        this.healthies = new ArrayList<>(healthies);
        this.sauces    = new ArrayList<>(sauces);
    }

    public Ingredient getProtein() {
        return protein;
    }

    public Ingredient getCarbohydrate() {
        return carbohydrate;
    }

    public List<Ingredient> getPickles() {
        return new ArrayList<>(pickles);
    }

    public List<Ingredient> getWraps() {
        return new ArrayList<>(wraps);
    }

    public List<Ingredient> getFibers() {
        return new ArrayList<>(fibers);
    }

    public List<Ingredient> getHealthies() {
        return new ArrayList<>(healthies);
    }

    public List<Ingredient> getSauces() {
        return new ArrayList<>(sauces);
    }

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
}
