package com.greenfoxacademy.springwebapp.common.services;

import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Service;

@EqualsAndHashCode
@Service
public class TimeServiceImpl implements TimeService {

  @Override
  public long actualTime() {
    return System.currentTimeMillis() / 1000;
  }

  @Override
  public long timeNSecondsFromNow(long seconds) {
    return actualTime() + seconds;
  }

  @Override
  public long timeNSecondsFromGivenTime(long seconds, long givenTime) {
    return givenTime + seconds;
  }

  @Override
  public boolean isGivenTimeInPast(long givenTime) {
    return givenTime < actualTime();
  }

  @Override
  public long secondsBetweenTwoGivenTimes(long givenTime1, long givenTime2) {
    return Math.abs(givenTime1 - givenTime2);
  }

}