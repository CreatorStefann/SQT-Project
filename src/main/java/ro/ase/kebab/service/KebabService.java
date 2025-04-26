package ro.ase.kebab.service;

import ro.ase.kebab.model.Kebab;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class KebabService {

    private final List<Kebab> kebabs = new ArrayList<>();

    public void addKebab(Kebab kebab) {
        this.kebabs.add(kebab);
    }

    public boolean deleteKebab(int index) {
        if (index >= 0 && index < this.kebabs.size()) {
            this.kebabs.remove(index);
            return true;
        }
        return false;
    }

    public List<Kebab> listAllKebabs() {
        return List.copyOf(this.kebabs);
    }

    public List<Kebab> filterKebabs(Predicate<Kebab> predicate) {
        return this.kebabs.stream()
                .filter(predicate)
                .toList();
    }

    public int countKebabs() {
        return this.kebabs.size();
    }
}
