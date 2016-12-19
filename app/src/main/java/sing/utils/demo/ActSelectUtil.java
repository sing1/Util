package sing.utils.demo;

import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import sing.util.SelectorUtil;
import sing.utils.R;

public class ActSelectUtil extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_select);

        init();
    }

    private void init() {
        GradientDrawable bg1 = SelectorUtil.getBgNoPress(this,"#123123","#FFFFFF");
        findViewById(R.id.bt1).setBackground(bg1);

        StateListDrawable bg2 = SelectorUtil.getBgPress(this,"#123123","#FFFFFF","#E5E5E5");
        findViewById(R.id.bt2).setBackground(bg2);

        GradientDrawable bg3 = SelectorUtil.getBgNoPressRadius(this,"#123123","#FFFFFF",new float[] {
                0, 0,
                0, 0,
                8, 8,
                8, 8
        });
        findViewById(R.id.bt3).setBackground(bg3);

        StateListDrawable bg4 = SelectorUtil.getBgPressRadius(this,"#123123","#FFFFFF","#E5E5E5",new float[] {
                0, 0,
                0, 0,
                8, 8,
                8, 8
        });
        findViewById(R.id.bt4).setBackground(bg4);
    }
}