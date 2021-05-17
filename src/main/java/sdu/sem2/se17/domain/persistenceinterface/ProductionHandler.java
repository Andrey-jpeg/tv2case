package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;

public interface ProductionHandler extends GenericCrud<Production> {
    ArrayList<Production> readAll();
    ArrayList<Production> findByCompanyId(long id);
}
