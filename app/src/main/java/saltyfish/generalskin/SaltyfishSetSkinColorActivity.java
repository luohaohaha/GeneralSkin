package saltyfish.generalskin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RadioButton;

import saltyfish.library.base.SaltyfishBaseActivity;
import saltyfish.library.skin.SaltyfishSkinColorTool;

import static saltyfish.library.utils.SaltyfishUtilTool.getSaltyfishInstance;

public class SaltyfishSetSkinColorActivity extends SaltyfishBaseActivity implements View.OnClickListener{

    private int colorBlue;
    private int colorBlueLight;

    private RadioButton[] radioButtons;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saltyfish_activity_set_skin);
        initViews();
    }

    private void initViews() {
        // 标题
        radioButtons = new RadioButton[] { (RadioButton) findViewById(R.id.crb_blue),
                (RadioButton) findViewById(R.id.crb_blue_light), (RadioButton) findViewById(R.id.crb_custom) };
        colorBlue = Color.parseColor("#09b6f2");
        colorBlueLight = Color.parseColor("#0066b3");
    }

    @Override
    public void onClick(View v) {

        int i = v.getId();
        int color = -1;
        int checkid = -1;
        if (i == R.id.cfl_blue) {
            color = colorBlue;
            checkid = R.id.crb_blue;
        } else if (i == R.id.cfl_blue_light) {
            color = colorBlueLight;
            checkid = R.id.crb_blue_light;
        } else if (i == R.id.cfl_custom) {
            checkid = R.id.crb_custom;
            startActivity(new Intent(this, SaltyfishSetSkinChooseColorAct.class));
        }

        if (-1 != checkid) {
            for (RadioButton rv : radioButtons) {
                if (rv.getId() == checkid) {
                    rv.setChecked(true);
                } else {
                    rv.setChecked(false);
                }
            }
        }
        if (-1 == color)
            return;
        getSaltyfishInstance(SaltyfishSkinColorTool.class).saveThemeColor(color, -1, -1);
        getSaltyfishInstance(SaltyfishSkinColorTool.class).changeSkin(this);
    }
}
