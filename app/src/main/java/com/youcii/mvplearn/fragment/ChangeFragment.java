package com.youcii.mvplearn.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.youcii.mvplearn.R;
import com.youcii.mvplearn.base.BaseFragment;
import com.youcii.mvplearn.utils.APILimitUtils;
import com.youcii.mvplearn.utils.FragmentUtils;
import com.youcii.mvplearn.fragment.interfaces.IFragChangeView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ChangeFragment extends BaseFragment implements IFragChangeView, View.OnClickListener {
    FragmentManager childFragmentManager;
    FragmentUtils fragmentUtils;

    @Bind(R.id.top_tap1)
    TextView topTap1;
    @Bind(R.id.top_tap2)
    TextView topTap2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_change, container, false);
        ButterKnife.bind(this, view);

        topTap1.setText(getString(R.string.change_fragment_one));
        topTap2.setText(getString(R.string.change_fragment_two));
        topTap1.setOnClickListener(this);
        topTap2.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        childFragmentManager = getChildFragmentManager();
        fragmentUtils = new FragmentUtils(childFragmentManager);

        changeFragment(getString(R.string.change_fragment_one));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void changeFragment(String tapTitle) {
        if (tapTitle.equals(getString(R.string.change_fragment_one))) {
            fragmentUtils.replaceContent(new ItemFragment(), R.id.fl_content);
            topTap1.setTextColor(APILimitUtils.getColor(getContext(), R.color.top_tap_select));
            topTap2.setTextColor(APILimitUtils.getColor(getContext(), R.color.top_tap_normal));
        } else {
            fragmentUtils.replaceContent(new BlockFragment(), R.id.fl_content);
            topTap2.setTextColor(APILimitUtils.getColor(getContext(), R.color.top_tap_select));
            topTap1.setTextColor(APILimitUtils.getColor(getContext(), R.color.top_tap_normal));
        }
    }

    @Override
    public void onClick(View v) {
        changeFragment(((TextView) v).getText().toString());
    }

}
