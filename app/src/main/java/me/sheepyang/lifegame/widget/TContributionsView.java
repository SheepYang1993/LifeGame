package me.sheepyang.lifegame.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import me.sheepyang.lifegame.R;
import me.sheepyang.lifegame.adapter.tcontributisview.BaseContributionsViewAdapter;
import me.sheepyang.lifegame.adapter.tcontributisview.TestContributionAdapter;
import me.sheepyang.lifegame.util.AppUtil;
import me.sheepyang.lifegame.util.LogUtils;

/**
 * https://github.com/barryhappy
 * Created by Barry on 2016/11/19
 */

public class TContributionsView extends View {
    private onItemClickListener mOnItemClickListener;
    private onClickListener mOnClickListener;
    protected BaseContributionsViewAdapter mAdapter;
    private long mStartTime = 0;
    private long mEndTime = 0;

    private int colorEmpty = Color.parseColor("#e0e0e0");
    private int colorL1 = Color.parseColor("#cde372");
    private int colorL2 = Color.parseColor("#7bbd52");
    private int colorL3 = Color.parseColor("#389631");
    private int colorL4 = Color.parseColor("#1a571b");

    protected int itemWidth = 30;
    protected int itemHeight = 30;
    protected int itemSpace = 6;

    protected Paint paintEmpty = new Paint();
    protected Paint paintL1 = new Paint();
    protected Paint paintL2 = new Paint();
    protected Paint paintL3 = new Paint();
    protected Paint paintL4 = new Paint();
    private RectF rectF;

    private boolean useCircleMode = false;

    public TContributionsView(Context context) {
        this(context, null);
    }

    public TContributionsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TContributionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TContributionsView);
        try {
            itemWidth = a.getDimensionPixelSize(R.styleable.TContributionsView_contributions_item_width, 20);
            itemHeight = a.getDimensionPixelSize(R.styleable.TContributionsView_contributions_item_height, 20);
            itemSpace = a.getDimensionPixelSize(R.styleable.TContributionsView_contributions_item_space, 2);
            colorEmpty = a.getColor(R.styleable.TContributionsView_contributions_color_0, colorEmpty);
            colorL1 = a.getColor(R.styleable.TContributionsView_contributions_color_1, colorL1);
            colorL2 = a.getColor(R.styleable.TContributionsView_contributions_color_2, colorL2);
            colorL3 = a.getColor(R.styleable.TContributionsView_contributions_color_3, colorL3);
            colorL4 = a.getColor(R.styleable.TContributionsView_contributions_color_4, colorL4);
            useCircleMode = a.getBoolean(R.styleable.TContributionsView_contributions_use_circle, useCircleMode);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }

        paintEmpty.setColor(colorEmpty);
        paintL1.setColor(colorL1);
        paintL2.setColor(colorL2);
        paintL3.setColor(colorL3);
        paintL4.setColor(colorL4);
        paintEmpty.setAntiAlias(true);
        paintL1.setAntiAlias(true);
        paintL2.setAntiAlias(true);
        paintL3.setAntiAlias(true);
        paintL4.setAntiAlias(true);


        rectF = new RectF(0, 0, itemWidth, itemHeight);
        if (isInEditMode()) {
            mAdapter = new TestContributionAdapter();
            this.setAdapter(mAdapter);
        }
    }

    public void setItemSpace(int itemSpace) {
        this.itemSpace = itemSpace;
    }

    public void setItemWidth(int itemWidth) {
        this.itemWidth = itemWidth;
    }

    public void setItemHeight(int itemHeight) {
        this.itemHeight = itemHeight;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnClickListener(onClickListener onClickListener) {
        mOnClickListener = onClickListener;
    }

    int mTouchCount = 0;
    PointF mStartPoint = new PointF();
    PointF mEndPoint = new PointF();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            mTouchCount++;
        } else if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL) {
            mTouchCount--;
        }
        //获取到手指处的横坐标和纵坐标
        int x = (int) event.getX();
        int y = (int) event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mStartTime = System.currentTimeMillis();
                mStartPoint.x = event.getX();
                mStartPoint.y = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                mEndPoint.x = event.getX();
                mEndPoint.y = event.getY();
                int itemIndexX = getItemIndexX(mStartPoint.x);
                int itemIndexY = getItemIndexY(mStartPoint.y);
                mEndTime = System.currentTimeMillis();

                if ((mEndTime - mStartTime) < 0.1 * 1000L) {// 触发点击事件
                    if (itemIndexX == getItemIndexX(mEndPoint.x) && itemIndexY == getItemIndexY(mEndPoint.y)) {
                        LogUtils.i("ItemClick:" + itemIndexX + "," + itemIndexY);
                        //TODO onItemClick
                        if (mOnClickListener != null) {
                            mOnClickListener.onClick();
                        } else {
                            if (mOnItemClickListener != null) {
                                mOnItemClickListener.onItemClick(itemIndexX, itemIndexY);
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_MOVE:
                int offX = 0;
                int offY = 0;
                View parent = ((ViewGroup) getParent());
                //计算移动的距离
                if (getMeasuredHeight() > parent.getMeasuredHeight()) {
                    offY = (int) (y - mStartPoint.y);
                }
                if (getMeasuredWidth() > parent.getMeasuredWidth()) {
                    offX = (int) (x - mStartPoint.x);
                }

                if (offX > 0) {
//                    LogUtils.i("右滑");
                    if (parent.getScrollX() < -(parent.getMeasuredWidth() - AppUtil.dip2px(getContext(), 150))) {
//                        LogUtils.i("不能右滑");
                        offX = 0;
                    }
                } else if (offX < 0) {
//                    LogUtils.i("左滑");
                    if (parent.getScrollX() > (getMeasuredWidth() - AppUtil.dip2px(getContext(), 150))) {
//                        LogUtils.i("不能左滑");
                        offX = 0;
                    }
                }

                if (offY > 0) {
//                    LogUtils.i("下滑");
                    if (parent.getScrollY() < -(parent.getMeasuredHeight() - AppUtil.dip2px(getContext(), 150))) {
//                        LogUtils.i("不能下滑");
                        offY = 0;
                    }
                } else if (offY < 0) {
//                    LogUtils.i("上滑");
                    if (parent.getScrollY() > (getMeasuredHeight() - AppUtil.dip2px(getContext(), 150))) {
//                        LogUtils.i("不能上滑");
                        offY = 0;
                    }
                }
                parent.scrollBy(-offX, -offY);
                break;
        }
        return true;
    }

    int getItemIndexX(float touchX) {
        if (touchX < 0 || touchX > getMeasuredWidth()) {
            return -1;
        }
        Float itemIndex = touchX / (itemWidth + itemSpace);
        return (itemIndex - itemIndex.intValue()) <= 1 - itemSpace / ((float) itemWidth + itemSpace) ?
                itemIndex.intValue() : -1;
    }

    int getItemIndexY(float touchY) {
        if (touchY < 0 || touchY > getMeasuredHeight()) {
            return -1;
        }
        Float itemIndex = touchY / (itemHeight + itemSpace);
        return (itemIndex - itemIndex.intValue()) <= 1 - itemSpace / ((float) itemHeight + itemSpace) ?
                itemIndex.intValue() : -1;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int row = 0;
        int column = 0;
        if (this.mAdapter != null) {
            row = mAdapter.getRowCount();
            column = mAdapter.getColumnCount();
        }
        int measureWidth = (column == 0 ? 0 : column * (itemWidth + itemSpace) - itemSpace)
                + getPaddingLeft() + getPaddingRight();
        int measureHeight = (row == 0 ? 0 : row * (itemHeight + itemSpace) - itemSpace)
                + getPaddingTop() + getPaddingBottom();

        int mWidth = widthMode == MeasureSpec.EXACTLY ?
                MeasureSpec.getSize(widthMeasureSpec) : measureWidth;
        int mHeight = heightMode == MeasureSpec.EXACTLY ?
                MeasureSpec.getSize(heightMeasureSpec) : measureHeight;
        setMeasuredDimension(mWidth, mHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.clipRect(getPaddingLeft(), getPaddingTop(),
                getRight() - getPaddingRight(),
                getBottom() - getPaddingBottom());
        if (mAdapter != null) {
            final int columnCount = mAdapter.getColumnCount();
            final int rowCount = mAdapter.getRowCount();
            for (int week = 0; week < columnCount; week++) {
                for (int day = 0; day < rowCount; day++) {
                    rectF.left = (week == 0 ? 0 : week * (itemWidth + itemSpace)) + getPaddingLeft();
                    rectF.right = rectF.left + itemWidth;
                    rectF.top = (day == 0 ? 0 : day * (itemHeight + itemSpace)) + +getPaddingTop();
                    rectF.bottom = rectF.top + itemHeight;
                    final int level = mAdapter.getLevel(day, week);
                    final Paint paintByLevel = getPaintByLevel(level);
                    if (mAdapter.getOnDrawItemListener() == null ||
                            (mAdapter.getOnDrawItemListener() != null && !mAdapter.getOnDrawItemListener().beforeDrawItem(rectF, canvas, paintByLevel, level))) {
                        if (level >= 0) {
                            drawItem(rectF, canvas, paintByLevel, level);
                        }
                    }
                    if (mAdapter.getOnDrawItemListener() != null) {
                        mAdapter.getOnDrawItemListener().afterDrawItem(rectF, canvas, paintByLevel, level);
                    }

                }
            }
        }
    }

    protected void drawItem(RectF rect, Canvas canvas, Paint paintByLevel, int level) {

        if (useCircleMode) {
            paintByLevel.setAntiAlias(true);
            canvas.drawCircle((rectF.left + rectF.right) / 2,
                    (rectF.top + rectF.bottom) / 2,
                    Math.min(itemWidth, itemHeight) / 2,
                    paintByLevel);
        } else {
            canvas.drawRect(rect, paintByLevel);
        }
    }

    private Paint getPaintByLevel(int level) {

        switch (level) {
            case 0:
                return paintEmpty;
            case 1:
                return paintL1;
            case 2:
                return paintL2;
            case 3:
                return paintL3;
            case 4:
                return paintL4;
        }
        return paintEmpty;
    }

    public void setAdapter(BaseContributionsViewAdapter adapter) {
        this.mAdapter = adapter;
        if (this.mAdapter != null) {
            this.mAdapter.setContributionsView(this);
            this.mAdapter.notifyDataSetChanged();
        }
    }

    public BaseContributionsViewAdapter getAdapter() {
        return mAdapter;
    }

    public int getItemWidth() {
        return itemWidth;
    }

    public int getItemHeight() {
        return itemHeight;
    }

    public int getItemSpace() {
        return itemSpace;
    }

    public boolean isUseCircleMode() {
        return useCircleMode;
    }

    public static interface OnDrawItemListener {
        /**
         * called before the default drawItem method,
         * You are abel to control if call default drawItem next.
         *
         * @param rect
         * @param canvas
         * @param level
         * @return True will not called default drawItem method, false otherwise.
         */
        boolean beforeDrawItem(RectF rect, Canvas canvas, Paint paint, int level);

        /**
         * called after the default drawItem method
         *
         * @param rect
         * @param canvas
         * @param level
         */
        void afterDrawItem(RectF rect, Canvas canvas, Paint paint, int level);
    }

    public interface onClickListener {
        void onClick();
    }

    public interface onItemClickListener {
        void onItemClick(int itemIndexX, int itemIndexY);
    }
}
