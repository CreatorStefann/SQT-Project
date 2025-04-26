package ro.ase.kebab.model;

import java.util.Objects;

public record Ingredient(String name, Category category, Integer shelfLifeHours) {

    public Ingredient {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Ingredient name cannot be null or blank");
        }
        Objects.requireNonNull(category, "Category cannot be null");
    }

    @Override
    public String toString() {
        if (this.category == Category.SAUCE && this.shelfLifeHours != null) {
            return this.name + " (" + this.shelfLifeHours + "h)";
        }
        return this.name;
    }
}
