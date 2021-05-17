package sdu.sem2.se17.domain;

import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;
import java.util.Optional;

public interface ConsumerHandler {
    Optional<Production> findProduction(long id);
    ArrayList<Production> allProductions();
}
