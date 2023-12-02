package pl.coderslab.end_project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.end_project.model.Era;

public interface EraRepository extends JpaRepository <Era, Long> {
}
