package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Entity representing owners. Contains whether they are resident or not as well
 * as all the dwellings they are offering
 *
 * @author Yeray Sampedro
 */
@Entity
@Table(schema = "bluroof", name ="owner")
@XmlRootElement
public class Owner extends User implements Serializable  {

        private static final long serialVersionUID = 1L;  
    /**
     * owner's attribute to know whether it will be resident or not
     */
    private Boolean isResident;
    /**
     * Relational field that contains the Dwellings offered
     */
    @OneToMany
    private List<Dwelling> dwellings;

    @XmlTransient
    public List<Dwelling> getDwellings() {
        return dwellings;
    }

    public void setDwellings(List<Dwelling> dwellings) {
        this.dwellings = dwellings;
    }

    /**
     * Gets whether the owner resides or not
     *
     * @return isResident if the owner is resident
     */
    public Boolean getIsResident() {
        return isResident;
    }

    /**
     * Sets if the owner resides or not
     *
     * @param isResident the residence value
     */
    public void setIsResident(Boolean isResident) {
        this.isResident = isResident;
    }

}
