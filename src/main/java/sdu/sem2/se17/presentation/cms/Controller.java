package sdu.sem2.se17.presentation.cms;

import sdu.sem2.se17.domain.CreditManagementController;

public abstract class Controller {
    public CreditManagementController creditManagementController;

    public Controller(CreditManagementController creditManagementController) {
        this.creditManagementController = creditManagementController;
    }
}
