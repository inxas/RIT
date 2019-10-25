#pragma once

#include "jni.h"

#ifdef __cplusplus
extern "C"{
#endif

	namespace RIT
	{
		jint throwJavaException(JNIEnv* env, jclass exception, const char* message);
		jboolean javaCheck(JNIEnv* env);

		JavaVM* getVM();
	}

#ifdef __cplusplus
}
#endif