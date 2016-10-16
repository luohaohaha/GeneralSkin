package saltyfish.generalskin;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import saltyfish.library.base.SaltyfishBaseFragment;

/**
 * Project: GeneralSkin<br/>
 * Package: saltyfish.generalskin<br/>
 * ClassName: TabFragment2<br/>
 * Description: ${TODO} <br/>
 * Date: 2016/10/12 9:33 <br/>
 * <p>
 * Author 昊<br/>
 * Version 1.0<br/>
 * since JDK 1.6<br/>
 * <p>
 * Copyright (c) 2016 luohaohaha@gmail.com All rights reserved
 */

public class TabFragment3 extends SaltyfishBaseFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.tab3,container,false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return view;
    }
}
