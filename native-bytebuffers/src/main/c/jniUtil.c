#include <jni.h>

#include <string.h> // strerror_r

int throwJniException(JNIEnv *env, int errorCode, const char *exceptionClassName)
{ 
  char message[256];
  int messageSuccess = strerror_r(errorCode, message, sizeof(message));
  if (messageSuccess != 0)
  {
    return -1;
  }

  jclass exceptionClass = (*env)->FindClass(env, exceptionClassName);
  if (exceptionClass == NULL)
  {
     return -1;
  }

  int success = (*env)->ThrowNew(env, exceptionClass, message);
  (*env)->DeleteLocalRef(env, exceptionClass); // not strictly necessary
  return success;
}
