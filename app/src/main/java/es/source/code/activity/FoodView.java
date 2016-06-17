package es.source.code.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class FoodView extends Activity implements View.OnClickListener {

    private ViewPager mViewPager;// ViewPager定义在xml中
    private int pWidth;// 图片宽度
    private int offset;// 图片偏移量
    private int currentIndex; // 当前标签位置
    private ImageView mTabLineIv;//下划线
    private int screenWidth;//手机屏幕宽度
    private TextView mTabCool, mTabHot, mTabSea, mTabWine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        mTabLineIv = (ImageView) this.findViewById(R.id.cursor);
        initViews();
        initTabLineWidth();
    }

    public void initViews() {

        //init ViewPager
        mViewPager = (ViewPager) findViewById(R.id.viewFlipper);
        List<View> mList = new ArrayList<View>();

        //通过LayoutInflater构建几个子标签页，并存入List集合
        LayoutInflater inflater = LayoutInflater.from(this);
        View view1 = inflater.inflate(R.layout.food_hot, null);
        View view2 = inflater.inflate(R.layout.food_cold, null);
        View view3 = inflater.inflate(R.layout.food_sea, null);
        View view4 = inflater.inflate(R.layout.food_wine, null);
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        mList.add(view4);
        // step3>>创建自定义PagerAdapter 并添加OnPageChangeListener监听
        MyPagerAdapter adapter = new MyPagerAdapter(mList);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(adapter);

        mTabCool = (TextView)findViewById(R.id.text1);
        mTabHot = (TextView) findViewById(R.id.text2);
        mTabSea = (TextView) findViewById(R.id.text3);
        mTabWine = (TextView) findViewById(R.id.text4);
        // step4>>导航栏上每一标签项添加onClick监听
        mTabCool.setOnClickListener(this);
        mTabHot.setOnClickListener(this);
        mTabSea.setOnClickListener(this);
        mTabWine.setOnClickListener(this);

        //获取设置后的下划线宽度
        pWidth = mTabLineIv.getWidth();

        // 获取手机屏幕分辨率(宽度)
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        int screenWidth = dm.widthPixels;

        // 初始化偏移量，计算原理如下
        // 每一个标签页所占：offset+pWidth+offset...[以此类推]
        offset = (screenWidth / 4 - pWidth) / 2;

//         【方法一】 可以通过Matrix属性设置ImageView组件的偏移
//         需要xml中ImageView宽度fill_parent，android:scaleType="matrix"
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        mTabLineIv.setImageMatrix(matrix);

    }

    class MyPagerAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener {

        private List<View> mList;

        public MyPagerAdapter(List<View> views) {
            mList = views;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mList.get(position), 0);
            return mList.get(position);
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int position, float offset, int offsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            resetTextView();
            switch (position) {
                case 0:
                    mTabCool.setTextColor(Color.BLUE);
                    break;
                case 1:
                    mTabHot.setTextColor(Color.BLUE);
                    break;
                case 2:
                    mTabSea.setTextColor(Color.BLUE);
                    break;
                case 3:
                    mTabWine.setTextColor(Color.BLUE);
                    break;
            }
        // 获取每一个标签页所占宽度
            int section = offset * 2 + pWidth;
            // currentIndex为上一次滑动所处标签页位置(0,1,2,3)
            Animation animation = new TranslateAnimation(
                    section * currentIndex, section * position, 0, 0);
            currentIndex = position;
            animation.setDuration(500);
            animation.setFillAfter(true);// 动画结束后停留在当前所处位置
            mTabLineIv.startAnimation(animation);
            Toast.makeText(FoodView.this,
                    "您选择了：" + currentIndex + " 标签页", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View v) {
        int index = 0;
        switch (v.getId()) {
            case R.id.text1:
                index = 0;
                break;
            case R.id.text2:
                index = 1;
                break;
            case R.id.text3:
                index = 2;
                break;
            case R.id.text4:
                index = 3;
                break;
        }
        mViewPager.setCurrentItem(index);
    }

    /**
     * 设置滑动条的宽度为屏幕的1/4(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 4;
        mTabLineIv.setLayoutParams(lp);
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        mTabCool.setTextColor(Color.BLACK);
        mTabHot.setTextColor(Color.BLACK);
        mTabSea.setTextColor(Color.BLACK);
        mTabWine.setTextColor(Color.BLACK);
    }
}
