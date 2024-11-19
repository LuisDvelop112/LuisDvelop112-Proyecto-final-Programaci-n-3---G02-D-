module com.luisdeveloper.billeteravirtualuq {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.mapstruct;
    requires java.desktop;
    requires java.logging;
    requires com.rabbitmq.client;
    requires java.sql;



    opens com.luisdeveloper.billeteravirtualuq to javafx.fxml;
    exports com.luisdeveloper.billeteravirtualuq;
    exports com.luisdeveloper.billeteravirtualuq.viewController;
    opens com.luisdeveloper.billeteravirtualuq.viewController to javafx.fxml;
    exports com.luisdeveloper.billeteravirtualuq.controller;
    exports com.luisdeveloper.billeteravirtualuq.mapping.dto;
    exports com.luisdeveloper.billeteravirtualuq.mapping.mappers;
    exports com.luisdeveloper.billeteravirtualuq.model;

}
