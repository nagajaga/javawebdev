package gifbin;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GifController {

    @Autowired
    private FileObjectRepository fileObjectRepository;

    @GetMapping("/gifs")
    public String redirectGifs() {
        return "redirect:/gifs/1";
    }

    @GetMapping("/gifs/{id}")
    public String getGif(Model model, @PathVariable Long id) {
        model.addAttribute("count", fileObjectRepository.count());
        model.addAttribute("current", id);
        if (fileObjectRepository.findAll().size() > id) {
            model.addAttribute("next", id + 1);
        }
        if (id > 1) {
            model.addAttribute("previous", id - 1);
        }

        return "gifs";

    }
    
    @GetMapping(path = "/gifs/{id}/content", produces = "image/gif")
    @ResponseBody
    public byte[] getGifContent(@PathVariable Long id) {
        return fileObjectRepository.getOne(id).getContent();
    }

    @PostMapping("/gifs")
    public String save(@RequestParam("file") MultipartFile file) throws IOException {
        FileObject fo = new FileObject();
        fo.setContent(file.getBytes());
        if(file.getContentType().equals("image/gif")){
            fileObjectRepository.save(fo);
        }
        return "redirect:/gifs";
    }

}
