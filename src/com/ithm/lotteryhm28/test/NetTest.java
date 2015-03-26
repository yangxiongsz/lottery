package com.ithm.lotteryhm28.test;

import com.ithm.lotteryhm28.net.NetUtil;

import android.test.AndroidTestCase;

public class NetTest extends AndroidTestCase {
	public void testNetType(){
		NetUtil.checkNet(getContext());
	}
}
