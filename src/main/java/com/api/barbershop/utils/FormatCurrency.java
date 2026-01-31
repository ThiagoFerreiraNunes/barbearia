package com.api.barbershop.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatCurrency {

    private static final Locale PT_BR = new Locale("pt", "BR");

    public static String format(BigDecimal value) {
        if (value == null) {
            return "R$ 0,00";
        }
        NumberFormat formatter = NumberFormat.getCurrencyInstance(PT_BR);
        return formatter.format(value);
    }


}
