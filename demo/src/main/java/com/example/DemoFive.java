
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
import javafx.scene.shape.Mesh;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.shape.TriangleMesh;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.embed.swing.SwingFXUtils;

public class DemoFive extends Application {

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
 
       
        Shape3D testshape = new Sphere(10);
        testshape.setMaterial(new PhongMaterial(Color.BLUE));
        testshape.setDrawMode(DrawMode.LINE);


        TriangleMesh mesh = new TriangleMesh();
        float width = 20;
        float height = 20;
        
         float[] points = {
            -width/2,  height/2, 0, // idx p0
            -width/2, -height/2, 0, // idx p1
             width/2,  height/2, 0, // idx p2
             width/2, -height/2, 0  // idx p3
        };
        float[] texCoords = {
            1, 1, // idx t0
            1, 0, // idx t1
            0, 1, // idx t2
            0, 0  // idx t3
        };
        /**
         * points:
         * 1      3
         *  -------   texture:
         *  |\    |  1,1    1,0
         *  | \   |    -------
         *  |  \  |    |     |
         *  |   \ |    |     |
         *  |    \|    -------
         *  -------  0,1    0,0
         * 0      2
         *
         * texture[3] 0,0 maps to vertex 2
         * texture[2] 0,1 maps to vertex 0
         * texture[0] 1,1 maps to vertex 1
         * texture[1] 1,0 maps to vertex 3
         * 
         * Two triangles define rectangular faces:
         * p0, t0, p1, t1, p2, t2 // First triangle of a textured rectangle 
         * p0, t0, p2, t2, p3, t3 // Second triangle of a textured rectangle
         */
        int[] faces = {
            2, 2, 1, 1, 0, 0,
            2, 2, 3, 3, 1, 1,
            2, 3, 0, 2, 1, 0,
            2, 3, 1, 0, 3, 1
        };

        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texCoords);
        mesh.getFaces().setAll(faces);

        MeshView mv = new MeshView(mesh);

        PhongMaterial mat = new PhongMaterial();
        Image diffuseMap = new Image("Zebra_print_pattern.png");
        mat.setDiffuseMap(diffuseMap);
        mv.setMaterial(mat);

        

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
        // root.getChildren().add(testshape);
        root.getChildren().add(mv);
 
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
