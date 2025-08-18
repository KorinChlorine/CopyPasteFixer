import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        TextArea input = new TextArea();
        TextArea output = new TextArea();
        output.setEditable(false);

        Button fixButton = new Button("Fix Text");
        fixButton.setOnAction(e -> {
            String text = input.getText();
            String fixed = text.replaceAll("\\s*\\n\\s*", " ").replaceAll("\\s+", " ").trim();
            output.setText(fixed);
        });

        VBox layout = new VBox(10, input, fixButton, output);
        Scene scene = new Scene(layout, 600, 400);

        stage.setScene(scene);
        stage.setTitle("Text Fixer");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
