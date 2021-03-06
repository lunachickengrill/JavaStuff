package eu.vrtime.BootWicketWebApp.web;

import javax.inject.Inject;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.PageCreator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.CompoundPropertyModel;
import org.modelmapper.ModelMapper;

import eu.vrtime.BootWicketWebApp.model.Customer;
import eu.vrtime.BootWicketWebApp.model.CustomerDTO;
import eu.vrtime.BootWicketWebApp.repositories.CustomerRepository;

public class SearchPage extends AbstractBasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7434379495347757123L;

	private static final String TEXTFIELD_CUSTOMERID = "customerId";
	private static final String TEXTFIELD_FIRSTNAME = "firstName";
	private static final String TEXTFIELD_LASTNAME = "lastName";
	private static final String TEXTFIELD_EMAIL = "email";
	private static final String BUTTON_SUBMIT = "submit";
	private static final String AJAX_LINK = "createCustomer";
	private static final String MODALWINDOW_TITLE = "modal panel";
	private static final String MODALWINDOW_COOKIENAME = "modal-2";

	@Inject
	private CustomerRepository customerRepository;

	@Inject
	private ModelMapper mapper;

	public SearchPage() {
		super();

		add(new Label("searchPageLabel", "this is the search page"));
		add(createSearchForm("searchForm"));
		add(createModalWithPanel("createCustomerWindow"));

	}

	private Form<CustomerDTO> createSearchForm(String id) {

		Form<CustomerDTO> searchForm = new Form<CustomerDTO>(id);
		CustomerDTO dto = new CustomerDTO();
		searchForm.add(new TextField<String>(TEXTFIELD_CUSTOMERID));
		searchForm.add(new TextField<String>(TEXTFIELD_FIRSTNAME));
		searchForm.add(new TextField<String>(TEXTFIELD_LASTNAME));
		searchForm.add(new TextField<String>(TEXTFIELD_EMAIL));
		CompoundPropertyModel<CustomerDTO> model = new CompoundPropertyModel<CustomerDTO>(dto);
		setDefaultModel(model);
		searchForm.add(new Button(BUTTON_SUBMIT) {

			private static final long serialVersionUID = 6187115801164320555L;

			@Override
			public void onSubmit() {
				System.out.println(">>> searchForm submit clicked <<<");
				Customer customer = mapper.map(this.getModelObject(), Customer.class);
				// TODO implement search and fill DataView
			}

		});
		return searchForm;
	}

	private DataView<CustomerDTO> createDataView(ListDataProvider<CustomerDTO> dataProvider) {
		DataView<CustomerDTO> customerView = new DataView<CustomerDTO>("rows", dataProvider) {
			private static final long serialVersionUID = -1877962082971422231L;

			@Override
			protected void populateItem(Item<CustomerDTO> item) {
				CustomerDTO dto = item.getModelObject();
				RepeatingView repeatingView = new RepeatingView("dataRow");
				repeatingView.add(new Label(repeatingView.newChildId(), dto.getCustomerId()));
				repeatingView.add(new Label(repeatingView.newChildId(), dto.getFirstName()));
				repeatingView.add(new Label(repeatingView.newChildId(), dto.getLastName()));
				repeatingView.add(new Label(repeatingView.newChildId(), dto.getEmail()));
				item.add(repeatingView);

			}

		};

		return customerView;
	}

	private ModalWindow createModalWithPanel(String id) {
		ModalWindow modalWindow = new ModalWindow(id);
		modalWindow.setContent(new CreateCustomerPanel(modalWindow.getContentId()));
		modalWindow.setTitle(MODALWINDOW_TITLE);
		modalWindow.setCookieName(MODALWINDOW_COOKIENAME);

		modalWindow.setWindowClosedCallback(new WindowClosedCallback() {

			private static final long serialVersionUID = 6440918971662021509L;

			@Override
			public void onClose(AjaxRequestTarget target) {

			}
		});

		add(new AjaxLink<Void>(AJAX_LINK) {
			private static final long serialVersionUID = 8016610384377578300L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.show(target);
				System.out.println(">>> modal window panel link clicked <<<");
			}

		});
		return modalWindow;
	}

}
