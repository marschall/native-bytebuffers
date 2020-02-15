#include <stdlib.h>

#include <jni.h>

#include "com_github_marschall_nativebytebuffers_Stdlib.h"

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_malloc0
  (JNIEnv *env, jclass clazz, jint size)
{
  void *ptr = malloc(size);
  if (ptr)
  {
    return (*env)->NewDirectByteBuffer(env, ptr, size);
  }
  else
  {
    return NULL;
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_free0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  void *ptr = (*env)->GetDirectBufferAddress(env, buf);
  if (ptr)
  {
    free(ptr);
  }
}
