package eu.vrtime.BootWicketWebApp.web;

import org.apache.wicket.markup.html.basic.Label;

public class HomePage extends AbstractBasePage{
	
	public HomePage() {
		super();
		
		add(new Label("homePageLable", "this is the homePage"));
	}

}