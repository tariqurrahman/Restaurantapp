package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application{

    public static void main(String[] args){
        launch(args);
    }

    private void priceView(GridPane pane, List<String> items, List<Integer> prices){
        pane.getChildren().clear();
        VBox b1 = new VBox();
        VBox b2 = new VBox();
        int total = 0;
        for(int i = 0; i < items.size(); i++){
            b1.getChildren().add(new Label(items.get(i)));
            b2.getChildren().add(new Label("$" + prices.get(i)));
            total += prices.get(i);
        }
        if(total > 0){
            b2.getChildren().add(new Label("-------------"));
            b2.getChildren().add(new Label("Total = $" + total));
            b2.setAlignment(Pos.BASELINE_RIGHT);
        }
        HBox b = new HBox();
        b.getChildren().addAll(b1, b2);
        b.setSpacing(100);
        pane.add(b, 0,0,1,1);
    }

    private void deliveryPage(){
        Stage s = new Stage();
        VBox country = new VBox();

        country.getChildren().add(new Label("Country"));
        country.getChildren().add(new TextField());

        VBox resType = new VBox();
        ComboBox box = new ComboBox();
        box.getItems().addAll("Residence", "Business", "University");
        resType.getChildren().addAll(new Label("Address Type"), box);

        HBox hbox = new HBox();
        hbox.getChildren().addAll(country, resType);

        VBox address = new VBox();
        address.getChildren().addAll(new Label("Street Address"), new TextField());

        VBox apt = new VBox();
        apt.getChildren().add(new Label("Apt / Ste / Floor"));
        ComboBox aptBox = new ComboBox();
        aptBox.getItems().addAll("None", "Apt", "Suite", "Floor");
        aptBox.getSelectionModel().selectFirst();
        apt.getChildren().add(aptBox);
        TextField aptInfo = new TextField();
        aptInfo.setEditable(false);
        aptBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.print("ONN");
                if(aptBox.getValue().equals("None")){
                    aptInfo.setEditable(false);
                } else {
                    aptInfo.setEditable(true);
                }
            }
        });

        VBox zip = new VBox();
        zip.getChildren().addAll(new Label("Zip Code"), new TextField());
        Button deliver = new Button("Deliver");
        deliver.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });


        GridPane pane = new GridPane();
        pane.add(hbox, 0,0,1,1);
        pane.add(address, 0,1,2,1);
        pane.add(apt, 0,2,1,2);
        pane.add(aptInfo, 1,3,1,1);
        pane.add(zip, 0, 4,1,1);
        pane.add(deliver, 1,5,1,1);
        Scene sc = new Scene(pane,500, 500);
        s.setScene(sc);
        s.show();
    }

    private List<ComboBox> getMenu(){
        ComboBox sandwich = new ComboBox();
        sandwich.getItems().addAll("Sandwiches", "Hot Brown Sandwich", "Cheesteak Sandwich", "Hot Buffalo Sandwich", "Phily Cheese Steak Sandwich");
        sandwich.getSelectionModel().selectFirst();
        ComboBox salad = new ComboBox();
        salad.getItems().addAll("Salad", "Classic Garden", "Chicken Ceaser");
        salad.getSelectionModel().selectFirst();
        ComboBox drinks = new ComboBox();
        drinks.getItems().addAll("Drinks", "Coke", "Pepsi", "Fanta", "Sprite");
        drinks.getSelectionModel().selectFirst();
        List<ComboBox> box = new ArrayList<>();
        box.add(sandwich);
        box.add(salad);
        box.add(drinks);
        return box;
    }

    private void checkoutPage(List<String> menu, List<Integer> prices){
        Stage checkout = new Stage();
        checkout.setTitle("Checkout");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        GridPane textarea = new GridPane();
        priceView(textarea, menu, prices);

        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("delivery");
        comboBox.getItems().add("Pickup");
        comboBox.getSelectionModel().selectFirst();

        Label payment = new Label("Payment Section");
        TextField cardinfo =new TextField();
        TextField name =new TextField();
        TextField address=new TextField();


        Button Checkout=new Button();
        Checkout.setText("Checkout");
        Checkout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                if(comboBox.getValue().equals("delivery")){
                    deliveryPage();
                    checkout.close();
                } else {
                    System.exit(0);
                }
            }
        });

        pane.add(textarea,0,0,1,1);
        pane.add(comboBox,0,2,1,1);
        pane.add(payment,0,3,1,1);
        pane.add(cardinfo,0,4,1,1);
        pane.add(name,0,5,1,1);
        pane.add(address,0,6,1,1);
        pane.add(Checkout,0,7,1,1);

        Scene newscene = new Scene(pane,500,500);
        checkout.setScene(newscene);
        checkout.show();
    }

    private void writeToTextArea(TextArea area, List<String> content){
        String text = "";
        for(int i = 0; i < content.size(); i++){
            if(i > 0) text +="\n";
            text += content.get(i);
        }
        area.setText(text);
    }

    private void addHandlerForComboBox(GridPane area, List<ComboBox> box, List<String> selectedMenu, List<Integer> prices){
        for(int i = 0; i < box.size(); i++){
            final int x = i;
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e){
                    selectedMenu.add(box.get(x).getValue().toString());
                    if(x == 0) prices.add(8);
                    else prices.add(5);
                    priceView(area, selectedMenu, prices);
                }
            };
            box.get(i).setOnAction(event);
        }
    }

    private void orderPage(List<String>menu, List<Integer> prices){
        Stage employeeView = new Stage();
        employeeView.setTitle("Order");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        Button checkout = new Button();
        checkout.setText("Checkout");
        checkout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                checkoutPage(menu, prices);
                employeeView.close();
            }
        });

        GridPane textArea = new GridPane();
        priceView(textArea, menu, prices);

        Button add=new Button("Add Item");
        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = new Stage();
                List<ComboBox> box = getMenu();
                List<String> items = new ArrayList<>();
                List<Integer> newPrice = new ArrayList<>();
                items.addAll(menu);
                newPrice.addAll(prices);
                GridPane newTextArea = new GridPane();
                addHandlerForComboBox(newTextArea, box, items, newPrice);
                GridPane p = new GridPane();
                HBox hb = new HBox();
                hb.getChildren().addAll(box);
                Button ok = new Button("Apply");
                ok.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        menu.clear();
                        prices.clear();
                        menu.addAll(items);
                        prices.addAll(newPrice);
                        priceView(textArea, items, prices);
                        s.close();
                    }
                });
                Button cancel = new Button("Cancel");
                cancel.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        s.close();
                    }
                });
                p.add(hb, 0,0,1,1);
                p.add(newTextArea, 0, 2, 1, 1);
                HBox button = new HBox();
                button.getChildren().add(cancel);
                button.getChildren().add(ok);
                p.add(button, 0,3,1,1);
                Scene newScene = new Scene(p, 500, 500);
                s.setScene(newScene);
                s.show();
            }
        });

        Button remove= new Button("Remove Item");
        remove.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = new Stage();
                VBox vb = new VBox();
                List<String> items = new ArrayList<>();
                List<Integer> newPrice = new ArrayList<>();
                items.addAll(menu);
                newPrice.addAll(prices);
                for(int i = 0; i < menu.size(); i++){
                    HBox box = new HBox();
                    Label l = new Label(menu.get(i));
                    Button cross = new Button("X");
                    final int x = i;
                    cross.setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            items.remove(menu.get(x));
                            newPrice.remove(prices.get(x));
                            l.setText("");
                            cross.setText("");
                        }
                    });
                    box.getChildren().addAll(l, cross);
                    vb.getChildren().add(box);
                }
                Button ok = new Button("Apply");
                ok.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        menu.clear();
                        prices.clear();
                        menu.addAll(items);
                        prices.addAll(newPrice);
                        priceView(textArea, items, prices);
                        s.close();
                    }
                });
                GridPane p = new GridPane();
                p.add(vb, 0,1,1,1);
                p.add(ok, 0,2,1,1);
                Scene ss = new Scene(p, 500, 500);
                s.setScene(ss);
                s.show();
            }
        });
        pane.add(textArea, 0, 2,1,1);
        pane.add(add,0,3,1,1);
        pane.add(remove,0,4,1,1);
        pane.add(checkout,0,5,1,1);

        Scene newscene = new Scene(pane,500,500);
        employeeView.setScene(newscene);
        employeeView.show();
    }

    private void menuPage(){
        Stage customerview = new Stage();
        customerview.setTitle("Menu");

        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        List<ComboBox> box = getMenu();

        Button checkout = new Button();
        checkout.setText("Checkout");
        List<String> selectedMenu = new ArrayList<>();
        List<Integer> prices = new ArrayList<>();

        checkout.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                orderPage(selectedMenu, prices);
                customerview.close();
            }
        });

        GridPane textArea = new GridPane();
        addHandlerForComboBox(textArea, box, selectedMenu, prices);

        HBox box1 = new HBox();

        for(int i = 0; i < box.size(); i++){
            box1.getChildren().add(box.get(i));
        }
        pane.add(box1, 0, 1,1,1);
        pane.add(textArea, 0,2,1,1);
        pane.add(checkout,0,3,1,1);
        Scene newscene = new Scene(pane,500,500);
        customerview.setScene(newscene);
        customerview.show();
    }

    private void homePage(final Stage primaryStage){
        primaryStage.setTitle("Home");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        Label name = new Label("ABCD Restaurant");

        Button section = new Button();
        section.setText("Contact us");

        section.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage s = new Stage();
                Label phone = new Label("Phone: +18723823");
                Label email = new Label("Email: no-reply@no.com");
                Label address = new Label("Address: abcd park");
                VBox box = new VBox();
                box.getChildren().addAll(phone, email, address);
                Button close = new Button("Close");
                close.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        s.close();
                    }
                });
                GridPane p = new GridPane();
                p.add(box, 0, 1,1,1);
                p.add(close, 0,2,1,1);
                Scene ss = new Scene(p,500,500);
                s.setScene(ss);
                s.show();
            }
        });

        Button customer = new Button();
        customer.setText("Menu");

        customer.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent e){
                menuPage();
                primaryStage.close();
            }
        });

        HBox hb=new HBox();
        hb.getChildren().addAll(section,customer);
        hb.setSpacing(20);

        grid.add(name,0,0,1,1);
        grid.add(hb,0,1,1,1);

        Scene scene = new Scene(grid,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void start(final Stage primaryStage) throws Exception{
        homePage(primaryStage);
    }
}