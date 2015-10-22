package com.kjstudy.frag;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BFrag extends Fragment {
	
	View mView = null;
	
	protected int getLayoutId() {
		return -1;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		int resId = getLayoutId();
		
		if (resId != -1)
			mView = inflater.inflate(resId, container, false);
		if (mView == null)
			mView = super
					.onCreateView(inflater, container, savedInstanceState);
		return mView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initWidget();
	}

	protected void initWidget() {
	}

	@Override
	public void onDetach() {
		super.onDetach();
		try {
			unRegisterReceiver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private InternalReceiver internalReceiver;

	protected final void registerReceiver(String... actions) {
		try {
			if (actions != null && actions.length > 0) {
				IntentFilter intentfilter = new IntentFilter();
				for (String action : actions) {
					intentfilter.addAction(action);
				}
				if (internalReceiver == null) {
					internalReceiver = new InternalReceiver();
				}
				getActivity().registerReceiver(internalReceiver, intentfilter);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected final void unRegisterReceiver() {
		if (internalReceiver != null) {
			getActivity().unregisterReceiver(internalReceiver);
		}
	}

	private class InternalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			dealBroadcase(intent);
		}
	}

	protected void dealBroadcase(Intent intent) {

	}

	protected <T> T findView(int vId){
		return (T) mView.findViewById(vId);
	}
}
