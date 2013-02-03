package com.ntraft.modlur;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

/**
 * Implement a simple rotation control.
 */
public final class TouchRotateView extends GLSurfaceView {

	private static final float TOUCH_SCALE_FACTOR = 180.0f / 320;
	private static final float TRACKBALL_SCALE_FACTOR = 36.0f;

	private ModelRenderer mRenderer;
	private float mPreviousX;
	private float mPreviousY;

	public TouchRotateView(Context context) throws IOException, SAXException {
		super(context);
//		InputStream model = getResources().getAssets().open("cube.dae");
		InputStream model = getResources().getAssets().open("football.dae");
		mRenderer = new ModelRenderer(model);
		setRenderer(mRenderer);
		setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
	}

	@Override
	public boolean onTrackballEvent(MotionEvent e) {
		// Apparently this doesn't work. Not getting any events.
		mRenderer.mAngleX += e.getX() * TRACKBALL_SCALE_FACTOR;
		mRenderer.mAngleY += e.getY() * TRACKBALL_SCALE_FACTOR;
		requestRender();
		return true;
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		float x = e.getX();
		float y = e.getY();
		switch (e.getAction()) {
			case MotionEvent.ACTION_MOVE:
				float dx = x - mPreviousX;
				float dy = y - mPreviousY;
				mRenderer.mAngleX += dx * TOUCH_SCALE_FACTOR;
				mRenderer.mAngleY -= dy * TOUCH_SCALE_FACTOR;
				requestRender();
		}
		mPreviousX = x;
		mPreviousY = y;
		return true;
	}

}
