package com.tcc.dagon.opus.utils;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;

import java.lang.reflect.Field;

/**
 * Created by cahwayan on 20/10/2016.
 *
 * ESSA CLASSE SOBRESCREVE A CLASSE DO VIEWPAGER, PARA BLOQUEAR O SWIPE ENTRE AS ETAPAS
 * TUDO O QUE PRECISA SER FEITO É CHAMAR ESSA CLASSE NO DRAWABLE DO CONTENT DO FRAGMENTO AO INVÉS
 * DE CHAMAR O VIEWPAGER NORMAL
 * EX: AO INVÉS DE CHAMAR
 * ANDROID.SUPPORT.V4.VIEWPAGER
 * CHAMAMOS
 * COM.TCC.DAGON.UTILS.VIEWPAGERSEMSWIPE
 * DAÍ É USADA ESTA CLASSE PARA MONTAR O VIEWPAGER
 */

public class ViewPagerSemSwipe extends ViewPager {

    private String[] tabTitulos;

    public ViewPagerSemSwipe(Context context) {
        super(context);
        setMyScroller();
    }

    public ViewPagerSemSwipe(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMyScroller();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        return false;
    }

    //down one is added for smooth scrolling

    private void setMyScroller() {
        try {
            Class<?> viewpager = ViewPager.class;
            Field scroller = viewpager.getDeclaredField("mScroller");
            scroller.setAccessible(true);
            scroller.set(this, new MyScroller(getContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class MyScroller extends Scroller {
        public MyScroller(Context context) {
            super(context, new DecelerateInterpolator());
        }

        @Override
        public void startScroll(int startX, int startY, int dx, int dy, int duration) {
            super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
        }
    }
}