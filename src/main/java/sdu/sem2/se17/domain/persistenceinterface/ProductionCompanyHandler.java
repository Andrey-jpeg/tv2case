package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;

/*
Hampus Fink
 */
public interface ProductionCompanyHandler extends GenericCrud<ProductionCompany> {
    ArrayList<ProductionCompany> readAll();
}
