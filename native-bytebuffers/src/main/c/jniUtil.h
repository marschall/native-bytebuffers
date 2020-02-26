#include <jni.h>

static const char ALLOCATION_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/AllocationFailedException";
static const char RELEASE_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/ReleaseFailedException";

int throwJniExceptionWithErrno(JNIEnv *env, int errorCode, const char *exceptionClassName);

int throwJniExceptionWithMessage(JNIEnv *env, const char *message, const char *exceptionClassName);
