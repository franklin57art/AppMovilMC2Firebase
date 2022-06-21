package com.example.appmovilmc2firebase.interfaces;

import com.example.appmovilmc2firebase.models.Client;
import com.example.appmovilmc2firebase.models.PuntosDeMedida;

public interface iComunicaFragments {

    public void enviarEstadoDeMedidores (PuntosDeMedida pdm);

    public void enviarPuntoDeMedida (PuntosDeMedida pdm);

    public void enviarCliente (Client cl);
}
