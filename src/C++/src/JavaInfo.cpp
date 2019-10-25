#include "JavaInfo.h"

namespace RIT
{
	jint throwJavaException(JNIEnv* env, jclass exception, const char* message)
	{
		return env->ThrowNew(exception, message);
	}
	jboolean javaCheck(JNIEnv* env)
	{
		return env->ExceptionCheck();
	}
	JavaVM* getVM()
	{
		JavaVM* vm;
		jsize ct;
		if (JNI_GetCreatedJavaVMs(&vm, 1, &ct) != JNI_OK) {
			return NULL;
		}
		return vm;
	}
}