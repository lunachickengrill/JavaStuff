package eu.vrtime.bootwicketappthree.web;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.panel.FeedbackPanel;

public class TestPage extends WebPage {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3866911983740970949L;

	public TestPage() {
		super();
		add(new FeedbackPanel("feedback"));
		add(new MapViewPanel("mapPanel").setOutputMarkupId(true));
	}

}
