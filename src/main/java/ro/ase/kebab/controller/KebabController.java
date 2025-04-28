package ro.ase.kebab.controller;

import ro.ase.kebab.model.Kebab;
import ro.ase.kebab.service.KebabService;

import java.util.List;
import java.util.function.Predicate;

public class KebabController {

    private final KebabService kebabService;

    public KebabController(KebabService kebabService) {
        this.kebabService = kebabService;
    }

    public void createKebab(Kebab kebab) {
        this.kebabService.addKebab(kebab);
    }

    public boolean deleteKebabByIndex(int index) {
        return this.kebabService.deleteKebab(index);
    }

    public List<Kebab> getAllKebabs() {
        return this.kebabService.listAllKebabs();
    }

    public List<Kebab> filterKebabs(Predicate<Kebab> predicate) {
        return this.kebabService.filterKebabs(predicate);
    }
}
