package eu.vrtime.vrm.domain.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import eu.vrtime.vrm.domain.shared.AbstractBaseEntity;
import eu.vrtime.vrm.domain.shared.ResourceIdentifier;
import eu.vrtime.vrm.domain.shared.ResourceStatus;

@Entity
public class Resource extends AbstractBaseEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6150543763294953271L;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FK_SESSIONMANAGER", nullable = false, updatable = true, unique = false)
	private SessionManager sessionManager;

	@Embedded
	@Column(name = "identifier", nullable = false, updatable = true, unique = true)
	private ResourceIdentifier identifier;

	@Enumerated(EnumType.STRING)
	private ResourceStatus status;

	public Resource(final ResourceIdentifier identifier, ResourceStatus status) {
		this.identifier = identifier;
		this.status = status;
	}

	public ResourceIdentifier getIdentifier() {
		return identifier;
	}

	public void setIdentifier(ResourceIdentifier identifier) {
		this.identifier = identifier;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public ResourceStatus getStatus() {
		return status;
	}

	public void setStatus(ResourceStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Resource [oid=" + toStringOid() + ", identifier=" + identifier.getIdentifier() + ", sessionManager="
				+ sessionManager.toStringOid() + ", status=" + status + ", oid=" + oid + ", createDate=" + createDate
				+ ", lastModified=" + lastModified + "]";
	}

	Resource() {

	}

}
