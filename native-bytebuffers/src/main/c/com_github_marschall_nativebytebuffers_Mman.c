#include <sys/mman.h>
#include <unistd.h>

#include <jni.h>

#include "com_github_marschall_nativebytebuffers_Mman.h"

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap0
  (JNIEnv *env, jclass clazz, jint length, jint flags)
{
  void *addr = NULL;
  int fd = -1;
  void *ptr = mmap(addr, length, int prot, int flags,
                  fd, off_t offset);
  if (ptr)
  {
    return (*env)->NewDirectByteBuffer(env, ptr, length);
  }
  else
  {
    return NULL;
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_nativebytebuffers_Mman_munmap0
  (JNIEnv *env, jclass clazz, jobject buf, jint length)
{
  jlong capatcity = (*env)->GetDirectBufferCapacity(JNIEnv* env, jobject buf);
  if (capatcity != -1)
  {
    void *ptr = (*env)->GetDirectBufferAddress(env, buf);
    if (ptr)
    {
      int success = int munmap(void *addr, size_t length);
    }
  }
}

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  return sysconf(_SC_PAGESIZE);
}
