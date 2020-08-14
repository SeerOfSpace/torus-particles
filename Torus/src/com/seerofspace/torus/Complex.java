package com.seerofspace.torus;

public class Complex {
	
	private double re;
	private double im;
	
	public Complex() {
		this(0, 0);
	}
	
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public Complex(Complex c) {
		set(c);
	}
	
	public static Complex add(Complex c1, Complex c2) {
		return cNum(c1.re + c2.re, c1.im + c2.im);
	}
	
	public Complex addBy(Complex other) {
		re += other.re;
		im += other.im;
		return this;
	}
	
	public static Complex sub(Complex c1, Complex c2) {
		return cNum(c1.re - c2.re, c1.im - c2.im);
	}
	
	public Complex subBy(Complex other) {
		re -= other.re;
		im -= other.im;
		return this;
	}
	
	public static Complex mult(Complex c1, Complex c2) {
		if((c1.isInf() && !c2.isZero() && !c2.isNaN()) || (c2.isInf() && !c1.isZero() && !c1.isNaN())) {
			return cNum(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
		}
		double real = c1.re * c2.re - c1.im * c2.im;
		double imaginary = c1.re * c2.im + c1.im * c2.re;
		return cNum(real, imaginary);
	}
	
	public Complex multBy(Complex other) {
		if((isInf() && !other.isZero() && !other.isNaN()) || (other.isInf() && !isZero() && !isNaN())) {
			im = Double.POSITIVE_INFINITY;
			re = Double.POSITIVE_INFINITY;
			return this;
		}
		double temp = re * other.re - im * other.im;
		im = re * other.im + im * other.re;
		re = temp;
		return this;
	}
	
	public static Complex div(Complex c1, Complex c2) {
		return mult(c1, recip(c2));
	}
	
	public Complex divBy(Complex other) {
		return multBy(recip(other));
	}
	
	public static Complex exp(Complex c) {
		Complex temp = cNum(Math.exp(c.re), 0);
		temp.multBy(cNum(Math.cos(c.im), Math.sin(c.im)));
		return temp;
	}
	
	public Complex exp() {
		set(exp(this));
		return this;
	}
	
	public static Complex cot(Complex c) {
		Complex temp1 = cNum(0, 2);
		Complex temp2 = mult(temp1, c).exp().subBy(cNum(1, 0));
		return temp1.divBy(temp2).addBy(cNum(0, 1));
	}
	
	public Complex cot() {
		set(cot(this));
		return this;
	}
	
	public static Complex recip(Complex c) {
		if(c.isZero()) {
			return cNum(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY); 
		} else if(c.isInf()) {
			return cNum(0, 0);
		} else {
			return cNum(c.re / c.normsqr(), -c.im / c.normsqr());
		}
	}
	
	public Complex recip() {
		if(isZero()) {
			re = Double.POSITIVE_INFINITY;
			im = Double.POSITIVE_INFINITY;
		} else if(isInf()) {
			re = 0;
			im = 0;
		} else {
			double temp = re / normsqr();
			im = -im / normsqr();
			re = temp;
		}
		return this;
	}
	
	public static double normsqr(Complex c) {
		return Math.pow(c.re, 2) + Math.pow(c.im, 2);
	}
	
	public double normsqr() {
		return normsqr(this);
	}
	
	public boolean isNaN() {
		return Double.isNaN(re) || Double.isNaN(im);
	}
	
	public boolean isInf() {
		return Double.isInfinite(re) || Double.isInfinite(im);
	}
	
	public boolean isZero() {
		return re == 0 && im == 0;
	}
	
	public void set(Complex c) {
		re = c.re;
		im = c.im;
	}
	
	public void set(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	public double getRe() {
		return re;
	}

	public void setRe(double re) {
		this.re = re;
	}

	public double getIm() {
		return im;
	}

	public void setIm(double im) {
		this.im = im;
	}
	
	public static Complex cNum(double re, double im) {
		return new Complex(re, im);
	}
	
	public boolean equals(Complex other) {
		return re == other.re && im == other.im;
	}
	
	@Override
	public String toString() {
		return re + ", " + im;
	}
	
}
