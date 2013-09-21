/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.comtel.javafx;

import java.awt.Point;
import java.util.Locale;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.util.Duration;
import javax.swing.JWindow;
import org.comtel.javafx.control.KeyBoardPopup;
import org.comtel.javafx.control.KeyBoardPopupBuilder;
import org.comtel.javafx.robot.RobotFactory;

/**
 *
 * @author user
 */

public class fxKeyboard {
    
    private KeyBoardPopup fxKeyboardPopup;
    private Transition transition;
    
    private static fxKeyboard m_keyboard=null;
    
    static {
        if (m_keyboard==null) {
            m_keyboard = new fxKeyboard();
        }
    }
    
    private fxKeyboard() {
        init();
    }
    
    private void init() {
        // create javafx panel
        final JFXPanel javafxPanel = new JFXPanel();
        javafxPanel.setFocusable(false);
        javafxPanel.setOpaque(false);

        JWindow fxKeyboard = new JWindow();
        fxKeyboard.setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        fxKeyboard.getContentPane().add(javafxPanel);
        fxKeyboard.setFocusable(false);
        fxKeyboard.setBackground(null);

        // fxKeyboard.pack();
        // fxKeyboard.setLocationByPlatform(true);
        // fxKeyboard.setVisible(false);

        // create JavaFX scene
        Platform.runLater(new Runnable() {
            public void run() {
                createScene(javafxPanel);
                System.out.println("JavaFX: " + System.getProperty("javafx.runtime.version"));
            }
        });
    }
    
    private void createScene(JFXPanel javafxPanel) {

        //set default embedded css style
        String css = this.getClass().getResource("/css/KeyboardButtonStyle.css").toExternalForm();

        // create empty scene
        Scene scene = new Scene(new Group(), 0, 0);

        javafxPanel.setScene(scene);
        scene.getStylesheets().add(css);
        fxKeyboardPopup = KeyBoardPopupBuilder.create().initLocale(Locale.ENGLISH)
                        .addIRobot(RobotFactory.createAWTRobot()).build();
        fxKeyboardPopup.getKeyBoard().setOnKeyboardCloseButton(new EventHandler<Event>() {
                public void handle(Event event) {
                        setKeyboardVisible(false, null);
                }
        });
        fxKeyboardPopup.setOwner(scene);

    }
    
    public static void showKeyboard(Point point) {
        
        m_keyboard.setKeyboardVisible(true, point);
    }
    
    public static void hideKeyboard() {
        m_keyboard.setKeyboardVisible(false, null);
    }

    public void setKeyboardVisible(boolean flag, Point point) {
        final boolean visible = flag;
        final Point location = point;
        Platform.runLater(new Runnable() {
            public void run() {
                if (fxKeyboardPopup == null) {
                    return;
                }   
                if (location != null) {
                    fxKeyboardPopup.setX(location.getX());
                    fxKeyboardPopup.setY(location.getY() + 20);
                }

                if (transition == null) {
                    transition = new FadeTransition(Duration.seconds(0.1), fxKeyboardPopup.getKeyBoard());
                    // transition = new ScaleTransition(Duration.seconds(0.1),
                    // fxKeyboardPopup.getKeyBoard());
                    // transition.setCycleCount(1);
                    // transition.setAutoReverse(false);
                }
                if (visible) {
                    if (fxKeyboardPopup.isVisible() && transition.getStatus() == Animation.Status.STOPPED) {
                        return;
                    }
                    System.err.println("fade in");
                    transition.stop();
                    transition.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                        }
                    });
                    // ((ScaleTransition) transition).setFromX(0.0d);
                    // ((ScaleTransition) transition).setFromY(0.0d);
                    // ((ScaleTransition)
                    // transition).setToX(fxKeyboardPopup.getKeyBoard().getScale());
                    // ((ScaleTransition)
                    // transition).setToY(fxKeyboardPopup.getKeyBoard().getScale());

                    fxKeyboardPopup.getKeyBoard().setOpacity(0.0);
                    fxKeyboardPopup.setVisible(true);
                    ((FadeTransition) transition).setFromValue(0.0f);
                    ((FadeTransition) transition).setToValue(1.0f);
                    transition.play();

                } else {
                    if (!fxKeyboardPopup.isVisible() && transition.getStatus() == Animation.Status.STOPPED) {
                        return;
                    }
                    System.err.println("fade out");
                    transition.stop();
                    transition.setOnFinished(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent arg0) {
                                fxKeyboardPopup.setVisible(false);
                        }
                    });
                    // ((ScaleTransition)
                    // transition).setFromX(fxKeyboardPopup.getKeyBoard().getScale());
                    // ((ScaleTransition)
                    // transition).setFromY(fxKeyboardPopup.getKeyBoard().getScale());
                    // ((ScaleTransition) transition).setToX(0.0d);
                    // ((ScaleTransition) transition).setToY(0.0d);

                    ((FadeTransition) transition).setFromValue(1.0f);
                    ((FadeTransition) transition).setToValue(0.0f);
                    transition.play();

                    // fxKeyboardPopup.hide();
                }
            }
        });
    }
}
