

#include <sys/errno.h>
#include <unistd.h>

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Unistd.h"

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Unistd_ftruncate0
  (JNIEnv *env, jclass clazz, jint fd, jlong length)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  int ret = ftruncate(fd, length);
  if (ret == 0)
  {
    return 0;
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, IO_EXCEPTION);
    return -1;
  }
}

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Unistd_close0
  (JNIEnv *env, jclass clazz, jint fd)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  int ret = close(fd);
  if (ret == 0)
  {
    return 0;
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, IO_EXCEPTION);
    return -1;
  }
}


