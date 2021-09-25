
#if __has_include(<linux/secretmem.h>)
#define _secretmem
#endif

#ifdef _secretmem
# include <linux/secretmem.h>
#endif

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Secretmem.h"

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Secretmem_memfd_1secret0
  (JNIEnv *env, jclass clazz, jlong flags)
{
#ifdef _secretmem
  _Static_assert (sizeof(jint) == sizeof(unsigned int), "sizeof(jint) == sizeof(unsigned int)");
  int fd =  memfd_secret(name, (unsigned int) flags);
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
  throwJniExceptionWithMessage(env, "memfd_secret is not supported", UNSUPPORTED_OPERATION_EXCEPTION);
  return -1;
#endif
}

