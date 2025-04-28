package ro.ase.kebab.service;

import ro.ase.kebab.model.Ingredient;
import ro.ase.kebab.model.Category;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SauceService {

    private final List<Ingredient> sauces = new ArrayList<>();

    public SauceService() {
        sauces.add(new Ingredient("Tahini", Category.SAUCE, null));
        sauces.add(new Ingredient("Samurai", Category.SAUCE, 8));
        sauces.add(new Ingredient("Tzatziki", Category.SAUCE, 12));
        sauces.add(new Ingredient("Ketchup", Category.SAUCE, null));
    }

    public void addSauce(Ingredient sauce) {
        this.sauces.add(sauce);
    }

    public boolean deleteSauce(String name) {
        return sauces.removeIf(s -> s.name().equalsIgnoreCase(name));
    }

    public List<Ingredient> getSauces() {
        return List.copyOf(sauces);
    }

    public void saveSaucesToFile(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (Ingredient sauce : sauces) {
                writer.println(sauce.name() + "," + (sauce.shelfLifeHours() == null ? "" : sauce.shelfLifeHours()));
            }
        } catch (IOException e) {
            System.out.println("Error saving sauces: " + e.getMessage());
        }
    }

    public void loadSaucesFromFile(String filename) {
        sauces.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 2);
                String name = parts[0];
                String shelfLifePart = parts.length > 1 ? parts[1] : "";
                Integer shelfLife = shelfLifePart.isBlank() ? null : Integer.parseInt(shelfLifePart);
                sauces.add(new Ingredient(name, Category.SAUCE, shelfLife));
            }
        } catch (IOException e) {
            System.out.println("Error loading sauces: " + e.getMessage());
        }
    }
}
