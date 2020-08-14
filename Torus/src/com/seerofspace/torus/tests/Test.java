package com.seerofspace.torus.tests;

import com.seerofspace.torus.Physics;

import static com.seerofspace.torus.Complex.cNum;

import com.seerofspace.torus.Complex;

public class Test {
	
	public static void main(String[] args) {
		for(int i = 0; i < 4; i++) {
			for(int j = 0; j < 4; j++) {
				Complex temp = Physics.gravy(cNum(i, j).divBy(cNum(4, 0)), 4);
				System.out.println(temp.getRe() + ", " + temp.getIm());
			}
			System.out.println();
		}
		
	}
	
}
