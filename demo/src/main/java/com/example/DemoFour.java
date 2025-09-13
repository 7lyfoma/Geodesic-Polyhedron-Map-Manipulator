
package com.example;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

public class DemoFour extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private double dragStartX = -1;
    private double dragStartY = -1;

    private double dragStopX = -1;
    private double dragStopY = -1;

    private boolean hasDragged = false;

    @Override
    public void start(Stage primaryStage) {
        Scene scene = new Scene(createContent(), 800, 800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

   public Parent createContent(){
 
        // Shape3D testshape = new Cylinder(10, 10);
        // Shape3D testshape = new Box(10,10,10);
        Shape3D testshape = new Sphere(10);
        
        
        //Create Material
        PhongMaterial mat = new PhongMaterial();
        Image diffuseMap = new Image("Zebra_print_pattern.png");
        // Image normalMap = new Image("Zebra_print_pattern.png");

        mat.setDiffuseMap(diffuseMap);
        // // mat.setBumpMap(normalMap);
        // mat.setSpecularColor(Color.WHITE);

        testshape.setMaterial(mat);
        // testshape.setMaterial(new PhongMaterial(Color.ORANGE));
        // testshape.setDrawMode(DrawMode.LINE);

        Image i =  diffuseMap; //(new PhongMaterial(Color.ORANGE)).getDiffuseMap();

        File f = new File("test.png");

        try {
            Boolean x = ImageIO.write(SwingFXUtils.fromFXImage(i, null), "png", f);
            System.out.println(x);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println(f.getAbsolutePath());

        



        // Create and position camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.getTransforms().addAll (
                new Rotate(-15, Rotate.X_AXIS),
                new Translate(0, 0, -100));
                
        //Determines how far something has to be away to not be rendered
        camera.setFarClip(200);

 
        // Build the Scene Graph
        Group root = new Group();       
        root.getChildren().add(camera);
        root.getChildren().add(testshape);
 
        // Use a SubScene       
        SubScene subScene = new SubScene(root, 800,800);
        subScene.setFill(Color.ALICEBLUE);
        subScene.setCamera(camera);

         subScene.addEventHandler(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (hasDragged){
                    dragStopX = e.getSceneX();
                    dragStopY = e.getSceneY();
                
                    double deltaX = dragStartX - dragStopX;
                    double deltaY = dragStartY - dragStopY;

                    System.out.println(deltaY);



                    double pitch = (-18 * ((deltaX/100)));
                    double yaw = (18 * ((deltaY/100)));

                    camera.getTransforms().addAll(
                    new Rotate(pitch, 0, 0, 100, Rotate.Y_AXIS),
                    new Rotate(yaw, 0, 0, 100, Rotate.X_AXIS)

                    );

                    dragStartX = dragStopX;
                    dragStartY = dragStopY;
                }
                else {
                    dragStartX = e.getSceneX();
                    dragStartY = e.getSceneY();
                    hasDragged = true;
                }
                
            }            
        });

        subScene.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                hasDragged = false;
            }            
        });

        Group group = new Group();
        group.getChildren().add(subScene);
        return group;
    }

}
