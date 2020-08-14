package com.seerofspace.torus;

import java.util.List;

import static com.seerofspace.torus.Complex.cNum;

public class Physics {
	
	public static void calculateSingle(List<? extends Particle> pList, Particle p, int index, double time, int steps) {
		for(int i = index + 1; i < pList.size(); i++) {
			Particle pOther = pList.get(i);
			Complex d = Complex.sub(p.getPosition(), pOther.getPosition());
			Complex g = gravy(d, steps);
			p.accelerate(Complex.mult(cNum(-pOther.getMass(), 0), g), time);
			pOther.accelerate(Complex.mult(cNum(p.getMass(), 0), g), time);
		}
		p.experienceTime(time);
		if(!inRange(p.getPosition())) {
			p.setPosition(mod(p.getPosition(), 1));
		}
	}
	
	public static Complex gravy(Complex d, int steps) {
		Complex f = cNum(0, 0);
		Complex cis = cNum(0, 2).multBy(cNum(Math.PI, 0)).multBy(d).exp();
		Complex cisi = Complex.recip(cis);
		double g = 1, g1 = Math.exp(2 * Math.PI);
		
		for(int m = 1; m <= steps; m++) {
			g *= g1;
			Complex temp1 = cNum(g, 0).multBy(cis).subBy(cNum(1, 0)).recip();
			Complex temp2 = cNum(1, 0).subBy(cNum(g, 0).multBy(cisi)).recip();
			f.addBy(temp1).addBy(temp2);
		}
		
		Complex temp1 = cNum(0, 2).multBy(cNum(Math.PI, 0)).multBy(f);
		Complex temp2 = cNum(0, 2).multBy(cNum(Math.PI, 0)).multBy(cNum(d.getIm(), 0));
		Complex temp3 = cNum(Math.PI, 0).multBy(d).cot().multBy(cNum(Math.PI, 0));
		temp1.addBy(temp2).addBy(temp3);
		return cNum(temp1.getRe(), -temp1.getIm());
	}
	
	private static boolean inRange(Complex c) {
		double re = c.getRe();
		double im = c.getIm();
		if(re <= 1 && re >= 0 && im <= 1 && im >= 0) {
			return true;
		}
		return false;
	}
	
	private static Complex mod(Complex c, double y) {
		return cNum(mod(c.getRe(), y), mod(c.getIm(), y));
	}
	
	private static double mod(double x, double y) {
		return x - Math.floor(x / y) * y;
	}
	
}
