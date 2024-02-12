package com.luheresbar.daily.domain.repository;

import com.luheresbar.daily.domain.IncomeCategory;
import com.luheresbar.daily.persistence.entity.IncomeCategoryPK;

import java.util.List;

public interface IIncomeCategoryRepository {

    List<IncomeCategory> getByUser(Integer userId);

    boolean exists(IncomeCategoryPK incomeCategoryPK);

    IncomeCategory save(IncomeCategory incomeCategory);

    void delete(IncomeCategoryPK incomeCategoryPK);

}
