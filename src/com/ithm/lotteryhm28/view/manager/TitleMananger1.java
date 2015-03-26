package com.ithm.lotteryhm28.view.manager;

import com.ithm.lotteryhm28.R;
import com.ithm.lotteryhm28.view.SecondUI;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TitleMananger1 {
	// 单例模式
	private static TitleMananger1 instance;

	private TitleMananger1() {
	}

	public static TitleMananger1 getInstance() {
		if (instance == null) {
			instance = new TitleMananger1();
		}
		return instance;
	}

	private RelativeLayout commonContainer;
	private RelativeLayout loginContainer;
	private RelativeLayout unLoginContainer;

	private ImageView goback;// 返回
	private ImageView help;// 帮助
	private ImageView login;// 登录

	private TextView titleContent;// 标题内容
	private TextView userInfo;// 用户信息

	public void init(Activity activity) {
		commonContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_common_container);
		unLoginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_unlogin_title);
		loginContainer = (RelativeLayout) activity
				.findViewById(R.id.ii_login_title);

		goback = (ImageView) activity.findViewById(R.id.ii_title_goback);
		help = (ImageView) activity.findViewById(R.id.ii_title_help);
		login = (ImageView) activity.findViewById(R.id.ii_title_login);

		titleContent = (TextView) activity.findViewById(R.id.ii_title_content);
		userInfo = (TextView) activity.findViewById(R.id.ii_top_user_info);

		setListener();
	}

	private void setListener() {
		goback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("返回键");

			}
		});
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("help");

			}
		});
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//不能传递对象，必须明确目标
				MiddleManager.getInstance().changeUI(SecondUI.class);
			}
		});

	}

	private void initTitle() {
		commonContainer.setVisibility(View.GONE);
		loginContainer.setVisibility(View.GONE);
		unLoginContainer.setVisibility(View.GONE);
	}

	/**
	 * 显示通用标题
	 */
	public void showCommonTitle() {
		initTitle();
		commonContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示未登录的标题
	 */
	public void showUnLoginTitle() {
		initTitle();
		unLoginContainer.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示登陆的标题
	 */
	public void showLoginTitle() {
		initTitle();
		loginContainer.setVisibility(View.VISIBLE);

	}

	public void changeTitle(String title) {
		titleContent.setText(title);
	}
}
