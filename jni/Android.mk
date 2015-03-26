   LOCAL_PATH := $(call my-dir)
   
   include $(CLEAR_VARS)
   LOCAL_MODULE    := des
   LOCAL_SRC_FILES := des.c
   include $(BUILD_SHARED_LIBRARY)
