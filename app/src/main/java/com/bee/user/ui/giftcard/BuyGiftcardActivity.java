package com.bee.user.ui.giftcard;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bee.user.R;
import com.bee.user.TextNumRangeInputFilter;
import com.bee.user.bean.MiLiChongzhiBean;
import com.bee.user.ui.adapter.MiLiChongzhiAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.AddRemoveView;
import com.bee.user.widget.MyGridView;
import com.jakewharton.rxbinding4.widget.RxTextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * 创建人：进京赶考
 * 创建时间：2020/11/05  19：05
 * 描述：
 */
public class BuyGiftcardActivity extends BaseActivity {

    @BindView(R.id.gridview)
    MyGridView gridview;
    @BindView(R.id.tv_confirm)
    TextView tv_confirm;

    @BindView(R.id.money)
    EditText money;

    @BindView(R.id.iv_goods_add)
    AddRemoveView iv_goods_add;


    @BindView(R.id.tv_heji_money)
    TextView tv_heji_money;
    @BindView(R.id.tv_youhui_value)
    TextView tv_youhui_value;

    MiLiChongzhiBean mCurrentBean;

    @OnClick({R.id.tv_confirm,R.id.tv_right})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_right:
                startActivity(new Intent(this, GiftcardRecordActivity.class));
                break;
            case R.id.tv_confirm:
                startActivity(new Intent(this,GiftcardStatusActivity.class));
                break;
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_buy_giftcard;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setmAdjustView(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initViews() {
        tv_confirm.setText("立即支付");



        RxTextView.textChanges(money).subscribe(new Observer<CharSequence>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull CharSequence charSequence) {
//                if(null != charSequence && !TextUtils.isEmpty(charSequence)){
//                    tv_heji_money.setText(Integer.valueOf(charSequence.toString()) * iv_goods_add.getNum()+"");
//                    tv_youhui_value.setText(Integer.valueOf(charSequence.toString()) * iv_goods_add.getNum()+"");
//                }else{
//                    tv_heji_money.setText(Integer.valueOf(charSequence.toString()) * iv_goods_add.getNum()+"");
//                    tv_youhui_value.setText(Integer.valueOf(charSequence.toString()) * iv_goods_add.getNum()+"");
//                }

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        money.setFilters(new InputFilter[]{new TextNumRangeInputFilter(9999,0)});


        ArrayList<MiLiChongzhiBean> miLiChongzhiBeans = new ArrayList<>();
        miLiChongzhiBeans.add(new MiLiChongzhiBean(100,"100米粒",""));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(300,"300米粒",""));
        miLiChongzhiBeans.add(new MiLiChongzhiBean(500,"500米粒",""));
        MiLiChongzhiAdapter  miLiChongzhiAdapter   = new MiLiChongzhiAdapter(this, miLiChongzhiBeans);
        gridview.setAdapter(miLiChongzhiAdapter);


        iv_goods_add.setMin(1);
        iv_goods_add.setNum(1);
        iv_goods_add.setOnNumChangedListener(new AddRemoveView.OnNumChangedListener() {
            @Override
            public void onAddListener(int num) {
                if(null != mCurrentBean){
                    tv_heji_money.setText(mCurrentBean.num * num +"");
                    tv_youhui_value.setText(mCurrentBean.num * num+"");
                }
            }

            @Override
            public void onRemoveListener(int num) {

            }

        });

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                miLiChongzhiAdapter.setSelected(i);
                miLiChongzhiAdapter.notifyDataSetChanged();

                mCurrentBean = miLiChongzhiBeans.get(i);
                tv_heji_money.setText(mCurrentBean.num * iv_goods_add.getNum() +"");
                tv_youhui_value.setText(mCurrentBean.num * iv_goods_add.getNum()+"");
            }
        });
    }


}
