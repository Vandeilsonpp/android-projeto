package com.example.ultimavez.persistence;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Encomenda;

public interface EncomendaPersistence {
    Result<Encomenda> saveEncomenda(Encomenda encomenda);
}
