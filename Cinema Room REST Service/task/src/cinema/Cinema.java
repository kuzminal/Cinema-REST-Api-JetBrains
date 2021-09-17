package cinema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.*;

public class Cinema {
    private int totalRows;
    private int totalColumns;
    private final Set<Ticket> availableSeats = new HashSet<>();
    private final Map<UUID, Ticket> purchasedTicket = new HashMap<>();

    public Cinema() {
    }

    public Cinema(int totalRows, int totalColumns) {
        this.totalRows = totalRows;
        this.totalColumns = totalColumns;
        for (int i = 1; i <= totalRows; i++) {
            for (int j = 1; j <= totalColumns; j++) {
                availableSeats.add(new Ticket(new Seat(i, j)));
            }
        }
    }

    @JsonProperty("total_rows")
    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    @JsonProperty("total_columns")
    public int getTotalColumns() {
        return totalColumns;
    }

    public void setTotalColumns(int totalColumns) {
        this.totalColumns = totalColumns;
    }

    @JsonProperty("available_seats")
    public Set<Ticket> getAvailableSeats() {
        return availableSeats;
    }

    public boolean removeFromAvailable(Ticket ticket) {
        if (availableSeats.contains(ticket)) {
            availableSeats.remove(ticket);
            return true;
        } else {
            return false;
        }
    }

    public boolean returnToAvailable(Ticket ticket) {
        if (!availableSeats.contains(ticket)) {
            availableSeats.add(ticket);
            return true;
        } else {
            return false;
        }
    }

    @JsonIgnore
    public Map<UUID, Ticket> getPurchasedTicket() {
        return purchasedTicket;
    }

    public Ticket getByToken(UUID token) {
        return purchasedTicket.get(token);
    }

    public boolean removeFromPurchased(UUID token) {
        if (purchasedTicket.get(token) != null) {
            purchasedTicket.remove(token);
            return true;
        } else {
            return false;
        }
    }

    public void addToPurchased(UUID token, Ticket ticket) {
        purchasedTicket.put(token, ticket);
    }
}
