package entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing one room of the dwelling. It contains the following
 * fields: boolean to identify if the room has natural light and the number of
 * outlets.
 *
 * @author jorge
 */
@Entity
@Table(schema = "bluroof", name ="room")
@XmlRootElement
public class Room extends Dwelling {

    private static final long serialVersionUID = 1L;
    /**
     * Natural light
     */
    @NotNull
    private Boolean naturalLight;
    /**
     * Number of outlets
     */
    @NotNull
    private Short nOutlets;

    /**
     *
     * @return returns if the room has natural light or not
     */
    public Boolean getNaturalLight() {
        return naturalLight;
    }

    /**
     *
     * @param naturalLight sets if the room has natural lights or not
     */
    public void setNaturalLight(Boolean naturalLight) {
        this.naturalLight = naturalLight;
    }

    /**
     *
     * @return number of outlets
     */
    public Short getnOutlets() {
        return nOutlets;
    }

    /**
     *
     * @param nOutlets set number of outlets
     */
    public void setnOutlets(Short nOutlets) {
        this.nOutlets = nOutlets;
    }

    /**
     * Obtains a string representation of the room
     *
     * @return
     */
    @Override
    public String toString() {
        return super.toString() + " Room{" + "naturalLight=" + naturalLight + ", nOutlets=" + nOutlets + '}';
    }

}
