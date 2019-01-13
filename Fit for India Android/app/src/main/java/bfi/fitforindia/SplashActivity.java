package bfi.fitforindia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.os.Handler;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ConstraintLayout splashRoot = findViewById(R.id.splash_container);
        final ConstraintSet finalConstraints = new ConstraintSet();
        final ImageView appNameLogo = findViewById(R.id.splash_txt);
        final EditText username = findViewById(R.id.inp_username), password = findViewById(R.id.inp_password);
        final Button loginBtn = findViewById(R.id.btn_login);
        final TextView signup = findViewById(R.id.txt_signup);
        final ProgressBar loginprogress = findViewById(R.id.login_progress);
        finalConstraints.load(this, R.layout.activity_splash_final);
        final Transition transition = new ChangeBounds().setDuration(800).setInterpolator(new AnticipateOvershootInterpolator(1.0f));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(splashRoot, transition);
                finalConstraints.applyTo(splashRoot);
                username.setEnabled(true);
                password.setEnabled(true);
                loginBtn.setEnabled(true);
                appNameLogo.animate().alpha(1).setStartDelay(800);
                username.animate().alpha(1).setStartDelay(800);
                password.animate().alpha(1).setStartDelay(800);
                loginBtn.animate().alpha(1).setStartDelay(800);
                signup.animate().alpha(1).setStartDelay(800);
            }
        }, 200);
    }
}
