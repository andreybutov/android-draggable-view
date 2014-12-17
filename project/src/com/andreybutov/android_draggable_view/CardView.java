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



import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;



public class CardView extends LinearLayout {

	
	private TextView _textView;
	
	
	public CardView(Context context) {
		super(context);
		init(context);
	}

	void setText(String text) {
		_textView.setText(text);
	}
	
	private void init(Context context) {
		
		setBackgroundColor(Color.WHITE);
		setOrientation(LinearLayout.VERTICAL);
		
		GradientDrawable shape = new GradientDrawable();
		shape.setColor(Color.WHITE);
		shape.setCornerRadius(10);
		
		setBackgroundDrawable(shape);
		
		_textView = new TextView(context);
		
		LinearLayout.LayoutParams textViewParams = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.WRAP_CONTENT, 
			LinearLayout.LayoutParams.WRAP_CONTENT
		);

		textViewParams.setMargins(0, 20, 0, 0);
		textViewParams.gravity = Gravity.CENTER;
		
		_textView.setLayoutParams(textViewParams);
		_textView.setTextColor(Color.BLACK);
		_textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
		_textView.setTypeface(null, Typeface.BOLD);
		
		addView(_textView);
	}

}
