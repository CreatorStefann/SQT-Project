package ro.ase.kebab.view;

import io.vavr.control.Try;
import ro.ase.kebab.builder.KebabBuilder;
import ro.ase.kebab.controller.KebabController;
import ro.ase.kebab.factory.IngredientFactory;
import ro.ase.kebab.model.Category;
import ro.ase.kebab.model.Ingredient;
import ro.ase.kebab.model.Kebab;
import ro.ase.kebab.service.SauceService;

import java.util.Scanner;

public class KebabCLI {

    private final SauceService sauceService;
    private final KebabController controller;
    private final Scanner scanner = new Scanner(System.in);

    private void saveSauces() {
        System.out.print("Enter filename to save sauces: ");
        String filename = scanner.nextLine().trim();
        this.sauceService.saveSaucesToFile(filename);
        System.out.println("Sauces saved successfully to " + filename);
    }

    private void loadSauces() {
        System.out.print("Enter filename to load sauces: ");
        String filename = scanner.nextLine().trim();
        this.sauceService.loadSaucesFromFile(filename);
    }

    private void createSauce() {
        System.out.print("Enter sauce name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter shelf life in hours (or leave empty if none): ");
        String shelfLifeInput = scanner.nextLine().trim();
        Integer shelfLife = shelfLifeInput.isEmpty() ? null : Integer.parseInt(shelfLifeInput);

        Ingredient sauce = new Ingredient(name, Category.SAUCE, shelfLife);
        sauceService.addSauce(sauce);
        System.out.println("Sauce created successfully.");
    }

    private void deleteSauce() {
        System.out.print("Enter sauce name to delete: ");
        String name = scanner.nextLine().trim();
        if (sauceService.deleteSauce(name)) {
            System.out.println("Sauce deleted successfully.");
        } else {
            System.out.println("No sauce with that name found.");
        }
    }

    public KebabCLI(KebabController controller, SauceService sauceService) {
        this.controller = controller;
        this.sauceService = sauceService;
    }

    public void start() {
        printBanner();

        boolean running = true;
        while (running) {
            printMenu();
            System.out.print("\nEnter your choice: ");
            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1" -> createKebab();
                case "2" -> deleteKebab();
                case "3" -> listKebabs();
                case "4" -> filterKebabs();
                case "5" -> createSauce();
                case "6" -> deleteSauce();
                case "7" -> saveSauces();
                case "8" -> loadSauces();
                case "0" -> {
                    System.out.println("\nThank you for using Kebap Builder. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid option. Please select a valid menu item.");
            }


        }
    }

    private void printBanner() {
        System.out.println("""
                     __        __   _                            _        
                     \\ \\      / /__| | ___ ___  _ __ ___   ___  | |_ ___  
                      \\ \\ /\\ / / _ \\ |/ __/ _ \\| '_ ` _ \\ / _ \\ | __/ _ \\ 
                       \\ V  V /  __/ | (_| (_) | | | | | |  __/ | || (_) |
                        \\_/\\_/ \\___|_|\\___\\___/|_| |_| |_|\\___|  \\__\\___/ 
                     """);
        System.out.println("Welcome to Kebap Builder\n");
    }

    private void printMenu() {
        System.out.println("""
            MENU
            [1] Create a new Kebab
            [2] Delete an existing Kebab
            [3] List all Kebabs
            [4] Filter Kebabs
            [5] Create new Sauce
            [6] Delete existing Sauce
            [7] Save sauces to file
            [8] Load sauces from file
            [0] Exit
            """);
    }

    private boolean readYesNo(String prompt) {
        while (true) {
            System.out.print(prompt + " (yes/no): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("yes")) {
                return true;
            } else if (input.equals("no")) {
                return false;
            } else {
                System.out.println("Invalid input. Please type 'yes' or 'no'.");
            }
        }
    }

    private void createKebab() {
        System.out.println("\nCreate your Kebab:");

        Try<KebabBuilder> builderTry = Try.success(new KebabBuilder());

        builderTry = builderTry.flatMap(builder -> chooseIngredient("protein")
                .map(builder::withProtein));

        builderTry = builderTry.flatMap(builder -> chooseIngredient("carbohydrate")
                .map(builder::withCarbohydrate));

        if (readYesNo("Would you like to add a pickle?")) {
            builderTry = builderTry.flatMap(builder -> chooseIngredient("pickle")
                    .map(builder::addPickle));
        }

        if (readYesNo("Would you like to add a wrap?")) {
            builderTry = builderTry.flatMap(builder -> chooseIngredient("wrap")
                    .map(builder::addWrap));
        }

        if (readYesNo("Would you like to add a fiber?")) {
            builderTry = builderTry.flatMap(builder -> chooseIngredient("fiber")
                    .map(builder::addFiber));
        }

        if (readYesNo("Would you like to add a healthy ingredient?")) {
            builderTry = builderTry.flatMap(builder -> chooseIngredient("healthy")
                    .map(builder::addHealthy));
        }

        System.out.println("You can add up to 3 sauces.");
        for (int i = 0; i < 3; i++) {
            if (!readYesNo("Would you like to add a sauce?")) {
                break;
            }
            builderTry = builderTry.flatMap(builder -> chooseIngredient("sauce")
                    .map(builder::addSauce));
        }

        builderTry.flatMap(KebabBuilder::build)
                .onSuccess(kebab -> {
                    this.controller.createKebab(kebab);
                    System.out.println("Kebab successfully created.");
                })
                .onFailure(e -> System.out.println("Error creating kebab: " + e.getMessage()));
    }

    private Try<Ingredient> chooseIngredient(String categoryName) {
        while (true) {
            printAvailableOptions(categoryName);
            System.out.print("\nEnter a " + categoryName + ": ");
            String ingredientName = scanner.nextLine();
            Try<Ingredient> ingredientTry = IngredientFactory.createIngredient(ingredientName);

            if (ingredientTry.isSuccess()) {
                return ingredientTry;
            } else {
                System.out.println("Invalid ingredient. Please try again.");
            }
        }
    }

    private void printAvailableOptions(String categoryName) {
        System.out.println("\nAvailable " + categoryName + " options:");

        IngredientFactory.getAllIngredients().stream()
                .filter(ingredient -> ingredient.category().name().equalsIgnoreCase(categoryName))
                .forEach(ingredient -> System.out.println("- " + ingredient.toString()));
    }

    private String prettyPrintKebab(Kebab kebab) {
        return "Protein: " + kebab.getProtein() +
                ", Carbohydrate: " + kebab.getCarbohydrate() +
                ", Pickles: " + kebab.getPickles() +
                ", Wraps: " + kebab.getWraps() +
                ", Fibers: " + kebab.getFibers() +
                ", Healthies: " + kebab.getHealthies() +
                ", Sauces: " + kebab.getSauces();
    }

    private void listKebabs() {
        var kebabs = this.controller.getAllKebabs();
        if (kebabs.isEmpty()) {
            System.out.println("\nNo kebabs available.");
            return;
        }
        System.out.println("\nKebabs:");
        for (int i = 0; i < kebabs.size(); i++) {
            System.out.println("[" + i + "] " + prettyPrintKebab(kebabs.get(i)));
        }
    }

    private void deleteKebab() {
        listKebabs();
        System.out.print("\nEnter the index of the Kebab to delete: ");
        try {
            int index = Integer.parseInt(scanner.nextLine());
            if (this.controller.deleteKebabByIndex(index)) {
                System.out.println("Kebab deleted successfully.");
            } else {
                System.out.println("Invalid index. No kebab deleted.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void filterKebabs() {
        System.out.print("\nEnter a keyword to filter by ingredient name: ");
        String keyword = scanner.nextLine().trim().toLowerCase();

        var filtered = this.controller.filterKebabs(kebab ->
                kebab.getProtein().name().toLowerCase().contains(keyword) ||
                        kebab.getCarbohydrate().name().toLowerCase().contains(keyword) ||
                        kebab.getPickles().stream().anyMatch(ing -> ing.name().toLowerCase().contains(keyword)) ||
                        kebab.getSauces().stream().anyMatch(ing -> ing.name().toLowerCase().contains(keyword))
        );

        if (filtered.isEmpty()) {
            System.out.println("\nNo kebabs matched the filter.");
            return;
        }

        System.out.println("\nFiltered Kebabs:");
        for (var kebab : filtered) {
            System.out.println("- " + prettyPrintKebab(kebab));
        }
    }
}
