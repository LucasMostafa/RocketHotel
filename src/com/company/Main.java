package com.company;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;



public class Main {

    public static void main(String[] args) throws IOException {
        ///Menu
        CollectionUser listUser = new CollectionUser();
        CollectionRoom listRoom= new CollectionRoom();
        CollectionReservation listReservation= new CollectionReservation();
        CollectionConsumption listConsumption= new CollectionConsumption();

        File fileAdmin = new File("Admin_rockethotel.json");
        File fileReceptionist = new File("Receptionist_rockethotel.json");
        File fileClient = new File("Client_rockethotel.json");
        File fileRoom = new File("room_rockethotel.json");
        File fileReservation = new File("reservation_rockethotel.json");
        File fileConsumption = new File("consumption_rockethotel.json");


        File fileAdministrativo = new File("backup_rockethotel.json");

        if(!fileAdmin.exists()) {
            fileAdmin.createNewFile();
            Administrator administratorPrincipal = new Administrator("admin", "admin", "12345", "indefinido", "argentina", "Rocket Hotel", "admin", "admin", "admin@gmail.com", true, 0, 0);
            listUser.addUser(administratorPrincipal);
            listUser.write(fileAdmin, fileReceptionist, fileClient);
        }
        else
            listUser.read(fileAdmin,fileReceptionist,fileClient);

        if(!fileReceptionist.exists()) {
            fileReceptionist.createNewFile();
            listUser.write(fileAdmin,fileReceptionist,fileClient);
        }
        else
            listUser.read(fileAdmin,fileReceptionist,fileClient);

        if(!fileClient.exists()) {
            fileClient.createNewFile();
            listUser.write(fileAdmin,fileReceptionist,fileClient);
        }
        else
            listUser.read(fileAdmin,fileReceptionist,fileClient);

        if(!fileRoom.exists()) {
            fileRoom.createNewFile();
            listRoom.loadRooms();
            listRoom.write(fileRoom);
        }
        else
            listRoom.read(fileRoom);

        if(!fileReservation.exists()) {
            fileReservation.createNewFile();
        }
        else
            listReservation.read(fileReservation);

        if(!fileConsumption.exists()) {
            fileConsumption.createNewFile();
            listConsumption.loadConsumption();
            listConsumption.write(fileConsumption);
        }
        else
            listConsumption.read(fileConsumption);

        Scanner scanner = new Scanner(System.in);

        User user= new User();

        char character = 0;
        String textInput;
        int option= 0;
        boolean quit = false;
        boolean quitProgram= false;

        do {
            try {
                option= 0;
                quit = false;
                System.out.println("ROCKET HOTEL: \n");
                System.out.println("1)Login.\n0)Salir.\n\nOpcion: ");
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1 -> {
                        System.out.println("LOGIN.");
                        do {
                            quit= false;

                            System.out.println("\nNombre de usuario o E-mail: ");
                            String userName = scanner.nextLine();
                            System.out.println("\nPassword: ");
                            String password = scanner.nextLine();
                            user = listUser.loginUser(userName, password);
                            if (user != null) {
                                if (user instanceof Client) {
                                    System.out.println("Usuario " + user.getUserName());
                                    System.out.println("""

                                            1)Ver habitaciones.
                                            2)Realizar reserva.
                                            3)Reserva actual.
                                            4)Listados de reservas.
                                            5)Cancelar reserva.
                                            6)Ver perfil.
                                            7)Modificar perfil.

                                            0)Salir.
                                            """);
                                    option = scanner.nextInt();
                                    do {


                                        try {
                                            switch (option) {
                                                case 1 -> {
                                                    System.out.println("Habitaciones:\n");
                                                    listRoom.showListRoom();
                                                }
                                                case 2 -> {
                                                    do {
                                                        quit= false;
                                                        System.out.println("Ingrese el numero de personas(capacidad minima 1 / capacidad maxima 4): ");
                                                        option = scanner.nextInt();
                                                        if (option > 0 && option < 5) {
                                                            do {
                                                                System.out.println("\nIngrese la fecha de ingreso: ");
                                                                String entry = scanner.nextLine();
                                                                System.out.println("\nIngrese la fecha de egreso: ");
                                                                String exit = scanner.nextLine();
                                                                List<Room> roomsAvailable = listReservation.searchRoomsForReservation(listRoom.searchForCapacity(option), LocalDate.parse(entry), LocalDate.parse(exit));
                                                                for (Room x : roomsAvailable) {
                                                                    System.out.println(x.toString());
                                                                }
                                                                do {
                                                                    System.out.println("Ingrese la habitacion que desea reservar: ");
                                                                    option = scanner.nextInt();
                                                                    for (Room x : roomsAvailable) {
                                                                        if (x.getIdRoom() == option) {
                                                                            System.out.println("\nReserva realizada");
                                                                            Reservation reservationDone= new Reservation(user.getDni(), option, LocalDate.parse(entry), LocalDate.parse(exit), false);
                                                                            listReservation.addReservation(reservationDone);
                                                                            listReservation.write(fileReservation);
                                                                            quit = true;
                                                                        } else {
                                                                            System.out.println("\nLa habitacion seleccionada no se encuentra disponible.");
                                                                        }
                                                                    }
                                                                } while (quit);
                                                            }
                                                            while (!quit);
                                                        } else {
                                                            System.out.println("La opcion ingresada es incorrecta. Presione 'n' para salir o cualquier otra tecla para volver a ingresar la capacidad.\n");
                                                            character = scanner.next().charAt(0);
                                                        }
                                                    }
                                                    while (!quit && character != 'n');

                                                }
                                                case 3 -> {
                                                    System.out.println("Reserva actual:\n");
                                                    List<Reservation> reservations= listReservation.searchReservationCurrent(user.getDni());
                                                    for (Reservation x:reservations) {
                                                        System.out.println(x.toString());
                                                    }
                                                }
                                                case 4 -> {
                                                    System.out.println("Historial de reservas:\n");
                                                    List<Reservation> reservationsClient = new ArrayList<>();
                                                    reservationsClient = listReservation.searchReservationHistory(user.getDni());
                                                    reservationsClient.forEach(System.out::println);
                                                }
                                                case 5 -> {
                                                    System.out.println("Reserva/s actual/es: ");
                                                    character = 's';
                                                    List<Reservation> reservationsClient = new ArrayList<>();
                                                    reservationsClient = listReservation.searchReservationCurrent(user.getDni());
                                                    reservationsClient.forEach(System.out::println);
                                                    do {
                                                        option = scanner.nextInt();
                                                        Reservation reservation = listReservation.searchReservation(option);
                                                        if (reservation != null) {
                                                            listReservation.cancelledReservartion(option);
                                                            System.out.println("\nReserva cancelada.");
                                                            listReservation.write(fileReservation);
                                                        } else {
                                                            System.out.println("El numero de reserva ingresado es incorrecto. Presione 's' para volver a intentarlo o cualquier otra tecla para salir.\n");
                                                            character = scanner.next().charAt(0);
                                                        }
                                                    } while (character == 's');
                                                }
                                                case 6 -> {
                                                    System.out.println("Perfil del cliente: \n");
                                                    System.out.println(user.toString());
                                                }
                                                case 7 -> {
                                                    System.out.println("Modificar datos: ");
                                                    listUser.userModify(user.getDni());
                                                    listUser.write(fileAdmin, fileReceptionist, fileClient);
                                                }
                                                case 0 -> {
                                                    System.out.println("\n");
                                                }
                                                default -> System.out.println("\nOpcion incorrecta.\n");
                                            }
                                        } catch (InputMismatchException e) {
                                            System.out.println("\nSe debe ingresar un numero.\n");
                                            scanner.next();
                                        }
                                    }while (option==0);
                                } else if (user instanceof Staff) {
                                    System.out.println("Miembro de staff: " + user.getUserName());
                                    if(user instanceof Administrator) {
                                        System.out.println("""

                                                1)Ver habitaciones.
                                                2)Ver reservas vigentes.
                                                3)Buscar reserva especifica.
                                                4)Buscar un cliente.
                                                5)Buscar un administrador.
                                                6)Buscar un recepcionista.
                                                7)Buscar una habitacion.
                                                8)Comidas y bebidas.
                                                9)Listados.                                       
                                                10)Generar reserva.
                                                11)Registrar nuevos usuarios.
                                                12)Ver perfil.
                                                13)Modificar perfil.
                                                14)Back up.

                                                0)Salir.
                                                """);
                                    }
                                    else {
                                        System.out.println("""

                                                1)Ver habitaciones.
                                                2)Ver reservas vigentes.
                                                3)Buscar reserva especifica.
                                                4)Buscar un cliente.
                                                5)Buscar un administrador.
                                                6)Buscar un recepcionista.
                                                7)Buscar una habitacion.
                                                8)Comidas y bebidas.
                                                9)Listados.                                       
                                                10)Ver perfil.
                                                11)Modificar perfil.

                                                0)Salir.
                                                """);
                                    }
                                    option = scanner.nextInt();
                                    try{
                                        switch (option){
                                            case 1->{
                                                System.out.println("Habitaciones:\n");
                                                listRoom.showListRoom();
                                            }
                                            case 2->{
                                                System.out.println("Reservas vigentes:\n");
                                                listReservation.showListReservationCurrent();
                                            }
                                            case 3->{
                                                System.out.println("Buscar reserva:\n1)Por DNI.\n2)Por numero de reserva.\n0)Salir.\n");
                                                option= scanner.nextInt();
                                                try {
                                                    switch (option) {
                                                        case 1 -> {
                                                            do {
                                                                System.out.println("Ingrese el numero de DNI: ");
                                                                textInput = scanner.nextLine();
                                                                Reservation reservation = listReservation.searchReservationAsDni(textInput);
                                                                if (reservation == null) {
                                                                    System.out.println("No existe ninguna reserva asignada al DNI ingresado. Presione 's' para ingresar otro DNI o cualquier otra tecla para salir.\n");
                                                                    character = scanner.next().charAt(0);
                                                                } else {
                                                                    System.out.println(reservation.toString());
                                                                    character = 'n';
                                                                }
                                                            } while (character == 's');
                                                        }
                                                        case 2 -> {
                                                            do {
                                                                System.out.println("Ingrese el numero de reserva: ");
                                                                option = scanner.nextInt();
                                                                Reservation reservation = listReservation.searchReservation(option);
                                                                if (reservation == null) {
                                                                    System.out.println("No existe ninguna reserva con bajo ese numero de identificacion. Presione 's' para ingresar otro numero de reserva o cualquier otra tecla para salir.\n");
                                                                    character = scanner.next().charAt(0);
                                                                } else {
                                                                    System.out.println(reservation.toString());
                                                                    character = 'n';
                                                                }
                                                            } while (character == 's');
                                                        }
                                                        case 0 -> {
                                                            System.out.println("\n");
                                                        }
                                                        default -> System.out.println("\nOpcion incorrecta.\n");
                                                    }
                                                }catch (InputMismatchException e) {
                                                    System.out.println("\nSe debe ingresar un numero.\n");
                                                    scanner.next();
                                                }
                                            }
                                            case 4->{
                                                System.out.println("Busqueda de clientes.\n");
                                                do {
                                                    System.out.println("Ingrese el DNI del cliente que desea buscar: ");
                                                    textInput = scanner.nextLine();
                                                    Client client= listUser.searchClient(textInput);
                                                    if (client == null) {
                                                        System.out.println("No existe ningun cliente con el DNI indicado. Presione 's' para ingresar otro DNI o cualquier otra tecla para salir.\n");
                                                        character = scanner.next().charAt(0);
                                                    } else {
                                                        System.out.println(client.toString());
                                                        System.out.println("\nSi desea modificar al cliente presione 'm', de lo contrario cualquier otra tecla para continuar.\n");
                                                        character= scanner.next().charAt(0);
                                                        if(character=='m'){
                                                            listUser.userModify(textInput);
                                                            listUser.write(fileAdmin, fileReceptionist, fileClient);
                                                        }
                                                        character = 'n';
                                                    }
                                                } while (character == 's');
                                            }
                                            case 5->{
                                                System.out.println("Busqueda de administrador.\n");
                                                do {
                                                    System.out.println("Ingrese el DNI del administrador que desea buscar: ");
                                                    textInput = scanner.nextLine();
                                                    Administrator administrator= listUser.searchAdministrator(textInput);
                                                    if (administrator == null) {
                                                        System.out.println("No existe ningun administrador con el DNI indicado. Presione 's' para ingresar otro DNI o cualquier otra tecla para salir.\n");
                                                        character = scanner.next().charAt(0);
                                                    } else {
                                                        System.out.println(administrator.toString());
                                                        System.out.println("\nSi desea modificar al administrador presione 'm', de lo contrario cualquier otra tecla para continuar.\n");
                                                        character= scanner.next().charAt(0);
                                                        if(character=='m'){
                                                            listUser.userModify(textInput);
                                                            listUser.write(fileAdmin, fileReceptionist, fileClient);
                                                        }
                                                        character = 'n';
                                                    }
                                                } while (character == 's');
                                            }
                                            case 6->{
                                                System.out.println("Busqueda de recepcionista.\n");
                                                do {
                                                    System.out.println("Ingrese el DNI del recepcionista que desea buscar: ");
                                                    textInput = scanner.nextLine();
                                                    Receptionist receptionist= listUser.searchReceptionist(textInput);
                                                    if (receptionist == null) {
                                                        System.out.println("No existe ningun recepcionista con el DNI indicado. Presione 's' para ingresar otro DNI o cualquier otra tecla para salir.\n");
                                                        character = scanner.next().charAt(0);
                                                    } else {
                                                        System.out.println(receptionist.toString());
                                                        System.out.println("\nSi desea modificar al recepcionista presione 'm', de lo contrario cualquier otra tecla para continuar.\n");
                                                        character= scanner.next().charAt(0);
                                                        if(character=='m'){
                                                            listUser.userModify(textInput);
                                                            listUser.write(fileAdmin, fileReceptionist, fileClient);
                                                        }
                                                        character = 'n';
                                                    }
                                                } while (character == 's');
                                            }
                                            case 7->{
                                                System.out.println("Busqueda de habitacion.\n");
                                                do {
                                                    System.out.println("Ingrese el numero de identificacion de la habitacion que desea buscar: ");
                                                    option = scanner.nextInt();
                                                    Room room= listRoom.searchRoom(option);
                                                    if (room == null) {
                                                        System.out.println("No existe ninguna habitacion con ese numero de identificacion. Presione 's' para ingresar otro DNI o cualquier otra tecla para salir.\n");
                                                        character = scanner.next().charAt(0);
                                                    } else {
                                                        System.out.println(room.toString());
                                                        System.out.println("\nSi desea modificar algun item de la habitacion presione 'm', de lo contrario cualquier otra tecla para continuar.\n");
                                                        character= scanner.next().charAt(0);
                                                        if(character=='m'){
                                                            listRoom.roomModify(option);
                                                            listRoom.write(fileRoom);
                                                        }
                                                        character = 'n';
                                                    }
                                                } while (character == 's');
                                            }
                                            case 8->{
                                                System.out.println("Comidas y bebidas.\n");
                                                listConsumption.showListConsumition();
                                                System.out.println("\nSi desea modificar alguna bebida o comida presione 'm', de lo contrario cualquier otra tecla para salir.\n");
                                                character= scanner.next().charAt(0);
                                                if (character=='m') {
                                                    do {
                                                        System.out.println("Ingrese el identificador que quiera modificar: ");
                                                        option= scanner.nextInt();
                                                        Consumption consumption= listConsumption.searchConsumption(option);
                                                        if(consumption==null) {
                                                            System.out.println("No existe ninguna bebida o comida con ese numero de identificacion. Presione 's' para ingresar otro identificador o cualquier otra tecla para salir.\n");
                                                            character = scanner.next().charAt(0);
                                                        }
                                                        else {
                                                            System.out.println(consumption.toString());
                                                            System.out.println("Ingrese el nuevo precio del producto: ");
                                                            double price = scanner.nextInt();
                                                            listConsumption.modifyPriceConsumption(option, price);
                                                            listConsumption.write(fileConsumption);
                                                            character= 'n';
                                                        }
                                                    }
                                                    while (character=='s');
                                                }
                                            }
                                            case 9->{
                                                do {
                                                    System.out.println("Listados:\n");
                                                    System.out.println("""

                                                            1)Listado de administradores.
                                                            2)Listado de recepcionistas.
                                                            3)Listado de clientes.
                                                            4)Listado de habitaciones.
                                                            5)Listado de reservas.

                                                            0)Salir.
                                                            """);
                                                    option = scanner.nextInt();
                                                    try {
                                                        switch (option) {
                                                            case 1 -> {
                                                                System.out.println("Listado de administradores:\n");
                                                                listUser.showListAdministrator();
                                                            }
                                                            case 2 -> {
                                                                System.out.println("Listado de recepcionistas:\n");
                                                                listUser.showListRecepcionist();
                                                            }
                                                            case 3 -> {
                                                                System.out.println("Listado de clientes:\n");
                                                                listUser.showListClient();
                                                            }
                                                            case 4 -> {
                                                                System.out.println("Listado de habitaciones:\n");
                                                                listRoom.showListRoom();
                                                            }
                                                            case 5 -> {
                                                                System.out.println("Listado de reservas:\n");
                                                                listReservation.showListReservationCurrent();
                                                            }
                                                            case 0 -> {
                                                                System.out.println("\n");
                                                            }
                                                            default -> System.out.println("\nOpcion incorrecta.\n");
                                                        }
                                                    } catch (InputMismatchException e) {
                                                        System.out.println("\nSe debe ingresar un numero.\n");
                                                        scanner.next();
                                                    }
                                                }while (option!=0);

                                            }
                                            case 10 ->{
                                                if(user instanceof Administrator){
                                                    System.out.println("Generar reserva.\n");
                                                    do {
                                                        quit= false;
                                                        System.out.println("Ingrese el numero de personas(capacidad minima 1 / capacidad maxima 4): ");
                                                        option = scanner.nextInt();
                                                        if (option > 0 && option < 5) {
                                                            do {
                                                                System.out.println("\nIngrese la fecha de ingreso: ");
                                                                String entry = scanner.nextLine();
                                                                System.out.println("\nIngrese la fecha de egreso: ");
                                                                String exit = scanner.nextLine();
                                                                List<Room> roomsAvailable = listReservation.searchRoomsForReservation(listRoom.searchForCapacity(option), LocalDate.parse(entry), LocalDate.parse(exit));
                                                                for (Room x : roomsAvailable) {
                                                                    System.out.println(x.toString());
                                                                }
                                                                do {
                                                                    System.out.println("Ingrese la habitacion que desea reservar: ");
                                                                    option = scanner.nextInt();
                                                                    for (Room x : roomsAvailable) {
                                                                        if (x.getIdRoom() == option) {
                                                                            System.out.println("\nIngrese el dni del cliente para asignar la reserva: ");
                                                                            String dniClient= scanner.nextLine();
                                                                            Reservation reservationDone= new Reservation(dniClient, option, LocalDate.parse(entry), LocalDate.parse(exit), false);
                                                                            System.out.println("\nReserva realizada");
                                                                            listReservation.addReservation(reservationDone);
                                                                            listReservation.write(fileReservation);
                                                                            quit = true;
                                                                        } else {
                                                                            System.out.println("\nLa habitacion seleccionada no se encuentra disponible.");
                                                                        }
                                                                    }
                                                                } while (quit);
                                                            }
                                                            while (!quit);
                                                        } else {
                                                            System.out.println("La opcion ingresada es incorrecta. Presione 'n' para salir o cualquier otra tecla para volver a ingresar la capacidad.\n");
                                                            character = scanner.next().charAt(0);
                                                        }
                                                    }
                                                    while (!quit && character != 'n');
                                                }
                                            }
                                            case 11 -> {
                                                if (user instanceof Administrator) {
                                                    System.out.println("REGISTRAR USUARIO.\n");
                                                    character = 's';

                                                    do {
                                                        try {
                                                            System.out.println("\n1)Cliente.\n2)Recepcionista.\n3)Administrador.\n\n0)Salir.\n");
                                                            option= scanner.nextInt();
                                                            User userAux;
                                                            User userAux2;
                                                            User userAux3;
                                                            quit = false;
                                                            switch (option) {
                                                                case 1 ->{
                                                                    do {
                                                                        Client clientNew = new Client();
                                                                        clientNew.register();
                                                                        userAux= listUser.searchUser(clientNew.getDni());
                                                                        userAux2= listUser.searchUserAsUserName(clientNew.getUserName());
                                                                        userAux3= listUser.searchUserAsMail(clientNew.getEmailAddress());
                                                                        if(userAux!=null || userAux2!=null || userAux3!=null) {
                                                                            if(userAux!=null){
                                                                            System.out.println("\nEl dni ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux2 != null) {
                                                                                System.out.println("\nEl nombre de usuario ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux3 != null) {
                                                                                System.out.println("\nEl e-mail ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                        }
                                                                        else {
                                                                            System.out.println("\nCliente registrado correctamente.\n");
                                                                            quit= true;
                                                                        }
                                                                    }while (!quit);
                                                                }
                                                                case 2 ->{
                                                                    do {
                                                                        Receptionist receptionistNew = new Receptionist();
                                                                        receptionistNew.register();
                                                                        userAux= listUser.searchUser(receptionistNew.getDni());
                                                                        userAux2= listUser.searchUserAsUserName(receptionistNew.getUserName());
                                                                        userAux3= listUser.searchUserAsMail(receptionistNew.getEmailAddress());
                                                                        if(userAux!=null || userAux2!=null || userAux3!=null) {
                                                                            if(userAux!=null){
                                                                                System.out.println("\nEl dni ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux2 != null) {
                                                                                System.out.println("\nEl nombre de usuario ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux3 != null) {
                                                                                System.out.println("\nEl e-mail ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                        }
                                                                        else {
                                                                            System.out.println("\nRecepcionista registrado correctamente.\n");
                                                                            quit= true;
                                                                        }
                                                                    }while (!quit);
                                                                }
                                                                case 3 ->{
                                                                    do {
                                                                        Administrator administratorNew = new Administrator();
                                                                        administratorNew.register();
                                                                        userAux= listUser.searchUser(administratorNew.getDni());
                                                                        userAux2= listUser.searchUserAsUserName(administratorNew.getUserName());
                                                                        userAux3= listUser.searchUserAsMail(administratorNew.getEmailAddress());
                                                                        if(userAux!=null || userAux2!=null || userAux3!=null) {
                                                                            if(userAux!=null){
                                                                                System.out.println("\nEl dni ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux2 != null) {
                                                                                System.out.println("\nEl nombre de usuario ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                            if (userAux3 != null) {
                                                                                System.out.println("\nEl e-mail ya se encuentra registrado en la base de datos.\n");
                                                                            }
                                                                        }
                                                                        else {
                                                                            System.out.println("\nAdministrador registrado correctamente.\n");
                                                                            quit= true;
                                                                        }
                                                                    }while (quit);
                                                                }
                                                                case 0 ->{
                                                                    System.out.println("\n");
                                                                }
                                                                default -> System.out.println("\nOpcion incorrecta.\n");
                                                            }

                                                        } catch (InputMismatchException e) {
                                                            System.out.println("\nSe debe ingresar un numero.\n");
                                                            scanner.next();
                                                        }
                                                    }
                                                    while (option!=0);
                                                }
                                            }
                                            case 12->{
                                                System.out.println("Perfil del administrador.\n");
                                                System.out.println(user.toString());
                                            }
                                            case 13->{
                                                System.out.println("Modificar datos: ");
                                                listUser.userModify(user.getDni());
                                                listUser.write(fileAdmin, fileReceptionist, fileClient);
                                            }
                                            case 14->{
                                                System.out.println("Back up");



                                            }
                                            case 0->{
                                                quit= true;
                                            }
                                            default -> System.out.println("\nOpcion incorrecta.\n");
                                        }
                                    }
                                    catch (InputMismatchException e) {
                                        System.out.println("\nSe debe ingresar un numero.\n");
                                        scanner.next();
                                    }
                                }
                            } else {
                                System.out.println("\nUsuario / E-mail o contraseña incorrectos. Presione 'n' para salir o cualquier otra tecla para continuar.\n");
                                character = scanner.next().charAt(0);
                                if (character == 'n') {
                                    quit = true;
                                }
                            }

                        }
                        while (!quit);
                    }
                    case 0 -> {
                        quitProgram= true;
                    }
                    default -> System.out.println("\nOpcion incorrecta.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("\nSe debe ingresar un numero.\n");
                scanner.next();
            }
        }
        while (!quitProgram);
    }


}

