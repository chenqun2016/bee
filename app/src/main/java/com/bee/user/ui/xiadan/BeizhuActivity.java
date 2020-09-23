package com.bee.user.ui.xiadan;

import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bee.user.R;
import com.bee.user.ui.adapter.TagsAdapter;
import com.bee.user.ui.base.activity.BaseActivity;
import com.bee.user.widget.FlowTagLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;

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


    @OnClick({R.id.iv_wancheng})
    public void onClick(View view){
        finish();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_beizhu;
    }

    @Override
    public void initViews() {

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


        TagsAdapter<String> tagsAdapter = new TagsAdapter<>(this);

//        tags.setTagCheckedMode(FlowTagLayout.FLOW_TAG_CHECKED_SINGLE);
        tags.setAdapter(tagsAdapter);

        tags.setOnTagClickListener(new FlowTagLayout.OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, View view, int position) {

                String string = et_content.getText().toString();
                string += " "+((String) parent.getAdapter().getItem(position));
                et_content.setText(string);
                et_content.setSelection(et_content.length());
            }
        });

        List<String> dataSource = new ArrayList<>();
        dataSource.add("不放辣");
        dataSource.add("多一点米饭");
        dataSource.add("请电话给我");
        dataSource.add("不要冰");
        dataSource.add("不要葱");
        dataSource.add("放到前台");
        dataSource.add("放门口");
        tagsAdapter.onlyAddAll(dataSource);
    }
}
