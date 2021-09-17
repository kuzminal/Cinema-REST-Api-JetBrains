package cinema;

import java.util.Objects;
import java.util.UUID;

public class Purchase {
    private UUID token;
    private Ticket ticket;

    public Purchase() {
    }

    public Purchase(Ticket ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID();
    }

    public Purchase(UUID token, Ticket ticket) {
        this.token = token;
        this.ticket = ticket;
    }

    public UUID getToken() {
        return token;
    }

    public void setToken(UUID token) {
        this.token = token;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(token, purchase.token) && Objects.equals(ticket, purchase.ticket);
    }

    @Override
    public int hashCode() {
        return Objects.hash(token, ticket);
    }
}
