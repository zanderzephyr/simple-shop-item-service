package com.simple_shop.item_service.item.dto.formatter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import tools.jackson.databind.annotation.JsonSerialize;

/*
 * TODO Move to common-utils lib
 */
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@JsonSerialize(using = PlainBigDecimalSerializer.class)
public @interface JsonPlainBigDecimalFormatter {

}