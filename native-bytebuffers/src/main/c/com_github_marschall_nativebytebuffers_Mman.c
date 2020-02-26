#include <sys/errno.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Mman.h"


JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap0
  (JNIEnv *env, jclass clazz, jint length)
{
  jint flags = MAP_SHARED | MAP_ANONYMOUS;
  return Java_com_github_marschall_nativebytebuffers_Mman_mmap1(env, clazz, length, flags);
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap1
  (JNIEnv *env, jclass clazz, jint length, jint flags)
{
  int prot = PROT_READ | PROT_WRITE;
  int fd = -1;
  off_t offset = 0;
  void *addr = mmap(NULL, length, prot, flags, fd, offset);
  if (addr != MAP_FAILED)
  {
    return (*env)->NewDirectByteBuffer(env, addr, length);
  }
  else
  {
    throwJniException(env, errno, ALLOCATION_FAILED_EXCEPTION);
    return NULL;
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_nativebytebuffers_Mman_munmap0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  jlong capatcity = (*env)->GetDirectBufferCapacity(env, buf);
  if (capatcity != -1)
  {
    void *addr = (*env)->GetDirectBufferAddress(env, buf);
    if (addr)
    {
      int success = munmap(addr, capatcity);
      if (success != 0)
      {
        throwJniException(env, errno, "com/github/marschall/nativebytebuffers/ReleaseFailedException");
      }
    }
  }
}

JNIEXPORT jlong JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  return sysconf(_SC_PAGESIZE);
}
