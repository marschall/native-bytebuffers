#include <sys/errno.h>
#include <sys/mman.h>
#include <string.h>
#include <unistd.h>

#ifdef _WIN32
#include <windows.h>
#endif

#include <jni.h>

#include "jniUtil.h"
#include "com_github_marschall_nativebytebuffers_Mman.h"

#ifdef _WIN32

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap0
  (JNIEnv *env, jclass clazz, jint length)
{
  // TODO
}

JNIEXPORT jobject JNICALL Java_com_github_marschall_nativebytebuffers_Mman_mmap1
  (JNIEnv *env, jclass clazz, jint length, jint flags)
{

  LPSECURITY_ATTRIBUTES lpFileMappingAttributes = PAGE_READWRITE;
  LPCSTR                lpName                  = NULL;
  HANDLE hMapFile = CreateFileMapping(
         INVALID_HANDLE_VALUE,    // use paging file
         lpFileMappingAttributes,
         flags,
         0,                       // max. object size
         length,                  // buffer size
         lpName);                 // name of mapping object
  if (hMapFile)
  {
    DWORD  dwDesiredAccess = FILE_MAP_ALL_ACCESS; // read/write permission
    void *ptr = MapViewOfFile(hMapFile,
         dwDesiredAccess,
         0,
         0,
         length);
     if (ptr)
     {
     }
     else
     {
     }
  }
  else
  {
  }
}

JNIEXPORT void JNICALL Java_com_github_marschall_nativebytebuffers_Mman_munmap0
  (JNIEnv *env, jclass clazz, jobject buf)
{
  // TODO
  // https://arvid.io/2018/04/02/memory-mapping-on-windows/
  // https://flylib.com/books/en/4.419.1.116/1/
}

JNIEXPORT jlong JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  return -1;
}

#else

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

JNIEXPORT jlong JNICALL Java_com_github_marschall_nativebytebuffers_Mman_getpagesize0
  (JNIEnv *env, jclass clazz)
{
  return sysconf(_SC_PAGESIZE);
}

#endif


