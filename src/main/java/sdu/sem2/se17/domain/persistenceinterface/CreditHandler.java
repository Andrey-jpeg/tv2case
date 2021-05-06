package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;

public interface CreditHandler extends GenericCrud<Credit> {
    ArrayList<Credit> findByProductionId(long id);
}
