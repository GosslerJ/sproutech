package com.greenfoxacademy.springwebapp.common.services;

public interface DelayService {
  void delay(Runnable task, long sec);

}
