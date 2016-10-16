package saltyfish.generalskin;


import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import saltyfish.library.SaltyfishLinearColorPicker;
import saltyfish.library.base.SaltyfishBaseActivity;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.log.SaltyfishLogManager.logD;
import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;
import static saltyfish.library.utils.SaltyfishUtilTool.readBitMap;

public class SaltyfishSetSkinChooseColorAct extends SaltyfishBaseActivity {
	private int[] mColors = new int[] { // 渐变色数组
			0xFFFF0000, 0xFFFFFF00, 0xFF00FF00, 0xFF00FFFF, 0xFF0000FF, 0xFFFF00FF, 0xFFFF0000 };

	private List<ColorModule> mColorModules = Collections.synchronizedList(new LinkedList<ColorModule>());

	/**
	 * 颜色主要容器
	 */
	private LinearLayout mColorContainer;

	/**
	 * 换肤背景
	 */
	private FrameLayout mSkinLayout;

	/**
	 * 主颜色条
	 */
	private SaltyfishLinearColorPicker mainColorPicker;

	/**
	 * 次要颜色条
	 */
	private SaltyfishLinearColorPicker mSubColorPicker;

	/**
	 * 颜色条容器
	 */
	private ViewSwitcher mColorPickerContainer;

	private View mBackArrow;

	/**
	 * 背景图片
     */
	private static  final  String[] IMGS;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.saltyfish_activity_set_skin_choose_color);
		initViews();

	}

	static {
		IMGS = new String[]{
				"saltyfish_skin_color_icon_01",
				"saltyfish_skin_color_icon_02",
				"saltyfish_skin_color_icon_03",
				"saltyfish_skin_color_icon_04",
				"saltyfish_skin_color_icon_05",
				"saltyfish_skin_color_icon_06",
				"saltyfish_skin_color_icon_07",
				"saltyfish_skin_color_icon_08",
				"saltyfish_skin_color_icon_09",
				"saltyfish_skin_color_icon_10",
				"saltyfish_skin_color_icon_11",
				"saltyfish_skin_color_icon_12",
				"saltyfish_skin_color_icon_13",
				"saltyfish_skin_color_icon_14",
				"saltyfish_skin_color_icon_15",
				"saltyfish_skin_color_icon_16",
				"saltyfish_skin_color_icon_gradient"

		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.saltyfish_setcolor_menu,menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(R.id.action_submit==item.getItemId()) {
			ColorModule colorModule = mColorModules.get(currentColorIndex);
			int color = -1;
			int mainProgress = -1;
			int subProgress = -1;
			if (null != colorModule) {
				color = colorModule.getColor();
				mainProgress = colorModule.getMainProgress();
				subProgress = colorModule.getSubProgress();
			}
			if (-1 == color || -1 == mainProgress || -1 == subProgress) {
				color = mSubColorPicker.getCurrentColor();
				mainProgress = mainColorPicker.getCurrentProgress();
				subProgress = mSubColorPicker.getCurrentProgress();
			}
			getSaltyfishInstance(SaltyfishSkinColorTool.class).saveThemeColor(color, -1, -1);
			getSaltyfishInstance(SaltyfishSkinColorTool.class).changeSkin(SaltyfishSetSkinChooseColorAct.this);
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void initViews() {


		ImageView skinBackground = (ImageView) findViewById(R.id.bg_skin);

		mColorContainer = (LinearLayout) findViewById(R.id.cll_color_container);
		mSkinLayout = (FrameLayout) findViewById(R.id.skin_bg);
		mSkinLayout.setBackgroundColor(getSaltyfishInstance(SaltyfishSkinColorTool.class).getSkinColor());


		mainColorPicker = (SaltyfishLinearColorPicker) findViewById(R.id.clcp_main);
		mainColorPicker.setProgress( getSaltyfishInstance(SaltyfishSkinColorTool.class).getMainProgress());
		mSubColorPicker = (SaltyfishLinearColorPicker) findViewById(R.id.clcp_sub);
		mSubColorPicker.setProgress( getSaltyfishInstance(SaltyfishSkinColorTool.class).getSubProgress());
		mainColorPicker.setOnColorSelectListener(new SaltyfishLinearColorPicker.OnColorSelectListener() {
			@Override
			public void onColorSelect(int color, int progress) {

				logD("mainProgress=====================================" + progress);

				mSubColorPicker.setColors(Color.BLACK, color);
			}
		});

		mSubColorPicker.setOnColorSelectListener(new SaltyfishLinearColorPicker.OnColorSelectListener() {
			@TargetApi(Build.VERSION_CODES.LOLLIPOP)
			@Override
			public void onColorSelect(int color, int progress) {

				mSkinLayout.setBackgroundColor(color);
				logD("subProgress=====================================" + progress);

				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
					ActivityManager.TaskDescription v = new ActivityManager.TaskDescription(null, null, color);
					setTaskDescription(v);
				}
			}
		});

		mColorPickerContainer = (ViewSwitcher) findViewById(R.id.cll_color_picker);


		mBackArrow = findViewById(R.id.ctv_back_arrow);
		mBackArrow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {


				Animation inAnimation= AnimationUtils.loadAnimation(SaltyfishSetSkinChooseColorAct.this,R.anim.saltyfish_left_to_right_exit);
				inAnimation.setDuration(250);
				Animation outAnimation= AnimationUtils.loadAnimation(SaltyfishSetSkinChooseColorAct.this,R.anim.saltyfish_left_to_right_enter);
				outAnimation.setDuration(250);
				mColorPickerContainer.setInAnimation(inAnimation);
				mColorPickerContainer.setOutAnimation(outAnimation);
				mColorPickerContainer.showPrevious();


			}
		});

		ColorModule colorModule = new ColorModule();
		colorModule.setMainProgress(52);
		colorModule.setSubProgress(15);
		colorModule.setColor(Color.parseColor("#fd5f8b"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(81);
		colorModule.setSubProgress(91);
		colorModule.setColor(Color.parseColor("#ff7d9e"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(73);
		colorModule.setSubProgress(64);
		colorModule.setColor(Color.parseColor("#ff76c8"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(60);
		colorModule.setSubProgress(47);
		colorModule.setColor(Color.parseColor("#9171ee"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(59);
		colorModule.setSubProgress(88);
		colorModule.setColor(Color.parseColor("#7380fa"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(11);
		colorModule.setSubProgress(87);
		colorModule.setColor(Color.parseColor("#4690ea"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(16);
		colorModule.setSubProgress(77);
		colorModule.setColor(Color.parseColor("#39afea"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(22);
		colorModule.setSubProgress(58);
		colorModule.setColor(Color.parseColor("#1ec391"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(34);
		colorModule.setSubProgress(50);
		colorModule.setColor(Color.parseColor("#2bb468"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(10);
		colorModule.setSubProgress(93);
		colorModule.setColor(Color.parseColor("#43be36"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(4);
		colorModule.setSubProgress(80);
		colorModule.setColor(Color.parseColor("#69c819"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(87);
		colorModule.setSubProgress(93);
		colorModule.setColor(Color.parseColor("#e3aa14"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(88);
		colorModule.setSubProgress(65);
		colorModule.setColor(Color.parseColor("#ff915a"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(88);
		colorModule.setSubProgress(65);
		colorModule.setColor(Color.parseColor("#fd716e"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(88);
		colorModule.setSubProgress(65);
		colorModule.setColor(Color.parseColor("#fd5550"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(88);
		colorModule.setSubProgress(65);
		colorModule.setColor(Color.parseColor("#323638"));
		mColorModules.add(colorModule);

		colorModule = new ColorModule();
		colorModule.setMainProgress(-1);
		colorModule.setSubProgress(-1);
		colorModule.setColor(-1);
		mColorModules.add(colorModule);

		addColorViews();

	}

	private int currentColorIndex;

	private void addColorViews() {
		for (int i = 0, count = mColorModules.size(); i < count; i++) {
			View contentView =View.inflate(this,R.layout.saltyfish_select_color_item,null);
			TextView colorView= (TextView) contentView.findViewById(R.id.ctv_color);
			final RadioButton selectView= (RadioButton) contentView.findViewById(R.id.crb_select);
			final ColorModule colorModule = mColorModules.get(i);
			Bitmap colorBitmap = readBitMap(this,IMGS[i]);
			if (null != colorBitmap) {
				colorView.setBackgroundDrawable(new BitmapDrawable(getResources(),colorBitmap));
			} else {
				colorView.setBackgroundColor(colorModule.getColor());
			}
			final int index = i;
			final int color = colorModule.getColor();
			final int mainProgress = colorModule.getMainProgress();
			final int subProgress = colorModule.getSubProgress();
			colorView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					setAllUnChecked();
					selectView.setChecked(true);
					selectView.setVisibility(View.VISIBLE);
					currentColorIndex = index;
					if (-1 == color || -1 == mainProgress || -1 == subProgress) {

						Rect frame = new Rect();
						getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
						// mColorPickerContainer.setTranslationX(-frame.width());
//						ObjectAnimator.ofFloat(mColorPickerContainer, "TranslationX", -frame.width()).setDuration(250).start();
						Animation inAnimation= AnimationUtils.loadAnimation(SaltyfishSetSkinChooseColorAct.this,R.anim.saltyfish_right_to_left_enter);
						inAnimation.setDuration(250);
						Animation outAnimation= AnimationUtils.loadAnimation(SaltyfishSetSkinChooseColorAct.this,R.anim.saltyfish_right_to_left_exit);
						outAnimation.setDuration(250);
						mColorPickerContainer.setInAnimation(inAnimation);
						mColorPickerContainer.setOutAnimation(outAnimation);
						mColorPickerContainer.showNext();
						return;
					}
					mSkinLayout.setBackgroundColor(colorModule.getColor());
					mainColorPicker.setProgress(mainProgress);
					mSubColorPicker.setProgress(subProgress);
				}
			});

			mColorContainer.addView(contentView);
		}
	}

	private synchronized void setAllUnChecked() {
		for (int i = 0, childCount = mColorContainer.getChildCount(); i < childCount; i++) {
			RadioButton selectView = (RadioButton) mColorContainer.getChildAt(i).findViewById(R.id.crb_select);
			selectView.setChecked(false);
			selectView.setVisibility(View.GONE);
		}
	}
}
