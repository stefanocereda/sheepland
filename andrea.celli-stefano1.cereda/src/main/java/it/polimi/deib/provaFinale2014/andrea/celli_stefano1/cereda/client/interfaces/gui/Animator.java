package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.GuiConstants;

import java.awt.Point;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComponent;

/**
 * An animator is an object that takes care of moving around objects. Right now
 * the animation is linear, it can be changed to quadratic
 * 
 * @author stefano
 * 
 */
public class Animator implements Runnable {
    /** The object that this animator is moving */
    private JComponent objectToMove;
    /** The end point */
    private Point endPoint;
    /** The starting point */
    private Point startPoint;
    /** The value of dX relative to dS */
    private double kX;
    /** the value of dY relative to dS */
    private double kY;
    /** The time to sleep between each movement */
    private long sleepTime = GuiConstants.ANIMATION_FPS;

    /**
     * Create an animator, without starting it
     * 
     * @param objectToMove
     *            The object to be moved by this animator
     * @param endPoint
     *            The point where this animator will stop
     */
    public Animator(JComponent objectToMove, Point endPoint) {
        this.objectToMove = objectToMove;
        this.endPoint = endPoint;
        this.startPoint = new Point(objectToMove.getX(), objectToMove.getY());

        initializeValues();
    }

    /** This method is used to initialize dX and dY and the timer values */
    private void initializeValues() {
        double distanceX = endPoint.x - startPoint.x;
        double distanceY = endPoint.y - startPoint.y;
        double alpha = Math.atan(distanceY / distanceX);

        kX = Math.cos(alpha);
        kY = Math.sin(alpha);

        // atan is in the range -pi/2 +pi/2 so we have to handle negative values
        if (distanceX < 0) {
            kX = -kX;
        }

        if (kX < 0) {
            kY = -kY;
        }

    }

    /** Start the animation */
    public void run() {
        long numberOfRepaints = GuiConstants.ANIMATION_FPS
                * (GuiConstants.ANIMATION_LENGTH / 1000);

        for (long i = 0; i < numberOfRepaints; i++) {
            Point dS = calculateMovement();
            int newX = objectToMove.getLocation().x + dS.x;
            int newY = objectToMove.getLocation().y + dS.y;
            Point newPosition = new Point(newX, newY);
            objectToMove.setLocation(newPosition);

            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE,
                        "Interrupted while moving an object", e);
            }
        }

        objectToMove.setLocation(endPoint);
    }

    /**
     * @return A point that represents the movement that this object has to made
     *         in this iteration
     */
    private Point calculateMovement() {
        double dS = calculateLinearMovement();
        int dX = (int) (dS * kX);
        int dY = (int) (dS * kY);

        return new Point(dX, dY);
    }

    /** @return the distance that the object has to cover during a single step */
    private double calculateLinearMovement() {
        long numberOfRepaints = GuiConstants.ANIMATION_FPS
                * (GuiConstants.ANIMATION_LENGTH / 1000);

        // we want to get a speed like v(t) = v(0) - v(0)/dist * t

        // v(0) = 2*Vc where Vc is the constant speed that we should keep in
        // order to cover the same distance in the same time
        double v0 = 2 * startPoint.distance(endPoint) / numberOfRepaints;

        double distance = startPoint.distance(endPoint);

        double t = startPoint.distance(objectToMove.getLocation());

        // add 1 to achieve a smoother end
        return v0 - v0 / distance * t + 1;
    }
}