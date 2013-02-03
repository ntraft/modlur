package com.ntraft.modlur;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implement a simple rotation control.
 */
public final class TouchRotateView extends GLSurfaceView {

	private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;

	private ModelRenderer renderer;
	private ScaleGestureDetector scaleDetector;
	private float mPreviousX;
	private float mPreviousY;
	private boolean skipNextMove;

	public TouchRotateView(Context context) throws IOException, SAXException {
		super(context);
//		InputStream model = getResources().getAssets().open("cube.dae");
		InputStream model = getResources().getAssets().open("football.dae");
		renderer = new ModelRenderer(model);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);

		scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		scaleDetector.onTouchEvent(e);

		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_MOVE:
			if (e.getPointerCount() == 1 && !skipNextMove) {
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;
				renderer.rotateX(dx * TOUCH_SCALE_FACTOR);
				renderer.rotateY(dy * TOUCH_SCALE_FACTOR);
				requestRender();
			} else {
				skipNextMove = false;
			}
			break;
		case MotionEvent.ACTION_POINTER_UP:
			skipNextMove = true;
			break;
		}
		mPreviousX = x;
		mPreviousY = y;
		return true;
	}

	private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		public boolean onScale(ScaleGestureDetector detector) {
			renderer.scaleBy(detector.getScaleFactor());
			requestRender();
			return true;
		}
	}
}
