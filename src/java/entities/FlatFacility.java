package entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jorge
 */
@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class FlatFacility implements Serializable {

    private static final long serialVersionUID = 1L;
    
    /**
     * Identification of the ids of flats and facilities.
     */
    @EmbeddedId
    private FlatfacilityId flatFacilityids;
    /**
     * Identification of the flat facility.
     */
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Object flat
     */
    @ManyToOne
    @MapsId("flatId")
    private Flat flat;
    /**
     * Object facility
     */
    
    @ManyToOne
    @MapsId("facilityId")
    private Facility facility;
    /**
     * Enumeration of the condition
     */
    @Enumerated(EnumType.STRING)
    private FacilityCondition condition;

    public FlatfacilityId getFlatFacilityids() {
        return flatFacilityids;
    }

    public void setFlatFacilityids(FlatfacilityId flatFacilityids) {
        this.flatFacilityids = flatFacilityids;
    }

  
    
    /**
     *
     * @return flat
     */
    public Flat getFlat() {
        return flat;
    }

    /**
     *
     * @param flat set flat
     */
    public void setFlat(Flat flat) {
        this.flat = flat;
    }

    /**
     *
     * @return facility
     */
    public Facility getFacility() {
        return facility;
    }

    /**
     *
     * @param facility
     */
    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    /**
     *
     * @return condition
     */
    public FacilityCondition getCondition() {
        return condition;
    }

    /**
     *
     * @param condition set condition
     */
    public void setCondition(FacilityCondition condition) {
        this.condition = condition;
    }

    /**
     *
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id set id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Integer representation of FlatFacility Instance
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
     * Compares two Flatacility objects for equality. This method consider a
     * FlatFacility equal to another FlatFacility if their id fields have the
     * same value.
     *
     * @param object The other FlatFacility object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof FlatFacility)) {
            return false;
        }
        FlatFacility other = (FlatFacility) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Flat.
     *
     * @return
     */
    @Override
    public String toString() {
        return "FlatFacility{" + "id=" + id + ", flat=" + flat + ", facility=" + facility + ", condition=" + condition + '}';
    }

}
