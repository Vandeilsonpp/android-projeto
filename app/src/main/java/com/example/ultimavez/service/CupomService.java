package com.example.ultimavez.service;

import com.example.ultimavez.MyCustomApplication;
import com.example.ultimavez.helper.Result;
import com.example.ultimavez.model.domain.Cupom;
import com.example.ultimavez.persistence.CupomPersistence;

import java.util.Optional;

public class CupomService {

    private CupomPersistence cupomPersistence;

    public CupomService() {
        this.cupomPersistence = MyCustomApplication.getCupomPersistence();
    }

    public Optional<Cupom> findCupom(String codigoCupom) {
        return cupomPersistence.findByCodigo(codigoCupom);
    }

    public Result<Cupom> attCupom(Cupom cupomAtualizado) {
        Result<Cupom> result = new Result<>(cupomAtualizado);
        verifyNullValues(cupomAtualizado, result);

        if (!result.isValid()) {
            return Result.invalid(result.getErrors());
        }

        Result<Cupom> dbResult = cupomPersistence.saveCupom(cupomAtualizado);
        if (!dbResult.isValid()) {
            return Result.invalid("Ocorreu um problema interno. Tente novamente mais tarde");
        }

        return Result.valid(cupomAtualizado);
    }

    private void verifyNullValues(Cupom cupomAtualizado, Result<Cupom> result) {
        if (isEmpty(cupomAtualizado.getCodigo())) {
            result.addError("Código é obrigatório");
        }

        if (cupomAtualizado.getValorDoDesconto() <= 0) {
            result.addError("Desconto precisa ser maior que 0");
        }

    }

    private boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

}
