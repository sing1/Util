package sing.utils.demo;

import android.os.Bundle;

import sing.utils.BaseActivity;
import sing.utils.R;
import sing.widget.RoundProgressBar;

public class ActRoundProgressBar extends BaseActivity {

    private int mTotalProgress;
    private int mCurrentProgress;
    private RoundProgressBar rpBar01, rpBar02 ,rpBar03, rpBar04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_round_progress_bar);
        mTotalProgress = 100;
        mCurrentProgress = 0;
        rpBar01 = (RoundProgressBar) findViewById(R.id.roundProgressBar01);
        rpBar02 = (RoundProgressBar) findViewById(R.id.roundProgressBar02);
        rpBar03 = (RoundProgressBar) findViewById(R.id.roundProgressBar03);
        rpBar04 = (RoundProgressBar) findViewById(R.id.roundProgressBar04);

        new Thread(new ProgressRunable()).start();
    }

    class ProgressRunable implements Runnable {

        @Override
        public void run() {
            while (mCurrentProgress < mTotalProgress) {
                mCurrentProgress += 1;

                rpBar01.setProgress(mCurrentProgress);
                rpBar02.setProgress(mCurrentProgress);
                rpBar03.setProgress(mCurrentProgress);
                rpBar04.setProgress(mCurrentProgress);

                try {
                    Thread.sleep(100);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}