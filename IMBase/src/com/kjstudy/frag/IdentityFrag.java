package com.kjstudy.frag;

import com.imbase.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class IdentityFrag extends BFrag {

	private TeacherFrag mTeacherFrag;
	private StudentFrag mStudentFrag;
	private Fragment mCurFrag;
	private FragmentManager mManager;
	private FragmentTransaction mTransaction;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.frag_layout_main, container, false);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTeacherFrag = new TeacherFrag();
		mStudentFrag = new StudentFrag();
		mCurFrag = mStudentFrag;
		showFragment();
	}

	private void showFragment() {
		if (mManager == null)
			mManager = getChildFragmentManager();
		mTransaction = mManager.beginTransaction();
		if (mCurFrag != null) {
			mTransaction.replace(R.id.msg_attention_container, mCurFrag);
		}

		mTransaction.commitAllowingStateLoss();
	}

	public void showStuFrag() {
		mCurFrag = mStudentFrag;
		showFragment();
	}

	public void showTerFrag() {
		mCurFrag = mTeacherFrag;
		showFragment();
	}
}
