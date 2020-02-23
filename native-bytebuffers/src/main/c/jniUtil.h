#include <jni.h>

static const char ALLOCATION_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/AllocationFailedException";
static const char RELEASE_FAILED_EXCEPTION[] = "com/github/marschall/nativebytebuffers/ReleaseFailedException";

int throwJniException(JNIEnv *env, int errorCode, const char *exceptionClassName);
