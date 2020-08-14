package com.seerofspace.torus;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Controller {
	
	@FXML private AnchorPane anchorPane;
	@FXML private Pane sideMenu;
	@FXML private Button button;
	private CanvasPane canvasPane;
	private Canvas canvas;
	private Graphics graphics;
	
	@FXML
	private void initialize() {
		canvasPane = new CanvasPane();
		//canvasPane.setStyle("-fx-background-color: white");
		anchorPane.getChildren().add(canvasPane);
		AnchorPane.setBottomAnchor(canvasPane, 0.0);
		AnchorPane.setLeftAnchor(canvasPane, 0.0);
		AnchorPane.setRightAnchor(canvasPane, 0.0);
		AnchorPane.setTopAnchor(canvasPane, 0.0);
		
		sideMenu.setStyle("-fx-background-color: rgb(240, 240, 240)");
		sideMenu.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.rgb(200, 200, 200), 30, 0.0, 0, 0));
		
		canvas = canvasPane.getCanvas();
		graphics = new Graphics(canvas);
		
		button.setOnAction(e -> {
			graphics.run();
		});
	}
	
	private class CanvasPane extends Pane {

	    private final Canvas canvas;

	    CanvasPane() {
	        canvas = new Canvas();
	        getChildren().add(canvas);
	        this.widthProperty().addListener(e -> {
	        	resize();
	        });
	        this.heightProperty().addListener(e -> {
	        	resize();
	        });
	    }
	    
	    private void resize() {
	    	double val;
        	if(this.getWidth() <= this.getHeight()) {
        		val = this.getWidth();
        	} else {
        		val = this.getHeight();
        	}
        	canvas.setWidth(val);
    		canvas.setHeight(val);
    		canvas.setTranslateX((this.getWidth() - val) / 2);
    		canvas.setTranslateY((this.getHeight() - val) / 2);
	    }
	    
	    public Canvas getCanvas() {
	    	return canvas;
	    }
	    
	}
	
	public void stop() {
		if(graphics != null) {
			graphics.stop();
		}
	}
	
}
