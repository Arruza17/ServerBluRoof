package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing dwellings. It contains the following fields: dwelling id,
 * dwelling address, if the dwelling hasWiFi, the squareMeters of the dwelling,
 * the different dwelling tags, in which neighbourhood is located, the creation
 * date of the dwelling, who is the owner of the dwelling,the average rating of
 * the dwelling based on, the rating given by the users, and all the comments
 * made by the users.
 *
 * @author Ander Arruza
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(schema = "bluroof")
@XmlRootElement
public class Dwelling implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * Identification field for the dwelling
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Where the Dwelling is located, the structure should be: STREET,
     * FLOOR-LETTER, CP example: Altzaga Kalea 1-A, 48950
     */
    @NotNull
    private String address;
    /**
     * If the Dwelling has WiFi or not
     */
    @NotNull
    private Boolean hasWiFi;
    /**
     * Square meters of the dwelling saved as m^2
     */
    private Double squareMeters;
    /**
     * List of all the tags of the dwelling, for example: VEGAN_FRIENDLY
     */
    @Enumerated(EnumType.STRING)
    @ElementCollection(targetClass = Tag.class)
    private List<Tag> tags;
    /**
     * Relational field containing Neighbourhood of the dwelling
     */
    @NotNull
    private Neighbourhood neighbourhood;
    /**
     * Date in which the dwelling was made
     */
    @NotNull
    private LocalDate constructionDate;
    /**
     * Relational field containing the host of the dwelling
     */
    @NotNull
    private Host host;
    /**
     * Rating of the dwelling. It is set the 0 when a new dwelling is created.
     * It will contain the average rating given by the users
     */
    @NotNull
    private Float rating;
    /**
     * List of the comments made by the users about the dwelling.
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dwelling")
    private List<Comment> comments;

    //GETTERS AND SETTERS
    /**
     * Returns the id
     *
     * @return the id to get
     */
    public Long getId() {
        return id;
    }

    /**
     * The id to set
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the address
     *
     * @return the address to get
     */
    public String getAddress() {
        return address;
    }

    /**
     * The address to set
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Returns if hasWiFi
     *
     * @return true if has WiFi, false if not
     */
    public Boolean getHasWiFi() {
        return hasWiFi;
    }

    /**
     * Sets if the dwelling has WiFi
     *
     * @param hasWiFi true if has WiFi, false if not
     */
    public void setHasWiFi(Boolean hasWiFi) {
        this.hasWiFi = hasWiFi;
    }

    /**
     * Return the square meters in m^2
     *
     * @return the square meters
     */
    public Double getSquareMeters() {
        return squareMeters;
    }

    /**
     * The square meters to set
     *
     * @param squareMeters the square meters to set
     */
    public void setSquareMeters(Double squareMeters) {
        this.squareMeters = squareMeters;
    }

    /**
     * Return the date in which the dwelling was made
     *
     * @return the date
     */
    public LocalDate getConstructionDate() {
        return constructionDate;
    }

    /**
     * The date of the construction date
     *
     * @param constructionDate the construction date to set
     */
    public void setConstructionDate(LocalDate constructionDate) {
        this.constructionDate = constructionDate;
    }

    /**
     * Returns all the ratings average
     *
     * @return the average rating
     */
    public Float getRating() {
        return rating;
    }

    /**
     * Sets the rating
     *
     * @param rating the rating to set
     */
    public void setRating(Float rating) {
        this.rating = rating;
    }

    /**
     * Integer representation for Account instance.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Dwelling objects for equality. This method consider a
     * Dwelling equal to another Dwelling if their id fields have the same
     * value.
     *
     * @param object The other Dwelling object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dwelling)) {
            return false;
        }
        Dwelling other = (Dwelling) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Dwelling.
     *
     * @return The String representing the Dwelling.
     */
    @Override
    public String toString() {
        return "Dwelling{" + "id=" + id + ", address=" + address + ", hasWiFi=" + hasWiFi + ", squareMeters=" + squareMeters + ", tags=" + tags + ", neighbourhood=" + neighbourhood + ", constructionDate=" + constructionDate + ", host=" + host + ", rating=" + rating + ", comments=" + comments + '}';
    }

}
