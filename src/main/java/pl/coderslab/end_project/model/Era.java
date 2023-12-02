package pl.coderslab.end_project.model;

import jakarta.persistence.*;

@Entity
@Table(name = "era")
public class Era {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String era_name;
    private String extreme_dates;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEra_name() {
        return era_name;
    }

    public void setEra_name(String era_name) {
        this.era_name = era_name;
    }

    public String getExtreme_dates() {
        return extreme_dates;
    }

    public void setExtreme_dates(String extreme_dates) {
        this.extreme_dates = extreme_dates;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
