package eu.vrtime.bootwicketappthree.web.admin;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.iterators.EmptyListIterator;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.NumberTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigation;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.springframework.util.Assert;

import eu.vrtime.bootwicketappthree.model.AppUser;
import eu.vrtime.bootwicketappthree.model.Customer;
import eu.vrtime.bootwicketappthree.model.CustomerSpecification;
import eu.vrtime.bootwicketappthree.repositories.CustomerRepository;

public class CustomerPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2565501203152150630L;

	@SpringBean
	private CustomerRepository customerRepository;

	private static final String FORM_ID = "searchCustomerForm";
	private static final String FORM_CUSTOMERID_ID = "customerId";
	private static final String FORM_FIRSTNAME_ID = "firstName";
	private static final String FORM_LASTNAME_ID = "lastName";
	private static final String FORM_BUTTON_ID = "submit";
	private static final String FEEDBACKPANEL_ID = "feedback";
	private static final String CUSTOMERLIST_ID = "customerList";
	private static final String LINK_CREATE_CUSTOMER = "createCustomerLink";
	private static final String NAVIGATOR_ID = "navigator";

//	private FeedbackPanel feedbackPanel;
	private CustomerSpecification customerSpec = new CustomerSpecification();
	private PageableListView<Customer> listView;
	private ModalWindow createCustomerWindow;
	private ModalWindow editCustomerWindow;

//	Use LoadableCustomerModel instead
//	
//	private IModel<List<Customer>> customerListModel = new LoadableDetachableModel<List<Customer>>() {
//
//		@Override
//		protected List<Customer> load() {
//
//			return (customerSpec != null && ((customerSpec.getCustomerId() != null)
//					|| (customerSpec.getFirstName() != null) || (customerSpec.getLastName() != null)))
//							? customerRepository.findAll(customerSpec)
//							: Collections.emptyList();
//		}
//
//	};

	public CustomerPanel(String id) {
		super(id);
//		feedbackPanel = new FeedbackPanel(FEEDBACKPANEL_ID);
		createCustomerWindow = new ModalWindow("modalWindow");
		editCustomerWindow = new ModalWindow("editCustomerWindow");

	}

	@Override
	protected void onInitialize() {

		super.onInitialize();
//		add(feedbackPanel);
		add(createCustomerForm(FORM_ID));
		listView = createListView(CUSTOMERLIST_ID);
		add(listView);
		add(new PagingNavigator(NAVIGATOR_ID, listView));
		add(addCreateCustomerWindow(createCustomerWindow, customerRepository));
		add(createCustomerLink(LINK_CREATE_CUSTOMER, createCustomerWindow));
		add(editCustomerWindow);

	}

	private Form<Void> createCustomerForm(String id) {

		Form<Void> form = new Form<>(id);
		CompoundPropertyModel<CustomerSpecification> model = new CompoundPropertyModel<CustomerSpecification>(
				customerSpec);
		form.setDefaultModel(model);

		TextField<Void> tfCustomerId = new TextField<>(FORM_CUSTOMERID_ID);
		tfCustomerId.add(new AttributeModifier("placeholder", "123"));
		tfCustomerId.add(new StringValidator(2,10));
		form.add(tfCustomerId);

		TextField<Void> tfFirstname = new TextField<>(FORM_FIRSTNAME_ID);
		tfFirstname.add(new AttributeModifier("placeholder", "Tom"));
		tfFirstname.add(new StringValidator(2, 10));
		form.add(tfFirstname);

		TextField<Void> tfLastname = new TextField<>(FORM_LASTNAME_ID);
		tfLastname.add(new AttributeModifier("placeholder", "Turbo"));
		tfLastname.add(new StringValidator(2,12));
		form.add(tfLastname);

		form.add(new Button(FORM_BUTTON_ID) {

			private static final long serialVersionUID = 1L;

			@Override
			public void onSubmit() {
				success("Button click");
				super.onSubmit();

			}

		});

		return form;

	}

	private PageableListView<Customer> createListView(String id) {

		PageableListView<Customer> listView = new PageableListView<Customer>(id,
				new LoadableCustomerModel(customerRepository, customerSpec), 10) {

			private static final long serialVersionUID = 6795844460169152926L;

			@Override
			protected void populateItem(ListItem<Customer> item) {
				Customer cust = (Customer) item.getModelObject();

				item.add(new Label("customerId", Model.of(cust.getCustomerId())));
				item.add(new Label("firstName", Model.of(cust.getFirstName())));
				item.add(new Label("lastName", Model.of(cust.getLastName())));
				
//				maybe the better idea
//				item.add(new EditLinkPanel("editLink"));
				
				item.add(editCustomerLink("editLink", editCustomerWindow, createCustomerModel(cust.getId())));

			}

		};

		return listView;
	}

	private ModalWindow addCreateCustomerWindow(final ModalWindow window, final CustomerRepository repository) {

		AddCustomerPanel addCustomerPanel = new AddCustomerPanel(window.getContentId(), repository);
		window.setContent(addCustomerPanel);
		window.setCookieName(this.getClass().getSimpleName());
		window.setTitle(Model.of("Add a new Customer"));
		window.setCssClassName(ModalWindow.CSS_CLASS_BLUE);
		window.setInitialWidth(400);
		window.setInitialHeight(400);

		window.setOutputMarkupId(true);

		return window;

	}

	private AjaxLink<Void> createCustomerLink(String id, ModalWindow window) {

		AjaxLink<Void> link = new AjaxLink<Void>(id) {
			private static final long serialVersionUID = 5218474796306160615L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				window.show(target);

			}
		};

		return link;
	}

	private AjaxLink<Void> editCustomerLink(String id, ModalWindow window, IModel<Customer> customerModel) {
		
		

		AjaxLink<Void> link = new AjaxLink<Void>(id) {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				EditCustomerPanel editCustomerPanel = new EditCustomerPanel(window.getContentId(), customerModel);
				window.setContent(editCustomerPanel);
				window.setCookieName(this.getClass().getSimpleName());
				window.setOutputMarkupId(true);
				window.setTitle(Model.of("Edit a customer"));
				window.setCssClassName(ModalWindow.CSS_CLASS_BLUE);
				window.setInitialWidth(400);
				window.setInitialHeight(400);
				window.show(target);

			}

		};

		return link;

	}

	private IModel<Customer> createCustomerModel(final Long oid) {
		IModel<Customer> customerModel = new LoadableDetachableModel<Customer>() {

			@Override
			protected Customer load() {
				return oid != null ? customerRepository.findByOid(oid).get() : new Customer();
			}

		};

		return customerModel;
	}
}
