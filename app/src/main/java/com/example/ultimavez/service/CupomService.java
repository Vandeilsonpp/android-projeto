package com.example.ultimavez.service;

import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;

import java.util.Optional;

public class CupomService {

    public CupomService() {
    }

    public Optional<Cupom> findCupom(String codigoCupom) {
        return Optional.empty();
    }

    public Result<Cupom> attCupom(Cupom cupomAtualizado) {
        return Result.valid(cupomAtualizado);
    }
}
