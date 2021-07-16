package com.bee.user.ui.search;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bee.user.R;
import com.bee.user.bean.ChartBean;
import com.bee.user.bean.HomeBean;
import com.bee.user.event.MainEvent;
import com.bee.user.ui.MainActivity;
import com.bee.user.ui.adapter.HomeAdapter;
import com.bee.user.ui.adapter.SearchFoodAdapter;
import com.bee.user.ui.adapter.SelectedFoodAdapter;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.ui.nearby.FoodActivity;
import com.bee.user.ui.xiadan.OrderingActivity;
import com.bee.user.utils.DisplayUtil;
import com.bee.user.utils.LoadmoreUtils;
import com.bee.user.widget.ClearEditText;
import com.bee.user.widget.DragDialogLayout;
import com.bee.user.widget.FlowTagLayout;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/29  20：38
 * 描述：
 */
public class SearchFoodActivity extends BaseActivity {
    @BindView(R.id.recyclerview_nodata)
    RecyclerView recyclerview_nodata;
    @BindView(R.id.recyclerview_data)
    RecyclerView recyclerview_data;

    @BindView(R.id.rl_history)
    RelativeLayout rl_history;

    @BindView(R.id.cet_search)
    ClearEditText cet_search;

    @BindView(R.id.tags)
    FlowTagLayout tags;


    @BindView(R.id.cl_qujiesuan)
    ConstraintLayout cl_qujiesuan;
    @BindView(R.id.view_selected)
    DragDialogLayout view_selected;
    @BindView(R.id.view_background)
    View view_background;



    private TagsAdapter<String> tagsAdapter;
    private List<String> dataSource = new ArrayList<>();

    private HomeAdapter homeAdapter;
    private SearchFoodAdapter adapter;

    LoadmoreUtils loadmoreUtils;

    @OnClick({R.id.iv_clear,R.id.tv_search,
            R.id.cl_qujiesuan,R.id.view_background,R.id.tv_confirm})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_clear:
                dataSource.clear();
                tagsAdapter.clearAndAddAll(new ArrayList<>());
                break;
            case R.id.tv_search:
                addHistoryDatas();

                getDatas();
                break;

            case R.id.cl_qujiesuan:
                showSelectedDialog();
                break;
            case R.id.view_background:

                close();
                break;
            case R.id.tv_confirm:
                startActivity(new Intent(this, OrderingActivity.class));
                break;
        }

    }


    private void addHistoryDatas() {
        Editable text = cet_search.getText();
        if(null != text && !dataSource.contains(text.toString())){
            dataSource.add(0,text.toString());
            tagsAdapter.clearAndAddAll(dataSource);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_search_food;
    }

    @Override
    public void initViews() {

        cet_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(TextUtils.isEmpty(charSequence)){
                    rl_history.setVisibility(View.VISIBLE);
                    recyclerview_nodata.setVisibility(View.GONE);
                    recyclerview_data.setVisibility(View.GONE);

                    if(null != homeAdapter){
                        homeAdapter.setNewInstance(null);
                        homeAdapter.removeAllHeaderView();
                    }
                    if(null != adapter){
                        adapter.setNewInstance(null);
                        adapter.removeAllHeaderView();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        cet_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(i == EditorInfo.IME_ACTION_SEARCH){
                    addHistoryDatas();
                    getDatas();
                    return true;
                }
                return false;
            }
        });
        tagsAdapter = new TagsAdapter<>(this);

//        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {
                cet_search.setText(""+((String) parent.getAdapter().getItem(position)));
                cet_search.setSelection(cet_search.length());

                getDatas();
            }
        });


        dataSource.add("肉");
        dataSource.add("鱼肉");
        dataSource.add("牛肉");
        dataSource.add("鸡肉");
        dataSource.add("羊肉");
        dataSource.add("蟹肉");
        tagsAdapter.onlyAddAll(dataSource);




        adapter   = new SearchFoodAdapter();

        recyclerview_data.setLayoutManager(new LinearLayoutManager(this));
        recyclerview_data.setAdapter(adapter);

        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(SearchFoodActivity.this, FoodActivity.class));
            }
        });
        loadmoreUtils = new LoadmoreUtils();
        loadmoreUtils.initLoadmore(adapter);

        initSelectedFoodDialog();
    }



    private void getDatas() {
        rl_history.setVisibility(View.GONE);
        recyclerview_nodata.setVisibility(View.GONE);
        recyclerview_data.setVisibility(View.VISIBLE);

//        initNoDatas();
        initDatas();
    }

    private void initDatas() {

        loadmoreUtils.refresh(adapter);

    }


    private void initNoDatas() {
        homeAdapter  = new HomeAdapter();
        recyclerview_nodata.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerview_nodata.setAdapter(homeAdapter);

        View head = View.inflate(this, R.layout.head_fragment_chart_nodata, null);
        ImageView iv_icon = head.findViewById(R.id.iv_icon);
        iv_icon.setImageResource(R.drawable.bg_kongbai5);
        TextView tv_1 = head.findViewById(R.id.tv_1);
        TextView tv_2 = head.findViewById(R.id.tv_2);
        tv_1.setText("没有搜索到你要的结果");
        tv_2.setText("换个关键词试试吧");
        TextView tv_tag = head.findViewById(R.id.tv_tag);
        tv_tag.setText("猜你喜欢");

        View tv_guangguang = head.findViewById(R.id.tv_guangguang);
        tv_guangguang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainEvent mainEvent = new MainEvent(MainEvent.TYPE_set_index);
                mainEvent.index = 1;
                EventBus.getDefault().post(mainEvent);

                Intent intent = new Intent(SearchFoodActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        homeAdapter.addHeaderView(head);

        homeAdapter.setOnItemClickListener(new com.chad.library.adapter.base.listener.OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(new Intent(SearchFoodActivity.this, FoodActivity.class));
            }
        });

        ArrayList<HomeBean> homeBeans = new ArrayList<>();
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeBeans.add(new HomeBean());
        homeAdapter.setNewInstance(homeBeans);
    }










//已选商品相关代码
private int heightSelected;
    private int windowHeight;

    ViewGroup.LayoutParams params;

    boolean isShow = false;

    private void showSelectedDialog() {
        if (!isShow) {
            show();

        } else {
            close();
        }


    }

    private ValueAnimator showAnimation;
    private ValueAnimator closeAnimation;
    private ValueAnimator alphaAnimation1;
    private ValueAnimator alphaAnimation2;


    public void show() {
        if (null != alphaAnimation2 && alphaAnimation2.isRunning()) {
            alphaAnimation2.end();
        }

        view_background.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        view_background.setLayoutParams(layoutParams);

        if (null == alphaAnimation1) {
            alphaAnimation1 = ValueAnimator.ofFloat(0, 1);
            alphaAnimation1.setDuration(300);
            alphaAnimation1.setInterpolator(new LinearInterpolator());
            alphaAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation1.start();




        if (null != closeAnimation && closeAnimation.isRunning()) {
            closeAnimation.end();
        }

        if (null == showAnimation) {
            showAnimation = ValueAnimator.ofInt(0, heightSelected);
            showAnimation.setDuration(300);
            showAnimation.setInterpolator(new LinearInterpolator());
            showAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }
        showAnimation.start();

        isShow = !isShow;
    }

    public void close() {
        closeOhter();


        if (null == closeAnimation) {
            closeAnimation = ValueAnimator.ofInt(heightSelected, 0);
            closeAnimation.setDuration(300);
            closeAnimation.setInterpolator(new LinearInterpolator());
            closeAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    params.height = (int) valueAnimator.getAnimatedValue();
                    view_selected.setLayoutParams(params);
                }
            });
        }

        closeAnimation.start();

        isShow = !isShow;
    }

    private void closeOhter() {
        if (null != alphaAnimation1 && alphaAnimation1.isRunning()) {
            alphaAnimation1.end();
        }

        if (null == alphaAnimation2) {
            alphaAnimation2 = ValueAnimator.ofFloat(1, 0);
            alphaAnimation2.setDuration(300);
            alphaAnimation2.setInterpolator(new LinearInterpolator());
            alphaAnimation2.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {
                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationCancel(Animator animator) {
                    view_background.setVisibility(View.GONE);
                    ViewGroup.LayoutParams layoutParams = view_background.getLayoutParams();
                    layoutParams.height = 1;
                    layoutParams.width = 1;
                    view_background.setLayoutParams(layoutParams);
                }

                @Override
                public void onAnimationRepeat(Animator animator) {
                }
            });
            alphaAnimation2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {


                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    view_background.setAlpha((Float) valueAnimator.getAnimatedValue());
                }
            });
        }
        alphaAnimation2.start();



        if (null != showAnimation && showAnimation.isRunning()) {
            showAnimation.end();
        }
    }


    private void initSelectedFoodDialog() {
        windowHeight = DisplayUtil.getWindowHeight(this);

        findViewById(R.id.clean).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                close();
            }
        });


        view_selected.setNextPageListener(new DragDialogLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {
                closeOhter();

                params.height = 0;
                view_selected.setLayoutParams(params);

                isShow = !isShow;
            }
        });

        RecyclerView recyclerview = findViewById(R.id.recyclerview);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));

        List<ChartBean> foodBeans = new ArrayList<>();
        foodBeans.add(new ChartBean());
        foodBeans.add(new ChartBean());
        foodBeans.add(new ChartBean());

        SelectedFoodAdapter selectedFoodAdapter = new SelectedFoodAdapter(foodBeans);
        recyclerview.setAdapter(selectedFoodAdapter);
        selectedFoodAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {

            }
        });

        view_background.setAlpha(0);
        view_selected.post(new Runnable() {
            @Override
            public void run() {
                heightSelected = view_selected.getMeasuredHeight();

                if (heightSelected > windowHeight / 2f) {
                    heightSelected = windowHeight / 2;
                }
                params = view_selected.getLayoutParams();
                params.height = 0;
                view_selected.setLayoutParams(params);
                view_selected.setVisibility(View.VISIBLE);
            }
        });
    }

}
