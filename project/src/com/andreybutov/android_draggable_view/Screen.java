/*
===============================================================================

Android Draggable View

Andrey Butov
http://www.andreybutov.com
andreybutov@antair.com

-------------------------------------------------------------------------------

The MIT License (MIT)

Copyright (c) 2014-2015, Andrey Butov. All Rights Reserved.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

===============================================================================
*/



package com.andreybutov.android_draggable_view;



import android.app.Activity;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;



final public class Screen extends Activity
{
	private ViewGroup _contentContainer;
	private Rect _screenRect;
	private boolean _dragging = false;
	private ImageView _draggingImage;
	private double _farthestDistance;


	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.screen);
		
		_contentContainer = (ViewGroup)findViewById(R.id.content_container);
	}

	
	
	@Override
	public void onWindowFocusChanged(boolean hasFocus) 
	{
		super.onWindowFocusChanged(hasFocus);
		
		// Controls sizes are now available - layout can be done.
		if ( hasFocus ) 
		{
			_screenRect = new Rect();
			_contentContainer.getLocalVisibleRect(_screenRect);

			Point center = new Point(_screenRect.centerX(), _screenRect.centerY());
			Point farthest = new Point(_screenRect.width(), _screenRect.height());
			 
			_farthestDistance = Math.sqrt(
				Math.pow((center.x - farthest.x), 2) + 
				Math.pow((center.y - farthest.y), 2)
			);
			
			placeCard();
		}
	}
	
	
	
	void placeCard() 
	{
		CardView card = new CardView(this);
		
		final int cardWidth = (int)(_screenRect.width() * 0.75f);
		final int cardHeight = (int)(_screenRect.height() * 0.5f);
		
		RelativeLayout.LayoutParams cardParams = 
			new RelativeLayout.LayoutParams(cardWidth, cardHeight);
		
		cardParams.setMargins(
			(_screenRect.width() - cardWidth) / 2,
			(_screenRect.height() - cardHeight) / 2, 
			0, 
			0);
		
		card.setLayoutParams(cardParams);
		
		card.setText("Drag me around.");
		
		card.setDrawingCacheEnabled(true);
		card.setOnTouchListener(new View.OnTouchListener() {

			private Matrix _matrix = new Matrix();
			private PointF _start = new PointF();
			
			@Override
			public boolean onTouch(final View v, MotionEvent event) {
				switch ( event.getAction()  ) {
					case MotionEvent.ACTION_DOWN:
						_dragging = true;

						//
						// To reduce flicker and improve performance, we'll be 
						// simulating the movement of the view by moving it's cached
						// Bitmap image instead of the view itself.
						//
						
						_draggingImage = new ImageView(Screen.this);
						_draggingImage.setImageBitmap(v.getDrawingCache());
						_draggingImage.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
						_draggingImage.setScaleType(ScaleType.MATRIX);
						
						_contentContainer.addView(_draggingImage);
						
						_start.x = event.getX();
						_start.y = event.getY();

						//
						// Initial location of the moving Bitmap should 
						// match the location of the original view.
						//
						
						Matrix initialMatrix = new Matrix();
						initialMatrix.postTranslate(
							v.getLeft(), 
							v.getTop()
						);
						
						_draggingImage.setImageMatrix(initialMatrix);
						
						//
						// Hide the original view while moving the Bitmap.
						//
						
						v.setVisibility(View.INVISIBLE);
						break;
						
						
					case MotionEvent.ACTION_UP:
						_dragging = false;

						//
						// Return the view back to its starting position.
						//
						
						TranslateAnimation anim = new TranslateAnimation(
							0, _start.x - event.getX(),
							0, _start.y - event.getY()
						);
						
						anim.setAnimationListener(new TranslateAnimation.AnimationListener() {
							@Override
							public void onAnimationStart(Animation animation) { 
							}

							@Override
							public void onAnimationRepeat(Animation animation) { 
							}

							@Override
							public void onAnimationEnd(Animation animation) {
								_contentContainer.removeView(_draggingImage);
								v.setVisibility(View.VISIBLE);
							}
						});
						
						anim.setDuration(250);
						_draggingImage.startAnimation(anim);
						
						break;
						
					case MotionEvent.ACTION_MOVE:
						if ( _dragging ) {
							_matrix.reset();
							_matrix.postTranslate(
								event.getX() - _start.x + v.getLeft(), 
								event.getY() - _start.y + v.getTop()
							);
							
							// 
							// Increase the transparency as the 
							// view moves closer to the edges of the
							// screen.
							//
							
							double distance = Math.sqrt(
								Math.pow((_start.x - event.getX()), 2) + 
								Math.pow((_start.y - event.getY()), 2)
							);
							
							double delta = (_farthestDistance - distance) * 0.80f;
							float alpha = (float)(delta / _farthestDistance);
							
							_draggingImage.setAlpha(alpha);
							
							_draggingImage.setImageMatrix(_matrix);
						}
						break;
				}
				return true;
			}
		});
		
		_contentContainer.addView(card);
	}
	
}
