package com.bee.user.ui.xiadan;

import android.content.Intent;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.bean.DictByTypeBean;
import com.bee.user.rest.Api;
import com.bee.user.rest.BaseSubscriber;
import com.bee.user.rest.HttpRequest;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.FlowTagLayout;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

import static com.bee.user.Constants.RESULT_CODE_ORDERING;
import static com.bee.user.Constants.TEXT_BEIZHU;
import static com.bee.user.bean.DictByTypeBean.TYPE_ORDER_REMARK;

/**
 * 创建人：进京赶考
 * 创建时间：2020/09/06  16：02
 * 描述：
 */
public class BeizhuActivity extends BaseActivity {

    @BindView(R.id.et_content)
    EditText et_content;

    @BindView(R.id.tags)
    FlowTagLayout tags;

    @BindView(R.id.iv_wancheng)
    TextView iv_wancheng;

    @BindView(R.id.tv_num)
    TextView tv_num;
    TagsAdapter<DictByTypeBean> tagsAdapter;

    @OnClick({R.id.iv_wancheng})
    public void onClick(View view){
        Intent intent = new Intent();
        intent.putExtra(TEXT_BEIZHU,et_content.getText().toString());
        setResult(RESULT_CODE_ORDERING,intent);
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_beizhu;
    }

    @Override
    public void initViews() {
        String text = getIntent().getStringExtra("text");
        et_content.setText(text);

        InputFilter[] filter = new InputFilter[2];
        filter[0] = new InputFilter() {

            Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

            @Override
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                Matcher emojiMatcher = emoji.matcher(source);
                if (emojiMatcher.find()) {
                    return "";
                }
                return null;
            }
        };
        filter[1] = new InputFilter.LengthFilter(50);
        et_content.setFilters(filter);

        et_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                int length = editable.toString().length();

                tv_num.setText(length+"/50个字");
            }
        });


        tagsAdapter = new TagsAdapter<>(this);

//        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {

                String string = et_content.getText().toString();
                string += " "+((DictByTypeBean) parent.getAdapter().getItem(position)).getDictValue();
                et_content.setText(string);
                et_content.setSelection(et_content.length());
            }
        });

        toDictByType();
    }


    /**
     * 获取反馈类型
     */
    private void toDictByType() {
        Api.getClient(HttpRequest.baseUrl_sys).getDictByType(TYPE_ORDER_REMARK).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseSubscriber<List<DictByTypeBean>>() {
                    @Override
                    public void onSuccess(List<DictByTypeBean> dictByType) {
                        if(null != dictByType && dictByType.size()>0){
                            tagsAdapter.onlyAddAll(dictByType);
                        }
                    }
                });
    }
}
