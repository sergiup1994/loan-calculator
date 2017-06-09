//** This Program allows the user to calculate mortgage and car payments. The user is asked to enter 
// * and amount and a duration for the loan. A total (including interest) is generated and payments 
// * are calculated on a weekly, monthly or yearly basis. 
// * There is error checking to ensure that suitable values are entered. 
// */
// imports
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.text.DecimalFormat;

// class definition
public class LoanCalculator extends Application {
    @Override
	public void init() {
		
		//setting a vertical and horizontal gap between the elements on the grid pane
		gp= new GridPane();
	    gp.setVgap(5);
	    gp.setHgap(5);
	    
	    //creating the labels and the text fields
	    lb_loan_type= new Label("Loan type");
	    GridPane.setConstraints(lb_loan_type, 0, 0);
	    gp.getChildren().add(lb_loan_type);
	    
	    lb_loan_amount= new Label("Loan amount");
	    GridPane.setConstraints(lb_loan_amount, 0, 2);
	    gp.getChildren().add(lb_loan_amount);
	    
        tf_loan_amount = new TextField();
		tf_loan_amount.getText();
        GridPane.setConstraints(tf_loan_amount, 2, 2);
        gp.getChildren().add(tf_loan_amount);
        
        
        lb_interest_rate= new Label("Intrest rate");
        GridPane.setConstraints(lb_interest_rate, 0, 4);
        gp.getChildren().add(lb_interest_rate);
        
        tf_interest_rate = new TextField();
        tf_interest_rate.setText("0.05");
        GridPane.setConstraints(tf_interest_rate, 2, 4);
        gp.getChildren().add(tf_interest_rate);
        
        
        lb_loan_duration = new Label("Loan duration");
        GridPane.setConstraints(lb_loan_duration, 0, 6);
        gp.getChildren().add(lb_loan_duration);
        
        tf_duration = new TextField();
        tf_duration.setText("15");
        GridPane.setConstraints(tf_duration, 2, 6);
        gp.getChildren().add(tf_duration);
        
        
        lb_total = new Label("Total(intrest)");
        GridPane.setConstraints(lb_total, 0, 8);
        gp.getChildren().add(lb_total);
        
        tf_total = new TextField();
        GridPane.setConstraints(tf_total, 2, 8);
        gp.getChildren().add(tf_total);
        
       
        lb_credit_cost = new Label("Credit Cost");
        GridPane.setConstraints(lb_credit_cost, 0, 10);
        gp.getChildren().add(lb_credit_cost);
        
        tf_credit_cost = new TextField();
        tf_credit_cost.setEditable(false);
        GridPane.setConstraints(tf_credit_cost, 2, 10);
        gp.getChildren().add(tf_credit_cost);
        
        
        lb_payments = new Label("Payments");
        GridPane.setConstraints(lb_payments, 0, 12);
        gp.getChildren().add(lb_payments);
        
        tf_payments = new TextField();
        GridPane.setConstraints(tf_payments, 2, 12);
        gp.getChildren().add(tf_payments);
        
        //creating the buttons 
        Button bt_calculate = new Button("Calculate");
        GridPane.setConstraints(bt_calculate, 0, 15);
        gp.getChildren().add(bt_calculate);
		
        Button bt_clear = new Button("clear");
        GridPane.setConstraints(bt_clear, 2, 15);
        gp.getChildren().add(bt_clear);
		
        //Populate the ComboBoxes
        
        cb_loan_type = new ComboBox<>();
	    cb_loan_type.setValue("Mortgage");
		cb_loan_type.getItems().addAll("Mortgage","Car");
		GridPane.setConstraints(cb_loan_type, 2, 0);
		
		cb_interest_rate_type = new ComboBox<>();
		cb_interest_rate_type.setValue("Fixed");
		cb_interest_rate_type.getItems().addAll("Variable","Fixed");
		GridPane.setConstraints(cb_interest_rate_type, 3, 4);
        
		cb_payments = new ComboBox<>();
		cb_payments.setValue("Yearly");
		cb_payments.getItems().addAll("Weekly","Monthly","Yearly");
		GridPane.setConstraints(cb_payments, 3, 12);
		
		
		//Set certain fields as non editable to avoid the user interfering with results	
		tf_interest_rate.setEditable(false);
		tf_duration.setEditable(false);
		tf_total.setEditable(false);
		tf_payments.setEditable(false);

		//Layout controls
		
		
		//Method call to initialize Interest Rates TextField based on the Loan Type and Loan Interest Type
		
		
		
		//Method call to initialize the Slider and the Loan Duration TextField
	    slider_loan_duration = new Slider(0, 30, 15);
		GridPane.setConstraints(slider_loan_duration, 3,6);
		gp.getChildren().addAll(slider_loan_duration );
		
		
		slider_loan_duration.setShowTickMarks(true);
		slider_loan_duration.setShowTickLabels(true);
		slider_loan_duration.setMajorTickUnit(10);
		slider_loan_duration.setBlockIncrement(1);
		
		
       //5 Event Handlers, one for each button. Each will call a method. Some will call the same methods. 
		tf_duration.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				tf_duration.getText();
				setSlider();
			}
		});
        slider_loan_duration.valueProperty().addListener(new ChangeListener<Number>(){
			public void changed(final ObservableValue<? extends Number> observable, final Number oldValue, final Number newValue){
				tf_duration.setText(String.valueOf(newValue.intValue()));
				calculateTotal();
				calculatePayments();
			}
		});
	    bt_calculate.setOnAction(new EventHandler<ActionEvent>(){
	    	@Override
	    	public void handle(ActionEvent event){
	    		calculateTotal();
	    		calculatePayments();
	    	}
	    });
	    
	    bt_clear.setOnAction(new EventHandler<ActionEvent>(){
	    	@Override
	    	public void handle(ActionEvent event){
	    		clear();
	    		
	    	}
	    });
	    //Action Listener for slider to set a variable and TextField
		gp.getChildren().addAll(cb_loan_type);
		cb_loan_type.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				cb_loan_type.getValue();
				calculateTotal();
				calculatePayments();
				setSlider();
				setRate();
				clear();
				
				
			}
			
		});
		
		gp.getChildren().addAll(cb_interest_rate_type);
		cb_interest_rate_type.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				cb_interest_rate_type.getValue();
				setRate();
				calculateTotal();
				calculatePayments();
				
				
			}
			
		});
		
		gp.getChildren().addAll(cb_payments);
		cb_payments.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				cb_payments.getValue();
				calculatePayments();
				
				
			}
			
		});
		
		tf_loan_amount.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				tf_loan_amount.getText();
			    
			}
		});
		
		 tf_interest_rate.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
				 tf_interest_rate.getText();
				 
				 setRate();
			}
		});
		 
	        tf_total.setOnAction(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event){
		        tf_total.getText();
		        calculateTotal();
		        clear();
				
				
			}
		});
	    
	        tf_credit_cost.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent event){
					tf_credit_cost.getText();
					
					clear();
					
					
					
				}
			});
	        
	}

	//Overridden start method
	public void start(Stage primaryStage) {
		// set a title on the window, set a scene, size, and show the window
		primaryStage.setTitle("calculator");
		primaryStage.setScene(new Scene(gp, 400, 300));
		primaryStage.show();
	}

	//Overridden stop method
	@Override
	public void stop() {}

	//Entry point to our program
	public static void main(String[] args) {
		launch(args);
		
	
	}
	
	
	//Method to calculate - the total (including interest) & the credit cost and set these TextFields.
	//It should set some variables with values read from TextFields and catch errors in this process.
	//Formula to calculate compound interest can be found here  http://bit.ly/1MtJqSw. The interest should be
	//compounded once per year. This should also calculate the payments by calling another method.
	private void calculateTotal(){
		
		//Decimal format is creating the format of total 
		DecimalFormat df = new DecimalFormat("#,##0.00");
		//get user input for mortgage amount
		double d_loan_amount = Integer.parseInt(tf_loan_amount.getText());
		//get input for duration
		double  d_loan_duration = Double.parseDouble(tf_duration.getText());
		//get input for rate
		double  d_interest_rate = Double.parseDouble(tf_interest_rate.getText());
		//calculate total	
		double total = d_loan_amount *Math.pow(1+d_interest_rate, d_loan_duration );
		//set the text for total text field
		tf_total.setText(" "+df.format(total));
		//calculate credit cost
		double credit_cost= (d_loan_amount *Math.pow(1+ d_interest_rate, d_loan_duration))-d_loan_amount;
		//set the text for credit cost text field
		tf_credit_cost.setText(" "+df.format(credit_cost));
		
		
	}
	//Method to calculate the payments and display them in the relevant TextField
	//If the total has not been calculated then an error message will be displayed 
	//in the TextField.
	private void calculatePayments(){
		//setting the format of payments
		DecimalFormat df = new DecimalFormat("#,##0.00");
		//input for amount
		double d_loan_amount = Integer.parseInt(tf_loan_amount.getText());
		//input for duration
		double  d_loan_duration = Double.parseDouble(tf_duration.getText());
		//input for interest rate
		double  d_interest_rate = Double.parseDouble(tf_interest_rate.getText());
			
		
		//check if the combo box contains "Yearly"
		if(cb_payments.getValue()=="Yearly"){
			//do calculation
			double payments = (d_loan_amount *Math.pow(1+ d_interest_rate, d_loan_duration))/d_loan_duration;
			// set text for payments
			tf_payments.setText(" "+df.format(payments));
			
		}
		//check if the combo box contains "Monthly"
		else if(cb_payments.getValue()=="Monthly"){
			//do calculation
			double payments = (d_loan_amount *Math.pow(1+ d_interest_rate, d_loan_duration))/(d_loan_duration*12);
			//set text for payments
			tf_payments.setText(" "+df.format(payments));
		}
		else{
			double payments = (d_loan_amount *Math.pow(1+ d_interest_rate, d_loan_duration))/(d_loan_duration*53);
			tf_payments.setText(" "+df.format(payments));
		}
		
		
		
	}

	//Method to set the Slider based on the Loan Type
	private void setSlider(){
		//check if combo box contains car
		if(cb_loan_type.getValue()=="Car"){
			//set the maximum size of the slider to 10
			slider_loan_duration.setMax(10);
			// set the of the pointer on the slider to 5
			slider_loan_duration.setValue(5);
		}
		else{
			//in this case the combo box must contain Mortgage
			//set the maximum size of the slider to 30
			slider_loan_duration.setMax(30);
			//set the pointer on the slider to 15
			slider_loan_duration.setValue(15);
			
		}
		
		
		
		
		
		
	}

	//Method to set the rate based on the Loan Type and the Interest Rate Type
	private void setRate(){
		
		//if and else statements to set the appropriate interest rates in the interest rate text field according to the loan
		//type and interest rate type
		if(cb_loan_type.getValue()=="Mortgage"&& cb_interest_rate_type.getValue() =="Fixed" ){
			tf_interest_rate.setText("0.05");
			
			
		}
		else if(cb_loan_type.getValue()=="Mortgage"&& cb_interest_rate_type.getValue() =="Variable" ){
			tf_interest_rate.setText("0.034");
			
		}
		else if(cb_loan_type.getValue()=="Car"&& cb_interest_rate_type.getValue() =="Fixed" ){
			tf_interest_rate.setText("0.11");
			
		}
		else{
			tf_interest_rate.setText("0.09");
		}
		
	}
	
	
	

	//Method to clear all the TextFields and update the Interest Rate and Loan Duration TextFields.
	private void clear(){
		//clear the following text fields when the combo box or clear buttons are pressed
		tf_total.setText("");
		tf_credit_cost.setText("");
		tf_payments.setText("");
		
		
	}

	//The following variables should be initialized in appropriate location. 
	//For the purposes of this practical you may to choose to initialize most of them here.
	
	//Layout
	private GridPane gp;

	//Loan Type
	private Label lb_loan_type;
	private ComboBox<String> cb_loan_type;

	//Loan Amount
	private Label lb_loan_amount;
	private TextField tf_loan_amount;
	private double d_loan_amount;

	//Interest Rate
	private Label lb_interest_rate;
	private TextField tf_interest_rate;
	private ComboBox<String> cb_interest_rate_type;
	private double d_interest_rate;
	//Values 0 and 1 refer to Variable Rate and Fixed Rate Mortgages respectively 
	//Values 2 and 3 refer to Variable Rate and Fixed Rate Car Loans respectively 
	private final double[] INTEREST_RATES = {0.034,.05,.09,.11};

	//Loan Duration
	private Label lb_loan_duration;
	private TextField tf_duration;
	private double d_loan_duration;
	private Slider slider_loan_duration;
	//Formating the values in the duration box
	DecimalFormat df;

	//Total (including interest)
	private Label lb_total;
	private TextField tf_total;
	private double total;

	//Credit Cost
	private Label lb_credit_cost;
	private TextField tf_credit_cost;
	private double credit_cost;

	//Payments
	private Label lb_payments;
	private TextField tf_payments;
	private ComboBox<String> cb_payments;
	private double payments;

	//Calculate Button
	private Button bt_calculate;
	
	//Clear Button
	private Button bt_clear;
}



