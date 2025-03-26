package at.Codefabrik.anlassManagement.service;

import at.Codefabrik.anlassManagement.entity.Anlass;
import at.Codefabrik.anlassManagement.repository.AnlassRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class AnlassService {
    private final AnlassRepository anlassRepository;

    public AnlassService(AnlassRepository anlassRepository) {
        this.anlassRepository = anlassRepository;
    }

    public List<Anlass> getAll() {
        return anlassRepository.findAll();
    }

    public Anlass create(Anlass anlass) {
        return anlassRepository.save(anlass);
    }

    public Optional<Anlass> getById(Long id) {
        anlassRepository.findById(id);
        return anlassRepository.findById(id);
    }

    public Anlass update(Long id, Anlass updatedAnlass) {
        return anlassRepository.findById(id).map(anlass -> {
            anlass.setTitle(updatedAnlass.getTitle());
            anlass.setBeschreibung(updatedAnlass.getBeschreibung());
            anlass.setMaxTeilnehmer(updatedAnlass.getMaxTeilnehmer());
            return anlassRepository.save(anlass);
                }).orElseThrow(()->new RuntimeException("Anlass not found"));
    }

    public void delete(Long id) {
        anlassRepository.deleteById(id);
    }

    public Anlass search(String query) {
        return (Anlass) anlassRepository.findByTitleContainingIgnoreCase(query);
    }

    public Anlass uploadFile(Long id, MultipartFile file) {
        return anlassRepository.findById(id).map(anlass -> {
            try {
                anlass.setFileData(file.getBytes());
                return anlassRepository.save(anlass);
            } catch (IOException e) {
                throw new RuntimeException("Fehler beim Speichern der Datei", e);
            }
        }).orElseThrow(()->new RuntimeException("Anlass nicht gefunden"));
    }

    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        return (ResponseEntity<byte[]>) anlassRepository.findById(id).map(anlass ->{
            if(anlass.getFileData() != null){
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=anlass_file")
                        .contentType(MediaType.APPLICATION_OCTET_STREAM)
                        .body(anlass.getFileData());
            }else {
                return ResponseEntity.notFound().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
