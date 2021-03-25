package saltyfish.generalskin;

/**
 * Project: GeneralSkin<br/>
 * Package: saltyfish.generalskin<br/>
 * ClassName: ColorModule<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/12 9:08 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class ColorModule {

	/**
	 * 颜色
	 */
	private int color;

	/**
	 * 主颜色条
	 */
	private int mainProgress;

	/**
	 * 次要颜色条
	 */
	private int subProgress;

	/**
	 * 背景图片
     */
	private String backgroundDrawable;

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getMainProgress() {
		return mainProgress;
	}

	public void setMainProgress(int mainProgress) {
		this.mainProgress = mainProgress;
	}

	public int getSubProgress() {
		return subProgress;
	}

	public void setSubProgress(int subProgress) {
		this.subProgress = subProgress;
	}

	public String getBackgroundDrawable() {
		return backgroundDrawable;
	}

	public void setBackgroundDrawable(String backgroundDrawable) {
		this.backgroundDrawable = backgroundDrawable;
	}
}
