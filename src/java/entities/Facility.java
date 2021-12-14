package entities;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import static javax.persistence.CascadeType.ALL;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing Facilities. Contains personal data, identification data
 * and relational data for accessing flat facilities.
 *
 * @author Adrián
 */
@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class Facility implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    /**
     * Identification field for facility.
     */
    private Long id;
    /**
     * Enumeration that contains diferent types of facilities.
     */
    @Enumerated(EnumType.STRING)
    private FacilityType type;
    
    /**
     * Adquisition date of the facility.
     */  
    private Date adquisitionDate;

    /**
     *
     * @return the id of the facility.
     */
    @OneToMany(cascade = ALL, mappedBy = "facility")
    private List<FlatFacility> flatFacilities;

    public Long getId() {
        return id;
    }

    /**
     *
     * @param id facility id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     * @return the type of the facility.
     */
    public FacilityType getType() {
        return type;
    }

    /**
     *
     * @param type Facility type.
     */
    public void setType(FacilityType type) {
        this.type = type;
    }

    /**
     *
     * @return Adquisition date of the facility.
     */
    public Date getAdquisitionDate() {
        return adquisitionDate;
    }

    /**
     *
     * @param adquisitionDate Facility adquisition date.
     */
    public void setAdquisitionDate(Date adquisitionDate) {
        this.adquisitionDate = adquisitionDate;
    }

    /**
     * Integer representation for Facility instance.
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    /**
     * Compares two Facility objects for equality. This method consider a
     * Facility equal to another Facility if their id fields have the same
     * value.
     *
     * @param object The other Facility object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Facility)) {
            return false;
        }
        Facility other = (Facility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Facility.
     *
     * @return The String representing the Facility.
     */
    @Override
    public String toString() {
        return "Facility{" + "id=" + id + ", type=" + type + ", adquisitionDate=" + adquisitionDate + ", flatFacilities=" + flatFacilities + '}';
    }

}
