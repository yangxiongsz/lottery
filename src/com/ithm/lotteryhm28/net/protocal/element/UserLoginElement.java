package com.ithm.lotteryhm28.net.protocal.element;

import org.xmlpull.v1.XmlSerializer;

import com.ithm.lotteryhm28.net.protocal.Element;
import com.ithm.lotteryhm28.net.protocal.Leaf;

/**
 * 用户登录用请求
 */
public class UserLoginElement extends Element {
	private Leaf actpassword = new Leaf("actpassword");

	@Override
	public void serializerElement(XmlSerializer serializer) {
		try {
			serializer.startTag(null, "element");
			actpassword.serializerLeaf(serializer);
			serializer.endTag(null, "element");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getTransactionType() {
		return "14001";
	}

	public Leaf getActpassword() {
		return actpassword;
	}

}
