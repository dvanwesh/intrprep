package leetcode.square;

import datastructures.util.Pair;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Queue;

public class ParkingLot {
    Map<ParkingSpot, Car> occupiedSpots;
    Queue<ParkingSpot> availableSpots;
    long ticketId = 1;
    public static void main(String[] args) throws NoSuchFieldException {
        ParkingLot lot = new ParkingLot(5);
        Ticket ticket = lot.parkCar(new Car("12abc", "Tesla", "Model_y"));
        System.out.println(ticket.printTicket());
        System.out.println(ticket.getPrice());
        System.out.println(lot.unParkCar(ticket));
    }

    public ParkingLot(int n) {
        occupiedSpots = new HashMap<>();
        availableSpots = new LinkedList<>();
        for(int i = 0 ; i < n; i++){
            for(int j = 0; j < n; j++){
                for(int k = 0; k < n; k++){
                    availableSpots.add(new ParkingSpot(i, j, k));
                }
            }
        }
    }

    public Ticket parkCar(Car car) throws NoSuchFieldException {
        if(availableSpots.isEmpty()){
            throw new NoSuchFieldException("Parking spot is not available");
        }
        ParkingSpot spot = availableSpots.poll();
        occupiedSpots.put(spot, car);
        return new Ticket(ticketId++, 10, spot);
    }

    public Car unParkCar(Ticket ticket){
        if(!occupiedSpots.containsKey(ticket.spot)){
            throw  new NoSuchElementException("invalid Ticket");
        }
        ticket.invalidate();
        return occupiedSpots.remove(ticket.spot);
    }

    static class ParkingSpot{
        String index;
        boolean occupied;
        public ParkingSpot(int i, int j, int k) {
            this.index = i+"-"+j+"-"+k;
        }

        @Override
        public String toString() {
            return index;
        }
    }

    static class Car{
        String license;
        String brand;
        String model;

        public Car(String license, String brand, String model) {
            this.license = license;
            this.brand = brand;
            this.model = model;
        }

        @Override
        public String toString() {
            return "Car{" +
                "license='" + license + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                '}';
        }
    }

    static class Ticket{
        long id;
        int pricePerHour;
        boolean active;
        ParkingSpot spot;
        ZonedDateTime startDate;
        ZonedDateTime endDate;

        public Ticket(long id, int pricePerHour, ParkingSpot spot) {
            this.id = id;
            active = true;
            this.spot = spot;
            this.pricePerHour = pricePerHour;
            startDate = ZonedDateTime.now();
        }

        public int getPrice(){
            return Math.max((int)Duration.between(ZonedDateTime.now(), startDate).toHours(), 1)
                * pricePerHour;
        }

        public void invalidate(){
            endDate = ZonedDateTime.now();
            active = false;
        }

        public String printTicket() {
            return "Ticket{" +
                "id=" + id +
                ", spot=" + spot +
                ", startDate=" + startDate +
                '}';
        }
    }
}
