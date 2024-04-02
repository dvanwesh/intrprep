package leetcode.square;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.*;

public class ParkingLot {
    Map<Car, ParkingSpot> carToParkingSpotMap;
    Map<Long, Car> ticketToCarMap;
    Queue<ParkingSpot> availableSpots;
    long ticketId = 1;
    public static void main(String[] args) throws NoSuchFieldException {
        ParkingLot lot = new ParkingLot(5);
        System.out.println("available spots:"+lot.availableSpots.size());
        Ticket ticket = lot.parkCar(new Car("12abc", "Tesla", "Model_y"));
        System.out.println("available spots:"+lot.availableSpots.size());
        System.out.println(ticket.printTicket());
        System.out.println(lot.unParkCar(ticket));
        System.out.println(ticket.getPrice());
        System.out.println("available spots:"+lot.availableSpots.size());
    }

    public ParkingLot(int n) {
        carToParkingSpotMap = new HashMap<>();
        availableSpots = new LinkedList<>();
        ticketToCarMap = new HashMap<>();
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
        carToParkingSpotMap.put(car, spot);
        ticketToCarMap.put(ticketId, car);
        return new Ticket(ticketId++, 10);
    }

    public Car unParkCar(Ticket ticket){
        Car car = ticketToCarMap.get(ticket.id);
        if(car == null){
            throw  new NoSuchElementException("invalid Ticket");
        }
        ticket.invalidate();
        ticketToCarMap.remove(ticket.id);
        ParkingSpot spot = carToParkingSpotMap.remove(car);
        availableSpots.offer(spot);
        return car;
    }

    static class ParkingSpot {
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
        ZonedDateTime startDate;
        ZonedDateTime endDate;

        public Ticket(long id, int pricePerHour) {
            this.id = id;
            active = true;
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
                ", startDate=" + startDate +
                '}';
        }
    }
}
