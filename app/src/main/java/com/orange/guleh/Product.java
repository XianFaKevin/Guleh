package com.orange.guleh;

import java.sql.Blob;

/**
 * Created by Kevin on 9/7/2015.
 */
public class Product {

    String code;
    double price;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
