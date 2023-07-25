package reservations;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/reservations")
    public String list(Model model) {
        model.addAttribute("reservations", reservationRepository.findAll());
        return "reservations";
    }

    @PostMapping("/reservations")
    public String addReservation(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate reservationTo) {
                reservationRepository.save(new Reservation(accountRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()),reservationFrom, reservationTo ));

        return "redirect:/reservations";
    }

}
