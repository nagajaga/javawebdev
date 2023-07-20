package reloadheroes;

import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ReloadController {

    @Autowired
    private ReloadStatusRepository reloadStatusRepository;

    @Autowired
    private HttpSession session;

    @RequestMapping("*")
    public String reload(Model model) {
        UUID userUuid = (UUID) session.getAttribute("name");
        if (userUuid == null) {
            userUuid = UUID.randomUUID();
            session.setAttribute("name", userUuid);
        }

        String statusName = "" + userUuid;
        ReloadStatus status = reloadStatusRepository.findByName(statusName);

        if (status == null) {
            status = new ReloadStatus(statusName, 1); 
        } else {
            int currentReloads = status.getReloads();
            status.setReloads(currentReloads + 1); 
        }

        reloadStatusRepository.save(status);

        model.addAttribute("status", status);
        Pageable pageable = PageRequest.of(0, 5, Sort.by("reloads").descending());
        model.addAttribute("scores", reloadStatusRepository.findAll(pageable));
        return "index";
    }

}
