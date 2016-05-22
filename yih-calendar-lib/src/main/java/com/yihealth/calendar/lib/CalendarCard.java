package com.yihealth.calendar.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CalendarCard extends View {

    private static final int TOTAL_COL = 7; // 7列  
    private static final int TOTAL_ROW = 6; // 6行  

    private Paint mCirclePaint; // 绘制圆形的画笔  
    private Paint mTextPaint; // 绘制文本的画笔  
    private int mViewWidth; // 视图的宽度  
    private int mViewHeight; // 视图的高度  
    private int mCellSpace; // 单元格间距  
    private Row rows[] = new Row[TOTAL_ROW]; // 行数组，每个元素代表一行  
    private static CustomDate mShowDate; // 自定义的日期，包括year,month,day
    private OnCellClickListener mCellClickListener; // 单元格点击回调事件  
    private int touchSlop; //  
    private boolean callBackCellSpace;

    private Cell mClickCell;
    private float mDownX;
    private float mDownY;

    private List<CustomDate> customDates;

    private List<CustomDate> selectedDates;
    private Date firstDate;
    private Date secondDate;

    /**
     * 单元格点击的回调接口
     *
     * @author wuwenjie
     */
    public interface OnCellClickListener {
        void clickDate(CustomDate date, List<CustomDate> selectedDates); // 回调点击的日期

        void changeDate(CustomDate date, List<CustomDate> selectedDates); // 回调滑动ViewPager改变的日期
    }

    public CalendarCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public CalendarCard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CalendarCard(Context context) {
        super(context);
        init(context);
    }

    public CalendarCard(Context context, OnCellClickListener listener) {
        super(context);
        this.mCellClickListener = listener;
        init(context);
    }
    public CalendarCard(Context context, OnCellClickListener listener,SelectType type) {
        super(context);
        this.mCellClickListener = listener;
        this.selectType = type;
        init(context);
    }


    private void init(Context context) {
        mShowDate = new CustomDate();
        customDates = new ArrayList<>();

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.parseColor("#F24949")); // 红色圆形  
        touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

        initDate();
        fillDate();
    }

    private void initDate() {
        customDates.clear();
        selectedDates = DateUtil.selectedMap.get(mShowDate.getYearMonth());
        if (selectedDates == null) {
            selectedDates = new ArrayList<>();
            DateUtil.selectedMap.put(mShowDate.getYearMonth(), selectedDates);
        }

        int monthDay = DateUtil.getCurrentMonthDay(); // 今天
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month - 1); // 上个月的天数
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month); // 当前月的天数
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);

        int day = 0;
        for (int j = 0; j < TOTAL_ROW; j++) {
            for (int i = 0; i < TOTAL_COL; i++) {
                int position = i + j * TOTAL_COL; // 单元格位置
                // 这个月的
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays) {
                    day++;
                    customDates.add(position, CustomDate.modifiDayForObject(mShowDate, day));
                    // 过去一个月
                } else if (position < firstDayWeek) {
                    customDates.add(position, new CustomDate(mShowDate.year,
                            mShowDate.month - 1, lastMonthDays
                            - (firstDayWeek - position - 1)));
                    // 下个月
                } else if (position >= firstDayWeek + currentMonthDays) {
                    customDates.add(position, new CustomDate(mShowDate.year,
                            mShowDate.month + 1, position - firstDayWeek
                            - currentMonthDays + 1));

                }
            }
        }


        /**
         * fenge
         */
//        mShowDate = new CustomDate();

    }


    private void fillDate() {
        int monthDay = DateUtil.getCurrentMonthDay(); // 今天
        int lastMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month - 1); // 上个月的天数
        int currentMonthDays = DateUtil.getMonthDays(mShowDate.year,
                mShowDate.month); // 当前月的天数
        int firstDayWeek = DateUtil.getWeekDayFromDate(mShowDate.year,
                mShowDate.month);
        boolean isCurrentMonth = false;
        if (DateUtil.isCurrentMonth(mShowDate)) {
            isCurrentMonth = true;
        }
        int day = 0;
        for (int j = 0; j < TOTAL_ROW; j++) {
            rows[j] = new Row(j);
            for (int i = 0; i < TOTAL_COL; i++) {
                int position = i + j * TOTAL_COL; // 单元格位置
                CustomDate date = customDates.get(position);
                // 这个月的
                if (position >= firstDayWeek
                        && position < firstDayWeek + currentMonthDays) {
                    day++;
                    rows[j].cells[i] = new Cell(date, State.CURRENT_MONTH_DAY, i, j);
                    // 今天
                    if (isCurrentMonth && day == monthDay) {
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                    }
                    if (isCurrentMonth && day > monthDay) { // 如果比这个月的今天要大，表示还没到
                        rows[j].cells[i] = new Cell(date,
                                State.UNREACH_DAY, i, j);
                    }
                    if (isDateSelected(date)) {
                        rows[j].cells[i] = new Cell(date, State.TODAY, i, j);
                    }

                    // 过去一个月
                } else if (position < firstDayWeek) {
                    rows[j].cells[i] = new Cell(date,
                            State.PAST_MONTH_DAY, i, j);
                    // 下个月
                } else if (position >= firstDayWeek + currentMonthDays) {
                    rows[j].cells[i] = new Cell(date,
                            State.NEXT_MONTH_DAY, i, j);

                }
            }
        }
        if (mCellClickListener != null)
            mCellClickListener.changeDate(mShowDate, selectedDates);
    }

    private boolean isDateSelected(CustomDate date) {

        for (CustomDate cd : selectedDates) {
            if (cd.isSameDay(date)) {
                return true;
            }
        }
        return false;
    }

    private Canvas cellCanvas;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        cellCanvas = canvas;
        for (int i = 0; i < TOTAL_ROW; i++) {
            if (rows[i] != null) {
                rows[i].drawCells(canvas);
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        mCellSpace = Math.min(mViewHeight / TOTAL_ROW, mViewWidth / TOTAL_COL);
        if (!callBackCellSpace) {
            callBackCellSpace = true;
        }
        mTextPaint.setTextSize(mCellSpace / 3);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float disX = event.getX() - mDownX;
                float disY = event.getY() - mDownY;
                if (Math.abs(disX) < touchSlop && Math.abs(disY) < touchSlop) {
                    int col = (int) (mDownX / mCellSpace);
                    int row = (int) (mDownY / mCellSpace);
                    measureClickCell(col, row);
                }
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * 计算点击的单元格
     *
     * @param col
     * @param row
     */
    private void measureClickCell(int col, int row) {
        if (col >= TOTAL_COL || row >= TOTAL_ROW)
            return;
        if (mClickCell != null) {
            rows[mClickCell.j].cells[mClickCell.i] = mClickCell;
        }
        if (rows[row] != null) {
            mClickCell = new Cell(rows[row].cells[col].date,
                    rows[row].cells[col].state, rows[row].cells[col].i,
                    rows[row].cells[col].j);

            CustomDate date = rows[row].cells[col].date;
            if(date.isSameMonth(mShowDate)){
                //是当前月
                date.selected = !date.selected;
                date.week = col;
                if (selectType == SelectType.SINGLE) {
                    //单选
                    for (CustomDate cd : selectedDates)
                        cd.selected = false;
                    selectedDates.clear();

                } else if (selectType == SelectType.MULTIPLE) {
                    //多选

                } else if (selectType == SelectType.RANGE) {
                    //选范围

                }
                if (date.selected) {
                    selectedDates.add(date);
                } else {
                    selectedDates.remove(date);
                }
                mShowDate = date;
                mClickCell.isSelected = true;
                if (mCellClickListener != null)
                    mCellClickListener.clickDate(date, selectedDates);

                // 刷新界面
                update();

            }else{
                //不是当前月
            }

        }
    }

    protected String getSelectedDates() {
        StringBuilder sb = new StringBuilder();
        for (CustomDate date : selectedDates)
            sb.append(date.getDay()).append(",");
        return sb.toString();
    }

    /**
     * 组元素
     *
     * @author wuwenjie
     */
    class Row {
        public int j;

        Row(int j) {
            this.j = j;
        }

        public Cell[] cells = new Cell[TOTAL_COL];

        // 绘制单元格  
        public void drawCells(Canvas canvas) {
            for (int i = 0; i < cells.length; i++) {
                if (cells[i] != null) {
                    cells[i].drawSelf(canvas);
                }
            }
        }

    }

    /**
     * 单元格元素
     *
     * @author wuwenjie
     */
    class Cell {
        public CustomDate date;
        public State state;
        public boolean isSelected;
        public int i;
        public int j;

        public Cell(CustomDate date, State state, int i, int j) {
            super();
            this.date = date;
            this.state = state;
            this.i = i;
            this.j = j;
        }

        public void drawSelf() {
            drawSelf(cellCanvas);
        }

        public void drawSelf(Canvas canvas) {
            if (isSelected) {
                mTextPaint.setColor(Color.parseColor("#f76b88"));
                canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                        (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                        mCirclePaint);
                return;
            } else {
                switch (state) {
                    case TODAY: // 今天
                        mTextPaint.setColor(Color.parseColor("#fffffe"));
                        canvas.drawCircle((float) (mCellSpace * (i + 0.5)),
                                (float) ((j + 0.5) * mCellSpace), mCellSpace / 3,
                                mCirclePaint);
                        break;
                    case CURRENT_MONTH_DAY: // 当前月日期
                        mTextPaint.setColor(Color.BLACK);
                        break;
                    case PAST_MONTH_DAY: // 过去一个月
                    case NEXT_MONTH_DAY: // 下一个月
                        mTextPaint.setColor(Color.parseColor("#cccccc"));
                        break;
                    case UNREACH_DAY: // 还未到的天
                        mTextPaint.setColor(Color.GRAY);
                        break;
                    default:
                        break;
                }
            }
            // 绘制文字  
            String content = date.day + "";
            canvas.drawText(content,
                    (float) ((i + 0.5) * mCellSpace - mTextPaint
                            .measureText(content) / 2), (float) ((j + 0.7)
                            * mCellSpace - mTextPaint
                            .measureText(content, 0, 1) / 2), mTextPaint);
        }
    }

    /**
     * @author wuwenjie 单元格的状态 当前月日期，过去的月的日期，下个月的日期
     */
    enum State {
        TODAY, CURRENT_MONTH_DAY, PAST_MONTH_DAY, NEXT_MONTH_DAY, UNREACH_DAY;
    }

    public enum SelectType {
        SINGLE, MULTIPLE, RANGE
    }

    private SelectType selectType = SelectType.SINGLE;

    public void setSelectType(SelectType type) {
        this.selectType = type;
    }

    // 从左往右划，上一个月  
    public void leftSlide() {
        if (mShowDate.month == 1) {
            mShowDate.month = 12;
            mShowDate.year -= 1;
        } else {
            mShowDate.month -= 1;
        }
        initDate();
        update();
    }

    // 从右往左划，下一个月  
    public void rightSlide() {
        if (mShowDate.month == 12) {
            mShowDate.month = 1;
            mShowDate.year += 1;
        } else {
            mShowDate.month += 1;
        }
        initDate();
        update();
    }

    public void update() {

        fillDate();
        invalidate();
    }


}
