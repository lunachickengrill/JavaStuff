package eu.vrtime.vrm.domain.shared;

import java.io.Serializable;

import eu.vrtime.vrm.domain.model.SessionManager;

public class ResourceCountingResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4870907522269680903L;
	private SessionManager sessionManager;
	private Long cnt;

	public ResourceCountingResult(final SessionManager sessionManager, final long cnt) {
		this.sessionManager = sessionManager;
		this.cnt = cnt;
	}

	public Long getCnt() {
		return cnt;
	}

	public SessionManager getSessionManager() {
		return sessionManager;
	}

	@Override
	public String toString() {
		return "ResourceCountingResult [sessionManager=" + sessionManager + ", count=" + cnt + "]";
	}

}