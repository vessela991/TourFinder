package fmi.java.web.tourFinder.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String name;
    private String destination;
    private String description;
    @ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
    @Column(name = "tour_pictures")
    private List<String> pictures;
    private double price;
}
