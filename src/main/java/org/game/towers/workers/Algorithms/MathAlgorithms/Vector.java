package org.game.towers.workers.Algorithms.MathAlgorithms;

import java.awt.geom.Point2D;

public class Vector {
	/**
	 * get the magnitude of the vector
	 *
	 * @return double
	 */
	public static double vec_mag(double x, double y) {
		return Math.sqrt(x * x + y * y);
	}

	/**
	 * subtract two vectors
	 *
	 * @return Point2D
	 */
	public static Point2D vec_sub(double x1, double y1, double x2, double y2) {
		Point2D p = new Point2D.Double();
		p.setLocation(x1 - x2, y1 - y2);
		return p;
	}

	/**
	 * add two vectors
	 *
	 * @return Point2D
	 */
	public static Point2D vec_add(double x1, double y1, Point2D p) {
		Point2D pr = new Point2D.Double();
		pr.setLocation(x1 + p.getX(), y1 + p.getY());
		return pr;
	}

	/**
	 * multiply a vector by a scalar
	 *
	 * @return Point2D
	 */
	public static Point2D vec_mul(Point2D p, double c) {
		Point2D pr = new Point2D.Double();
		pr.setLocation(pr.getX() * c, pr.getY() * c);
		return pr;
	}

	/**
	 * divide == multiply by 1/c
	 *
	 * @return Point2D
	 */
	public static Point2D vec_div(double x, double y, double c) {
		Point2D p = new Point2D.Double();
		p.setLocation(x, y);
		return vec_mul(p, 1.0 / c);
	}

	/**
	 * normalize vector
	 *
	 * @return Point2D
	 */
	public static Point2D vec_normal(double x, double y) {
		return vec_div(x, y, vec_mag(x, y));
	}
}
