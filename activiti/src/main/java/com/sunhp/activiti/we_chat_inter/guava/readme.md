### 文档地址
https://github.com/google/guava/wiki/CachesExplained

可以使用 getAll 方法(Iterable < ？延伸 K >)。默认情况下，getAll 将针对缓存中不存在的每个键单独调用 CacheLoader.load。当批量检索比许多单独的查找更有效时，可以重写 CacheLoader.loadAll 来利用这一点。GetAll (Iterable)的性能将相应得到改进。

注意，您可以编写一个 CacheLoader.loadAll 实现，该实现加载没有特别请求的键的值。例如，如果计算来自某个组的任何键的值就可以得到该组中所有键的值，那么 loadAll 可能会同时加载该组的其余部分。

```
LoadingCache<Key, Graph> graphs = CacheBuilder.newBuilder()
       .expireAfterAccess(10, TimeUnit.MINUTES)
       .build(
           new CacheLoader<Key, Graph>() {
             public Graph load(Key key) { // no checked exception
               return createExpensiveGraph(key);
             }
           });

...
return graphs.getUnchecked(key);
```