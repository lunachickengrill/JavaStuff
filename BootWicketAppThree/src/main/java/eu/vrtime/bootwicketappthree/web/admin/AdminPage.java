package eu.vrtime.bootwicketappthree.web.admin;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import eu.vrtime.bootwicketappthree.model.AppUser;
import eu.vrtime.bootwicketappthree.web.TestPanel;
import eu.vrtime.bootwicketappthree.web.auth.AppAuthenticatedWebSession;
import eu.vrtime.bootwicketappthree.web.login.LoginPage;

public class AdminPage extends WebPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -854204171725844717L;
	private static final String PANEL_ID = "panel";
	private static final String LOGOUTLINK_ID = "logout";
	private LinkPanel linkPanel;
	private Panel customerPanel = new CustomerPanel(PANEL_ID);
	private Panel devicePanel = new DevicePanel(PANEL_ID);
	private Panel TestPanel = new TestPanel(PANEL_ID);
	private Panel current = customerPanel;
	private AppAuthenticatedWebSession session;

	public AdminPage() {
		super();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		linkPanel = new LinkPanel("linkPanel", this);
		add(linkPanel);
		add(current);
		add(new AppUserPanel("userInfoPanel"));

	}

	@Override
	protected void onConfigure() {
		super.onConfigure();

		AuthenticatedWebApplication app = (AuthenticatedWebApplication) Application.get();
		if (!AuthenticatedWebSession.get().isSignedIn()) {
			app.restartResponseAtSignInPage();
		}
		
		if(AuthenticatedWebSession.get().isSignedIn()) {
			this.session = (AppAuthenticatedWebSession) AuthenticatedWebSession.get();
		}
	}
	


	public Panel getCustomerPanel() {
		return customerPanel;
	}

	public void setCustomerPanel(Panel customerPanel) {
		this.customerPanel = customerPanel;
	}

	public Panel getDevicePanel() {
		return devicePanel;
	}

	public void setDevicePanel(Panel devicePanel) {
		this.devicePanel = devicePanel;
	}

	public Panel getTestPanel() {
		return TestPanel;
	}

	public void setTestPanel(Panel testPanel) {
		this.TestPanel = testPanel;
	}

	public Panel getCurrent() {
		return this.current;
	}

	public void setCurrent(Panel panel) {
		this.current = panel;
	}
	
	protected AppUser getUser() {
		return session.getAppUser();
	}

}
