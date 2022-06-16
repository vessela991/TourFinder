package fmi.java.web.tourFinder.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import fmi.java.web.tourFinder.request.TourCreateRequest;
import fmi.java.web.tourFinder.util.CommonUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
    @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
    private List<TourPicture> pictures;
    private double price;
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date startDate;
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    private Date endDate;

    public static Tour fromTourCreateRequest(TourCreateRequest request) {
        Tour tour = new Tour();
        tour.setName(request.getName());
        tour.setDestination(request.getDestination());
        tour.setDescription(request.getDescription());
        tour.setPrice(request.getPrice());
        tour.setStartDate(request.getStartDate());
        tour.setEndDate(request.getEndDate());
        tour.setPictures(CommonUtils.convertFromMultipartToTourPicture(request.getPictures()));
        return tour;
    }
}
