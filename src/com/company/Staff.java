package com.company;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.time.temporal.ChronoUnit.DAYS;

public class Staff extends User {
    ///Atributos
    private boolean permissionUser;
    private double salary;
    private int antiquity;

    ///Constructores
    public Staff(){
        super();
    }

    public Staff(String name, String surname, String dni, String gender, String origin, String address, String userName, String password, String emailAddress, boolean permissionUser, double salary, int antiquity) {
        super(name, surname, dni, gender, origin, address, userName, password, emailAddress);
        this.permissionUser = permissionUser;
        this.salary = salary;
        this.antiquity = antiquity;
    }

    ///Getter and Setter
    public boolean isPermissionUser() {
        return permissionUser;
    }

    public void setPermissionUser(boolean permissionUser) {
        this.permissionUser = permissionUser;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAntiquity() {
        return antiquity;
    }

    public void setAntiquity(int antiquity) {
        this.antiquity = antiquity;
    }

    ///Metodos
    @Override
    public void register(){
    }

    public int calculatedAntiquity(String day) {
        LocalDate now = LocalDate.now();

        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        LocalDate localDate;
        try {
            localDate = LocalDate.parse(day, dateTimeFormatter);
        } catch (DateTimeParseException e) {
            return 0;
        }
        return (int)DAYS.between(localDate, now) / 365;
    }

    public void calculatedSalary(int horas){
    }
}

