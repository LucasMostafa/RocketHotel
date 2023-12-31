package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CollectionUser {
    ///Atributos
     List<User> listUser = new ArrayList<>();

    ///Metodos
    public User loginUser(String userName, String password){
        User user= new User();
        user= null;

        for (User x: listUser) {
            if(userName.equals(x.getUserName()) || userName.equals(x.getEmailAddress())){
                if(password.equals(x.getPassword())) {
                    user= x;
                }
            }
        }

        return user;
    }

    public void addUser(User user){
        listUser.add(user);
    }

    public void removeUser(User user){
        listUser.remove(user);
    }

    public User searchUser(String dni){
        User user= null;
        for (User x: listUser) {
            if (x.getDni().equals(dni)){
                user= x;
            }
        }
        return user;
    }

    public Client searchClient(String dni){
        Client client= null;
        for (User x: listUser) {
            if(x instanceof Client) {
                if (x.getDni().equals(dni)) {
                    client= (Client) x;
                }
            }
        }
        return client;
    }

    public Administrator searchAdministrator(String dni){
        Administrator administrator= null;
        for (User x: listUser) {
            if(x instanceof Administrator) {
                if (x.getDni().equals(dni)) {
                    administrator= (Administrator) x;
                }
            }
        }
        return administrator;
    }

    public Receptionist searchReceptionist(String dni){
        Receptionist receptionist= null;
        for (User x: listUser) {
            if(x instanceof Receptionist) {
                if (x.getDni().equals(dni)) {
                    receptionist = (Receptionist) x;
                }
            }
        }
        return receptionist;
    }

    public User searchUserAsMail(String email){
        User user= null;
        for (User x: listUser) {
            if (x.getEmailAddress().equals(email)){
                user= x;
            }
        }
        return user;
    }

    public User searchUserAsUserName(String userName){
        User user= null;
        for (User x: listUser) {
            if (x.getUserName().equals(userName)){
                user= x;
            }
        }
        return user;
    }

    public void userModify(String dni){
        Scanner scanner = new Scanner(System.in);
        int opcion= 0;

        for (User x: listUser) {
            if(x.getDni().equals(dni)) {
                do {
                    System.out.println("Ingrese la opcion que desea modificar: \n");
                    System.out.println("1)Nombre.\n2)Apellido.\n3)DNI.\n4)Genero.\n5)Pais de origen.\n6)Direccion.\n7)Nombre de usuario.\n8)Constraseña.\n9)E-mail.\n\n0)Salir.\n\nOpcion: ");
                    opcion = scanner.nextInt();

                    switch (opcion) {
                        case 1 -> {
                            System.out.println("Nombre: ");
                            x.setName(scanner.next());
                        }
                        case 2 -> {
                            System.out.println("Apellido: ");
                            x.setSurname(scanner.next());
                        }
                        case 3 -> {
                            System.out.println("DNI: ");
                            x.setDni(scanner.next());
                        }
                        case 4 -> {
                            System.out.println("Género: ");
                            x.setGender(scanner.next());
                        }
                        case 5 -> {
                            System.out.println("País de Origen: ");
                            x.setOrigin(scanner.next());
                        }
                        case 6 -> {
                            System.out.println("Dirección: ");
                            x.setAddress(scanner.next());
                        }
                        case 7 -> {
                            System.out.println("Nombre de usuario: ");
                            x.setUserName(scanner.next());
                        }
                        case 8 -> {
                            System.out.println("Contraseña: ");
                            x.setPassword(scanner.next());
                        }
                        case 9 -> {
                            System.out.println("E-Mail: ");
                            x.setEmailAddress(scanner.next());
                        }
                        case 0 -> {
                            System.out.println("\n");
                        }
                        default -> System.out.println("\nOpcion incorrecta.\n");
                    }
                }
                while (opcion != 0);
            }
        }
    }

    public void showAll(){
        for (User x:listUser) {
            System.out.println(x.toString());
        }
    }

    public void showListClient(){
        for (User x:listUser) {
            if(x instanceof Client){
                System.out.println(x.toString());
            }
        }
    }

    public void showListAdministrator(){
        for (User x:listUser) {
            if(x instanceof Administrator){
                System.out.println(x.toString());
            }
        }
    }

    public void showListRecepcionist(){
        for (User x:listUser) {
            if(x instanceof Receptionist){
                System.out.println(x.toString());
            }
        }
    }

    public  void writeAdmin(File fileAdmin) throws  IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Administrator> listAdministrator = new ArrayList<>();

        for (User x : listUser) {
            if (x instanceof Administrator) {
                listAdministrator.add((Administrator)x);
            }
        }
        mapper.writeValue(fileAdmin, listAdministrator);


    }

    public void readAdmin (File fileAdmin) throws IOException {
        ObjectMapper mapper = new ObjectMapper();


        if (fileAdmin.length() > 0) {
            List<Administrator> listAdministrator = mapper.readValue(fileAdmin, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Administrator.class));
            listUser.addAll(listAdministrator);
        }

    }

    public void writeRecep (File fileRecep) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Receptionist> listReceptionst = new ArrayList<>();


        for (User x : listUser) {
            if (x instanceof Receptionist) {
                listReceptionst.add((Receptionist) x);
            }
        }

        mapper.writeValue(fileRecep, listReceptionst);


    }

    public void readRecep (File fileRecep) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Receptionist> listReceptionist;

        if (fileRecep.length() > 0) {
            listReceptionist = mapper.readValue(fileRecep, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Receptionist.class));
            listUser.addAll(listReceptionist);
        }

    }


    public void writeClient (File fileClient) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Client> listClient = new ArrayList<>();


        for (User x : listUser) {
            if (x instanceof Client) {
                listClient.add((Client) x);
            }
        }

        mapper.writeValue(fileClient, listClient);


    }

    public void readClient (File fileClient) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Client> listClient;

        if (fileClient.length() > 0) {
            listClient = mapper.readValue(fileClient, mapper.getTypeFactory().constructCollectionType(ArrayList.class, Client.class));
            listUser.addAll(listClient);
        }

    }
}
