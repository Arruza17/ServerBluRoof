package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Entity representing comments. It contains the following fields: comment id,
 * comment text, the date in which the comment was made, the commenter who made
 * the comment and the building to which the comment has been made.
 *
 * @author Ander Arruza
 */
@Entity
@Table(schema = "bluroof")
@XmlRootElement
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    /**
     * Identification field for the comment
     */
    @EmbeddedId
    private CommentId commentId;
    /**
     * The text of the comment
     */
    @NotNull
    private String text;
    /**
     * Current time in which the comment was made
     */
    @NotNull
    private Timestamp commentDate;
    /**
     * Relational field containing the commenter who made it
     */
    @JoinColumn(name = "guestId", updatable = false, insertable = false)
    @NotNull
    @ManyToOne
    private Guest commenter;
    /**
     * Relational field containing the relation between comment and the dwelling
     */
    @JoinColumn(name = "dwellingId", updatable = false, insertable = false)
    @ManyToOne
    private Dwelling dwelling;

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
     * Returns the text
     *
     * @return the text of the comment
     */
    public String getText() {
        return text;
    }

    /**
     * Sets the text
     *
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Returns the date in which the comment was made
     *
     * @return the date of the comment
     */
    public Timestamp getCommentDate() {
        return commentDate;
    }

    /**
     * Sets the date in which the comment was made
     *
     * @param commentDate the date of the comment
     */
    public void setCommentDate(Timestamp commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * Returns the commenter
     *
     * @return the author of the comment
     */
    public Guest getCommenter() {
        return commenter;
    }

    /**
     * Sets the commenter of the comment
     *
     * @param commenter the Guest(commenter) to set
     */
    public void setCommenter(Guest commenter) {
        this.commenter = commenter;
    }

    /**
     * Returns the dwelling
     *
     * @return the dwelling to get
     */
    public Dwelling getDwelling() {
        return dwelling;
    }

    /**
     * Sets the dwelling
     *
     * @param dwelling the dwelling to set
     */
    public void setDwelling(Dwelling dwelling) {
        this.dwelling = dwelling;
    }

    /**
     * Returns the Comment id
     *
     * @return the CommentId to get
     */
    public CommentId getCommentId() {
        return commentId;
    }

    /**
     * Sets the comment id
     *
     * @param commentId
     */
    public void setCommentId(CommentId commentId) {
        this.commentId = commentId;
    }

    /**
     * Integer representation for Comment instance.
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
     * Compares two Comment objects for equality. This method consider a Comment
     * equal to another Comment if their id fields have the same value.
     *
     * @param object The other Comment object to compare to.
     * @return true if ids are equals.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    /**
     * Obtains a string representation of the Comment.
     *
     * @return The String representing the Comment.
     */
    @Override
    public String toString() {
        return "entities.Comment[ id=" + id + " ]";
    }

}