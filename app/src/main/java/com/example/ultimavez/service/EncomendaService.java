package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Encomenda;
import com.example.ultimavez.persistence.EncomendaPersistence;

public class EncomendaService {

    private EncomendaPersistence database;

    public EncomendaService() {
        this.database = MyCustomApplication.getEncomendaPersistence();
    }

    public Result<Encomenda> salvarEncomenda(Encomenda encomenda) {
        database.saveEncomenda(encomenda);
        return Result.valid(null);
    }
}
