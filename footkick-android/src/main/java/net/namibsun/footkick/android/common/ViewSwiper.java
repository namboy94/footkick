package net.namibsun.footkick.android.common;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;
import net.namibsun.footkick.android.R;


public class ViewSwiper extends GestureDetector.SimpleOnGestureListener {

    private ViewAnimator switcher;
    private Animation slide_in_left, slide_out_right;
    private Animation slide_in_right, slide_out_left;
    
    public ViewSwiper(ActivityFrameWork activity, ViewAnimator switcher) {
        this.switcher = switcher;
        this.slide_in_left = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
        this.slide_in_right = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
        this.slide_out_left = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
        this.slide_out_right = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
    }
    
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        // Swipe left (next)

        if (Math.abs(velocityX) > 3000.0) {
            if (e1.getX() > e2.getX()) {
                this.switcher.setInAnimation(this.slide_in_right);
                this.switcher.setOutAnimation(this.slide_out_left);
            }
            else {
                this.switcher.setInAnimation(this.slide_in_left);
                this.switcher.setOutAnimation(this.slide_out_right);
            }
            this.switcher.showNext();
        }
        return super.onFling(e1, e2, velocityX, velocityY);
    }

}