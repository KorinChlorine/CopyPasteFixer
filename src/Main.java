import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class Main extends Application {
    private static void applyFont(Text t, boolean bold, boolean italic) {
        Font f = t.getFont();
        t.setFont(Font.font(f.getFamily(), bold ? FontWeight.BOLD : FontWeight.NORMAL, italic ? FontPosture.ITALIC : FontPosture.REGULAR, f.getSize()));
    }

    @Override
    public void start(Stage stage) {
        TextArea input = new TextArea();

        Text styledText = new Text();
        TextFlow outputFlow = new TextFlow(styledText);
        ScrollPane outputPane = new ScrollPane(outputFlow);
        outputPane.setFitToWidth(true);

        Button copyButton = new Button("Copy Output");
        Button clearButton = new Button("Clear");
        ToggleButton boldButton = new ToggleButton("Bold");
        ToggleButton italicButton = new ToggleButton("Italic");
        CheckBox alwaysOnTop = new CheckBox("Always on Top");

        Label reminder = new Label("✔ Fixed text is copied to clipboard automatically");
        reminder.setStyle("-fx-font-size: 11px; -fx-text-fill: gray;");

        Runnable fixAndCopy = () -> {
            String text = input.getText();
            String fixed = text.replaceAll("\\s*\\n\\s*", " ").replaceAll("\\s+", " ").trim();
            styledText.setText(fixed);
            ClipboardContent content = new ClipboardContent();
            content.putString(fixed);
            Clipboard.getSystemClipboard().setContent(content);
        };

        copyButton.setOnAction(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(styledText.getText());
            Clipboard.getSystemClipboard().setContent(content);
        });
        clearButton.setOnAction(e -> {
            input.clear();
            styledText.setText("");
        });

        boldButton.selectedProperty().addListener((obs, was, is) -> applyFont(styledText, is, italicButton.isSelected()));
        italicButton.selectedProperty().addListener((obs, was, is) -> applyFont(styledText, boldButton.isSelected(), is));

        alwaysOnTop.selectedProperty().addListener((obs, was, is) -> stage.setAlwaysOnTop(is));

        input.textProperty().addListener((obs, old, val) -> fixAndCopy.run());

        HBox controls = new HBox(10, copyButton, clearButton, boldButton, italicButton, alwaysOnTop);

        BorderPane bottomBar = new BorderPane();
        bottomBar.setLeft(controls);
        BorderPane.setAlignment(reminder, Pos.CENTER_RIGHT);
        bottomBar.setRight(reminder);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> stage.close());
        fileMenu.getItems().add(exitItem);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutItem = new MenuItem("About");
        aboutItem.setOnAction(e -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("About");
            alert.setHeaderText("Made by Kharl P. Asuncion");
            alert.setContentText("This app was made to reduce time of reformatting broken texts from PPTs and slides, so I could paste it directly in Knowt.");
            alert.showAndWait();
        });
        helpMenu.getItems().add(aboutItem);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        SplitPane center = new SplitPane(input, outputPane);
        center.setDividerPositions(0.5);

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(center);
        layout.setBottom(bottomBar);

        Scene scene = new Scene(layout, 900, 550);
        stage.setScene(scene);
        stage.setTitle("Text Fixer");
        stage.show();

        applyFont(styledText, false, false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
