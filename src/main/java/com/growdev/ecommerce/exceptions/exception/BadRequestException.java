package com.growdev.ecommerce.exceptions.exception;

public class BadRequestException extends RuntimeException {
  public BadRequestException(String msg) {
    super(msg);
  }
}
