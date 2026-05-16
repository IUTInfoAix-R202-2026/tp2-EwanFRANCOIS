package fr.univ_amu.iut.exercice8;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

/**
 * Exercice 8 (capstone) - Convertisseur de températures.
 *
 * <p>Cet exercice synthétise tous les types de bindings vus dans le TP :
 *
 * <ul>
 *   <li>Binding unidirectionnel (Labels de lecture)
 *   <li>Binding bidirectionnel (TextField ↔ Slider via {@link NumberStringConverter})
 *   <li>{@code ChangeListener} pour la conversion avec formule (C = (F-32)*5/9)
 *   <li>Sliders verticaux ({@code Orientation.VERTICAL})
 * </ul>
 *
 * <p>L'application affiche deux panneaux côte à côte : un pour Celsius, un pour Fahrenheit.
 * Modifier l'un met à jour l'autre automatiquement.
 */
public class ConvertisseurTemperatures extends Application {

  private boolean updating = false;

  @Override
  public void start(Stage primaryStage) {
    // TODO exercice 8 : construire le convertisseur de températures.
    //
    // 1. Créer le panneau Celsius (VBox) :
    //    - Label "°C" (style bold, 16px)
    //    - Slider vertical [0, 100], valeur initiale 0, id "slider-celsius"
    //    - TextField, id "tf-celsius", maxWidth 50
    //
    BorderPane squelette = new BorderPane();

    Label labelC = new Label("°C");
    labelC.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    Slider sliderC = new Slider(0, 100, 0);
    sliderC.setOrientation(Orientation.VERTICAL);
    sliderC.setId("slider-celsius");
    sliderC.setShowTickLabels(true);
    sliderC.setShowTickMarks(true);

    TextField tfC = new TextField();
    tfC.setId("tf-celsius");
    tfC.setMaxWidth(60);

    VBox vboxC = new VBox(10, labelC, sliderC, tfC);
    squelette.setTop(vboxC);
    // 2. Créer le panneau Fahrenheit (VBox) :
    //    - Label "°F" (style bold, 16px)
    //    - Slider vertical [0, 212], valeur initiale 32, id "slider-fahrenheit"
    //    - TextField, id "tf-fahrenheit", maxWidth 50
    //
    Slider sliderF = new Slider(32, 212, 0);
    Label labelF = new Label("°F");
    labelF.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");
    sliderF.setOrientation(Orientation.VERTICAL);
    sliderF.setId("slider-fahrenheit");
    sliderF.setShowTickLabels(true);
    sliderF.setShowTickMarks(true);

    TextField tfF = new TextField();
    tfF.setId("tf-fahrenheit");
    tfF.setMaxWidth(60);

    VBox vboxF = new VBox(10, labelF, sliderF, tfF);
    squelette.setBottom(vboxF);
    // 3. Ajouter un ChangeListener sur le slider Celsius :
    //    fahrenheit = celsius * 9/5 + 32
    //    (utiliser un flag "updating" pour éviter les boucles infinies)
    //
    sliderC
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                double fahrenheit = newVal.doubleValue() * 9 / 5 + 32;
                sliderF.setValue(fahrenheit);
                updating = false;
              }
            });
    // 4. Ajouter un ChangeListener sur le slider Fahrenheit :
    //    celsius = (fahrenheit - 32) * 5/9
    //
    sliderF
        .valueProperty()
        .addListener(
            (obs, oldVal, newVal) -> {
              if (!updating) {
                updating = true;
                double celsius = (newVal.doubleValue() - 32) * 5 / 9;
                sliderC.setValue(celsius);
                updating = false;
              }
            });
    // 5. Lier chaque TextField à son slider via
    //    Bindings.bindBidirectional(tf.textProperty(), slider.valueProperty(),
    //        new NumberStringConverter())
    //
    Bindings.bindBidirectional(
        tfC.textProperty(), sliderC.valueProperty(), new NumberStringConverter());
    Bindings.bindBidirectional(
        tfF.textProperty(), sliderF.valueProperty(), new NumberStringConverter());
    // 6. Composer les panneaux dans un HBox, créer la Scene, afficher.
    Scene scene = new Scene(squelette);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
