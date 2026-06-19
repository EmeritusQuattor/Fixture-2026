package com.mundial;

import com.mundial.dao.GroupDAO;
import com.mundial.dao.MatchDAO;
import com.mundial.dao.TeamDAO;
import com.mundial.db.SchemaInitializer;
import com.mundial.model.Group;
import com.mundial.model.Match;
import com.mundial.model.Team;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;

public class Main extends Application {

    private GroupDAO groupDAO = new GroupDAO();
    private TeamDAO teamDAO = new TeamDAO();
    private MatchDAO matchDAO = new MatchDAO();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            SchemaInitializer.createTables();
            poblarDatosSiVacio();
        } catch (SQLException e) {
            mostrarError("Error de base de datos", e.getMessage());
        }

        TabPane tabPane = new TabPane();

        Tab tabGroups = new Tab("Grupos y Equipos");
        tabGroups.setContent(crearPanelGrupos());
        tabGroups.setClosable(false);

        Tab tabMatches = new Tab("Fixture");
        tabMatches.setContent(crearPanelFixture());
        tabMatches.setClosable(false);

        Tab tabResults = new Tab("Resultados");
        tabResults.setContent(crearPanelResultados());
        tabResults.setClosable(false);

        tabPane.getTabs().addAll(tabGroups, tabMatches, tabResults);

        Scene scene = new Scene(tabPane, 900, 600);
        stage.setTitle("Mundial 2026 - Fixture");
        stage.setScene(scene);
        stage.show();
    }

    private void poblarDatosSiVacio() throws SQLException {
        if (!groupDAO.findAll().isEmpty()) return;

        int gA = groupDAO.save(new Group(1, "A"));
        int gB = groupDAO.save(new Group(2, "B"));
        int gC = groupDAO.save(new Group(3, "C"));
        int gD = groupDAO.save(new Group(4, "D"));
        int gE = groupDAO.save(new Group(5, "E"));
        int gF = groupDAO.save(new Group(6, "F"));
        int gG = groupDAO.save(new Group(7, "G"));
        int gH = groupDAO.save(new Group(8, "H"));
        int gI = groupDAO.save(new Group(9, "I"));
        int gJ = groupDAO.save(new Group(10, "J"));
        int gK = groupDAO.save(new Group(11, "K"));
        int gL = groupDAO.save(new Group(12, "L"));

        teamDAO.save(new Team(0, "México", "México", gA));
        teamDAO.save(new Team(0, "Sudáfrica", "Sudáfrica", gA));
        teamDAO.save(new Team(0, "Corea del Sur", "Corea del Sur", gA));
        teamDAO.save(new Team(0, "Chequia", "República Checa", gA));

        teamDAO.save(new Team(0, "Canadá", "Canadá", gB));
        teamDAO.save(new Team(0, "Bosnia y Herzegovina", "Bosnia y Herzegovina", gB));
        teamDAO.save(new Team(0, "Qatar", "Qatar", gB));
        teamDAO.save(new Team(0, "Suiza", "Suiza", gB));

        teamDAO.save(new Team(0, "Brasil", "Brasil", gC));
        teamDAO.save(new Team(0, "Marruecos", "Marruecos", gC));
        teamDAO.save(new Team(0, "Haití", "Haití", gC));
        teamDAO.save(new Team(0, "Escocia", "Escocia", gC));

        teamDAO.save(new Team(0, "Estados Unidos", "Estados Unidos", gD));
        teamDAO.save(new Team(0, "Paraguay", "Paraguay", gD));
        teamDAO.save(new Team(0, "Australia", "Australia", gD));
        teamDAO.save(new Team(0, "Turquía", "Turquía", gD));

        teamDAO.save(new Team(0, "Alemania", "Alemania", gE));
        teamDAO.save(new Team(0, "Curazao", "Curazao", gE));
        teamDAO.save(new Team(0, "Costa de Marfil", "Costa de Marfil", gE));
        teamDAO.save(new Team(0, "Ecuador", "Ecuador", gE));

        teamDAO.save(new Team(0, "Países Bajos", "Países Bajos", gF));
        teamDAO.save(new Team(0, "Japón", "Japón", gF));
        teamDAO.save(new Team(0, "Suecia", "Suecia", gF));
        teamDAO.save(new Team(0, "Túnez", "Túnez", gF));

        teamDAO.save(new Team(0, "Bélgica", "Bélgica", gG));
        teamDAO.save(new Team(0, "Egipto", "Egipto", gG));
        teamDAO.save(new Team(0, "Irán", "Irán", gG));
        teamDAO.save(new Team(0, "Nueva Zelanda", "Nueva Zelanda", gG));

        teamDAO.save(new Team(0, "España", "España", gH));
        teamDAO.save(new Team(0, "Cabo Verde", "Cabo Verde", gH));
        teamDAO.save(new Team(0, "Arabia Saudita", "Arabia Saudita", gH));
        teamDAO.save(new Team(0, "Uruguay", "Uruguay", gH));

        teamDAO.save(new Team(0, "Francia", "Francia", gI));
        teamDAO.save(new Team(0, "Senegal", "Senegal", gI));
        teamDAO.save(new Team(0, "Irak", "Irak", gI));
        teamDAO.save(new Team(0, "Noruega", "Noruega", gI));

        teamDAO.save(new Team(0, "Argentina", "Argentina", gJ));
        teamDAO.save(new Team(0, "Argelia", "Argelia", gJ));
        teamDAO.save(new Team(0, "Austria", "Austria", gJ));
        teamDAO.save(new Team(0, "Jordania", "Jordania", gJ));

        teamDAO.save(new Team(0, "Portugal", "Portugal", gK));
        teamDAO.save(new Team(0, "R.D. Congo", "República Democrática del Congo", gK));
        teamDAO.save(new Team(0, "Uzbekistán", "Uzbekistán", gK));
        teamDAO.save(new Team(0, "Colombia", "Colombia", gK));

        teamDAO.save(new Team(0, "Inglaterra", "Inglaterra", gL));
        teamDAO.save(new Team(0, "Croacia", "Croacia", gL));
        teamDAO.save(new Team(0, "Ghana", "Ghana", gL));
        teamDAO.save(new Team(0, "Panamá", "Panamá", gL));
    }

    private ScrollPane crearPanelGrupos() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        try {
            List<Group> groups = groupDAO.findAll();
            for (Group g : groups) {
                Label label = new Label("Grupo " + g.getName());
                label.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

                TableView<Team> table = new TableView<>();
                table.setPrefHeight(120);

                TableColumn<Team, String> colName = new TableColumn<>("Equipo");
                colName.setCellValueFactory(new PropertyValueFactory<>("name"));
                colName.setPrefWidth(200);

                TableColumn<Team, String> colCountry = new TableColumn<>("País");
                colCountry.setCellValueFactory(new PropertyValueFactory<>("country"));
                colCountry.setPrefWidth(200);

                table.getColumns().addAll(colName, colCountry);

                List<Team> teams = teamDAO.findAll().stream()
                        .filter(t -> t.getGroupId() == g.getId())
                        .toList();
                table.getItems().addAll(teams);

                container.getChildren().addAll(label, table);
            }
        } catch (SQLException e) {
            container.getChildren().add(new Label("Error: " + e.getMessage()));
        }

        return new ScrollPane(container);
    }

    private ScrollPane crearPanelFixture() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        try {
            List<Match> matches = matchDAO.findAll();
            if (matches.isEmpty()) {
                container.getChildren().add(new Label("No hay partidos cargados. Agregue resultados en la pestaña Resultados."));
            } else {
                TableView<Match> table = new TableView<>();
                TableColumn<Match, Integer> colLocal = new TableColumn<>("Local");
                colLocal.setCellValueFactory(new PropertyValueFactory<>("localId"));
                TableColumn<Match, Integer> colVisitor = new TableColumn<>("Visitante");
                colVisitor.setCellValueFactory(new PropertyValueFactory<>("visitorId"));
                TableColumn<Match, String> colDate = new TableColumn<>("Fecha");
                colDate.setCellValueFactory(new PropertyValueFactory<>("date"));
                TableColumn<Match, String> colPhase = new TableColumn<>("Fase");
                colPhase.setCellValueFactory(new PropertyValueFactory<>("phase"));
                TableColumn<Match, String> colStatus = new TableColumn<>("Estado");
                colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

                table.getColumns().addAll(colLocal, colVisitor, colDate, colPhase, colStatus);
                table.getItems().addAll(matches);
                container.getChildren().add(table);
            }
        } catch (SQLException e) {
            container.getChildren().add(new Label("Error: " + e.getMessage()));
        }

        return new ScrollPane(container);
    }

    private ScrollPane crearPanelResultados() {
        VBox container = new VBox(10);
        container.setPadding(new Insets(10));

        Label lblLocal = new Label("Equipo Local:");
        ComboBox<String> cbLocal = new ComboBox<>();
        Label lblVisitor = new Label("Equipo Visitante:");
        ComboBox<String> cbVisitor = new ComboBox<>();
        Label lblGoalsLocal = new Label("Goles Local:");
        TextField tfGoalsLocal = new TextField();
        Label lblGoalsVisitor = new Label("Goles Visitante:");
        TextField tfGoalsVisitor = new TextField();
        Label lblPhase = new Label("Fase:");
        ComboBox<String> cbPhase = new ComboBox<>();
        cbPhase.getItems().addAll("Grupo A", "Grupo B", "Grupo C", "Grupo D", "Grupo E", "Grupo F",
                "Grupo G", "Grupo H", "Grupo I", "Grupo J", "Grupo K", "Grupo L",
                "16avos", "Octavos", "Cuartos", "Semis", "Final");
        Label lblDate = new Label("Fecha (YYYY-MM-DD):");
        TextField tfDate = new TextField();

        try {
            List<Team> allTeams = teamDAO.findAll();
            allTeams.forEach(t -> {
                cbLocal.getItems().add(t.getName());
                cbVisitor.getItems().add(t.getName());
            });
        } catch (SQLException e) {
            container.getChildren().add(new Label("Error cargando equipos: " + e.getMessage()));
        }

        Button btnSave = new Button("Guardar Partido");
        TextArea log = new TextArea();
        log.setEditable(false);
        log.setPrefHeight(100);

        btnSave.setOnAction(e -> {
            try {
                String localName = cbLocal.getValue();
                String visitorName = cbVisitor.getValue();
                if (localName == null || visitorName == null || localName.equals(visitorName)) {
                    log.setText("Seleccione dos equipos diferentes.");
                    return;
                }
                int gl = Integer.parseInt(tfGoalsLocal.getText());
                int gv = Integer.parseInt(tfGoalsVisitor.getText());
                String phase = cbPhase.getValue();
                String date = tfDate.getText();
                if (phase == null || date.isEmpty()) {
                    log.setText("Complete fase y fecha.");
                    return;
                }

                Team local = teamDAO.findAll().stream().filter(t -> t.getName().equals(localName)).findFirst().orElse(null);
                Team visitor = teamDAO.findAll().stream().filter(t -> t.getName().equals(visitorName)).findFirst().orElse(null);
                if (local == null || visitor == null) {
                    log.setText("Error seleccionando equipos.");
                    return;
                }

                matchDAO.save(new Match(0, local.getId(), visitor.getId(), local.getGroupId(),
                        phase, date, gl, gv, gl > gv ? "Local" : gl < gv ? "Visitante" : "Empate"));
                log.setText("Partido guardado: " + localName + " " + gl + " - " + gv + " " + visitorName);
            } catch (NumberFormatException ex) {
                log.setText("Ingrese goles válidos.");
            } catch (SQLException ex) {
                log.setText("Error SQL: " + ex.getMessage());
            }
        });

        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        form.add(lblLocal, 0, 0); form.add(cbLocal, 1, 0);
        form.add(lblVisitor, 0, 1); form.add(cbVisitor, 1, 1);
        form.add(lblGoalsLocal, 0, 2); form.add(tfGoalsLocal, 1, 2);
        form.add(lblGoalsVisitor, 0, 3); form.add(tfGoalsVisitor, 1, 3);
        form.add(lblPhase, 0, 4); form.add(cbPhase, 1, 4);
        form.add(lblDate, 0, 5); form.add(tfDate, 1, 5);
        form.add(btnSave, 1, 6);
        form.add(log, 0, 7, 2, 1);

        container.getChildren().add(form);
        return new ScrollPane(container);
    }

    private void mostrarError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
