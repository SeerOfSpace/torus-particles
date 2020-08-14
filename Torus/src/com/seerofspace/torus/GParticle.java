package com.seerofspace.torus;

import javafx.scene.paint.Color;

public class GParticle extends Particle {
	
	private Color color;
	private double size;
	
	public GParticle() {
		this(0, 0, 1, 10, Color.RED);
	}
	
	public GParticle(double re, double im, double mass, double size, Color color) {
		this(re, im, mass, 0, 0, size, color);
	}
	
	public GParticle(double re, double im, double mass, double vre, double vim, double size, Color color) {
		super(re, im, mass, vre, vim);
		this.size = size;
		this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
	}
	
	public GParticle(Complex position, double mass, Complex velocity, double size, Color color) {
		super(position, mass, velocity);
		this.size = size;
		this.color = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getOpacity());
	}
	
	public GParticle(GParticle p) {
		this(p.getPosition(), p.getMass(), p.getVelocity(), p.size, p.color);
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}
	
	public void set(GParticle p) {
		super.set(p);
		size = p.size;
		color = new Color(p.color.getRed(), p.color.getGreen(), p.color.getBlue(), p.color.getOpacity());
	}
	
}
