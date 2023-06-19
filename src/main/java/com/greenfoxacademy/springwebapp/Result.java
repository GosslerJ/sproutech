package com.greenfoxacademy.springwebapp;

import java.util.List;

class Result {

  /*
   * Complete the 'findNumber' function below.
   *
   * The function is expected to return a STRING.
   * The function accepts following parameters:
   *  1. INTEGER_ARRAY arr
   *  2. INTEGER k
   */

  public static String findNumber(List<Integer> arr, int k) {
    if (arr.contains(k)) {
      return "YES";
    } else {
      return "NO";
    }
  }

}
