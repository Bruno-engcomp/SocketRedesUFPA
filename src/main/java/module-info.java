module br.com.dio.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;

    opens br.com.dio.tictactoe to javafx.fxml;
    exports br.com.dio.tictactoe;
}