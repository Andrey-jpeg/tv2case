module sdu.sem2.SE17 {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires gson;
    requires java.sql;

    opens sdu.sem2.se17.presentation.cms;
    opens sdu.sem2.se17.presentation.consumersystem;
    opens sdu.sem2.se17.domain;
    opens sdu.sem2.se17.domain.auth;
    opens sdu.sem2.se17.domain.credit;
    opens sdu.sem2.se17.domain.production;

    exports sdu.sem2.se17.presentation.cms;
    exports sdu.sem2.se17.presentation.consumersystem;
    exports sdu.sem2.se17.domain;
    exports sdu.sem2.se17.domain.auth;
    exports sdu.sem2.se17.domain.credit;
    exports sdu.sem2.se17.domain.production;
}