package sdu.sem2.se17.presentation.cms;

import sdu.sem2.se17.domain.CreditManagementHandler;

public abstract class Controller {
    public CreditManagementHandler creditManagementHandler;

    public Controller(CreditManagementHandler creditManagementHandler) {
        this.creditManagementHandler = creditManagementHandler;
    }
}
