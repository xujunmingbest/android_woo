package com.example.administrator.myapplication;

import java.util.Random;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;


public class FlowerView extends View {

	Bitmap mFlowers = null;
	MyFlower flowers [] = new MyFlower[50];
	private Integer[] offsetX ;
	private Integer[] offsetY ;
	Random r = new Random();
	Matrix m = new Matrix();
	Paint p = new Paint();
	
	int mW = 480;
	int mH = 800;
	float de = 0f;
	
	public void setWH(int pW, int pH, float de){
		this.mW = pW;
		this.mH = pH;
		this.de = de;
		System.out.println("de ---->" + de);
		offsetX = new Integer[]{(int)(2*de), (int)(-2*de), (int)(-1*de), 0, (int)(1*de), (int)(2*de), (int)(1*de)};
		offsetY = new Integer[]{(int)(3*de), (int)(5*de), (int)(5*de), (int)(3*de), (int)(4*de)};
	}
	
	public FlowerView(Context context) {
		super(context);
	}

	public FlowerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowerView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		for (int i = 0; i < flowers.length; i++) {
			MyFlower rect = flowers[i];
			int t = rect.t;
			t--;
			if (t <= 0) {
				rect.y += rect.g;
				canvas.save();
				m.reset();
				m.setScale(rect.s, rect.s);
				canvas.setMatrix(m);
				p.setAlpha(rect.a);
				canvas.drawBitmap(mFlowers, rect.x, rect.y, p);
				canvas.restore();
			}
			rect.t = t;
			if (rect.y >= mH) {
				rect.init();
			}
			if (rect.x >= mW || rect.x < - 20) {
				rect.init();
			}
			flowers[i] = rect;
		}
	}
	
	
	
	public void loadFlower(){
		Resources r = this.getContext().getResources();
		mFlowers = ((BitmapDrawable)r.getDrawable(R.drawable.snow)).getBitmap();
	}
	
	public void recly(){
		if (mFlowers != null && !mFlowers.isRecycled()) {
			mFlowers.recycle();
		}
	}
	
	public void addRect(){
		for (int i = 0; i < flowers.length; i++) {
			flowers[i] = new MyFlower();
		}
	}
	
	public void inva(){
		invalidate();
	}
	
	
	class MyFlower{
		int x;
		int y;
		float s;
		int a;
		int t;
		int g;
		
		public void init(){
			float aa = r.nextFloat();			
			this.x = r.nextInt(mW - 80) + 80;
			this.y = 0;
			if (aa >= 1) {
				this.s = 1.1f;
			}else if (aa <= 0.2) {
				this.s = 0.4f;
			}else{
				this.s = aa;
			}
			this.a = r.nextInt(155) + 100;
			this.t = r.nextInt(105) + 1;
			this.g = offsetY[r.nextInt(4)];
			//this.g = r.nextInt(50)+50;
		}
		
		public MyFlower(){
			super();
			init();
		}		
		
	}
	
}