// for memfd_create
#define _GNU_SOURCE

#include <sys/errno.h>
#include <sys/mman.h>
#include <sys/syscall.h>      /* for SYS_* definitions */
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
  void *addr = mmap(NULL, (size_t) length, prot, flags, fd, offset);
  if (addr != MAP_FAILED)
  {
    return (*env)->NewDirectByteBuffer(env, addr, length);
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, ALLOCATION_FAILED_EXCEPTION);
    return NULL;
  }
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap2
  (JNIEnv *env, jclass clazz, jint length, jint flags, jint fd)
{
  int prot = PROT_READ | PROT_WRITE;
  off_t offset = 0;
  void *addr = mmap(NULL, (size_t) length, prot, flags, fd, offset);
  if (addr != MAP_FAILED)
  {
    return (*env)->NewDirectByteBuffer(env, addr, length);
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, ALLOCATION_FAILED_EXCEPTION);
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
      int success = munmap(addr, (size_t) capatcity);
      if (success != 0)
      {
        throwJniExceptionWithErrno(env, errno, RELEASE_FAILED_EXCEPTION);
      }
    }
    else
    {
      throwJniExceptionWithMessage(env, "could not get buffer address", RELEASE_FAILED_EXCEPTION);
    }
  }
  else
  {
    throwJniExceptionWithMessage(env, "could not get buffer capacity", RELEASE_FAILED_EXCEPTION);
  }
}

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Mman_memfd_1create0
  (JNIEnv *env, jclass clazz, jbyteArray jname, jint jnameLength, jint flags)
{
#ifdef SYS_memfd_create
  _Static_assert (sizeof(jbyte) == sizeof(char), "sizeof(jbyte) == sizeof(char)");
  _Static_assert (sizeof(jint) == sizeof(unsigned int), "sizeof(jint) == sizeof(unsigned int)");
  char name[250];

  (*env)->GetByteArrayRegion(env, jname, 0, jnameLength, (jbyte *) name);
  if ((*env)->ExceptionOccurred(env) != NULL)
  {
    return -1;
  }
  name[jnameLength] = 0;

  int fd =  memfd_create(name, (unsigned int) flags);
  if (fd != -1)
  {
    return fd;
  }
  else
  {
    throwJniExceptionWithErrno(env, errno, IO_EXCEPTION);
    return -1;
  }
#else
  throwJniExceptionWithMessage(env, "memfd_create is not supported", UNSUPPORTED_OPERATION_EXCEPTION);
  return -1;
#endif
}
