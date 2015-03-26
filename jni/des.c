#include <stdio.h>
#include <com_ithm_lotteryhm28_NativeValue.h>

JNIEXPORT jstring JNICALL Java_com_ithm_lotteryhm28_NativeValue_getAgenterPassword
  (JNIEnv * env, jclass class){
	return (*env)->NewStringUTF(env,"9ab62a694d8bf6ced1fab6acd48d02f8");
}

JNIEXPORT jstring JNICALL Java_com_ithm_lotteryhm28_NativeValue_getDesPassword
  (JNIEnv * env, jclass class){
	return (*env)->NewStringUTF(env,"9b2648fcdfbad80f");
}

JNIEXPORT jstring JNICALL Java_com_ithm_lotteryhm28_NativeValue_getLotteryUrl
  (JNIEnv * env, jclass class){
	return (*env)->NewStringUTF(env,"http://192.168.123.1:8088/ZCWService/Entrance");
}
