package com.greenfoxacademy.springwebapp.common.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TimeServiceImplTest {

  private TimeServiceImpl timeService;

  @BeforeEach
  void setUp() {
    timeService = new TimeServiceImpl();
  }

  @Test
  void actualTime() {
    long expected = Instant.now().toEpochMilli() / 1000;
    long actual = timeService.actualTime();

    assertEquals(expected, actual);
  }

  @Test
  void timeNSecondsFromNow() {
    long seconds = 12345;
    long expected = Instant.now().toEpochMilli() / 1000 + seconds;
    long actual = timeService.timeNSecondsFromNow(seconds);

    assertEquals(expected, actual);
  }

  @Test
  void timeNSecondsFromGivenTime() {
    long seconds = 12345;
    long givenTime = 1677547346;
    long expected = givenTime + seconds;
    long actual = timeService.timeNSecondsFromGivenTime(seconds, givenTime);

    assertEquals(expected, actual);
  }

  @Test
  void isGivenTimeInPast() {
    long givenTime = 1677547346;

    assertTrue(timeService.isGivenTimeInPast(givenTime));
  }

  @Test
  void isGivenTimeNotInPast() {
    long givenTime = 1772241746;

    assertFalse(timeService.isGivenTimeInPast(givenTime));
  }

  @Test
  void secondsBetweenTwoGivenTimes() {
    long givenTime1 = 1677547346;
    long givenTime2 = 1772241746;

    long expected = givenTime2 - givenTime1;
    long actual = timeService.secondsBetweenTwoGivenTimes(givenTime1, givenTime2);

    assertEquals(expected, actual);
  }
}