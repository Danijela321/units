package se.goteborg.units.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;


import java.util.List;

@Entity
@Table(name = "units")
public class Units {
    @Id
    @NotNull
    @Column(name = "unit_id", unique = true, nullable = false)
    private String id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "category", nullable = false)
    private List<String> category;

    @Column(name = "total_visits", nullable = false)
    private int totalVisits;

    public Units(String id, String name, String address, List<String> category, int totalVisits) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.category = category;
        this.totalVisits = totalVisits;
    }

    public Units(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getCategory() {
        return category;
    }

    public void setCategory(List<String> category) {
        this.category = category;
    }
    public int getTotalVisits() {
        return totalVisits;
    }

    public void setTotalVisits(int totalVisits) {
        this.totalVisits = totalVisits;
    }
}
