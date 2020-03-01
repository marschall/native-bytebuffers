#include <jni.h>

#include <string.h> // strerror_r

int throwJniExceptionWithMessage(JNIEnv *env, const char *message, const char *exceptionClassName)
{ 
  jclass exceptionClass = (*env)->FindClass(env, exceptionClassName);
  if (exceptionClass == NULL)
  {
     return -1;
  }

  int success = (*env)->ThrowNew(env, exceptionClass, message);
  (*env)->DeleteLocalRef(env, exceptionClass); // not strictly necessary
  return success;
}

int throwJniExceptionWithErrno(JNIEnv *env, int errorCode, const char *exceptionClassName)
{ 
  char message[256];
  int messageSuccess = strerror_r(errorCode, message, sizeof(message));
  if (messageSuccess != 0)
  {
    return -1;
  }

  return throwJniExceptionWithMessage(env, message, exceptionClassName);
}
