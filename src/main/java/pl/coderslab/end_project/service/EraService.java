package pl.coderslab.end_project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.coderslab.end_project.model.Era;
import pl.coderslab.end_project.repository.EraRepository;

import java.util.List;

@Service
public class EraService {

    @Autowired
    private EraRepository eraRepository;

    public List<Era> getAllEras() {
        return eraRepository.findAll();
    }
}
