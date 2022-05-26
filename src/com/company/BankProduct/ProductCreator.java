package com.company.BankProduct;

import com.company.BankProduct.Data.BankProductData;

public interface ProductCreator {
    public BankProduct create(BankProductType type, BankProductData bankProductData);
}
