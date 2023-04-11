package com.greenfoxacademy.springwebapp.common.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class DelayServiceImpl implements DelayService {

  @Override
  public void delay(Runnable task, long sec) {
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        task.run();
        timer.cancel();
      }
    };

    timer.schedule(timerTask, sec);
  }

}
