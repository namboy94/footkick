package net.namibsun.footkick.android.common;

import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewAnimator;
import net.namibsun.footkick.android.R;


/**
 * Class that defines a GestureDetector that can be used to swipe left and right between Views
 * in a ViewSwitcher or Viewflipper
 */
public class ViewSwiper extends GestureDetector.SimpleOnGestureListener {

    /**
     * The view switcher of flipper to be used
     */
    private ViewAnimator switcher;

    /**
     * The animation to be used when swiping left
     */
    private Animation slide_in_left, slide_out_right;

    /**
     * The animation to be used when swiping right
     */
    private Animation slide_in_right, slide_out_left;

    private Runnable nextRunner = null;
    private Runnable previousRunner = null;

    /**
     * The Constructor of the ViewSwiper class. It saves the switcher as a class variable
     * and also initializes the animations.
     * @param activity the activity creating this ViewSwiper, needed to initialize the animations
     * @param switcher the View switcher/flipper to be used
     */
    @SuppressWarnings("unused")
    public ViewSwiper(ActivityFrameWork activity, ViewAnimator switcher) {
        this.switcher = switcher;
        this.slide_in_left = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
        this.slide_in_right = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
        this.slide_out_left = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
        this.slide_out_right = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
    }

    /**
     * Alternate constructor that also defines runnables to be run when switching to the next or
     * previous view
     * @param activity the activity creating this ViewSwiper, needed to initialize the animations
     * @param switcher the View switcher/flipper to be used
     * @param previousMethod the runnable to be run whenever the previous View will be shown
     * @param nextMethod the runnable to be run whenever the next View will be shown
     */
    public ViewSwiper(ActivityFrameWork activity, ViewAnimator switcher, Runnable previousMethod,
                      Runnable nextMethod) {
        this.switcher = switcher;
        this.slide_in_left = AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
        this.slide_in_right = AnimationUtils.loadAnimation(activity, R.anim.slide_in_right);
        this.slide_out_left = AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
        this.slide_out_right = AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
        this.nextRunner = nextMethod;
        this.previousRunner = previousMethod;
    }

    /**
     * Handles the actual fling motion. Checks if the swipe has reached a certain threshold
     * and if the fling was right or left
     * @param event1 the first motion event, i.e. where the user started touching the screen
     * @param event2 the second motion event, i.e. where the user stopped touching the screen
     * @param velocityX the speed of the fling in X direction, used to check
     *                  if the screen was swiped fast enough to trigger a View switch
     * @param velocityY the y Velocity of the fling. Not used here.
     * @return if the event triggered???
     */
    @Override
    public boolean onFling(MotionEvent event1, MotionEvent event2,
                           float velocityX, float velocityY) {

        if (Math.abs(velocityX) > 3000.0) {  //Velocity must be at least 3000.0
            // Swipe left (next)
            if (event1.getX() > event2.getX()) {
                //Set the animations
                this.switcher.setInAnimation(this.slide_in_right);
                this.switcher.setOutAnimation(this.slide_out_left);
                this.switcher.showNext();
                if (this.nextRunner != null) {
                    this.nextRunner.run();
                }
            }
            // Swipe left (next)
            else {
                this.switcher.setInAnimation(this.slide_in_left);
                this.switcher.setOutAnimation(this.slide_out_right);
                this.switcher.showPrevious();
                if (this.previousRunner != null) {
                    this.previousRunner.run();
                }
            }
        }
        return super.onFling(event1, event2, velocityX, velocityY);
    }

}