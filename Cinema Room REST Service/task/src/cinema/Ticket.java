package cinema;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.Objects;

public class Ticket {
    private int price;
    private Seat seat;

    public Ticket() {
    }

    public Ticket(Seat seat) {
        this.seat = seat;
        setPrice(seat);
    }

    public Ticket(int price, Seat seat) {
        this.price = price;
        this.seat = seat;
    }

    public void setPrice(Seat seat) {
        if (seat.getRow() <= 4) {
            this.price = 10;
        } else {
            this.price =  8;
        }
    }
    @JsonProperty("price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @JsonUnwrapped
    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return price == ticket.price && Objects.equals(seat, ticket.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, seat);
    }
}
