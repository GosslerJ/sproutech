package com.greenfoxacademy.springwebapp.bookingSystem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookingSystem {

  private final List<Flight> flights;

  public BookingSystem(List<Flight> flights) {
    this.flights = flights;
  }

  public List<Flight> findAvailableFlights(String origin, String destination, LocalDateTime departureTime) {
    List<Flight> availableFlights = new ArrayList<>();

    for (Flight flight : flights) {
      LocalDateTime flightDepartureTime = flight.getDepartureTime();
      if (flight.getOrigin().equals(origin) && flight.getDestination().equals(destination)
              && flightDepartureTime.isAfter(departureTime) && flight.getAvailableSeats() > 0) {
        availableFlights.add(flight);
      }
    }

    return availableFlights;
  }

  public Booking bookFlight(Flight flight, int seatNumber, Passenger passenger) throws BookingException {
    // books the specified flight and seat for the specified passenger
    // throws a BookingException if the seat is already booked or if the flight is fully booked
    if (!flights.contains(flight)) {
      throw new BookingException("Flight not found.");
    }

    return flight.bookSeat(seatNumber, passenger);
  }

  public void cancelBooking(Booking booking) throws BookingException {
    // cancels the specified booking associated with one of the Flight objects from the BookingSystem
    boolean bookingCanceled = false;

    for (Flight flight : flights) {
      try {
        flight.cancelBooking(booking);
        bookingCanceled = true;
        break;
      } catch (BookingException e) {
      }
    }

    if (!bookingCanceled) {
      throw new BookingException("Booking not found or cannot be canceled.");
    }
  }

  public float getBookedPercentageForFlight(String flightNumber) {
    Flight flight = findFlightByNumber(flightNumber);

    if (flight != null) {
      int totalSeats = flight.getTotalSeats();
      int availableSeats = flight.getAvailableSeats();

      if (totalSeats > 0) {
        float bookedPercentage = (float) ((totalSeats - availableSeats) * 100) / totalSeats;
        return bookedPercentage;
      }
    }

    return 0.0f;
  }


  private Flight findFlightByNumber(String flightNumber) {
    for (Flight flight : flights) {
      if (flight.getFlightNumber().equals(flightNumber)) {
        return flight;
      }
    }
    return null;
  }

  public List<Booking> getBookingsForPassenger(String email) {
    // returns a list of all bookings associated with the given passenger email address, regardless of the flight.
    List<Booking> passengerBookings = new ArrayList<>();

    for (Flight flight : flights) {
      List<Booking> flightBookings = flight.getBookings();

      for (Booking booking : flightBookings) {
        Passenger passenger = booking.getPassenger();

        if (passenger.getEmail().equals(email)) {
          passengerBookings.add(booking);
        }
      }
    }
    return passengerBookings;
  }

  public Passenger findMostBookedPassenger() {
    // We would like to reward the passenger with the most booked seats.
    // Therefor we need to find the passenger that has the most booked seats in our bookingsystem
    // returns which passenger has the most amount of Bookings?
    Map<Passenger, Integer> passengerBookingCount = new HashMap<>();

    for (Flight flight : flights) {
      List<Booking> flightBookings = flight.getBookings();

      for (Booking booking : flightBookings) {
        Passenger passenger = booking.getPassenger();
        passengerBookingCount.put(passenger, passengerBookingCount.getOrDefault(passenger, 0) + 1);
      }
    }

    // Find the passenger with the maximum number of bookings
    Passenger mostBookedPassenger = null;
    int maxBookings = 0;

    for (Map.Entry<Passenger, Integer> entry : passengerBookingCount.entrySet()) {
      Passenger passenger = entry.getKey();
      int bookings = entry.getValue();

      if (bookings > maxBookings) {
        maxBookings = bookings;
        mostBookedPassenger = passenger;
      }
    }

    return mostBookedPassenger;
  }


  public List<Flight> getFlights() {
    return flights;
  }
}
