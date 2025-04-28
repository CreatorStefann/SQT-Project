package ro.ase.kebab.main;

import ro.ase.kebab.controller.KebabController;
import ro.ase.kebab.factory.IngredientFactory;
import ro.ase.kebab.service.KebabService;
import ro.ase.kebab.service.SauceService;
import ro.ase.kebab.view.KebabCLI;

public class Main {
    public static void main(String[] args) {
        SauceService sauceService = new SauceService();
        IngredientFactory.initialize(sauceService);

        KebabService kebabService = new KebabService();
        KebabController kebabController = new KebabController(kebabService);
        KebabCLI kebabCLI = new KebabCLI(kebabController, sauceService);
        kebabCLI.start();
    }
}
