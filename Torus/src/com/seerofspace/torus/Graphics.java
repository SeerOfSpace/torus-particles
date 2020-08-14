package com.seerofspace.torus;

import static com.seerofspace.torus.Complex.cNum;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Graphics {
	
	private Canvas canvas;
	private GraphicsContext gc;
	private List<GParticle> pList;
	private List<GParticle> copyList;
	List<Complex> transList;
	private Thread mainThread;
	private boolean stop;
	
	private static final double timeScale = 0.01;
	private static final int steps = 4;
	private static final int frameRate = 60;
	
	public Graphics(Canvas canvas) {
		this.canvas = canvas;
		gc = this.canvas.getGraphicsContext2D();
		stop = false;
	}
	
	public void run() {
		if(mainThread != null) {
			stop();
			try {
				mainThread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		stop = false;
		mainThread = new Thread(() -> {
			run2();
		});
		setup2();
		mainThread.start();
	}
	
	public void run1() {
		long t1 = System.currentTimeMillis();
		long t2 = t1;
		
		while(!stop) {
			double dt = (t2 - t1) / 1000.0 * timeScale;
			t1 = t2;
			for(int i = 0; i < pList.size(); i++) {
				Physics.calculateSingle(pList, pList.get(i), i, dt, steps);
			}
			render(pList);
			t2 = System.currentTimeMillis();
			long tRem = 1000 / frameRate - (t2 - t1);
			if(tRem > 0) {
				sleep(tRem);
				t2 += tRem;
			}
		}
	}
	
	public void run2() {
		render(pList);
		long t1 = System.currentTimeMillis();
		long t2 = t1;
		copyList = new ArrayList<>(pList.size());
		transList = new ArrayList<>(pList.size());
		for(int i = 0; i < pList.size(); i++) {
			copyList.add(new GParticle());
			transList.add(new Complex());
		}
		
		final MDouble dt = new MDouble();
		List<Task> tasks = new ArrayList<>(pList.size());
		for(int i = 0; i < pList.size(); i++) {
			tasks.add(new Task(i, dt));
		}
		ExecutorService executor = Executors.newFixedThreadPool(6);
		
		while(!stop) {
			dt.num = (t2 - t1) / 1000.0 * timeScale;
			t1 = t2;
			try {
				executor.invokeAll(tasks);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			List<GParticle> temp = pList;
			pList = copyList;
			copyList = temp;
			render3(pList, transList);
			t2 = System.currentTimeMillis();
			long tRem = 1000 / frameRate - (t2 - t1);
			if(tRem > 0) {
				sleep(tRem);
				t2 += tRem;
			}
		}
		executor.shutdown();
	}
	
	@SuppressWarnings("unused")
	private class Task implements Callable<Boolean> {
		private int index;
		private MDouble dt;
		public Task(int index, MDouble dt) {
			this.index = index;
			this.dt = dt;
		}
		@Override
		public Boolean call() throws Exception {
			GParticle p = copyList.get(index);
			p.set(pList.get(index));
			Physics.calculateSingle(pList, p, index, dt.num, steps);
			Complex pos = transList.get(index);
			pos.set(p.getPosition());
			pos.multBy(cNum(canvas.getWidth(), 0)).subBy(cNum(p.getSize() / 2, p.getSize() / 2));
			return true;
		}
	}
	
	@SuppressWarnings("unused")
	private class Task2 implements Callable<Boolean> {
		private int index;
		private MDouble dt;
		private int threads;
		public Task2(int index, MDouble dt, int threads) {
			this.index = index;
			this.dt = dt;
			this.threads = threads;
		}
		@Override
		public Boolean call() throws Exception {
			int range = pList.size() / threads * index;
			GParticle p = copyList.get(index);
			p.set(pList.get(index));
			Physics.calculateSingle(pList, p, index, dt.num, steps);
			Complex pos = transList.get(index);
			pos.set(p.getPosition());
			pos.multBy(cNum(canvas.getWidth(), 0)).subBy(cNum(p.getSize() / 2, p.getSize() / 2));
			return true;
		}
	}
	
	private class MDouble {
		double num;
	}
	
	@SuppressWarnings("unused")
	private void setup1() {
		pList = new ArrayList<>();
		pList.add(new GParticle(Math.random(), Math.random(), 1, 0, 0, 20, Color.RED));
		pList.add(new GParticle(0.3, 0.3, 1, 0, 0, 20, Color.BLUE));
	}
	
	@SuppressWarnings("unused")
	private void setup2() {
		pList = new ArrayList<>();
		int num = 600;
		for(int i = 0; i < num; i++) {
			double hue = (double) i / num * 360;
			pList.add(new GParticle(Math.random(), Math.random(), 1, 20, 
					Color.hsb(hue, 0.9, 0.9, 0.5)));
		}
	}
	
	private void render(List<GParticle> pList) {
		Platform.runLater(() -> {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			for(GParticle p : pList) {
				gc.setFill(p.getColor());
				Complex pos = new Complex(p.getPosition());
				pos.multBy(cNum(canvas.getWidth(), 0)).subBy(cNum(p.getSize() / 2, p.getSize() / 2));
				gc.fillOval(pos.getRe(), pos.getIm(), p.getSize(), p.getSize());
			}
		});
	}
	
	@SuppressWarnings("unused")
	private void render2(List<GParticle> pList, List<Complex> transList) {
		Platform.runLater(() -> {
			gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			gc.setFill(Color.BLACK);
			gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			for(int i = 0; i < pList.size(); i++) {
				GParticle p = pList.get(i);
				gc.setFill(p.getColor());
				Complex pos = transList.get(i);
				gc.fillOval(pos.getRe(), pos.getIm(), p.getSize(), p.getSize());
			}
		});
	}
	
	private void render3(List<GParticle> pList, List<Complex> transList) {
		Platform.runLater(() -> {
			//gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
			//gc.setFill(Color.BLACK);
			//gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
			for(int i = 0; i < pList.size(); i++) {
				GParticle p = pList.get(i);
				gc.setFill(p.getColor());
				Complex pos = transList.get(i);
				gc.fillOval(pos.getRe(), pos.getIm(), p.getSize(), p.getSize());
			}
		});
	}
	
	private void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void stop() {
		stop = true;
	}
	
}
