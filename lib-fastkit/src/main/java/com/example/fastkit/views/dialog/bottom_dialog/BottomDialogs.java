package com.example.fastkit.views.dialog.bottom_dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.fastkit.R;
import com.example.fastkit.utils.animation_deal.AnimationUtil;
import com.example.fastkit.db.db_system.DaoSupportFactory;


/**
 * Created by caik on 2017/3/20.
 */

public class BottomDialogs extends DialogFragment {

    private static BottomDialogs bottomDialog;

    private String titleText = "请设置标题";
    private String cancelText = "取消";
    private String[] mDataArray = {"请填充数据"};

    private static AppCompatActivity appCompatActivity;

    public static BottomDialogs getInstance(AppCompatActivity fa) {
        appCompatActivity = fa;
        if (bottomDialog == null) {
            synchronized (DaoSupportFactory.class) {
                if (bottomDialog == null) {
                    bottomDialog = new BottomDialogs();
                }
            }
        }
        return bottomDialog;
    }


    private OnClickListener mListener;

    private CancelListener mCancelListener;

    public BottomDialogs setOnClickListener(OnClickListener listener) {
        this.mListener = listener;

        return bottomDialog;
    }

    public BottomDialogs setCancelListener(CancelListener mCancelListener) {
        this.mCancelListener = mCancelListener;

        return bottomDialog;
    }

    public BottomDialogs setTitle(String title) {
        this.titleText = title;
        return bottomDialog;
    }

    public BottomDialogs setData(String[] array) {

        this.mDataArray = array;
        return bottomDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.getDecorView().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                dismiss();
                return true;
            }
        });

    }

    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.kit_bottom_lib_layout, container, false);
        view.setAnimation(AnimationUtil.bottomToView());
        initView();

        return view;
    }

    private TextView titleView;
    private ListView listView;
    private Button cancel;

    private void initView() {
        /**
         * 标题
         */
        titleView = view.findViewById(R.id.title);
        titleView.setText(titleText);
        /**
         * 列表
         */
        listView = (ListView) view.findViewById(R.id.bottom_lib_listView);
        listView.setAdapter(new ArrayAdapter(getContext(), R.layout.kit_bottom_lib_item, R.id.item, mDataArray));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mListener != null) {
                    mListener.click(position);
                }

                dismiss();
            }
        });
        /**
         * 取消
         */
        cancel = view.findViewById(R.id.cancel);
        cancel.setText(cancelText);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCancelListener != null) {
                    mCancelListener.dismissDialog();
                }
                dismiss();
            }
        });
    }


    public void show() {
        show(appCompatActivity.getSupportFragmentManager(), "dialog");
    }


    public interface OnClickListener {
        void click(int position);

    }

    public interface CancelListener {
        void dismissDialog();
    }
}
