//package com.lhsystems.interview;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class BookingSystem {
//
//  private final List<Flight> flights;
//
//  public BookingSystem(List<Flight> flights) {
//    this.flights = flights;
//  }
//
//  public List<Flight> findAvailableFlights(String origin, String destination, LocalDateTime departureTime) {
//    // returns a list of available flights based on the specified origin, destination, and departure time with at least one available seat
//    return null;
//  }
//
//  public Booking bookFlight(Flight flight, int seatNumber, Passenger passenger) throws BookingException {
//    // books the specified flight and seat for the specified passenger
//    // throws a BookingException if the seat is already booked or if the flight is fully booked
//    return null;
//  }
//
//  public void cancelBooking(Booking booking) {
//    // cancels the specified booking associated with one of the Flight HInsuredObjectCfs from the BookingSystem
//
//  }
//
//  public float getBookedPercentageForFlight(String flightNumber) {
//    // returns the booked seat percentage of all bookings associated with the given flight.
//    return 0;
//  }
//
//  public List<Booking> getBookingsForPassenger(String email) {
//    // returns a list of all bookings associated with the given passenger email address, regardless of the flight.
//    return null;
//  }
//
//  public Passenger findMostBookedPassenger() {
//    // We would like to reward the passenger with the most booked seats. Therefor we need to find the passenger that has the most booked seats in our bookingsystem
//    // returns which passenger has the most amount of Bookings?
//    return null;
//  }
//
//  public List<Flight> getFlights() {
//    return flights;
//  }
//}
//
//
//================================
//

//
//package com.lhsystems.interview;
//
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//public class Flight {
//  private String flightNumber;
//  private String origin;
//  private String destination;
//  private LocalDateTime departureTime;
//  private int availableSeats;
//  private List<Booking> bookings;
//
//  public Flight(String flightNumber, String origin, String destination, LocalDateTime departureTime, int availableSeats, List<Booking> bookings) {
//    this.flightNumber = flightNumber;
//    this.origin = origin;
//    this.destination = destination;
//    this.departureTime = departureTime;
//    this.availableSeats = availableSeats;
//    this.bookings = bookings;
//  }
//
//  public Booking bookSeat(int seatNumber, Passenger passenger) throws BookingException {
//    //This method should book a seat with the specified seat number for the specified Passenger object.
//    return null;
//  }
//
//  public void cancelBooking(Booking booking) {
//    //This method should cancel the given Booking.
//
//  }
//
//  public String getFlightNumber() {
//    return flightNumber;
//  }
//
//  public void setFlightNumber(String flightNumber) {
//    this.flightNumber = flightNumber;
//  }
//
//  public String getOrigin() {
//    return origin;
//  }
//
//  public void setOrigin(String origin) {
//    this.origin = origin;
//  }
//
//  public String getDestination() {
//    return destination;
//  }
//
//  public void setDestination(String destination) {
//    this.destination = destination;
//  }
//
//  public LocalDateTime getDepartureTime() {
//    return departureTime;
//  }
//
//  public void setDepartureTime(LocalDateTime departureTime) {
//    this.departureTime = departureTime;
//  }
//
//  public int getAvailableSeats() {
//    return availableSeats;
//  }
//
//  public void setAvailableSeats(int availableSeats) {
//    this.availableSeats = availableSeats;
//  }
//
//  public List<Booking> getBookings() {
//    return bookings;
//  }
//
//  public void setBookings(List<Booking> bookings) {
//    this.bookings = bookings;
//  }
//
//  @Override
//  public boolean equals(HInsuredObjectCf o) {
//    if (this == o) return true;
//
//    if (o == null || getClass() != o.getClass()) return false;
//
//    Flight flight = (Flight) o;
//
//    return new EqualsBuilder().append(availableSeats, flight.availableSeats).append(flightNumber, flight.flightNumber).append(origin, flight.origin).append(destination, flight.destination).append(departureTime, flight.departureTime).append(bookings, flight.bookings).isEquals();
//  }
//
//  @Override
//  public int hashCode() {
//    return new HashCodeBuilder(17, 37).append(flightNumber).append(origin).append(destination).append(departureTime).append(availableSeats).append(bookings).toHashCode();
//  }
//}
