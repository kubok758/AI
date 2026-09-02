package com.artem.decisionpulse;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.HapticFeedbackConstants;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.SecureRandom;

public class MainActivity extends Activity {
    private final SecureRandom random = new SecureRandom();
    private LinearLayout root;
    private TextView answer;
    private TextView hint;
    private TextView button;
    private boolean firstRun = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Window window = getWindow();
        window.setStatusBarColor(Color.rgb(10, 10, 14));
        window.setNavigationBarColor(Color.rgb(10, 10, 14));

        root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(dp(24), dp(32), dp(24), dp(32));
        root.setBackgroundColor(Color.rgb(10, 10, 14));

        TextView title = new TextView(this);
        title.setText("DECISION PULSE");
        title.setTextColor(Color.rgb(172, 177, 196));
        title.setTextSize(13f);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setLetterSpacing(0.14f);
        root.addView(title, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        View spacerTop = new View(this);
        root.addView(spacerTop, new LinearLayout.LayoutParams(1, 0, 1f));

        TextView question = new TextView(this);
        question.setText("Не можешь решить?");
        question.setTextColor(Color.WHITE);
        question.setTextSize(25f);
        question.setGravity(Gravity.CENTER);
        question.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        root.addView(question, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        hint = new TextView(this);
        hint.setText("Нажми кнопку — приложение выберет за тебя");
        hint.setTextColor(Color.rgb(145, 149, 167));
        hint.setTextSize(15f);
        hint.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        hintParams.topMargin = dp(10);
        root.addView(hint, hintParams);

        answer = new TextView(this);
        answer.setText("?");
        answer.setTextColor(Color.WHITE);
        answer.setTextSize(78f);
        answer.setGravity(Gravity.CENTER);
        answer.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        LinearLayout.LayoutParams answerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(170));
        answerParams.topMargin = dp(24);
        root.addView(answer, answerParams);

        button = new TextView(this);
        button.setText("РЕШИТЬ");
        button.setTextColor(Color.WHITE);
        button.setTextSize(17f);
        button.setGravity(Gravity.CENTER);
        button.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        button.setClickable(true);
        button.setFocusable(true);
        setButtonBackground(Color.rgb(90, 80, 255));
        LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(60));
        buttonParams.topMargin = dp(12);
        root.addView(button, buttonParams);

        TextView footer = new TextView(this);
        footer.setText("50 / 50 • полностью офлайн");
        footer.setTextColor(Color.rgb(94, 98, 116));
        footer.setTextSize(12f);
        footer.setGravity(Gravity.CENTER);
        LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        footerParams.topMargin = dp(16);
        root.addView(footer, footerParams);

        View spacerBottom = new View(this);
        root.addView(spacerBottom, new LinearLayout.LayoutParams(1, 0, 1f));

        button.setOnClickListener(v -> decide());
        root.setOnClickListener(v -> {
            if (!firstRun) decide();
        });

        setContentView(root);
    }

    private void decide() {
        firstRun = false;
        boolean yes = random.nextBoolean();
        answer.setText(yes ? "ДА" : "НЕТ");
        answer.setTextColor(yes ? Color.rgb(97, 230, 167) : Color.rgb(255, 100, 121));
        hint.setText(yes ? "Сегодня вселенная говорит: делай" : "Сегодня лучше пропустить");
        setButtonBackground(yes ? Color.rgb(38, 164, 108) : Color.rgb(207, 62, 85));

        button.performHapticFeedback(HapticFeedbackConstants.CONFIRM);
        answer.setScaleX(0.72f);
        answer.setScaleY(0.72f);
        answer.setAlpha(0.2f);
        answer.animate()
                .scaleX(1f)
                .scaleY(1f)
                .alpha(1f)
                .setDuration(260)
                .setInterpolator(new AccelerateDecelerateInterpolator())
                .start();
    }

    private void setButtonBackground(int color) {
        GradientDrawable shape = new GradientDrawable();
        shape.setColor(color);
        shape.setCornerRadius(dp(20));
        button.setBackground(shape);
    }

    private int dp(int value) {
        return Math.round(value * getResources().getDisplayMetrics().density);
    }
}
