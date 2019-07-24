package eu.vrtime.bootwicketappthree.web;

import org.apache.wicket.Application;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

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

	public AdminPage() {
		super();

	}

	@Override
	protected void onInitialize() {
		// TODO Auto-generated method stub
		super.onInitialize();
		linkPanel = new LinkPanel("linkPanel", this);
		add(linkPanel);
		add(current);
		add(createLogoutLink(LOGOUTLINK_ID));

	}

	@Override
	protected void onConfigure() {
		super.onConfigure();

		AuthenticatedWebApplication app = (AuthenticatedWebApplication) Application.get();
		if (!AuthenticatedWebSession.get().isSignedIn()) {
			app.restartResponseAtSignInPage();
		}
	}
	
	private Link<Void> createLogoutLink(final String id) {
		Link<Void> logoutLink = new Link<Void>(id) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				AuthenticatedWebSession.get().invalidate();
				setResponsePage(AdminPage.class);
				
			}
			
		};
		
		return logoutLink;
//		add(new Link("logOut") {
//
//	         @Override
//	         public void onClick() {
//	            AuthenticatedWebSession.get().invalidate();
//	            setResponsePage(getApplication().getHomePage());
//	         }
//	      });
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

}
