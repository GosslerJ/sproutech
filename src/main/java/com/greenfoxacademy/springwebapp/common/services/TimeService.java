package com.greenfoxacademy.springwebapp.common.services;

public interface TimeService {

  /**
   * Returns the current time in seconds in epoch format.
   *
   * @return  the difference, measured in seconds, between
   *          the current time and midnight, January 1, 1970 UTC.
   */
  long actualTime();

  /**
   * Returns the time in epoch format
   * which will be the time the given seconds later from now.
   * Given seconds should be a {@code long} value.
   *
   * @param   seconds   the argument whose value is to be added to the actual time
   * @return  the time n seconds from current time.
   */
  long timeNSecondsFromNow(long seconds);

  /**
   * Returns the time in epoch format
   * which will be the time the given seconds later from given time.
   * Given seconds should be a {@code long} value.
   *
   * @param   seconds   the argument whose value is to be added to <code>givenTime</code>
   * @param   givenTime   the given time
   * @return  the time n seconds from given time.
   */
  long timeNSecondsFromGivenTime(long seconds, long givenTime);

  /**
   * Returns whether the given time is in the past or not.
   * If the given time is the current time, returns with false.
   * Given time should be an epoch time in seconds.
   *
   * @param   givenTime   the given time
   * @return  whether the time is in the past or not.
   */
  boolean isGivenTimeInPast(long givenTime);

  /**
   * Returns the seconds between the given times.
   * Always returns a positive value.
   * Given times should be an epoch time in seconds.
   *
   * @param   givenTime1   an argument, time in epoch
   * @param   givenTime2   another argument, time in epoch
   * @return  the seconds between the given times.
   */
  long secondsBetweenTwoGivenTimes(long givenTime1, long givenTime2);

}
