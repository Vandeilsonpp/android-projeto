package com.example.ultimavez.persistence;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.model.domain.User;
import com.example.ultimavez.model.enums.UserEnum;

import java.util.List;
import java.util.Optional;

public interface CupomPersistence {

    Result<Cupom> saveCupom(Cupom cupom);
    Optional<Cupom> findByCodigo(String codigo);
    Optional<Cupom> findActiveByCodigo(String codigo);
    boolean existsByCodigo(String codigo);
    Optional<List<Cupom>> findAllById(long sellerId);
}
