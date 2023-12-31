package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class Reservation {
    ///Atributos
    private String dni;
    private int idRoom;
    private int reservationNumber;
    private static int reservationNumberNext= 1;
    private String checkIn;
    private String checkOut;
    private boolean cancelled;

    ///Constructores
    public Reservation(){
    }

    public Reservation(String dni, int idRoom, String checkIn, String checkOut,boolean cancelled) {
        this.dni = dni;
        this.idRoom = idRoom;
        this.reservationNumber = reservationNumberNext;
        reservationNumberNext++;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.cancelled = cancelled;
    }

    ///Getters and Setters
    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public int getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(int idRoom) {
        this.idRoom = idRoom;
    }

    public int getReservationNumber() {
        return reservationNumber;
    }

    public void setReservationNumber(int reservationNumber) {
        this.reservationNumber = reservationNumber;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(String checkIn) {
        this.checkIn = checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(String checkOut) {
        this.checkOut = checkOut;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }


    ///Equal y hascode

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return idRoom == that.idRoom && reservationNumber == that.reservationNumber && dni.equals(that.dni);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dni, idRoom, reservationNumber);
    }

    ///Metodos
    @Override
    public String toString(){
        return "Reserva: " +  "\n Dni de Cliente " +this.dni +  "Nro de Habitación: " +  this.idRoom +  "Nro de Reserva: " + this.reservationNumber +
                "Hora de entrada: " +this.checkIn +  "Hora de Salida: " + this.checkOut;
    }

    public static LocalDate stringToLocalDate(String date) {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(date, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return null;
        }
        return localDate;
    }

}
