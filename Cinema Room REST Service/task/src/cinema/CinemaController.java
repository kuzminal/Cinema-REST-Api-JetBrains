package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
public class CinemaController {
    private static final Cinema cinema;

    static {
        cinema = new Cinema(9, 9);
    }

    @PostMapping(value = "/purchase", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> purchase(@RequestBody() Seat seat) {
        Ticket ticket = new Ticket(seat);
        if ((seat.getRow() < 0 || seat.getRow() > 9) || (seat.getColumn() > 9 || seat.getColumn() < 0)) {
            return new ResponseEntity(Map.of("error", "The number of a row or a column is out of bounds!"), HttpStatus.BAD_REQUEST);
        } else {
            if (cinema.removeFromAvailable(ticket)) {
                Purchase purchase = new Purchase(ticket);
                cinema.addToPurchased(purchase.getToken(), ticket);
                return new ResponseEntity(purchase, HttpStatus.OK);
            } else {
                return new ResponseEntity(Map.of("error", "The ticket has been already purchased!"), HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PostMapping(value = "/return", consumes = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> returning(@RequestBody ReturnRequest request) {
        UUID token = request.getToken();
        Ticket ticket = cinema.getByToken(token);
        if (ticket != null) {
            cinema.returnToAvailable(ticket);
            cinema.removeFromPurchased(token);
            return new ResponseEntity(Map.of("returned_ticket", ticket), HttpStatus.OK);
        } else {
            return new ResponseEntity(Map.of("error", "Wrong token!"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/stats")
    public ResponseEntity<?> statistics(@RequestParam(value = "password", required = false) Optional<String> superSecret) {
        if (superSecret.isPresent() && superSecret.get().equals("super_secret")) {
            int totalIncome = cinema.getPurchasedTicket().values().stream().mapToInt(Ticket::getPrice).sum();
            int numberOfAvailable = cinema.getAvailableSeats().size();
            int numberOfPurchased = cinema.getPurchasedTicket().size();
            Statistics statistics = new Statistics(totalIncome, numberOfAvailable, numberOfPurchased);
            return new ResponseEntity(statistics, HttpStatus.OK);
        } else {
            return new ResponseEntity(Map.of("error", "The password is wrong!"), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinema;
    }
}
