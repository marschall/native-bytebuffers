#include <sys/errno.h>
#include <stdint.h>
#include <stdlib.h>

#include <jni.h>

#include "jniUtil.h"
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

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_calloc0
  (JNIEnv *env, jclass clazz, jint size)
{
  void *addr = calloc(size, sizeof(int8_t));
  if (addr)
  {
    return (*env)->NewDirectByteBuffer(env, addr, size);
  }
  else
  {
    return NULL;
  }
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Stdlib_aligned_1alloc0
  (JNIEnv *env, jclass clazz, jint alignment, jint size)
{
  void *addr = aligned_alloc(alignment, size);
  if (addr)
  {
    return (*env)->NewDirectByteBuffer(env, addr, size);
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, ALLOCATION_FAILED_EXCEPTION);
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
  else
  {
    throwJniExceptionWithMessage(env, "could not get buffer address", RELEASE_FAILED_EXCEPTION);
  }
}
