import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        TextArea input = new TextArea();
        TextArea output = new TextArea();
        output.setEditable(false);

        Button fixButton = new Button("Fix Text");
        Button copyButton = new Button("Copy Output");
        Button clearButton = new Button("Clear");

        fixButton.setOnAction(e -> {
            String text = input.getText();
            String fixed = text.replaceAll("\\s*\\n\\s*", " ").replaceAll("\\s+", " ").trim();
            output.setText(fixed);
        });

        copyButton.setOnAction(e -> {
            ClipboardContent content = new ClipboardContent();
            content.putString(output.getText());
            Clipboard.getSystemClipboard().setContent(content);
        });

        clearButton.setOnAction(e -> {
            input.clear();
            output.clear();
        });

        HBox controls = new HBox(10, fixButton, copyButton, clearButton);

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(e -> stage.close());
        fileMenu.getItems().add(exitItem);
        menuBar.getMenus().add(fileMenu);

        BorderPane layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(new SplitPane(input, output));
        layout.setBottom(controls);

        Scene scene = new Scene(layout, 800, 500);
        stage.setScene(scene);
        stage.setTitle("Text Fixer");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
