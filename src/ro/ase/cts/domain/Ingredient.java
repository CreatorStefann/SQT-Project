package ro.ase.cts.domain;

public class Ingredient {
    private final String name;
    private final Category category;
    private final Integer shelfLifeHours;

    public Ingredient(String name, Category category, Integer shelfLifeHours) {
        this.name = name;
        this.category = category;
        this.shelfLifeHours = shelfLifeHours;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Integer getShelfLifeHours() {
        return shelfLifeHours;
    }

    @Override
    public String toString() {
        if (category == Category.SAUCE && shelfLifeHours != null) {
            return name + " (“" + shelfLifeHours + "h”)";
        }
        return name;
    }
}
