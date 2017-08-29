package ng.autotopup.engagement_utils.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * @author PC
 *
 */

@MappedSuperclass
public class JEntity implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(unique = true, nullable = false, name = "pk", updatable = false)
	private Long pk;
	
	@Column(name = "deleted", nullable = false, insertable = true, updatable = true)
	private boolean deleted;
	
	@CreationTimestamp
	@Column(name="date_created")
	private Date dateCreated ;
	
	@UpdateTimestamp
	@Column(name="last_modification")
	private Date dateModified ;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

}