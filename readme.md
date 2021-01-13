Benchmark of several methods for replacing characters in a string.
Results (lower score means faster) :
```
JAVA_VERSION="1.8.0_271" ORACLE
OS_NAME="Windows"
OS_VERSION="5.2"
OS_ARCH="amd64"

Benchmark                        Mode  Samples     Score  Score error  Units
r.m.BenchMark.forLoopReplace     avgt       10  1045,298       39,461  ns/op
r.m.BenchMark.replaceAllChain    avgt       10  8121,526      213,071  ns/op
r.m.BenchMark.replaceChain       avgt       10  4995,234      140,400  ns/op
```