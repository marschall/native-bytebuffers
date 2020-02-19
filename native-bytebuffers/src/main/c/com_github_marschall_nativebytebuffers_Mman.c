#include <sys/errno.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>

#include <jni.h>

#include "com_github_marschall_nativebytebuffers_Mman.h"

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
     // no need to call DeleteLocalRef because it's null
     return -1;
  }

  int success = (*env)->ThrowNew(env, exceptionClass, message);
  (*env)->DeleteLocalRef(env, exceptionClass); // not strictly necessary
  return success;
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap0
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
    int errorCode = errno;
    throwJniException(env, errorCode, "com/github/marschall/nativebytebuffers/AllocationFailedException");
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
        int errorCode = errno;
        throwJniException(env, errorCode, "com/github/marschall/nativebytebuffers/ReleaseFailedException");
      }
    }
  }
}

JNIEXPORT jlong JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  return sysconf(_SC_PAGESIZE);
}

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getMapAnonymous0
  (JNIEnv *env, jclass clazz)
{
  return MAP_ANONYMOUS;
}
