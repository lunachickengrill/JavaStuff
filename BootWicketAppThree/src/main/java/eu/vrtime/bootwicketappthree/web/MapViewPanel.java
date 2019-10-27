package eu.vrtime.bootwicketappthree.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

/**
 * Having a map displayed as listview. Ajaxlink populates a new key into the LoadableDetachableModel. ListView is backed with the Model, has the keys and gets the value from the map.
 * @author babis
 *
 */

public class MapViewPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5792373405779007258L;
	private Map<String, String> map = new HashMap<>();
	private List<String> keys = new ArrayList<>();
	private WebMarkupContainer wmc = new WebMarkupContainer("wmc");
	
	private IModel<List<String>> keysModel = new LoadableDetachableModel<List<String>>() {

		private static final long serialVersionUID = 1L;

		@Override
		protected List<String> load() {
			return keys != null ? keys : Collections.EMPTY_LIST;
		}
	};

	public MapViewPanel(final String id) {
		super(id);

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		setOutputMarkupId(true);
		wmc.add(createListView("dataView"));
		wmc.setOutputMarkupId(true);
		add(wmc);
		add(createAddLink("add"));
	}


	


	private ListView<String> createListView(final String id){
		ListView<String> view = new ListView<String>(id, keysModel) {
		
			
			@Override
			protected void populateItem(ListItem<String> item) {
			String key = item.getModelObject();
			item.add(new Label("key_column", key));
			item.add(new Label("value_column", map.get(key)));
				
			}
		};
		
		return view;
	}


	
	private AjaxLink<Void> createAddLink(final String id) {

		AjaxLink<Void> link = new AjaxLink<Void>(id) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				setOutputMarkupId(true);
				String key = StringUtils.leftPad(RandomStringUtils.randomAlphanumeric(3), 3, "0");
				String value = StringUtils.leftPad(RandomStringUtils.randomNumeric(3), 3, "0");
				map.putIfAbsent(key, value);
				keys.add(key);
				
				
				target.add(wmc);
				success("siz " + map.size());
				
			}
		};
		return link;
	}
	
}
