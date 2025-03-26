package at.Codefabrik.anlassManagement.repository;

import at.Codefabrik.anlassManagement.entity.Anlass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnlassRepository extends JpaRepository<Anlass, Long> {
    List<Anlass> findByTitleContainingIgnoreCase(String title);
}
