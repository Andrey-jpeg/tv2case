package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

public interface ProductionCompanyHandler extends GenericCrud<ProductionCompany> {
    ArrayList<ProductionCompany> readAll();
}
