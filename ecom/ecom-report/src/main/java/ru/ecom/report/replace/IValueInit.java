package ru.ecom.report.replace;

import java.util.Map;

/**
 * Настрайка параметров
 */
public interface IValueInit {

    void init(Map<String, String> aParams, IValueGetter aValueGetter) throws SetValueException;
}
