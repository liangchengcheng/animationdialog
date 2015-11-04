package mddemo.library.com.activityanimation_master;

import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import eu.chainfire.libsuperuser.Shell;

public class FirtMainActivity extends AppCompatActivity implements DialogInterface.OnDismissListener{
    private CircularRevealView revealView;
    private View selectedView;
    android.os.Handler handler;
    private int backgroundColor;
    RelativeLayout layout;
    int maxX, maxY;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firt_main);
        backgroundColor = Color.parseColor("#11303030");
        layout = (RelativeLayout) findViewById(R.id.layout);
        Display mdisp = getWindowManager().getDefaultDisplay();
        Point mdispSize = new Point();
        mdisp.getSize(mdispSize);
        maxX = mdispSize.x;
        maxY = mdispSize.y;

        revealView = (CircularRevealView) findViewById(R.id.reveal);
        button= (Button) findViewById(R.id.btn_ok);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int color = Color.parseColor("#00bcd4");
                final Point p = getLocationInView(revealView, v);

                revealView.reveal(p.x, p.y, color, v.getHeight() / 2, 440, null);
                selectedView = v;

                handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showPowerDialog();
                    }
                }, 50);
            }
        });

    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private void showPowerDialog() {
        FragmentManager fm = getFragmentManager();
        PowerDialog powerDialog = new PowerDialog();
        powerDialog.show(fm, "fragment_power");

    }

    public void revealFromTop() {
        final int color = Color.parseColor("#ffffff");


        final Point p = new Point(maxX / 2, maxY / 2);

        revealView.reveal(p.x, p.y, color, button.getHeight() / 2, 440, null);


    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        View v = button;
        final Point p = getLocationInView(revealView, v);

        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                revealView.hide(p.x, p.y, backgroundColor, 0, 330, null);
            }
        }, 300);


        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                layout.setVisibility(View.VISIBLE);
            }
        }, 500);

    }


}
