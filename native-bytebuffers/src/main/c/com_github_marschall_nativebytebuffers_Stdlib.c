#include <stdlib.h>

#include <jni.h>

#include "com_github_marschall_nativebytebuffers_Stdlib.h"

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_malloc0
  (JNIEnv *env, jclass clazz, jint size)
{
  void *addr = malloc(size);
  if (addr)
  {
    return (*env)->NewDirectByteBuffer(env, addr, size);
  }
  else
  {
    return NULL;
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_free0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  void *addr = (*env)->GetDirectBufferAddress(env, buf);
  if (addr)
  {
    free(addr);
  }
}
