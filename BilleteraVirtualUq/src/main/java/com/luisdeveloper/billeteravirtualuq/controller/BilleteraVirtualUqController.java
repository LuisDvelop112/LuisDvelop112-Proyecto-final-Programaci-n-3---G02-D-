package com.luisdeveloper.billeteravirtualuq.controller;

import com.luisdeveloper.billeteravirtualuq.controller.services.IBilleteraVirtualUqControllerService;

public class BilleteraVirtualUqController implements IBilleteraVirtualUqControllerService {

    ModelFactoryController modelFactoryController;

    public BilleteraVirtualUqController(){
        System.out.println("Llamando al singleton desde BilleteraVirtualController");
        modelFactoryController = ModelFactoryController.getInstance();
    }
}
