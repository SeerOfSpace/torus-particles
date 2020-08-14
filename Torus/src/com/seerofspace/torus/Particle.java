package com.seerofspace.torus;

import static com.seerofspace.torus.Complex.cNum;

public class Particle {
	
	private Complex position;
	private double mass;
	private Complex velocity;
	
	public Particle() {
		this(0, 0, 1);
	}
	
	public Particle(double re, double im, double mass) {
		this(re, im, mass, 0, 0);
	}
	
	public Particle(double re, double im, double mass, double vre, double vim) {
		position = new Complex(re, im);
		this.mass = mass;
		velocity = new Complex(vre, vim);
	}
	
	public Particle(Complex position, double mass, Complex velocity) {
		this.position = new Complex(position);
		this.mass = mass;
		this.velocity = new Complex(velocity);
	}
	
	public Particle(Particle p) {
		this(p.position, p.mass, p.velocity);
	}
	
	public void accelerate(Complex acceleration, double time) {
		velocity.addBy(Complex.mult(acceleration, cNum(time, 0)));
	}
	
	public void experienceTime(double time) {
		position.addBy(Complex.mult(velocity, cNum(time, 0)));
	}
	
	public Complex getPosition() {
		return position;
	}

	public void setPosition(Complex position) {
		this.position = position;
	}
	
	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}

	public Complex getVelocity() {
		return velocity;
	}

	public void setVelocity(Complex velocity) {
		this.velocity = velocity;
	}
	
	public void set(Particle p) {
		position = new Complex(p.position);
		mass = p.mass;
		velocity = new Complex(p.velocity);
	}
	
}
