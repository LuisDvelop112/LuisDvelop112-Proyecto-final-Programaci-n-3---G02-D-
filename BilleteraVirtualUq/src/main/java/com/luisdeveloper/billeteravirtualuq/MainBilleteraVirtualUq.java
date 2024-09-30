package com.luisdeveloper.billeteravirtualuq;

import com.luisdeveloper.billeteravirtualuq.controller.ModelFactoryController;
import com.luisdeveloper.billeteravirtualuq.mapping.dto.CuentaDto;

import java.util.List;

public class MainBilleteraVirtualUq {

    public static void main(String[] args) {
        ModelFactoryController modelFactoryController = ModelFactoryController.getInstance();

        CuentaDto cuentaDto = new CuentaDto("323332","Bancolombia","2211", "ahorros");

        if(modelFactoryController.agregarCuenta(cuentaDto)){
            System.out.println("No existe se agrego correctamente");
        }else{
            System.out.println("Ya existe");
        }

        List<CuentaDto> cuentaDtoList = modelFactoryController.obtenerCuentas();
        cuentaDtoList.forEach(System.out::println);
    }
}
