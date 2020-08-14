package com.seerofspace.torus.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.seerofspace.torus.Complex;
import static com.seerofspace.torus.Complex.cNum;

class ComplexTest {
	
	private Complex c1;
	private Complex c2;
	private Complex c3;
	private Complex c4;
	
	@BeforeEach
	void setUp() throws Exception {
		c1 = new Complex(8, 2);
		c2 = new Complex(-10, 3);
		c3 = new Complex(5, 0);
		c4 = new Complex(0, 0);
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void addTest() {
		assertTrue(Complex.add(c1, c2).equals(cNum(-2, 5)));
		assertTrue(Complex.add(c1, c4).equals(c1));
		assertTrue(Complex.add(c3, c1).equals(cNum(13, 2)));
	}
	
	@Test
	void multTest() {
		assertTrue(Complex.mult(c1, c2).equals(cNum(-86, 4)));
		assertTrue(Complex.mult(c2, c3).equals(cNum(-50, 15)));
		assertTrue(Complex.mult(c1, c4).equals(cNum(0, 0)));
	}
	
	@Test
	void recipTest() {
		assertTrue(Complex.recip(c1).equals(cNum(2.0 / 17.0, -1.0 / 34.0)));
		assertTrue(Complex.recip(c2).equals(cNum(-10.0 / 109.0, -3.0 / 109.0)));
		assertTrue(Complex.recip(c3).equals(cNum(1.0 / 5.0, 0)));
		assertTrue(Complex.recip(c4).equals(cNum(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)));
	}
	
	@Test
	void normsqrTest() {
		assertTrue(Complex.normsqr(c1) == 68);
		assertTrue(Complex.normsqr(c2) == 109);
	}
	
	@Test
	void expTest() {
		double t1 = Math.exp(8) * Math.cos(2);
		double t2 = Math.exp(8) * Math.sin(2);
		c1.exp();
		assertEquals(t1, c1.getRe());
		assertEquals(t2, c1.getIm());
	}
	
}
