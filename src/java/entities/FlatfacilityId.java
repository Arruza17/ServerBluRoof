package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * Class containing the embedded ids.
 *
 * @author jorge
 */
@Embeddable
public class FlatfacilityId implements Serializable {

    /**
     * embedded id of the flat
     */
    private Long flatId;
    /**
     * embedded id of the facility
     */
    private Long facilityId;
}
