package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;
import java.util.Optional;

public class ConsumerHandlerImpl implements ConsumerHandler {
    @Override
    public Optional<Production> findProduction(long id) {
        return Optional.empty();
    }

    @Override
    public ArrayList<Production> findProduction(String name) {
        return null;
    }

    @Override
    public ArrayList<Production> allProductions() {
        return null;
    }
}
