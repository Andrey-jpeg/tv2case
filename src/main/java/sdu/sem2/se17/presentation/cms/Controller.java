package sdu.sem2.se17.presentation.cms;

import sdu.sem2.se17.domain.CreditManagementHandler;

/*
Hampus Fink
Casper Jensen
 */
public abstract class Controller {
    public CreditManagementHandler creditManagementHandler;

    public Controller(CreditManagementHandler creditManagementHandler) {
        this.creditManagementHandler = creditManagementHandler;
    }
}
