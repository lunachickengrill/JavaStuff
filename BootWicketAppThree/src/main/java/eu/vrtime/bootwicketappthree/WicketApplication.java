package eu.vrtime.bootwicketappthree;

import org.apache.wicket.Application;
import org.apache.wicket.Page;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;

import eu.vrtime.bootwicketappthree.web.AdminPage;
import eu.vrtime.bootwicketappthree.web.BreadCrumbPage;
import eu.vrtime.bootwicketappthree.web.auth.AppAuthenticatedWebSession;
import eu.vrtime.bootwicketappthree.web.login.LoginPage;

public class WicketApplication extends WebApplication {

	public WicketApplication() {
		super.init();
	}

	@Override
	protected void init() {
		super.init();
		getComponentInstantiationListeners().add(new SpringComponentInjector(this));		
		mountPage("/admin", AdminPage.class);
		mountPage("/breadcrumb", BreadCrumbPage.class);
		mountPage("/login", LoginPage.class);
		

		

	}

	@Override
	public Class<? extends Page> getHomePage() {
		return AdminPage.class;
	}
	

//	@Override
//	protected Class<? extends AbstractAuthenticatedWebSession> getWebSessionClass() {
//		return AppAuthenticatedWebSession.class;
//	}
//
//	@Override
//	protected Class<? extends WebPage> getSignInPageClass() {
//		return LoginPage.class;
//	}

	public static WicketApplication get() {
		return (WicketApplication) Application.get();
	}

}
