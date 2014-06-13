package it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.client.interfaces.gui;

import it.polimi.deib.provaFinale2014.andrea.celli_stefano1.cereda.constants.TimeConstants;

import java.awt.Point;
import java.awt.geom.Line2D.Double;
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
	private long sleepTime;

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

		sleepTime = TimeConstants.NUMBER_OF_ANIMATION_REPAINTS
				/ TimeConstants.LENGTH_OF_ANIMATION;
	}

	/** Start the animation */
	public void run() {
		while (!isFinished()) {
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
	}

	/** Check if the animation is finished */
	private boolean isFinished() {
		return objectToMove.getLocation().equals(endPoint);
	}

	/**
	 * @return A point that represents the movement that this object has to made
	 *         in this iteration
	 */
	private Point calculateMovement() {
		double dS = calculateLinearMovement();
		int dX = (int) ((int) dS * kX);
		int dY = (int) ((int) dS * kY);
		return new Point(dX, dY);
	}

	/** @return the distance that the object has to cover during a single step */
	private double calculateLinearMovement() {
		return startPoint.distance(endPoint)
				/ TimeConstants.NUMBER_OF_ANIMATION_REPAINTS;
	}
}
