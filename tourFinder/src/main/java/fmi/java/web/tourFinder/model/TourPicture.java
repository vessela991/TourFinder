package fmi.java.web.tourFinder.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TourPicture {
    private int id;
    private String picture;
    @ManyToOne
    @JoinColumn(name = "tour_id")
    private Tour tour;

    public TourPicture(String picture, Tour tour) {
        this.picture = picture;
        this.tour = tour;
    }
}
