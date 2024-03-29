package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.credit.Credit;

import java.util.ArrayList;

/*
Hampus Fink
 */
public interface CreditHandler extends GenericCrud<Credit> {
    ArrayList<Credit> findByProductionId(long id);
}
