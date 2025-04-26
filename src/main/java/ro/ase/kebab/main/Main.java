package ro.ase.kebab.main;

import ro.ase.kebab.controller.KebabController;
import ro.ase.kebab.service.KebabService;
import ro.ase.kebab.view.KebabCLI;

public class Main {
    public static void main(String[] args) {
        KebabService kebabService = new KebabService();
        KebabController kebabController = new KebabController(kebabService);
        KebabCLI kebabCLI = new KebabCLI(kebabController);

        kebabCLI.start();
    }
}
