
#include <fcntl.h>

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Fcntl.h"

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Fcntl_fcntl0
  (JNIEnv *env, jclass clazz, jint fd, jint cmd)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  return fcntl(fd, cmd);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Fcntl_fcntl1
  (JNIEnv *env, jclass clazz, jint fd, jint cmd, jint arg)
{
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  return fcntl(fd, cmd, arg);
}
