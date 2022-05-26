
#include <sys/syscall.h>      /* Definition of SYS_* constants */
#include <unistd.h>
#include <errno.h>

#ifdef SYS_memfd_secret
#define _secretmem
#endif

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Secretmem.h"


#ifdef _secretmem
static inline long int memfd_secret(int flags)
{
  return syscall(SYS_memfd_secret, flags);
}
#endif

JNIEXPORT jint JNICALL Java_com_github_marschall_nativebytebuffers_Secretmem_memfd_1secret0
  (JNIEnv *env, jclass clazz, jint flags)
{
#ifdef _secretmem
  _Static_assert (sizeof(jint) == sizeof(int), "sizeof(jint) == sizeof(int)");
  int fd = (int) memfd_secret(flags);
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

