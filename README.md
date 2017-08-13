# typemaps

**typemaps** contains an implementation of maps with types as keys, henceforth
referred to as _typemaps_.

The type of a typemap contains both its keys and the types of its values.
Typemaps are similar to [shapeless] records, but are less focused on singleton
type keys created from strings and symbols, and are implemented as balanced
binary trees instead of HLists.

[shapeless]: https://github.com/milessabin/shapeless

Attempting to look up a nonexistent key in a typemap will cause a compile
error, as will attempting to insert a key that already exists (though you can
update the value associated with it), and attempting to remove a key that does
not exist.

## Example usage

Building a typemap:

```scala
scala> import typemaps._
import typemaps._

scala> val x = TypeMap.empty.insert[String](4).insert[Float]("foo")
x: typemaps.TypeMap.Bin[String,Int,typemaps.TypeMap.Tip,typemaps.TypeMap.Bin[Float,String,typemaps.TypeMap.Tip,typemaps.TypeMap.Tip]] = Bin(4,Tip,Bin(foo,Tip,Tip))

scala> x.insert[Float](false) // fails because Float is already present as a key in x
<console>:16: error: could not find implicit value for parameter insert: typemaps.Insert.Aux[typemaps.TypeMap.Bin[String,Int,typemaps.TypeMap.Tip,typemaps.TypeMap.Bin[Float,String,typemaps.TypeMap.Tip,typemaps.TypeMap.Tip]],Float,Boolean,O]
       x.insert[Float](false)
```

Looking up keys in a typemap:

```scala
scala> x[String]
res0: Int = 4

scala> x[Float]
res1: String = foo

scala> x[Double] // fails because Double is not present as a key in x
<console>:16: error: Double is not present in the map
       x[Double]
```

Updating values in and removing keys from a typemap:

```scala
scala> val y = x.update[Float].using(_ + "bar").remove[String]
y: typemaps.TypeMap.Bin[Float,String,typemaps.TypeMap.Tip.type,typemaps.TypeMap.Tip.type] = Bin(foobar,Tip,Tip)

scala> y[String] // fails because String is not present as a key in y
<console>:16: error: String is not present in the map
       y[String]
        ^

scala> y[Float]
res4: String = foobar
```

### Performance

Typemaps perform favorably compared to Scala immutable maps, shapeless HMaps,
and shapeless records for most operations on small map sizes. Using types as
keys means that the location of any key in a typemap is known statically at
compile time, so no comparisons need to be performed or hash codes computed.
Additionally, the balanced binary tree representation of typemaps means that
many operations can be implemented with O(lg n) time cost, whereas shapeless
records being represented as HLists means that most operations on them take
O(n) time.

However, for n <= 12 (and perhaps greater), shapeless records outperform
typemaps at lookups.

The "regular" benchmarks below use n = 7, and the "big" benchmarks use n = 12.

### Change key

```
Benchmark                              Mode  Cnt         Score        Error  Units
ChangeKey.updateScalaMap              thrpt   10   2329217.937 ±  12078.962  ops/s
ChangeKey.updateShapelessHMap         thrpt   10   1786881.215 ±  11452.541  ops/s
ChangeKey.updateShapelessRecord       thrpt   10   1744022.031 ±   5737.463  ops/s
ChangeKey.updateTypeMap               thrpt   10   4635741.784 ±  31050.033  ops/s
ChangeKeyBig.updateScalaMap           thrpt   10   1189961.116 ±   6436.938  ops/s
ChangeKeyBig.updateShapelessHMap      thrpt   10    998149.275 ±   9800.172  ops/s
ChangeKeyBig.updateShapelessRecord    thrpt   10    681640.417 ±   4371.553  ops/s
ChangeKeyBig.updateTypeMap            thrpt   10   1599180.357 ±  12636.085  ops/s
```

### Insert

```
Benchmark                              Mode  Cnt         Score        Error  Units
Insert.insertScalaMap                 thrpt   10   2727883.371 ±  15308.048  ops/s
Insert.insertShapelessHMap            thrpt   10   2204123.018 ±  14909.684  ops/s
Insert.insertShapelessHMapBuilder     thrpt   10   1733646.420 ±   7267.130  ops/s
Insert.insertShapelessRecord          thrpt   10   4476913.651 ±  54833.859  ops/s
Insert.insertTypeMap                  thrpt   10   6682855.408 ±  33370.332  ops/s
InsertBig.insertScalaMap              thrpt   10   1647740.060 ±  13321.145  ops/s
InsertBig.insertShapelessHMap         thrpt   10   1521731.446 ±  13093.086  ops/s
InsertBig.insertShapelessHMapBuilder  thrpt   10   1187543.212 ±   6778.927  ops/s
InsertBig.insertShapelessRecordBig    thrpt   10   1849702.198 ±  15183.482  ops/s
InsertBig.insertTypeMap               thrpt   10   2888690.460 ±  28989.088  ops/s
```

### Lookup

```
Benchmark                              Mode  Cnt         Score        Error  Units
Lookup.lookupScalaMap                 thrpt   10  33313670.569 ±  13832.645  ops/s
Lookup.lookupShapelessHMap            thrpt   10  32655683.526 ±  14549.754  ops/s
Lookup.lookupShapelessRecord          thrpt   10  39624013.283 ±  19526.197  ops/s
Lookup.lookupTypeMap                  thrpt   10  33949314.959 ± 284162.613  ops/s
LookupBig.lookupScalaMap              thrpt   10   8067966.630 ±  63240.023  ops/s
LookupBig.lookupShapelessHMap         thrpt   10   9251071.659 ±  63531.846  ops/s
LookupBig.lookupShapelessRecord       thrpt   10  16264888.764 ±   9258.202  ops/s
LookupBig.lookupTypeMap               thrpt   10  15011835.160 ± 121162.270  ops/s
```

### Remove

```
Benchmark                              Mode  Cnt         Score        Error  Units
Remove.removeScalaMap                 thrpt   10   5130376.237 ±  37824.818  ops/s
Remove.removeShapelessHMap            thrpt   10   4998012.829 ±  28432.601  ops/s
Remove.removeShapelessRecord          thrpt   10   3296765.761 ±  48062.369  ops/s
Remove.removeTypeMap                  thrpt   10   7751088.177 ±  42255.587  ops/s
RemoveBig.removeScalaMap              thrpt   10   2386315.591 ±  16364.281  ops/s
RemoveBig.removeShapelessHMap         thrpt   10   2372782.464 ±  17620.659  ops/s
RemoveBig.removeShapelessRecord       thrpt   10   2391472.439 ±  35618.114  ops/s
RemoveBig.removeTypeMap               thrpt   10   3189985.814 ±  28955.456  ops/s
```

### Replace

```
Benchmark                              Mode  Cnt         Score        Error  Units
Replace.replaceScalaMap               thrpt   10   3801359.013 ±  27467.073  ops/s
Replace.replaceShapelessHMap          thrpt   10   3188580.418 ±  30727.206  ops/s
Replace.replaceShapelessRecord        thrpt   10   4003711.524 ±  33200.208  ops/s
Replace.replaceTypeMap                thrpt   10  13544664.864 ±  95869.572  ops/s
ReplaceBig.replaceScalaMap            thrpt   10   2390756.805 ±  18302.562  ops/s
ReplaceBig.replaceShapelessHMap       thrpt   10   1623960.853 ±   8909.949  ops/s
ReplaceBig.replaceShapelessRecord     thrpt   10   1608891.192 ±  11990.275  ops/s
ReplaceBig.replaceTypeMap             thrpt   10   5741380.900 ±  72490.560  ops/s
```

### Update

```
Benchmark                              Mode  Cnt         Score        Error  Units
Update.updateScalaMap                 thrpt   10   3374485.801 ±  11618.741  ops/s
Update.updateShapelessHMap            thrpt   10   3643760.629 ±  15592.357  ops/s
Update.updateShapelessRecord          thrpt   10   3472675.448 ±  30274.187  ops/s
Update.updateTypeMap                  thrpt   10   9372052.674 ± 134442.405  ops/s
UpdateBig.updateScalaMap              thrpt   10   1809778.557 ±   8325.446  ops/s
UpdateBig.updateShapelessHMap         thrpt   10   1551913.499 ±   8560.148  ops/s
UpdateBig.updateShapelessRecord       thrpt   10   1564964.627 ±  17708.586  ops/s
UpdateBig.updateTypeMap               thrpt   10   4252773.034 ±  36329.864  ops/s
```