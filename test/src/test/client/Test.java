package test.client;

import test.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Test implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting service.
	 */
	private final GreetingServiceAsync greetingService = GWT
			.create(GreetingService.class);

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
	
		final Button calButton = new Button("Calculate");
		
		final TextBox aField = new TextBox();
		final TextBox bField = new TextBox();
		final TextBox cField = new TextBox();
		
		final Label errorLabel = new Label();

		
		calButton.addStyleName("calButton");

		RootPanel.get("aFieldContainer").add(aField);
		RootPanel.get("bFieldContainer").add(bField);
		RootPanel.get("cFieldContainer").add(cField);
	
		RootPanel.get("calButtonContainer").add(calButton);
		RootPanel.get("errorLabelContainer").add(errorLabel);

	

		// Create the popup dialog box
		final DialogBox dialogBox = new DialogBox();
	dialogBox.setText("Remote Procedure Call");
		dialogBox.setAnimationEnabled(true);
		final Button closeButton = new Button("Close");
		// We can set the id of a widget by accessing its Element
		closeButton.getElement().setId("closeButton");
		final Label resultServerLabel = new Label();
		final HTML serverResponseLabel = new HTML();
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.addStyleName("dialogVPanel");
		dialogVPanel.add(new HTML("<b>Result:</b>"));
		dialogVPanel.add(resultServerLabel);
		dialogVPanel.add(new HTML("<br><b>Server replies:</b>"));
		dialogVPanel.add(serverResponseLabel);
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
		dialogVPanel.add(closeButton);
		dialogBox.setWidget(dialogVPanel);

		// Add a handler to close the DialogBox
		closeButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				dialogBox.hide();
				calButton.setEnabled(true);
				calButton.setFocus(true);
			}
		});

		// Create a handler for the sendButton and nameField
		class MyHandler1 implements ClickHandler, KeyUpHandler {
			/**
			 * Fired when the user clicks on the sendButton.
			 */
			public void onClick(ClickEvent event) {
				sendValues();
			}
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					sendValues();
				}
				}
				private void sendValues() {
					// First, we validate the input.
					errorLabel.setText("");
					String str1 = aField.getText();
					int a = Integer.valueOf(str1);
					String str2 = bField.getText();
					int b = Integer.valueOf(str2);
					String str3 = cField.getText();
					int c = Integer.valueOf(str3);
				/*	if (!FieldVerifier.isValidName(str1)) {
						errorLabel.setText("Please enter at least four characters");
						return;
					}*/

					// Then, we send the input to the server.
					calButton.setEnabled(false);
					
					resultServerLabel.setText("equation is "+str1+ "X^2+"+str2+ "X+" +str3+"=0");
					serverResponseLabel.setText("");
					greetingService.greetServer(a,b,c,
							new AsyncCallback<String>() {
								public void onFailure(Throwable caught) {
									// Show the RPC error message to the user
									dialogBox
											.setText("Remote Procedure Call - Failure");
									serverResponseLabel
											.addStyleName("serverResponseLabelError");
									serverResponseLabel.setHTML(SERVER_ERROR);
									dialogBox.center();
									closeButton.setFocus(true);
								}

								public void onSuccess(String result) {
									dialogBox.setText("GWT quadratic equation");
									serverResponseLabel
											.removeStyleName("serverResponseLabelError");
									serverResponseLabel.setHTML(result);
									dialogBox.center();
									closeButton.setFocus(true);
								}
							});
				}
			}

		// Add a handler to send the name to the server
		MyHandler1 handler = new MyHandler1();
		calButton.addClickHandler(handler);
		//nameField.addKeyUpHandler(handler);
	
}
}
