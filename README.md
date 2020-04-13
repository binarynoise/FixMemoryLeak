# FixMemoryLeak
In Android P, ViewLocationHolder has an mRoot field that is not cleared in its clear() method. 
Introduced [here](https://github.com/aosp-mirror/platform_frameworks_base/commit/86b326012813f09d8f1de7d6d26c986a909d). 
[Bug report](https://issuetracker.google.com/issues/112792715)

This module fixes that.


## Found other memory leaks?
create an [issue](issues)

## Curious, if there are memory leaks in your app?
use [LeakCanary](https://square.github.io/leakcanary/)
