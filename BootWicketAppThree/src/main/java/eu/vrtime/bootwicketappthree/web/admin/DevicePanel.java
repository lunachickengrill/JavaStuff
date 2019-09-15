package eu.vrtime.bootwicketappthree.web.admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.HeadersToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.NavigationToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.IDataProvider;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;

import eu.vrtime.bootwicketappthree.model.Device;
import eu.vrtime.bootwicketappthree.model.DeviceSpecification;
import eu.vrtime.bootwicketappthree.repositories.DeviceRepository;
import eu.vrtime.bootwicketappthree.web.provider.DeviceDataProvider;

public class DevicePanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6549786897721784260L;

	@SpringBean
	private DeviceRepository deviceRepo;

	private static final String FORM_ID = "searchDeviceForm";
	private static final String FORM_BTN_ID = "submit";
	private static final String FORM_MAC_ID = "mac";
	private static final String FORM_SN_ID = "serial";
	private static final String FORM_TYPE_ID = "type";
	private static final String FEEDBACK_ID = "feedback";
	private static final String DATATABLE_ID = "deviceDataTable";
	private static final String EDIT_LINK_ID = "editLink";
	private static final String DROPDOWN_ID = "drpChoice";

	private FeedbackPanel feedback;
	private DataTable<Device, String> deviceTable;
	private DeviceSpecification specification = new DeviceSpecification();
	private String deviceMac = new String();
	private String deviceSn = new String();
	private String deviceType = new String();


	public DevicePanel(final String id) {
		super(id);
		feedback = new FeedbackPanel(FEEDBACK_ID);
	}

	@Override
	protected void onInitialize() {

		super.onInitialize();
		add(feedback);
		add(createForm(FORM_ID));
		add(createDataTable(DATATABLE_ID, 10));

	}

	private Form<DeviceSpecification> createForm(final String id) {

		Form<DeviceSpecification> form = new Form<DeviceSpecification>(id);

		CompoundPropertyModel<DeviceSpecification> model = new CompoundPropertyModel<DeviceSpecification>(
				specification);
		setDefaultModel(model);

		TextField<String> macField = new TextField<>(FORM_MAC_ID);
		macField.add(new AttributeModifier("placeholder", "00:00:00:00:00:00"));
		macField.add(new StringValidator(2, 12));
		form.add(macField);

		TextField<String> snField = new TextField<>(FORM_SN_ID);
		snField.add(new StringValidator(2,12));
		snField.add(new AttributeModifier("placeholder", "SN0123456789"));
		form.add(snField);

		form.add(createDeviceTypeChoice(FORM_TYPE_ID));

		form.add(new Button(FORM_BTN_ID) {

			private static final long serialVersionUID = 2355446899052737371L;

			@Override
			public void onSubmit() {

				super.onSubmit();
				deviceTable.setVisible(true);
			}
		});

		return form;
	}

	private Component createDeviceTypeChoice(String id) {

		IModel<List<String>> deviceTypes = new LoadableDetachableModel<List<String>>() {

			@Override
			protected List<String> load() {
				return deviceRepo.listDeviceTypes();
			}
		};

		return new DropDownChoice<>(id, deviceTypes);

	}

	private DataTable<Device, String> createDataTable(String id, int rows) {

		final List<IColumn<Device, String>> columns = new ArrayList<>();

		columns.add(new PropertyColumn<>(Model.of("MAC"), "mac", "mac"));
		columns.add(new PropertyColumn<>(Model.of("SN"), "serial", "serial"));
		columns.add(new PropertyColumn<>(Model.of("Type"), "type", "type"));

		columns.add(new PropertyColumn(new Model(""), "") {

			private static final long serialVersionUID = -7813266441720935690L;

			@Override
			public void populateItem(Item item, String componentId, IModel rowModel) {
				item.add(new EditLinkPanel(componentId));
			}

		});
		DeviceDataProvider dataProvider = new DeviceDataProvider(deviceRepo, specification);
		deviceTable = new DataTable<>(id, columns, dataProvider, rows);
		deviceTable.addTopToolbar(new HeadersToolbar<>(deviceTable, dataProvider));
		deviceTable.addBottomToolbar(new NavigationToolbar(deviceTable));
		deviceTable.setVisible(false);
		return deviceTable;

	}

	private IColumn<Device, String> createEditColumn() {
		// to be implemented
		// should create the column with the edit link
		return null;
	}

}
