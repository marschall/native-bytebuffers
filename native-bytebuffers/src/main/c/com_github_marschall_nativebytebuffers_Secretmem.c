
#include <jni.h>

#include "com_github_marschall_nativebytebuffers_Secretmem.h"

#if __has_include(<linux/secretmem.h>)
# include <linux/secretmem.h>
#else

#endif
