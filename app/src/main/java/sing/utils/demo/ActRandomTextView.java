package sing.utils.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import sing.utils.BaseActivity;
import sing.utils.R;
import sing.widget.RandomTextView;

public class ActRandomTextView extends BaseActivity {

    private RandomTextView randomTextView;
    private int[] pianyiliang = new int[6];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_random_textview);

        randomTextView = (RandomTextView) findViewById(R.id.rtv);

        pianyiliang[0] = 10;
        pianyiliang[1] = 9;
        pianyiliang[2] = 8;
        pianyiliang[3] = 7;
        pianyiliang[4] = 6;
        pianyiliang[5] = 5;
        randomTextView.setPianyilian(pianyiliang);
        randomTextView.start();
    }

    /**
     * 速度相同
     */
    public void start1(View v) {
        randomTextView.setText("987654");
        randomTextView.setPianyilian(RandomTextView.ALL);
        randomTextView.start();
    }

    /**
     * 自定义速度
     */
    public void start2(View v) {
        randomTextView.setText("252342");
        pianyiliang[0] = 7;
        pianyiliang[1] = 6;
        pianyiliang[2] = 12;
        pianyiliang[3] = 8;
        pianyiliang[4] = 18;
        pianyiliang[5] = 10;
        randomTextView.setMaxLine(20);
        randomTextView.setPianyilian(pianyiliang);
        randomTextView.start();
    }

    /**
     * 前慢后快
     */
    public void start3(View v) {
        randomTextView.setText("234523");
        randomTextView.setPianyilian(RandomTextView.FIRSTF_LAST);
        randomTextView.start();
    }

    /**
     * 前快后慢
     */
    public void start4(View v) {
        randomTextView.setText("534522");
        randomTextView.setPianyilian(RandomTextView.FIRSTF_FIRST);
        randomTextView.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        randomTextView.destroy();
    }
}