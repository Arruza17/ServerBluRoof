package entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 * This class contains the embedded ids
 *
 * @author Ander Arruza
 */
@Embeddable
public class CommentId implements Serializable {

    /**
     * Embedded id of the Comment class
     */
    private Long commenterId;
    /**
     * Embedded id of the Dwelling class
     */
    private Long dwellingId;

    public Long getCommenterId() {
        return commenterId;
    }

    public void setCommenterId(Long commenterId) {
        this.commenterId = commenterId;
    }

    public Long getDwellingId() {
        return dwellingId;
    }

    public void setDwellingId(Long dwellingId) {
        this.dwellingId = dwellingId;
    }

}
