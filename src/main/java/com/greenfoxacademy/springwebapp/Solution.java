package com.greenfoxacademy.springwebapp;

class Solution {
  public static boolean validateHello(String greetings) {
    if (greetings.toLowerCase().matches(".*hello.*|.*ciao.*|.*salut.*|.*hallo.*|.*hola.*|.*ahoj.*|.*czesc.*")) return true;
    return false;
  }

  public static void main(String[] args) {
    System.out.println(validateHello("HELLO"));
  }
}