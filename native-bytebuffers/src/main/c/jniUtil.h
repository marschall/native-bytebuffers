#include <jni.h>

static const char ALLOCATION_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/AllocationFailedException";
static const char RELEASE_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/ReleaseFailedException";
static const char IO_EXCEPTION[] = "java/io/IOException";
static const char ILLEGAL_ARGUMENT_EXCEPTION[] = "java/lang/IllegalArgumentException";
static const char UNSUPPORTED_OPERATION_EXCEPTION[] = "java/lang/UnsupportedOperationException";

int throwJniExceptionWithErrno(JNIEnv *env, int errorCode, const char *exceptionClassName);

int throwJniExceptionWithMessage(JNIEnv *env, const char *message, const char *exceptionClassName);
