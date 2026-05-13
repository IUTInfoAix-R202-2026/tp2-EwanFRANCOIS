package fr.univ_amu.iut.exercice7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 7 - Cercle interactif avec binding bidirectionnel.
 *
 * <p>Trois contrôles sont synchronisés en permanence :
 *
 * <ul>
 *   <li>Un {@link Circle} dont le rayon change visuellement
 *   <li>Un {@link Slider} qui contrôle le rayon
 *   <li>Un {@link TextField} qui affiche et permet de saisir le rayon
 * </ul>
 *
 * <p>Modifier l'un des trois met à jour les deux autres automatiquement grâce à {@code
 * bindBidirectional()}.
 *
 * <p>Concepts :
 *
 * <ul>
 *   <li>{@code bindBidirectional()} entre Slider et Circle
 *   <li>{@code Bindings.bindBidirectional()} avec {@link NumberStringConverter}
 *   <li>{@link TextFormatter} avec filtre de validation
 *   <li>Binding unidirectionnel pour le centrage du cercle
 * </ul>
 */
public class CercleInteractif extends Application {

  private final Circle cercle = new Circle();
  private final Slider slider = new Slider();
  private final TextField tfRayon = new TextField();
  private final Pane panneauCercle = new Pane();
  private final BorderPane root = new BorderPane();

  @Override
  public void start(Stage primaryStage) {
    // TODO exercice 7 : assembler l'interface et créer les bindings.
    //
    // 1. Appeler ajouterPanneau() pour configurer le panneau avec le cercle.
    ajouterPanneau();
    // 2. Appeler ajouterSlider() pour configurer le slider en haut.
    ajouterSlider();
    // 3. Appeler ajouterTextField() pour ajouter le champ de saisie en bas.
    ajouterTextField();
    // 4. Appeler creerBindings() pour lier les trois contrôles.
    creerBindings();
    // 5. Créer la Scene, l'attacher au Stage, afficher.
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  void ajouterPanneau() {
    // TODO exercice 7 : ajouter le panneau avec le cercle.
    //
    // panneauCercle.getChildren().add(cercle)
    panneauCercle.getChildren().add(cercle);
    // panneauCercle.setPrefSize(500, 500)
    panneauCercle.setPrefSize(500, 500);
    // panneauCercle.setId("panneau")
    panneauCercle.setId("panneau");
    // cercle.setId("cercle")
    cercle.setId("cercle");
    // root.setCenter(panneauCercle)
    root.setCenter(panneauCercle);
  }

  void ajouterSlider() {
    // TODO exercice 7 : configurer le slider [0, 250] et le placer en haut.
    //
    // slider.setMin(0), slider.setMax(250)
    slider.setMin(0);
    slider.setMax(250);
    // slider.setId("slider")
    slider.setId("slider");
    // root.setTop(slider)
    root.setTop(slider);
  }

  void ajouterTextField() {
    // TODO exercice 7 : ajouter le TextField avec un TextFormatter en bas.
    //
    // Le TextFormatter filtre la saisie pour n'accepter que des nombres.
    // Ce code est fourni - ne pas modifier.
    tfRayon.setId("rayon");
    root.setBottom(tfRayon);
  }

  void creerBindings() {
    // TODO exercice 7 : créer les bindings bidirectionnels.
    //
    // 1. Centrer le cercle dans le panneau (unidirectionnel) :
    //    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2))
    //    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2))
    //
    cercle.centerXProperty().bind(panneauCercle.widthProperty().divide(2));
    cercle.centerYProperty().bind(panneauCercle.heightProperty().divide(2));
    // 2. Lier le rayon du cercle au slider (bidirectionnel) :
    //    cercle.radiusProperty().bindBidirectional(slider.valueProperty())
    //
    cercle.radiusProperty().bindBidirectional(slider.valueProperty());
    // 3. Lier le slider au TextField via NumberStringConverter :
    //    Bindings.bindBidirectional(textField.textProperty(),
    //        slider.valueProperty(), new NumberStringConverter())
    //
    TextFormatter<Number> formatter = new TextFormatter<>(new NumberStringConverter());
    tfRayon.setTextFormatter(formatter);
    // 4. Initialiser le rayon à 150 :
    //    slider.setValue(150)
    formatter.valueProperty().bindBidirectional(slider.valueProperty());
    slider.setValue(150);
  }

  public static void main(String[] args) {
    launch(args);
  }
}
