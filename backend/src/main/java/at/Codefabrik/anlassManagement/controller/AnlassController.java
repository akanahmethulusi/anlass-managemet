package at.Codefabrik.anlassManagement.controller;

import at.Codefabrik.anlassManagement.entity.Anlass;
import at.Codefabrik.anlassManagement.service.AnlassService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/anlaesse")
public class AnlassController {
    private final AnlassService anlassService;

    public AnlassController(AnlassService anlassService) {
        this.anlassService = anlassService;
    }

    @GetMapping
    public List<Anlass> getAll(){
        return anlassService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Anlass> getById(@PathVariable Long id){
        return anlassService.getById(id);
    }

    @GetMapping("/search")
    public Anlass search(@RequestBody String query){
        return anlassService.search(query);
    }

    @PostMapping
    public Anlass create(@RequestBody Anlass anlass){
        return anlassService.create(anlass);
    }

    @PostMapping("/upload/{id}")
    public Anlass uploadFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return anlassService.uploadFile(id, file);
    }

    @GetMapping("/donwload/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id){
        return anlassService.downloadFile(id);
    }

    @PutMapping("/{id}")
    public Anlass update(@PathVariable Long id, @RequestBody Anlass updatedAnlass){
        return anlassService.update(id, updatedAnlass);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        anlassService.delete(id);
    }


}
