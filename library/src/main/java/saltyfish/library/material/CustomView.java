package saltyfish.library.material;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

public class CustomView extends RelativeLayout {

	protected static String MATERIALDESIGNXML = "http://schemas.android.com/apk/res-auto";
	protected static String ANDROIDXML = "http://schemas.android.com/apk/res/android";

	final int disabledBackgroundColor = Color.parseColor("#E2E2E2");
	protected int beforeBackground;

	// Indicate if user touched this view the last time
	public boolean isLastTouch = false;

	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {

		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

	public CustomView(Context context) {

		super(context);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled)
			setBackgroundColor(beforeBackground);
		else
			setBackgroundColor(disabledBackgroundColor);
		invalidate();
	}

	boolean animation = false;

	@Override
	protected void onAnimationStart() {
		super.onAnimationStart();
		animation = true;
	}

	@Override
	protected void onAnimationEnd() {
		super.onAnimationEnd();
		animation = false;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (animation)
			invalidate();
	}
}
