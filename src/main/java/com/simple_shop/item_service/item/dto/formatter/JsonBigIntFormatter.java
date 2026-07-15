package com.simple_shop.item_service.item.dto.formatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tools.jackson.databind.annotation.JsonSerialize;
import tools.jackson.databind.ser.std.ToStringSerializer;

/**
 * Can be used for Long and BigInt. Serializing needs this, de-serializing works
 * natively.
 */
/*
 * TODO What to do for BigDecimal? It should be possible to use plain or
 * scientific notation according to generally documented guidelines or
 * field-wise definitions.
 * 
 * TODO Move to common-utils lib
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = ToStringSerializer.class)
public @interface JsonBigIntFormatter {

}